package com.lunatech.training.quarkus;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
@Slf4j
public class GreetingResource {

    //@Inject
    Template greet;

    //@Inject
    SubjectBean subjectBean;

    @ConfigProperty(name = "greeting")
    String greeting;

    public GreetingResource(Template greet, SubjectBean subjectBean) {
        log.info("GreetingResource Ready");
        this.greet = greet;
        this.subjectBean = subjectBean;
    }

    @GET
    @Path("greet")
    public String greet() {
        return "Hello, " + greeting;
    }

    @GET
    @Path("greet2")
    public TemplateInstance greetTemplate() {
        return greet.instance();
    }

    @GET
    @Path("/hello/{subject}")
    public String getHello(@PathParam("subject") String subject){

        return "Hello " + subject;
    }
}
