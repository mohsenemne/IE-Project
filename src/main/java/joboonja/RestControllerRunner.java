package joboonja;

import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;


@SpringBootApplication
public class RestControllerRunner {

    public static void main(String[] args) throws SQLException, IOException, ParseException {
        DatabaseLoader dbl = new DatabaseLoader(); // Instantiate DatabaseLoader class
        dbl.loadDataBase();

        SpringApplication.run(RestControllerRunner.class, args);
    }

    @Configuration
    public class MyConfiguration {

        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurerAdapter() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**").allowedOrigins("http://localhost:3000");
                    registry.addMapping("/**").allowedMethods("GET", "POST", "DELETE", "PUT");
                }
            };
        }
    }
}

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:9000");
//            }
//        };
//    }
