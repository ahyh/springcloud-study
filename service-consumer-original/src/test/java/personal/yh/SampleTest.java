package personal.yh;


import com.netflix.client.ClientFactory;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.ConfigurationManager;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import com.netflix.niws.client.http.RestClient;
import org.junit.Test;

import java.net.URI;

public class SampleTest {


    /**
     * Demo one for ribbon
     *
     * code from: https://github.com/Netflix/ribbon/wiki/Getting-Started
     *
     * 1-Load the properties file using Archaius ConfigurationManager.
       2-Use ClientFactory to create client and the load balancer.
       3-Build the http request using the builder. Note that we only supply the path part (“/”) of the URI.
         The complete URI will be computed by the client once the server is chosen by the load balancer.
       4-Call client.executeWithLoadBalancer() API, not the execute() API.
       5-Dynamically change the server pool from the configuration.
       6-Wait until server list is refreshed (2 seconds refersh interval defined in properties file)
       7-Print out the server stats recorded by the load balancer.
     */
    @Test
    public void testRibbonDemo1() throws Exception {
        ConfigurationManager.loadPropertiesFromResources("sample-client.properties");  // 1
        System.out.println(ConfigurationManager.getConfigInstance().getProperty("sample-client.ribbon.listOfServers"));
        RestClient client = (RestClient) ClientFactory.getNamedClient("sample-client");  // 2
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("/")).build(); // 3
        for (int i = 0; i < 20; i++) {
            HttpResponse response = client.executeWithLoadBalancer(request); // 4
            System.out.println("Status code for " + response.getRequestedURI() + "  :" + response.getStatus());
        }
        ZoneAwareLoadBalancer lb = (ZoneAwareLoadBalancer) client.getLoadBalancer();
        System.out.println(lb.getLoadBalancerStats());
        ConfigurationManager.getConfigInstance().setProperty(
                "sample-client.ribbon.listOfServers", "www.linkedin.com:80,www.google.com:80"); // 5
        System.out.println("changing servers ...");
        Thread.sleep(3000); // 6
        for (int i = 0; i < 20; i++) {
            HttpResponse response = client.executeWithLoadBalancer(request);
            System.out.println("Status code for " + response.getRequestedURI() + "  : " + response.getStatus());
        }
        System.out.println(lb.getLoadBalancerStats()); // 7
    }

}
