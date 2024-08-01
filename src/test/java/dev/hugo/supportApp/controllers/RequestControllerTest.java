package dev.hugo.supportApp.controllers;

import dev.hugo.supportApp.models.Request;
import dev.hugo.supportApp.services.RequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RequestControllerTest {

    @InjectMocks
    private RequestController requestController;

    @Mock
    private RequestService requestService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllRequests() {
        // Arrange
        Request request1 = new Request();
        Request request2 = new Request();
        List<Request> requests = Arrays.asList(request1, request2);
        when(requestService.getAll()).thenReturn(requests);

        // Act
        ResponseEntity<List<Request>> response = requestController.getAllRequests();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(requests, response.getBody());
    }

    @Test
    public void testGetRequestById_Success() {
        // Arrange
        Long id = 1L;
        Request request = new Request();
        when(requestService.findById(id)).thenReturn(Optional.of(request));

        // Act
        ResponseEntity<Request> response = requestController.getRequestById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(request, response.getBody());
    }

    @Test
    public void testGetRequestById_NotFound() {
        // Arrange
        Long id = 1L;
        when(requestService.findById(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Request> response = requestController.getRequestById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateRequest_Success() {
        // Arrange
        Request newRequest = new Request();
        Request createdRequest = new Request();
        when(requestService.store(newRequest)).thenReturn(createdRequest);

        // Act
        ResponseEntity<?> response = requestController.createRequest(newRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdRequest, response.getBody());
    }

    @Test
    public void testCreateRequest_Error() {
        // Arrange
        Request newRequest = new Request();
        when(requestService.store(newRequest)).thenThrow(new RuntimeException("Error"));

        // Act
        ResponseEntity<?> response = requestController.createRequest(newRequest);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error creating request: Error", response.getBody());
    }

    @Test
    public void testUpdateRequest_Success() {
        // Arrange
        Long id = 1L;
        Request updatedRequest = new Request();
        when(requestService.update(id, updatedRequest)).thenReturn(updatedRequest);

        // Act
        ResponseEntity<Request> response = requestController.updateRequest(id, updatedRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedRequest, response.getBody());
    }

    @Test
    public void testUpdateRequest_NotFound() {
        // Arrange
        Long id = 1L;
        Request updatedRequest = new Request();
        when(requestService.update(id, updatedRequest)).thenThrow(new IllegalArgumentException("Not found"));

        // Act
        ResponseEntity<Request> response = requestController.updateRequest(id, updatedRequest);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteRequest_Success() {
        // Arrange
        Long id = 1L;
        doNothing().when(requestService).delete(id);

        // Act
        ResponseEntity<Void> response = requestController.deleteRequest(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteRequest_NotFound() {
        // Arrange
        Long id = 1L;
        doThrow(new IllegalArgumentException("Not found")).when(requestService).delete(id);

        // Act
        ResponseEntity<Void> response = requestController.deleteRequest(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testStatusCheck() {
        // Act
        ResponseEntity<String> response = requestController.statusCheck();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("API is working", response.getBody());
    }
}
