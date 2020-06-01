package personal.yh.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * eureka server启动类
 * 启动eureka server后访问：localhost:8101可以进入eureka server管理页面
 *
 * @author yanhuan
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerA {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerA.class, args);
    }
}
