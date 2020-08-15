package personal.yh.service.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceProviderAController {

    @GetMapping(value = "/service/provider")
    public String getServiceProvider() {
        return "This is service provider AAA";
    }
}
