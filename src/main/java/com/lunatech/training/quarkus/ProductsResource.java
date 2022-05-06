package com.lunatech.training.quarkus;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class ProductsResource {

  /*  @Inject
    Template catalogue;

    @Inject
    Template details;*/

    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PanacheEntity> products(){

      /*  List<Product> list = Product.findAll().list();
        list.stream().forEach(System.out::println);*/
        return Product.findAll().list();
       // return catalogue.instance();
    }

    @GET
    @Path("/products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PanacheEntity getProductsById(@PathParam("id") String id){

        PanacheEntity byId = Product.findById(id);
        System.out.println(byId);
        return byId;
    }
}
