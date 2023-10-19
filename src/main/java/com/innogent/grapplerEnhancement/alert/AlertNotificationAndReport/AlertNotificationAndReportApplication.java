package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AlertNotificationAndReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlertNotificationAndReportApplication.class, args);
	}

}
