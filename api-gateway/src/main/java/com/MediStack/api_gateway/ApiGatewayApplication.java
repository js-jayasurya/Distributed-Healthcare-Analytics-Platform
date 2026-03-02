package com.MediStack.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // --- AUTH SERVICE ---
                // Matches /auth/login, /auth/register, etc.
                .route("auth-service-route", r -> r.path("/auth/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://auth-service:4005"))

                // --- PATIENT SERVICE ---
                // Matches /api/patients and /api/patients/123
                .route("patient-service-route", r -> r.path("/api/patients/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://patient-service:4000"))

                // --- API DOCS: PATIENT ---
                .route("api-docs-patient-route", r -> r.path("/api-docs/patients")
                        .filters(f -> f.rewritePath("/api-docs/patients", "/v3/api-docs"))
                        .uri("http://patient-service:4000"))

                // --- API DOCS: AUTH ---
                .route("api-docs-auth-route", r -> r.path("/api-docs/auth")
                        .filters(f -> f.rewritePath("/api-docs/auth", "/v3/api-docs"))
                        .uri("http://auth-service:4005"))
                .build();
    }
}