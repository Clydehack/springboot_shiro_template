package com.template.shirotemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value = {"applicationContext.xml"})
public class ShiroTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShiroTemplateApplication.class, args);
	}

}
