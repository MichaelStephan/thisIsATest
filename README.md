# thisIsATest

Payroll Management Application - A Spring Boot application for payroll management.

## Build and Deployment

* Install maven and podman (or docker - change later commands accordingly)

* Build with `mvn spring-boot:build-image`

* Push image to remote repo `podman push docker.io/library/payrollmngmt:0.0.1-SNAPSHOT michaelstephan/payrollmngmt:0.0.1`

## Development

* Build: `mvn clean package`
* Run tests: `mvn test`
* Run application: `mvn spring-boot:run`

## Security

This application has been hardened against CVE-2023-6481 (HIGH severity vulnerability in logback-core).

**Key Security Measures:**
- logback-core and logback-classic explicitly set to version 1.5.18 (safe version)
- logback-receiver components (ServerSocketReceiver/ServerSocketAppender) are explicitly disabled
- Comprehensive test coverage to verify security configuration

For detailed information about the CVE remediation, see [docs/CVE-2023-6481-REMEDIATION.md](docs/CVE-2023-6481-REMEDIATION.md).
