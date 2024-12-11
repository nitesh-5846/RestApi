package main.java.com.example.restapi;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.eclipse.jetty.server.Server;

import java.net.URI;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class Main {

	public static void main(String[] args) throws Exception
	{		
		 ResourceConfig config = new ResourceConfig(PlayerResource.class, CORSFilter.class);
	     //config.packages("com.example.restapi");
        
        Server server = JettyHttpContainerFactory.createServer(URI.create("http://172.237.32.32:8080/"), config);
        server.start();
        
        System.out.println("Server started at http://172.237.32.32:8080/");
        server.join();
	}
}
