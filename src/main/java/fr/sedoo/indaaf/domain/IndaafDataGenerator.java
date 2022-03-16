package fr.sedoo.indaaf.domain;

import java.io.InputStream;

public class IndaafDataGenerator {

	
	private InputStream metadataStream;
	private InputStream dataStream;
	private Integer dataSheetIndex;
	private MetadataSet metadataSet;

	
	public IndaafDataGenerator(InputStream metadataStream, InputStream dataStream, Integer dataSheetIndex, MetadataSet metadataSet) {
		this.metadataStream = metadataStream;
		this.dataStream = dataStream;
		this.dataSheetIndex = dataSheetIndex;
		this.metadataSet = metadataSet;
		
	}
	
	public String generate() throws Exception {
		Header header = Header.fromInputStream(metadataStream, dataStream, dataSheetIndex, metadataSet);
		return header.getHeader();
	}
	
	
}
