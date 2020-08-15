package personal.yh.consumer.custom.components;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerStatusChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

public class CustomServerStatusChangeListener implements ServerStatusChangeListener {

    private static final Logger logger = LoggerFactory.getLogger(CustomServerStatusChangeListener.class);

    @Override
    public void serverStatusChanged(Collection<Server> servers) {
        if (CollectionUtils.isEmpty(servers)) {
            logger.info("CustomLoadBalancer servers empty, time:{}", System.currentTimeMillis());
        } else {
            for (Server server : servers) {
                logger.info("CustomLoadBalancer server:{} changed and now alive status is {}", server.getHostPort(), server.isAlive());
            }
        }
    }
}
