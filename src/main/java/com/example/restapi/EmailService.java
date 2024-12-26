package main.java.com.example.restapi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

@Path("/email")
public class EmailService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendEmail(EmailRequest emailRequest) {
        System.out.println("Received email request: " + emailRequest);

        if (emailRequest == null || emailRequest.getTo() == null || emailRequest.getSubject() == null || emailRequest.getBody() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid email request data").build();
        }
        
        try {
            // Log before sending the email
            System.out.println("Sending email to: " + emailRequest.getTo());

            // SMTP server configuration
            Properties props = new Properties();
            props.put("mail.smtp.host", "niteshjanyani.in"); // Your SMTP server
            props.put("mail.smtp.port", "587");             // SMTP port
            props.put("mail.smtp.auth", "false");            // Disable authentication
            props.put("mail.smtp.starttls.enable", "false"); // Disable STARTTLS

            // Authenticate with the SMTP server
            Session session = Session.getInstance(props);
            
            // Create the email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("admin@niteshjanyani.in")); // Update with your email address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailRequest.getTo()));
            message.setSubject(emailRequest.getSubject());
            message.setText(emailRequest.getBody());
            
            Transport.send(message);

            // Log after sending the email
            System.out.println("Email sent successfully to: " + emailRequest.getTo());

            return Response.status(Response.Status.OK).entity("Email sent successfully").build();     
            
        }
        	
        catch (MessagingException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error sending email: " + e.getMessage())
                    .build();
        }
        
        
	}
}
