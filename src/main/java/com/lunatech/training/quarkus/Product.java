package com.lunatech.training.quarkus;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Product extends PanacheEntity {
        
        @Length(min = 3)
        public String name;
        @Length(min=10)
        public String description;
        @DecimalMin(value = "0.0", inclusive = true)
        @Digits(fraction =2, integer = 10)
        public BigDecimal price;

        public Product() {
        }

        public Product(Long id, String name, String description, BigDecimal price) {
                this.id = id;
                this.name = name;
                this.description = description;
                this.price = price;
        }

        public static Uni<Product> findByName(String name){
                return find("name", name).firstResult();
        }

        public static Uni<List<Product>> findExpensive(){
                return list("price < ?1", new BigDecimal(100));
        }

        public static void deleteChairs(){
                delete("name","Chair");
        }

        public static Product from(Row row){
                return new Product(
                        row.getLong("id"),
                        row.getString("name"),
                        row.getString("description"),
                        row.getBigDecimal("price")
                );
        }

}
