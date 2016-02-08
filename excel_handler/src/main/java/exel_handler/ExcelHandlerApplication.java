package exel_handler;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableBatchProcessing
public class ExcelHandlerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExcelHandlerApplication.class, args);
	}
}

