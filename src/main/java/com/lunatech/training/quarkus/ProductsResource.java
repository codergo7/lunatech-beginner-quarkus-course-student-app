package com.lunatech.training.quarkus;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class ProductsResource {

    @Inject
    Template catalogue;

    @Inject
    Template details;

    @GET
    @Path("/products")
    public TemplateInstance products(){
        return catalogue.instance();
    }

    @GET
    @Path("/products/{id}")
    public TemplateInstance getProductsById(@PathParam("id") String id){
        details.data(id, Product.class);
        return details.instance();
    }


}
