package personal.yh.consumer.custom.components;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerListChangeListener;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomServerListChangeListener implements ServerListChangeListener {

    private static final Logger logger = LoggerFactory.getLogger(CustomServerListChangeListener.class);

    @Override
    public void serverListChanged(List<Server> oldList, List<Server> newList) {
        if (isServersChanged(oldList, newList)) {
            if (org.springframework.util.CollectionUtils.isEmpty(oldList)) {
                logger.info("CustomLoadBalancer oldList empty, time:{}", System.currentTimeMillis());
            } else {
                for (int i = 0; i < oldList.size(); i++) {
                    logger.info("CustomLoadBalancer oldList {}, and host port:{}", i, oldList.get(i).getHostPort());
                }
            }

            if (org.springframework.util.CollectionUtils.isEmpty(newList)) {
                logger.info("CustomLoadBalancer newList empty, time:{}", System.currentTimeMillis());
            } else {
                for (int i = 0; i < newList.size(); i++) {
                    logger.info("CustomLoadBalancer newList {}, and host port:{}", i, newList.get(i).getHostPort());
                }
            }
        }
    }

    private static boolean isServersChanged(List<Server> oldList, List<Server> newList) {
        if (CollectionUtils.isEmpty(oldList) && CollectionUtils.isEmpty(newList)) {
            return true;
        }
        List<String> oldServers = new ArrayList<>();
        if (!CollectionUtils.isEmpty(oldList)) {
            oldServers = oldList.stream().map(Server::getHostPort).collect(Collectors.toList());
        }
        List<String> newServers = new ArrayList<>();
        if (!CollectionUtils.isEmpty(oldList)) {
            newServers = newList.stream().map(Server::getHostPort).collect(Collectors.toList());
        }
        return !CollectionUtils.isEqualCollection(oldServers, newServers);
    }
}
