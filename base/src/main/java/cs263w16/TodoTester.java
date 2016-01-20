package cs263w16;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

/**
 * @Project: base
 * @Title: TodoTester.java
 * @Package cs263w16
 * @Description: TODO
 * @author YuesongWang
 * @date Jan 19, 2016 11:11:10 AM
 * @version V1.0
 */
public class TodoTester {
    public static void main(String[] args) {
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(getBaseURI());

        String xmlResponse = target.path("rest").path("todo").request()
                .accept(MediaType.TEXT_XML).get(String.class);
        // Get XML for application
        String xmlAppResponse = target.path("rest").path("todo").request()
                .accept(MediaType.APPLICATION_XML).get(String.class);
        System.out.println(xmlResponse);
        System.out.println(xmlAppResponse);
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080").build();
    }
}
