package fr.sedoo.indaaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;



@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@ComponentScan({"fr.sedoo"})
public class IndaafApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(IndaafApplication.class, args);
	}


}
