# thisIsATest

* install maven and podman (or docker - change later commands accordingly)

* build with `mvn spring-boot:build-image`

* push image to remote repo `podman push docker.io/library/payrollmngmt:0.0.1-SNAPSHOT michaelstephan/payrollmngmt:0.0.1`

## Security

### CVE-2023-6481 Remediation

**Status:** ✅ Remediated

This application has been secured against CVE-2023-6481 (Uncontrolled Resource Consumption in logback-core):

- **Current Version:** logback-core 1.5.18 (safe version, vulnerability affects 1.2.0-1.2.12, 1.3.0-1.3.13, 1.4.0-1.4.13)
- **Remediation Actions:**
  1. Explicitly set `logback.version` property to 1.5.18 in pom.xml
  2. Verified that logback-receiver component is not included in dependencies
  3. Added secure logback configuration (`logback-spring.xml`) that does not enable network receivers
  4. Configuration explicitly documents that logback-receiver is disabled

**Verification:**
```bash
# Verify logback-core version
mvn dependency:tree -Dincludes=ch.qos.logback:logback-core

# Check for logback-receiver (should return no results)
mvn dependency:tree | grep receiver
```

**References:**
- CVE: https://nvd.nist.gov/vuln/detail/CVE-2023-6481
- Issue: #17
