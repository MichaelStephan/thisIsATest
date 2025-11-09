package com.acme.payrollmngmt;

import javax.persistence.Table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Table(name = "employee")
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Spring Data JPA will automatically implement basic CRUD methods.
}