package com.company.service.ms.common;

import com.company.service.ms.entity.Address;
import com.company.service.ms.entity.Driver;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CompanyParameterResolver implements ParameterResolver {
    final String MEKNES_CITY = "Meknes";

    private Map<String, Driver> drivers;

    public CompanyParameterResolver() {
        Map<String, Driver> driverMap = new HashMap<>();
        driverMap.put("driver1", Driver.builder()
                .id(1L)
                .firstname("driver-firstName1")
                .address(
                        Address.builder()
                                .city(MEKNES_CITY)
                                .build()
                )
                .lastname("driver-lastName1")
                .build()
        );
        driverMap.put("driver2", Driver.builder()
                .id(2L)
                .firstname("driver-firstName1")
                .address(
                        Address.builder()
                                .city(MEKNES_CITY)
                                .build()
                )
                .lastname("driver-lastName2")
                .build());
        this.drivers = driverMap;
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
                                     ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        return Objects.equals(parameter.getParameterizedType().getTypeName(),
                "java.util.Map<java.lang.String, com.company.service.ms.entity.Driver>");
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext,
                                   ExtensionContext extensionContext) throws ParameterResolutionException {
        /*ExtensionContext.Store store = extensionContext.getStore(ExtensionContext.Namespace.create(Driver.class));
        store.getOrComputeIfAbsent("drivers", k -> getDrivers());*/
        return drivers;
    }
}
