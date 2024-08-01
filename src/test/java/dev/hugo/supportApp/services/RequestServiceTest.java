package dev.hugo.supportApp.services;

import dev.hugo.supportApp.models.Request;
import dev.hugo.supportApp.repositories.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;  // Importa la clase List
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RequestServiceTest {

    @Mock
    private RequestRepository requestRepository;

    @InjectMocks
    private RequestService requestService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        // Arrange
        Request request1 = new Request();
        request1.setRequestName("Alice");
        request1.setSubject("Feature Request");
        request1.setDescription("Request for a new feature.");
        request1.setRequestDate(LocalDateTime.now());

        Request request2 = new Request();
        request2.setRequestName("Bob");
        request2.setSubject("Support");
        request2.setDescription("Request for support.");
        request2.setRequestDate(LocalDateTime.now());

        when(requestRepository.findAll()).thenReturn(List.of(request1, request2));

        // Act
        List<Request> requests = requestService.getAll();

        // Assert
        assertThat(requests).hasSize(2);
        assertThat(requests.get(0).getRequestName()).isEqualTo("Alice");
        assertThat(requests.get(1).getRequestName()).isEqualTo("Bob");
    }

    @Test
    public void testFindByIdFound() {
        // Arrange
        Request request = new Request();
        request.setRequestName("Charlie");
        request.setSubject("Inquiry");
        request.setDescription("Inquiry about the system.");
        request.setRequestDate(LocalDateTime.now());

        when(requestRepository.findById(1L)).thenReturn(Optional.of(request));

        // Act
        Optional<Request> foundRequest = requestService.findById(1L);

        // Assert
        assertThat(foundRequest).isPresent();
        assertThat(foundRequest.get().getRequestName()).isEqualTo("Charlie");
    }

    @Test
    public void testFindByIdNotFound() {
        // Arrange
        when(requestRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Request> foundRequest = requestService.findById(1L);

        // Assert
        assertThat(foundRequest).isNotPresent();
    }

    @Test
    public void testStoreSuccess() {
        // Arrange
        Request request = new Request();
        request.setRequestName("Diana");
        request.setSubject("Feedback");
        request.setDescription("Feedback on the product.");
        request.setRequestDate(LocalDateTime.now());

        when(requestRepository.save(request)).thenReturn(request);

        // Act
        Request savedRequest = requestService.store(request);

        // Assert
        assertThat(savedRequest).isNotNull();
        assertThat(savedRequest.getRequestName()).isEqualTo("Diana");
    }

    @Test
    public void testStoreFailure() {
        // Arrange
        Request request = new Request();
        request.setRequestName("");  // Invalid request name
        request.setSubject("Feedback");
        request.setDescription("Feedback on the product.");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> requestService.store(request));
    }

    @Test
    public void testUpdateSuccess() {
        // Arrange
        Request existingRequest = new Request();
        existingRequest.setId(1L);
        existingRequest.setRequestName("Eve");
        existingRequest.setSubject("Update");
        existingRequest.setDescription("Initial description");
        existingRequest.setRequestDate(LocalDateTime.now());

        Request updatedRequest = new Request();
        updatedRequest.setRequestName("Eve Updated");
        updatedRequest.setSubject("Update Updated");
        updatedRequest.setDescription("Updated description");

        when(requestRepository.findById(1L)).thenReturn(Optional.of(existingRequest));
        when(requestRepository.save(existingRequest)).thenReturn(existingRequest);

        // Act
        Request result = requestService.update(1L, updatedRequest);

        // Assert
        assertThat(result.getRequestName()).isEqualTo("Eve Updated");
        assertThat(result.getSubject()).isEqualTo("Update Updated");
        assertThat(result.getDescription()).isEqualTo("Updated description");
    }

    @Test
    public void testUpdateNotFound() {
        // Arrange
        Request updatedRequest = new Request();
        updatedRequest.setRequestName("Eve Updated");

        when(requestRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> requestService.update(1L, updatedRequest));
    }

    @Test
    public void testDeleteSuccess() {
        // Arrange
        when(requestRepository.existsById(1L)).thenReturn(true);

        // Act
        requestService.delete(1L);

        // Assert
        verify(requestRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteNotFound() {
        // Arrange
        when(requestRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> requestService.delete(1L));
    }
}
