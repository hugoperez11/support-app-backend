package dev.hugo.supportApp.repositories;

import dev.hugo.supportApp.models.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RequestRepositoryTest {

    @Autowired
    private RequestRepository repository;

    @BeforeEach
    public void setUp() {
        // Clear the repository before each test
        repository.deleteAll();
    }

    @Test
    public void testSaveAndFindById() {
        // Arrange
        Request request = new Request();
        request.setRequestName("John Doe");
        request.setSubject("Technical Issue");
        request.setDescription("Issue with the application.");
        request.setRequestDate(LocalDateTime.now());

        // Act
        Request savedRequest = repository.save(request);
        Optional<Request> foundRequest = repository.findById(savedRequest.getId());

        // Assert
        assertThat(foundRequest).isPresent();
        assertThat(foundRequest.get().getRequestName()).isEqualTo("John Doe");
        assertThat(foundRequest.get().getSubject()).isEqualTo("Technical Issue");
        assertThat(foundRequest.get().getDescription()).isEqualTo("Issue with the application.");
    }

    @Test
    public void testDeleteById() {
        // Arrange
        Request request = new Request();
        request.setRequestName("Jane Doe");
        request.setSubject("Bug Report");
        request.setDescription("Bug in the system.");
        request.setRequestDate(LocalDateTime.now());

        Request savedRequest = repository.save(request);
        Long id = savedRequest.getId();

        // Act
        repository.deleteById(id);
        Optional<Request> deletedRequest = repository.findById(id);

        // Assert
        assertThat(deletedRequest).isNotPresent();
    }

    @Test
    public void testFindAll() {
        // Arrange
        Request request1 = new Request();
        request1.setRequestName("Alice");
        request1.setSubject("Feature Request");
        request1.setDescription("Request for a new feature.");
        request1.setRequestDate(LocalDateTime.now());
        repository.save(request1);

        Request request2 = new Request();
        request2.setRequestName("Bob");
        request2.setSubject("Support");
        request2.setDescription("Request for support.");
        request2.setRequestDate(LocalDateTime.now());
        repository.save(request2);

        // Act
        Iterable<Request> requests = repository.findAll();

        // Assert
        assertThat(requests).hasSize(2);
    }

    @Test
    public void testFindByIdNotFound() {
        // Act
        Optional<Request> foundRequest = repository.findById(Long.MAX_VALUE);

        // Assert
        assertThat(foundRequest).isNotPresent();
    }
}
