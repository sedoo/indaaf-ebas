package fr.sedoo.indaaf.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MetadataElements {
	
	private static Map<String, Boolean> metadataElements = new HashMap<>();
	
	public static final String FILE_NAME ="File name";
	public static final String ORIGINATOR ="Originator";
	public static final String SUBMITTER ="Submitter";
	public static final String DATA_DEFINITION ="Data definition";
	public static final String INSTRUMENT_NAME = "Instrument name";
	public static final String INSTRUMENT_TYPE = "Instrument type";
	public static final String STATION_GAW_NAME = "Station GAW-Name";
	public static final String STATION_GAW_ID = "Station GAW-ID";
	public static final String STARTDATE = "Startdate";
	public static final String LABORATORY_CODE = "Laboratory code";
	public static final String STATION_CODE = "Station code";
	public static final String METHOD_REF = "Method ref";
	
	static {
		metadataElements.put(DATA_DEFINITION, true);
		metadataElements.put("Set type code", true);
		metadataElements.put("Timezone", true);
		metadataElements.put("Timeref", false);
		metadataElements.put(FILE_NAME, true);
		metadataElements.put("File creation", true);
		metadataElements.put("Export state", false);
		metadataElements.put("Export filter", false);
		metadataElements.put(STARTDATE, true);
		metadataElements.put("Revision date", true);
		metadataElements.put("Version", false);
		metadataElements.put("Version description", false);
		metadataElements.put("Statistics", false);
		metadataElements.put("Data level", true);
		metadataElements.put("Period code", true);
		metadataElements.put("Resolution code", true);
		metadataElements.put("Sample duration", false);
		metadataElements.put("Orig. time res.", false);
		metadataElements.put(STATION_CODE, true);
		metadataElements.put("Platform code", true);
		metadataElements.put("Station name", false);
		metadataElements.put("Station WDCA-ID", false);
		metadataElements.put(STATION_GAW_ID, false);
		metadataElements.put(STATION_GAW_NAME, false);
		metadataElements.put("Station AIRS-ID", false);
		metadataElements.put("Station other IDs", false);
		metadataElements.put("Station state/province", false);
		metadataElements.put("Station land use", false);
		metadataElements.put("Station setting", false);
		metadataElements.put("Station GAW type", false);
		metadataElements.put("Station WMO region", false);
		metadataElements.put("Station latitude", false);
		metadataElements.put("Station longitude", false);
		metadataElements.put("Station altitude", false);
		metadataElements.put("Measurement latitude", false);
		metadataElements.put("Measurement longitude", false);
		metadataElements.put("Measurement altitude", false);
		metadataElements.put("Measurement height", false);
		metadataElements.put("Regime", true);
		metadataElements.put("Component", true);
		metadataElements.put("Unit", false);
		metadataElements.put("Matrix", true);
		metadataElements.put(LABORATORY_CODE, true);
		metadataElements.put(INSTRUMENT_TYPE, true);
		metadataElements.put(INSTRUMENT_NAME, true);
		metadataElements.put("Instrument manufacturer", false);
		metadataElements.put("Instrument model", false);
		metadataElements.put("Instrument serial number", false);
		metadataElements.put("Sensor type", false);
		metadataElements.put("Analytical laboratory code", false);
		metadataElements.put("Analytical measurement technique", false);
		metadataElements.put("Analytical instrument name", false);
		metadataElements.put("Analytical instrument manufacturer", false);
		metadataElements.put("Analytical instrument model", false);
		metadataElements.put("Analytical instrument serial number", false);
		metadataElements.put("Ext. lab. code", false);
		metadataElements.put(METHOD_REF, true);
		metadataElements.put("Standard method", false);
		metadataElements.put("Calibration scale", false);
		metadataElements.put("Calibration standard ID", false);
		metadataElements.put("Secondary standard ID", false);
		metadataElements.put("Inlet type", false);
		metadataElements.put("Inlet description", false);
		metadataElements.put("Inlet tube material", false);
		metadataElements.put("Inlet tube outer diameter", false);
		metadataElements.put("Inlet tube inner diameter", false);
		metadataElements.put("Inlet tube length", false);
		metadataElements.put("Time from entry inlet line to entry of converter", false);
		metadataElements.put("Duration of stay in converter or bypass line", false);
		metadataElements.put("Duration of stay in converter", false);
		metadataElements.put("Converter temperature", false);
		metadataElements.put("Maintenance description", false);
		metadataElements.put("Flow rate", false);
		metadataElements.put("Filter face velocity", false);
		metadataElements.put("Exposed filter area", false);
		metadataElements.put("Filter description", false);
		metadataElements.put("Medium", false);
		metadataElements.put("Coating/Solution", false);
		metadataElements.put("Filter prefiring", false);
		metadataElements.put("Filter conditioning", false);
		metadataElements.put("Filter type", false);
		metadataElements.put("Sample preparation", false);
		metadataElements.put("Blank correction", false);
		metadataElements.put("Artifact correction", false);
		metadataElements.put("Artifact correction description", false);
		metadataElements.put("Charring correction", false);
		metadataElements.put("Ozone correction", false);
		metadataElements.put("Water vapor correction", false);
		metadataElements.put("Zero/span check type", false);
		metadataElements.put("Zero/span check interval", false);
		metadataElements.put("Humidity/temperature control", false);
		metadataElements.put("Humidity/temperature control description", false);
		metadataElements.put("Volume std. temperature", false);
		metadataElements.put("Volume std. pressure", false);
		metadataElements.put("Detection limit", false);
		metadataElements.put("Detection limit expl.", false);
		metadataElements.put("Upper range limit", false);
		metadataElements.put("Measurement uncertainty", false);
		metadataElements.put("Measurement uncertainty expl.", false);
		metadataElements.put("Zero/negative values code", false);
		metadataElements.put("Zero/negative values", false);
		metadataElements.put("Absorption cross section", false);
		metadataElements.put("Mass absorption cross section", false);
		metadataElements.put("Multi-scattering correction factor", false);
		metadataElements.put("Maximum attenuation", false);
		metadataElements.put("Leakage factor zeta", false);
		metadataElements.put("Compensation threshold attenuation 1", false);
		metadataElements.put("Compensation threshold attenuation 2", false);
		metadataElements.put("Compensation parameter k min", false);
		metadataElements.put("Compensation parameter k max", false);
		metadataElements.put("Coincidence correction", false);
		metadataElements.put("Charge type", false);
		metadataElements.put("QA measure ID", false);
		metadataElements.put("QA measure description", false);
		metadataElements.put("QA date", false);
		metadataElements.put("QA outcome", false);
		metadataElements.put("QA bias", false);
		metadataElements.put("QA variability", false);
		metadataElements.put("QA document name", false);
		metadataElements.put("QA document date", false);
		metadataElements.put("QA document URL", false);
		metadataElements.put(ORIGINATOR, true);
		metadataElements.put(SUBMITTER, true);
		metadataElements.put("Acknowledgement", false);
		metadataElements.put("Comment", false);
	}
	
	public static boolean isMetadataElement(String key) {
		Set<String> keySet = metadataElements.keySet();
		for (String aux : keySet) {
			if (aux.equalsIgnoreCase(key)) {
				return true;
			}
		}
		return false;
	}
	
	public static List<String> getMandatoryKeys() {
		List<String> result = new ArrayList<>();
		Set<Entry<String, Boolean>> keySet = metadataElements.entrySet();
		for (Entry<String, Boolean> aux : keySet) {
			if (aux.getValue()) {
				result.add(aux.getKey());
			}
		}
		return result;
	}
	
	public static String getCorrectKey(String key)  {
		Set<String> keySet = metadataElements.keySet();
		for (String aux : keySet) {
			if (aux.equalsIgnoreCase(key)) {
				return aux;
			}
		}
		return key;
	}

}
