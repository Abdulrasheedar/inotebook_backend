package com.inotebook.inotebook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000") // Allow the frontend origin
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow necessary methods
//                .allowedHeaders("*")
//                .allowCredentials(true); // Allow cookies and credentials if necessary
//    }
//}

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")  // Allow CORS on /api/** routes
                        .allowedOrigins("http://localhost:3000")  // Allow only this origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Include OPTIONS for preflight
                        .allowedHeaders("*")  // Allow all headers
                        .exposedHeaders("Authorization")  // Expose Authorization header if needed
                        .allowCredentials(true);  // If you're sending cookies/auth headers
            }
        };
    }
}

