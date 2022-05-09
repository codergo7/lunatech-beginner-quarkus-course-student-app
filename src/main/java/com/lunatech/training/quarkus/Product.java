package com.lunatech.training.quarkus;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Product extends PanacheEntity {
        public String name;
        public String description;
        public BigDecimal price;

        public Product() {
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

}
