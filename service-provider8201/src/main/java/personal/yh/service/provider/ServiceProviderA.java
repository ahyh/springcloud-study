package personal.yh.service.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * service provider A Main Class
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class ServiceProviderA {

    public static void main(String[] args) {
        SpringApplication.run(ServiceProviderA.class, args);
    }
}
