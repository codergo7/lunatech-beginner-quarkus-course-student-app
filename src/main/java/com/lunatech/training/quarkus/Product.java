package com.lunatech.training.quarkus;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Product extends PanacheEntity {
        @JsonProperty("name")
        private String name;
        private String description;
        private BigDecimal price;

        public Product() {
        }

        public Product(String name, String description, BigDecimal price) {
                this.name = name;
                this.description = description;
                this.price = price;
        }

        public static Product findByName(String name){
                return find("name", name).firstResult();
        }

        public static List<Product> findExpensive(){
                return list("price < ?1", new BigDecimal(100));
        }

        public static void deleteChairs(){
                delete("name","Chair");
        }

        @Override
        public String toString() {
                return "Product{ id=" + id +
                        " name='" + name + '\'' +
                        ", description='" + description + '\'' +
                        ", price=" + price +
                        '}';
        }
}
