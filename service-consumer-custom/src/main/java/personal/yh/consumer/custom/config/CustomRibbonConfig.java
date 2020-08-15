package personal.yh.consumer.custom.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import com.netflix.loadbalancer.ServerListFilter;
import com.netflix.loadbalancer.ServerListUpdater;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import org.springframework.context.annotation.Bean;
import personal.yh.consumer.custom.components.CustomRule;
import personal.yh.consumer.custom.components.CustomServerListChangeListener;
import personal.yh.consumer.custom.components.CustomServerStatusChangeListener;

public class CustomRibbonConfig {

    /**
     * 自定义IRule组件
     */
    @Bean
    public IRule customRule() {
        return new CustomRule();
    }

    /**
     * 除了IRule使用自定义的组件外，其他组件使用默认的组件
     */
    @Bean
    public ILoadBalancer ribbonLoadBalancer(IClientConfig config,
                                            ServerList<Server> serverList, ServerListFilter<Server> serverListFilter,
                                            IRule rule, IPing ping, ServerListUpdater serverListUpdater) {
        ZoneAwareLoadBalancer<Server> loadBalancer = new ZoneAwareLoadBalancer<>(config, rule, ping, serverList,
                serverListFilter, serverListUpdater);
        loadBalancer.addServerListChangeListener(new CustomServerListChangeListener());
        loadBalancer.addServerStatusChangeListener(new CustomServerStatusChangeListener());
        return loadBalancer;
    }


}
