package personal.yh.consumer.custom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerCustomController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/custom/{serviceId}")
    public String custom(@PathVariable("serviceId") String serviceId){
        String url = "http://" + serviceId + "/service/provider";
        String result = restTemplate.getForObject(url, String.class);
        return result;
    }


}
