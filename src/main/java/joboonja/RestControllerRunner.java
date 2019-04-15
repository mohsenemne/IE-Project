package joboonja;

import joboonja.domain.Database;
import joboonja.DatabaseLoader ;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.annotation.WebListener;


@SpringBootApplication
public class RestControllerRunner {

    public static void main(String[] args) {
        DatabaseLoader.contextInitialized();
        SpringApplication.run(RestControllerRunner.class, args);
    }

    @Configuration
    public class MyConfiguration {

        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurerAdapter() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**").allowedMethods("GET, OPTIONS, HEAD, PUT, POST");
                }
            };
        }
    }
}
