package fr.sedoo.indaaf.domain;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Header {

	List<Person> originators = new ArrayList<>();
	List<Person> submitters = new ArrayList<>();
	Date startDate = null;
	Integer numberOfVariables = 0;
	List<Variable> variables = new ArrayList<>();
	MetadataSet fileMetadata = new MetadataSet();
	List<String> dataColumns = new ArrayList<>();





	private static final String SEPARATOR =" ";

	public String getHeader() throws Exception {
		StringBuilder result = new StringBuilder();
		List<String> lines = new ArrayList<>();
		lines.add(originatorsToEbas());
		lines.add(organisationToEbas());
		lines.add(submittersToEbas());
		lines.add(missionToEbas());
		lines.add("1"+SEPARATOR+"1"); //IVOL NVOL
		lines.add(startDateToEbas());
		lines.add("0"); //non uniform interval
		lines.add("days from file reference point"); //XNAME
		lines.add(""+numberOfVariables);
		lines.add(scaleFactorsToEbas());
		lines.add(missingValuesToEbas());
		lines.addAll(variablesToEbas());
		lines.add("0"); //no normal comment line
		lines.add(""+(fileMetadata.getMetadataList().size()+1)); //no normal comment line - The extra one will correspond to the data header line

		for (Metadata metadata : fileMetadata.getMetadataList()) {
			lines.add(metadata.toString());	
		}
		lines.add(variableNamesToEbas());

		result.append(""+(lines.size()+1)+SEPARATOR+"1001\n");
		result.append(String.join("\n", lines));
		return result.toString();
	}

	private List<String> variablesToEbas() {
		List<String> result = new ArrayList<>();
		result.add("end_time of measurement, days from the file reference point");
		for (Variable variable : variables) {
			List<String> aux = new ArrayList<>();
			aux.add(variable.getName());
			aux.add(variable.getUnit());
			aux.addAll(variable.getInfo());
			result.add(String.join(", ", aux));
		}
		return result;
	}

	private String variableNamesToEbas() {
		List<String> values = new ArrayList<>();
		values.add("starttime");
		values.add("endtime");

		for (String name : dataColumns) {
			values.add(name);
		}
		return String.join(" ", values);
	}

	private String missingValuesToEbas() {
		List<String> values = new ArrayList<>();

		values.add("9999"); //EndTime

		for (Variable variable: variables) {
			values.add(variable.getMissingValue());
		}
		return String.join(" ", values);
	}

	private String scaleFactorsToEbas() {
		List<String> values = new ArrayList<>();
		for (int i = 0; i<numberOfVariables; i++) {
			values.add("1");
		}
		return String.join(" ", values);
	}

	private String organisationToEbas() {
		return "CCCXX, Norwegian Institute for Air Research, NILU, , Instituttveien 18, , 2007, Kjeller, Norway";
	}

	private String missionToEbas() {
		return "ImDAAF";
	}

	private String startDateToEbas() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy MM dd");
		return formatter.format(getStartDate())+" "+formatter.format(getStartDate());
	}

	private String originatorsToEbas() throws Exception {
		return personsToEbas("Originators", originators);
	}

	private String submittersToEbas() throws Exception {
		return personsToEbas("Submitters", submitters);
	}

	private String personsToEbas(String label, List<Person> persons) throws Exception {
		if (persons.isEmpty()) {
			throw new Exception(label+" can't be empty");
		}

		StringBuilder result = new StringBuilder();
		Iterator<Person> iterator = persons.iterator();
		while (iterator.hasNext()) {
			Person person = (Person) iterator.next();
			result.append(person.getLastName()+", "+person.getFirstName());
			if (iterator.hasNext()) {
				result.append("; ");
			}
		}
		return result.toString();

	}

	public static Header fromInputStream(InputStream metadataStream, InputStream dataStream, Integer dataSheetIndex, MetadataSet auxiliaryMetadata) throws Exception {
		Header result = new Header();
		String content = IOUtils.toString(metadataStream, Charset.defaultCharset());
		String[] lines = content.split("\\r?\\n");
		result.setOriginators(getOriginatorsFromIndaaf(Arrays.asList(lines)));
		result.setSubmitters(getSubmittersFromIndaaf(Arrays.asList(lines)));
		result.setVariables(getVariablesFromIndaaf(Arrays.asList(lines)));
		result.setFileMetadata(getFileMetadaFromIndaaf(Arrays.asList(lines), auxiliaryMetadata));
		
		Metadata startDateMetadata = result.getFileMetadata().getMetadataFromKey(MetadataElements.STARTDATE);
		if (startDateMetadata == null) {
			throw new Exception("StartDate metadata is missing");
		}
		else {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			result.setStartDate(formatter.parse(startDateMetadata.getValue()));
		}

		try (InputStream is = dataStream; ReadableWorkbook wb = new ReadableWorkbook(is)) {
			Sheet sheet = wb.getSheet(dataSheetIndex).get();
			try (Stream<Row> rows = sheet.openStream()) {
				Iterator<Row> iterator = rows.iterator();
				while (iterator.hasNext()) {
					Row row = (Row) iterator.next();
					result.setNumberOfVariables(getNumberOfCols(row)+1); //We add endDate as a variable
					result.setDataColumns(getDataColumnsFromRow(row));
					break;

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (result.getVariables().size() != result.getNumberOfVariables()-1) {
			throw new Exception("Data and metadata variable does not match");
		}
		return result;
	}

	private static List<String> getDataColumnsFromRow(Row row) {
		List<String> result = new ArrayList<>();

		boolean firstEmptyRowPassed = false;
		for (Cell cell : row) {
			String content = StringUtils.trimToEmpty(cell.getRawValue());
			if (StringUtils.isEmpty(content)) {
				if (!firstEmptyRowPassed) {
					firstEmptyRowPassed = true;
				} else {
					return result;
				}
			} else {
				if (firstEmptyRowPassed) {
					result.add(content);
				}
			}
		}
		return result;
	}

	private static MetadataSet getFileMetadaFromIndaaf(List<String> lines, MetadataSet auxiliaryMetadata) throws Exception {
		MetadataSet result = new MetadataSet();

		result.add(MetadataElements.DATA_DEFINITION,"EBAS_1.1");            

		//We look for standard metadata lines:
		for (String line : lines) {
			String aux = StringUtils.trimToEmpty(line);
			if (StringUtils.isNoneEmpty(aux)) {
				boolean matches = aux.matches("^[^:]+:\\s+\\w*");
				if (matches) {
					aux=aux.substring(1);//We remove the #
					String[] split = aux.split(":"); //We remove the leading spaces...
					if (split.length != 2) {
						throw  new Exception("This metadata line is not correct: "+aux);
					}
					else {
						result.add(split[0].trim(),split[1]);
					}
				} 
			}
		}

		//We look for the other metadata lines - we do two passing to maintain the order
		for (String line : lines) {
			String aux = StringUtils.trimToEmpty(line);
			if (StringUtils.isNoneEmpty(aux)) {
				boolean matches = aux.matches("^#[^=]+=.*");
				if (matches) {
					aux=aux.substring(1);//We remove the #
					String[] split = aux.split("="); //We remove the leading spaces...
					if (split.length != 2) {
						//We ignore this case
					}
					else {
						if (MetadataElements.isMetadataElement(split[0].trim())) {
							result.add(split[0].trim(),split[1]);
						}
					}
				} 
			}
		}

		if (auxiliaryMetadata != null) {
			Collection<Metadata> metadataList = auxiliaryMetadata.getMetadataList();
			for (Metadata metadata : metadataList) {
				result.add(metadata.getKey(), metadata.getValue());
			}
		}

		Metadata laboratoryCodeMetadata = result.getMetadataFromKey(MetadataElements.LABORATORY_CODE);
		if (laboratoryCodeMetadata == null) {
			throw new Exception("Laboratory code metadata is missing");
		}

		Metadata methodRefMetadata = result.getMetadataFromKey(MetadataElements.METHOD_REF);
		if (methodRefMetadata == null) {
			throw new Exception("Method Ref metadata is missing");
		}
		
		String value = methodRefMetadata.getValue();
		if (!value.toLowerCase().startsWith(laboratoryCodeMetadata.getValue().toLowerCase())) {
			methodRefMetadata.setValue(laboratoryCodeMetadata.getValue()+"_"+value);
		}

		List<String> missingMandatoryKeys = result.getMissingMandatoryKeys();

		if (missingMandatoryKeys.size()>0) {
			//			throw new Exception("Missing mandatory keys: "+missingMandatoryKeys);
		}



		return result;
	}

	private static List<Variable> getVariablesFromIndaaf(List<String> lines) throws Exception {

		List<Variable> result = new ArrayList<>();
		String prefix = "#variable=";
		for (String line : lines) {
			if (line.toLowerCase().startsWith(prefix.toLowerCase())) {
				String aux = line.substring(prefix.length()).trim();
				String[] split2 = aux.trim().split(",");
				if (split2.length < 4) {
					throw new Exception("This Variable line must have at least 4 items: "+line);
				}
				Variable variable = new Variable();
				variable.setName(split2[0].trim());
				variable.setUnit(split2[1].trim());
				variable.setMissingValue(split2[split2.length-1].trim());
				List<String> info = new ArrayList<>();
				for (int i=2; i < split2.length-1; i++) {
					info.add(split2[i].trim());
				}
				variable.setInfo(info);
				result.add(variable);
			}
		}

		return result;
	}

	private static Integer getNumberOfCols(Row row) {
		boolean firstEmptyRowPassed = false;
		int result = 0;
		for (Cell cell : row) {
			String content = StringUtils.trimToEmpty(cell.getRawValue());
			if (StringUtils.isEmpty(content)) {
				if (!firstEmptyRowPassed) {
					firstEmptyRowPassed = true;
				} else {
					return result;
				}
			} else {
				if (firstEmptyRowPassed) {
					result++;
				}
			}
		}
		return result;
	}

	private static List<Person> getPersonFromIndaaf(List<String> lines, String prefix) {
		List<Person> result = new ArrayList<>();

		for (String line : lines) {
			if (line.toLowerCase().startsWith(prefix.toLowerCase())) {
				String[] split = line.split("=");
				String[] split1 = split[1].split(",");
				Person person = new Person();
				person.setFirstName(split1[1].trim());
				person.setLastName(split1[0].trim());
				result.add(person);
			}
		}

		return result;
	}

	private static List<Person> getOriginatorsFromIndaaf(List<String> lines) {
		return getPersonFromIndaaf(lines, "#Originator");
	}

	private static List<Person> getSubmittersFromIndaaf(List<String> lines) {
		return getPersonFromIndaaf(lines, "#Submitter");
	}

}
