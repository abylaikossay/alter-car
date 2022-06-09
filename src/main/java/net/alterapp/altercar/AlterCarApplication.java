package net.alterapp.altercar;

import net.alterapp.altercar.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.mappers.ModelMapper;

//@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class AlterCarApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(AlterCarApplication.class, args);
	}

}
