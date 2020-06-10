# 使用Ribbon实现客户端负载均衡
* 1-服务提供者(Service Provider)启动多个实例并注册到一个服务中心
* 2-服务消费者(Service Consumer)直接使用被@LoadBalanced修饰过的RestTemplate来通过serviceId来调用接口