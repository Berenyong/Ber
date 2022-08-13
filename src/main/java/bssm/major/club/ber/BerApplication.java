package bssm.major.club.ber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BerApplication.class, args);
	}

}
