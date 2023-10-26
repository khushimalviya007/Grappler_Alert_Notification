package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;


@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(
		info= @Info ( title = "Grappler Enhancement ",
				version="1.0.0",
				description="The project is to define proper Notification , Alert and Report over Grappler",
				termsOfService="https://grappler.innogent.in/dashboard"  ,
				contact = @Contact ( name = "Rahul Kumar Sen , Chirag Tongia, Khushi Malvia" , email ="rahul.sen@innogent.in ,chirag.tongia@innogent.in , khushi.malviya@innogent.in " ) ,
				license = @License ( name = "licence",url="https://github.com/khushimalviya007/Grappler_Enhancement")
		)
)

public class AlertNotificationAndReportApplication {
	public static void main(String[] args) {
		SpringApplication.run(AlertNotificationAndReportApplication.class, args);
		System.out.println("Grappler enhancement");
	}
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

}
