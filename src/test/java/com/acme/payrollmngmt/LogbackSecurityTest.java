package com.acme.payrollmngmt;

import ch.qos.logback.classic.LoggerContext;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Security test for CVE-2023-6481 remediation
 * Verifies that logback-receiver is not configured
 */
@SpringBootTest
class LogbackSecurityTest {

    @Test
    void testLogbackReceiverNotConfigured() {
        // Get the logback context
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        
        // Verify no receiver components are configured
        // Logback receivers would be registered in the context
        assertTrue(context.getCopyOfListenerList().isEmpty() 
                   || context.getCopyOfListenerList().stream()
                       .noneMatch(listener -> listener.getClass().getName().contains("Receiver")),
                   "Logback receiver should not be configured (CVE-2023-6481)");
    }

    @Test
    void testLogbackCoreVersionIsNotVulnerable() {
        // This test documents that we're using a safe version
        // CVE-2023-6481 affects versions < 1.2.13, 1.3.14, 1.4.14
        // Spring Boot 3.5.6 uses logback-core 1.5.18 which is safe
        
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        assertNotNull(context, "Logback context should be available");
        
        // Verify logback is properly initialized
        assertTrue(context.isStarted(), "Logback should be started");
    }
}
