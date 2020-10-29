package personal.yh.service.provider.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import personal.yh.service.provider.service.UserService;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * user service实现
 *
 * @author yanhuan
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    /**
     * 模拟正常的返回结果
     */
    @Override
    public String getInfoSuccess(Integer id) {
        return id + "ok";
    }

    /**
     * 模拟失败的返回结果，超时返回结果
     */
    /**
     * HystrixCommand 服务降级，超时了超过3秒后调用failureHandler方法
     */
    @HystrixCommand(fallbackMethod = "failureHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    @Override
    public String getInfoFailure(Integer id) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            log.error("sleep:{}", e);
        }
        return id + "failure";
    }

    public String failureHandler(Integer id) {
        return "provider failureHandler" + id;
    }

    /**
     * 模拟熔断，id < 0的时候熔断
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "1000"), //时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") //失败率达到多少后跳闸

    })
    @Override
    public String paymentCircuitBreaker(Integer id) {
        if (id < 0) {
            throw new RuntimeException("id cannot less 0");
        }
        String serial = UUID.randomUUID().toString();
        return "success:" + serial;
    }

    public String paymentCircuitBreaker_fallback(Integer id) {
        return "id cannot less 0";
    }
}
