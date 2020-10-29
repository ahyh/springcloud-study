package personal.yh.consumer.hystrix.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 通过RestTemplate的方式来调用User微服务
 *
 * @author yanhuan
 */
@Slf4j
@RestController
public class RestUserConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/user/get/{id}")
    public String getUserSuccessInfo(@PathVariable("id") String id) {
        return restTemplate.getForObject("http://service-provider/user/get/" + id, String.class);
    }

    @GetMapping(value = "/user/get/f/{id}")
    public String getUserFailureInfo(@PathVariable("id") String id) {
        return restTemplate.getForObject("http://service-provider/user/get/f/" + id, String.class);
    }

    @GetMapping(value = "/user/get/circuit/{id}")
    public String getUserCircuitInfo(@PathVariable("id") String id) {
        return restTemplate.getForObject("http://service-provider/user/get/circuit/" + id, String.class);
    }

}
