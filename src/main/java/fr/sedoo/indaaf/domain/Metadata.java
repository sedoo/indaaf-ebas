package fr.sedoo.indaaf.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Metadata  {
	private String key;
	private String value;
	
	@Override
	public String toString() {
		return key+": "+value;
	}

	
	public void setKey(String key) {
		this.key = MetadataElements.getCorrectKey(key);
	}
}
