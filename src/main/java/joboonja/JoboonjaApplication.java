package joboonja;

import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.security.Security;
import java.sql.SQLException;



@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class JoboonjaApplication {

    public static void main(String[] args) throws SQLException, IOException, ParseException {
        DatabaseLoader dbl = new DatabaseLoader(); // Instantiate DatabaseLoader class
        dbl.loadDataBase();

        SpringApplication.run(JoboonjaApplication.class, args);
    }

    @Configuration
    public class MyConfiguration {

        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurerAdapter() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**").allowedOrigins("http://localhost:3000");
                    registry.addMapping("/**").allowedMethods("GET", "POST", "DELETE", "PUT","OPTIONS");
                    registry.addMapping("/**").allowedHeaders("Authorization, Content-Type");
                    registry.addMapping("/**").allowCredentials(true);
                }
            };
        }
    }


    @Configuration
    public class AppConfig {

        public FilterRegistrationBean filterRegistrationBean() {
            FilterRegistrationBean registrationBean = new FilterRegistrationBean();
            FilterJWT customURLFilter = new FilterJWT();

            registrationBean.setFilter(customURLFilter);
            registrationBean.setOrder(2); //set precedence
            return registrationBean;
        }
    }


}