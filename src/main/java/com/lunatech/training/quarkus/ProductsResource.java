package com.lunatech.training.quarkus;

import io.smallrye.mutiny.Uni;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductsResource {

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Product>> products(){

        return Product.findAll().list();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Product> getProductsById(@PathParam("id") Long id){

        return Product.findById(id);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Uni<Product> updateProduct(@PathParam("id") Long id, @Valid Product product){

        //Uni<Product> productUni = Product.findById(id);


        return Product.<Product>findById(id).flatMap(productUpdate ->{
            if(productUpdate == null){
                return Uni.createFrom().failure(new EntityNotFoundException(String.format("Product by id= {} not found",id)));
            }
            productUpdate.name = product.name;
            productUpdate.description = product.description;
            productUpdate.price = product.price;
            return productUpdate.persistAndFlush().map(__ -> productUpdate);
        } );
    }
}
