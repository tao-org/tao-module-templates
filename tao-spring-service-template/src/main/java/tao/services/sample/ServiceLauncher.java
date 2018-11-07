package tao.services.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ImportResource("classpath:sample-service-context.xml")
@EnableWebMvc
public class ServiceLauncher implements ro.cs.tao.services.commons.ServiceLauncher {

    public static void main(String[] args) {
        SpringApplication.run(ServiceLauncher.class, args);
    }

}
