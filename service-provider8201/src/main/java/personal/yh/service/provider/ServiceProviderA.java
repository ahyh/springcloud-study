package personal.yh.service.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * service provider A Main Class
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class ServiceProviderA {

    public static void main(String[] args) {
        SpringApplication.run(ServiceProviderA.class, args);
    }
}
