package main.java.com.example.restapi;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.logging.LoggingFeature;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.jersey.jackson.JacksonFeature;

import java.net.URI;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class Main {

	public static void main(String[] args) throws Exception
	{	
	     try 
	     {
			Logger logger = Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME);
			 ResourceConfig config = new ResourceConfig(PlayerResource.class, CORSFilter.class);
			 //config.packages("com.example.restapi");
			 //config.register(new LoggingFeature(logger, Level.ALL, LoggingFeature.Verbosity.PAYLOAD_ANY, 8192));
			 config.register(JacksonFeature.class);
			 config.register(GlobalExceptionMapper.class);
			
			Server server = JettyHttpContainerFactory.createServer(URI.create("http://0.0.0.0:8080/"), config);
			server.start();
			
			System.out.println("Server started at http://0.0.0.0:8080/");
			server.join();
	     } 
		 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
