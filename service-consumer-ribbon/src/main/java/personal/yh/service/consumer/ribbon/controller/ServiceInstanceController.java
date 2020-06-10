package personal.yh.service.consumer.ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ServiceInstanceController {

    /**
     * 此处注入的是LoadBalanced注解修饰过的restTemplate
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 此处注入的就是RibbonLoadBalancerClient
     */
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * 一种原始的方式来实现负载均衡————随机负载均衡
     * 没有实现Ribbon的方式来实现负载均衡
     */
    @GetMapping(value = "/find/{serviceId}")
    public String findService(@PathVariable("serviceId") String serviceId) {
        String url = "http://" + serviceId + "/service/provider";
        String result = restTemplate.getForObject(url, String.class);
        return result;
    }

    /**
     * 根据serviceId选择一个实例的IP和port
     */
    @GetMapping(value = "/pick/{serviceId}")
    public String pickService(@PathVariable("serviceId") String serviceId) {
        //通过RibbonLoadBalancerClient选择ServiceInstance
        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);
        return serviceInstance.getHost() + ":" + serviceInstance.getPort();
    }
}
