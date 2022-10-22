package Distribuidor_alimentos;
import Distribuidor_alimentos.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan()
@SpringBootApplication
public class DistribuidorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistribuidorApplication.class, args);
	}

}
