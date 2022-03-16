package fr.sedoo.indaaf.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetadataSet {
	
	private Collection<Metadata>  metadataList = new ArrayList<>();
	

	public boolean containsKey(String key) {
		for (Metadata metadata : metadataList) {
			if (metadata.getKey().equalsIgnoreCase(key)) {
				return true;
			}
		}
		return false;
	}
	
	public void add(String key, String value) {
		
		String curatedKey = key.trim();
		String curatedValue = curateValue(key, value);
		
		if (!containsKey(key.trim())) {
			Metadata aux = new Metadata();
			aux.setKey(key.trim());
			aux.setValue(curatedValue);
			metadataList.add(aux);
		} else {
			//Some keys can be repeated
			if (key.trim().equalsIgnoreCase(MetadataElements.ORIGINATOR) || key.trim().equalsIgnoreCase(MetadataElements.SUBMITTER)) {
				Metadata aux = new Metadata();
				aux.setKey(key.trim());
				aux.setValue(curatedValue);
				metadataList.add(aux);
			}
		}
	}

	private String curateValue(String key, String value) {
		String result = StringUtils.trimToEmpty(value);
		result=result.replaceAll("\\s+", " ");
		if (key.equalsIgnoreCase(MetadataElements.INSTRUMENT_NAME)) {
			result = result.replace(' ', '_');
		}
		if (key.equalsIgnoreCase(MetadataElements.INSTRUMENT_TYPE)) {
			result = result.replace(' ', '_');
		}
		if (key.equalsIgnoreCase(MetadataElements.METHOD_REF)) {
			result = result.replace(' ', '_');
		}
		if (key.equalsIgnoreCase(MetadataElements.STARTDATE)) {
			if (result.length()==12) {
				result = result+"00";
			}
		}
		if (result.indexOf(':')>=0) {
			result = "\""+result+"\"";
		}
		return result;
		
	}

	public List<String> getMissingMandatoryKeys() {
		List<String> result = new ArrayList<>();
		
		List<String> mandatoryKeys = MetadataElements.getMandatoryKeys();
		for (String key : mandatoryKeys) {
			if (!containsKey(key)) {
				result.add(key);
			}
		}
		
		return result;
	}

	public Metadata getMetadataFromKey(String key) {
		for (Metadata metadata : metadataList) {
			if (metadata.getKey().equalsIgnoreCase(key)) {
				return metadata;
			}
		}
		return null;
		
	}
	

	
}
