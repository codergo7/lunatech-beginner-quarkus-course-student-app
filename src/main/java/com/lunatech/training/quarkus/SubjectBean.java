package com.lunatech.training.quarkus;

import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.RequestScoped;
import javax.inject.Singleton;

//@Singleton
@RequestScoped
@Slf4j
public class SubjectBean {

    public SubjectBean() {

        System.out.println("SubjectBean Constructed!");
        log.info("SubjectBean Constructed!");
    }

    public String subject() {
        return "everyone";
    }

}
