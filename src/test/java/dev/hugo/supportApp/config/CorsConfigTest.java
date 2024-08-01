package dev.hugo.supportApp.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CorsConfigTest {

    @Test
    public void testCorsConfiguration() {
        // Obtener la configuración de CORS directamente desde la instancia
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);

        // Verificar la configuración
        CorsConfiguration registeredConfig = source.getCorsConfigurations().get("/**");
        assertNotNull(registeredConfig, "CorsConfiguration should not be null");
        assertEquals(true, registeredConfig.getAllowCredentials(), "AllowCredentials should be true");
        assertEquals("http://localhost:5173", registeredConfig.getAllowedOrigins().get(0), "AllowedOrigin should be http://localhost:5173");
        assertEquals("*", registeredConfig.getAllowedHeaders().get(0), "AllowedHeader should be *");
        assertEquals("*", registeredConfig.getAllowedMethods().get(0), "AllowedMethod should be *");
    }
}
