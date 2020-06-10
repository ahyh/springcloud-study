package personal.yh.service.consumer.original;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

/**
 * 通过原始的方式来实现负载均衡
 *
 * @author yanhuan
 */
@SpringBootApplication
@RestController
public class ServiceConsumerOriginal {

    public static void main(String[] args) {
        SpringApplication.run(ServiceConsumerOriginal.class, args);
    }

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 一种原始的方式来实现负载均衡————随机负载均衡
     * 没有实现Ribbon的方式来实现负载均衡
     */
    @GetMapping(value = "/find/{serviceId}")
    public String findService(@PathVariable("serviceId") String serviceId) {
        //1-服务发现
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        //2-随机获取一个ServiceInstance
        ServiceInstance serviceInstance = getServiceInstance(instances);
        //3-调用随机获取到的service实例
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/service/provider";
        String result = restTemplate.getForObject(url, String.class);
        return result;
    }

    private ServiceInstance getServiceInstance(List<ServiceInstance> instances) {
        if (instances == null || instances.size() == 0) {
            return null;
        }
        return instances.get(new Random().nextInt(instances.size()));
    }

}
