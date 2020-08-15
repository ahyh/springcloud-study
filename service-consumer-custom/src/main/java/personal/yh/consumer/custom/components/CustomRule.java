package personal.yh.consumer.custom.components;

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义IRule组件
 * <p>
 * 当前时间的秒数如果[45,60), 将请求路由到8203端口
 * 否则在8201和8202之间做轮询
 */
public class CustomRule extends ZoneAvoidanceRule {

    private final AtomicInteger nextIndex = new AtomicInteger();

    @Override
    public Server choose(Object key) {
        ILoadBalancer lb = getLoadBalancer();
        AbstractServerPredicate predicate = getPredicate();
        List<Server> eligibleServers = predicate.getEligibleServers(lb.getAllServers(), key);
        //模拟8203是重构的方法
        Server anotherServer = null;
        for (Server server : eligibleServers) {
            if (server.getPort() == 8203) {
                anotherServer = server;
                break;
            }
        }
        eligibleServers.remove(anotherServer);
        long seconds = DateUtils.getFragmentInSeconds(new Date(), Calendar.MINUTE);
        if (seconds < 45) {
            return eligibleServers.get(incrementAndGetModulo(eligibleServers.size()));
        } else {
            return anotherServer == null ? eligibleServers.get(incrementAndGetModulo(eligibleServers.size())) : anotherServer;
        }
    }

    private int incrementAndGetModulo(int modulo) {
        for (; ; ) {
            int current = nextIndex.get();
            int next = (current + 1) % modulo;
            if (nextIndex.compareAndSet(current, next) && current < modulo)
                return current;
        }
    }
}
