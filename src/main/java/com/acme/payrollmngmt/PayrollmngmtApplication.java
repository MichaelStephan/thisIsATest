package com.acme.payrollmngmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PayrollmngmtApplication {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        LOGGER.info("this uses a vulnerable log4j version");
        SpringApplication.run(PayrollmngmtApplication.class, args);
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to Payroll Management System";
    }
}
