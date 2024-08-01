package dev.hugo.supportApp.models;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

public class RequestTest {

    @Test
    public void testRequestCreation() {
        // Arrange
        Request request = new Request();
        
        // Act
        request.setRequestName("John Doe");
        request.setSubject("Technical Support");
        request.setDescription("Need help with the application");
        LocalDateTime now = LocalDateTime.now();
        request.setRequestDate(now);

        // Assert
        assertThat(request.getRequestName()).isEqualTo("John Doe");
        assertThat(request.getSubject()).isEqualTo("Technical Support");
        assertThat(request.getDescription()).isEqualTo("Need help with the application");
        assertThat(request.getRequestDate()).isEqualTo(now);
    }

    @Test
    public void testDefaultConstructor() {
        // Arrange
        Request request = new Request();
        LocalDateTime now = LocalDateTime.now();
        
        // Act
        LocalDateTime requestDate = request.getRequestDate();

        // Assert
        // Manually compare the request date with a tolerance
        long secondsDifference = java.time.Duration.between(requestDate, now).getSeconds();
        assertThat(secondsDifference).isLessThanOrEqualTo(1); // Allowing a 1-second difference
    }

    @Test
    public void testSettersAndGetters() {
        // Arrange
        Request request = new Request();
        request.setId(1L);
        request.setRequestName("Alice");
        request.setSubject("Issue");
        request.setDescription("Description of the issue.");
        
        // Act and Assert
        assertThat(request.getId()).isEqualTo(1L);
        assertThat(request.getRequestName()).isEqualTo("Alice");
        assertThat(request.getSubject()).isEqualTo("Issue");
        assertThat(request.getDescription()).isEqualTo("Description of the issue.");
    }
}
