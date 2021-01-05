package com.company.service.ms.common;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

public abstract class DependenciesInitializer {

    @DisplayName("Initializing dependencies")
    @BeforeAll
    static void initializer() {
        System.out.println("Test beforeALL");
    }

    @DisplayName("Destroying dependencies")
    @AfterAll
    static void destroy() {
        System.out.println("Test AfterALL");
    }
}
