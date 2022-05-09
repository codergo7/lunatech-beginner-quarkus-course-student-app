package com.lunatech.training.quarkus;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductsResource {

    @Inject
    PgPool client;

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

    @GET
    @Path("search/{term}")
    public Multi<Product> search(@PathParam("term") String term){
        return client
                .preparedQuery("SELECT id, name, description, price FROM product WHERE name ILIKE $1 OR description ILIKE $1")
                .execute(Tuple.of("%" + term + "%"))
                .toMulti().flatMap(Multi.createFrom()::iterable)
                .map(Product::from);
    }
}
