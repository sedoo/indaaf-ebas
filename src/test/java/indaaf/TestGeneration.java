package indaaf;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import fr.sedoo.indaaf.IndaafApplication;
import fr.sedoo.indaaf.domain.IndaafDataGenerator;
import fr.sedoo.indaaf.domain.MetadataElements;
import fr.sedoo.indaaf.domain.MetadataSet;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IndaafApplication.class)
public class TestGeneration {
	
	@Value("classpath:metadonnees_BANIZOUMBOU_PLUIE.txt")
	Resource banizoumbouRainMetadataResource;
	
	@Value("classpath:donnees_BANIZOUMBOU_PLUIE.xlsx")
	Resource banizoumbouRainDataResource;
	
	@Test
	public void test() throws Exception {
		log.info("Test");
		String fileName = "banizoumbouRain.nas";
		
		MetadataSet metadata = new MetadataSet();
		metadata.add(MetadataElements.FILE_NAME, fileName);
		metadata.add(MetadataElements.STATION_GAW_ID, "BNZ");
		metadata.add(MetadataElements.STATION_GAW_NAME, "Banizoumbou");
		metadata.add(MetadataElements.LABORATORY_CODE, "XX11L");
		metadata.add(MetadataElements.STATION_CODE, "BNZ");
		metadata.add(MetadataElements.STARTDATE, "20041125000000");
		metadata.add("Method ref", "UNKNOWN");
		
		
		IndaafDataGenerator generator = new IndaafDataGenerator(banizoumbouRainMetadataResource.getInputStream(), banizoumbouRainDataResource.getInputStream(),1, metadata);
		File folder = new File("/tmp/indaaf");
		folder.mkdirs();
		String content = generator.generate();
		
		FileUtils.writeStringToFile(new File(folder, fileName), content , Charset.defaultCharset());
		System.out.println(content);
//		RestTemplate restTemplate = new RestTemplate();
//		HttpHeaders headers = new HttpHeaders();
//		HttpEntity<String> request = 
//			      new HttpEntity<String>(content, headers);
//		//String response = restTemplate.postForObject("https://ebas-submit-tool.nilu.no/", request, String.class);
//		//System.out.println(response);
//		CloseableHttpClient client = HttpClients.createDefault();
//		HttpPost httpPost = new HttpPost("https://ebas-submit-tool.nilu.no/");
//		httpPost.setEntity(new StringEntity("60 1001"));
//		CloseableHttpResponse response = client.execute(httpPost);
//		    client.close();
	}

}
