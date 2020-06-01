package personal.yh.service.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceProviderBController {

    @GetMapping(value = "/service/provider")
    public String getServiceProvider() {
        return "This is service provider BBBBBBBBB";
    }
}
