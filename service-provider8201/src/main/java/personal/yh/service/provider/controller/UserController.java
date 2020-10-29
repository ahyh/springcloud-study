package personal.yh.service.provider.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import personal.yh.service.provider.service.UserService;

import javax.annotation.Resource;

/**
 * User相关的微服务
 *
 * @author yanhuan
 */
@Slf4j
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping(value = "/user/get/{id}")
    public String get(@PathVariable("id") Integer id) {
        String infoSuccess = userService.getInfoSuccess(id);
        return infoSuccess;
    }

    @GetMapping(value = "/user/get/f/{id}")
    public String getF(@PathVariable("id") Integer id) {
        String infoFailure = userService.getInfoFailure(id);
        return infoFailure;
    }

    @GetMapping(value = "/user/get/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        String result = userService.paymentCircuitBreaker(id);
        return result;
    }

}
