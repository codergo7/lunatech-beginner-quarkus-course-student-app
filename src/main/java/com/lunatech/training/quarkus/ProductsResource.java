package com.lunatech.training.quarkus;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;


@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductsResource {

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> products(){

        return Product.findAll().list();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product getProductsById(@PathParam("id") Long id){

        return Product.findById(id);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateProduct(@PathParam("id") Long id, @Valid Product product){

        Optional<Product> byId = Product.findByIdOptional(id);
        if(byId.isEmpty()){
            throw new EntityNotFoundException("Product not found.");
        }
        Product entityBase = Product.findById(id);

        if(entityBase == null){
            throw new EntityNotFoundException("Product not found.");
        }
        entityBase.name = product.name;
        entityBase.description = product.description;
        entityBase.price = product.price;
        //Product.persist(entityBase);

        return Response.accepted().entity(entityBase).build();
    }
}
