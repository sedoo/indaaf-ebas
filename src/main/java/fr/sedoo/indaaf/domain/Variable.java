package fr.sedoo.indaaf.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Variable {
	
	private String name;
	private String unit;
	private String missingValue;
	private List<String> info;

}
