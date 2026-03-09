package commerce.nobreza.loja.masculina.nobreza_loja;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SiteNobrezaLojaDeTernosMasculinaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiteNobrezaLojaDeTernosMasculinaApplication.class, args);
	}

}
