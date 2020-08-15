package personal.yh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import personal.yh.consumer.custom.config.CustomRibbonConfig;

@RibbonClient(name = "service-provider", configuration = CustomRibbonConfig.class)
@SpringBootApplication
public class ServiceConsumerCustom {

    public static void main(String[] args) {
        SpringApplication.run(ServiceConsumerCustom.class, args);
    }
}
