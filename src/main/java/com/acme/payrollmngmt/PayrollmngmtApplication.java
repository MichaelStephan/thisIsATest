package com.acme.payrollmngmt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.persistence.EntityManager;

@SpringBootApplication
@RestController
public class PayrollmngmtApplication {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    public static void main(String[] args) {
        LOGGER.info("this uses a vulnerable log4j version");
        // LOGGER.info("Starting Payroll Management System Application");
        SpringApplication.run(PayrollmngmtApplication.class, args);
    }

    @GetMapping("/")
    public String home() {
        LOGGER.info("Accessed home endpoint");
        return "Welcome to Payroll Management System";
    }

    @GetMapping("/employee/{employeename}")
    public String getEmployeeByName(@PathVariable String employeename) {
        LOGGER.info("Searching for employee with name: {}", employeename);
        entityManager.createQuery("SELECT e from Employee e where e.name = '" + employeename + "'").getResultList();
        return "Searched for employee: " + employeename;
    }

    @GetMapping("/employees")
    public List<Employee> listEmployees() {
        LOGGER.info("Accessed employees endpoint");
        return employeeRepository.findAll();
    }

    /**
     * Use CommandLineRunner to execute code on application startup.
     * Here, we're pre-loading the in-memory database with some sample employees.
     */
    @Bean
    public CommandLineRunner loadData(EmployeeRepository repository) {
        return (args) -> {
            repository.save(new Employee("Frodo Baggins", "ring bearer"));
            repository.save(new Employee("Bilbo Baggins", "burglar"));
            repository.save(new Employee("Gandalf the White", "wizard"));
            repository.save(new Employee("Samwise Baggins", "burglar"));
            repository.save(new Employee("Meriadoc Brandybuck", "burglar"));
            repository.save(new Employee("Peregrin Took", "burglar"));
            repository.save(new Employee("Pippin Took", "burglar"));
            repository.save(new Employee("Merry Brandybuck", "burglar"));
            repository.save(new Employee("Tom Bombadil", "burglar"));
            repository.save(new Employee("Pippy Longstocking", "ring wearer"));
            repository.save(new Employee("Jim Bombardier", "buddy"));
            LOGGER.info("Loaded sample employees into H2 database.");
        };
    }

    /**
     * Simulates a generic runtime exception.
     * Access at: GET /error/runtime
     */
    @GetMapping("/error/runtime")
    public String simulateRuntimeException() {
        LOGGER.error("Simulating a RuntimeException!");
        throw new RuntimeException("Something went wrong unexpectedly!");
    }

    /**
     * Simulates an internal server error (HTTP 500) by returning a ResponseEntity.
     * Access at: GET /error/http500
     */
    @GetMapping("/error/http500")
    public ResponseEntity<String> simulateHttp500Error() {
        LOGGER.error("Simulating an HTTP 500 Internal Server Error!");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Simulated Internal Server Error: Could not process your request.");
    }

    /**
     * Simulates a delay, potentially leading to a timeout or slow response.
     * Access at: GET /error/delay
     */
    @GetMapping("/error/delay")
    public String simulateDelay() throws InterruptedException {
        LOGGER.warn("Simulating a 30-second delay...");
        Thread.sleep(30000); // Sleep for 30 seconds
        return "Response after a 30-second delay.";
    }
}
