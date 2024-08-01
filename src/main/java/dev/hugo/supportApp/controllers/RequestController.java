package dev.hugo.supportApp.controllers;

import dev.hugo.supportApp.models.Request;
import dev.hugo.supportApp.services.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support-requests")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class RequestController {

    private final RequestService service;

    public RequestController(RequestService service) {
        this.service = service;
    }

    
    @GetMapping
    public ResponseEntity<List<Request>> getAllRequests() {
        List<Request> requests = service.getAll();
        return ResponseEntity.ok(requests);
    }

  
    @GetMapping("/{id}")
    public ResponseEntity<Request> getRequestById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

   
    @PostMapping
    public ResponseEntity<?> createRequest(@RequestBody Request newRequest) {
        try {
            Request createdRequest = service.store(newRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
        } catch (Exception e) {
            e.printStackTrace(); // This will print the stack trace to your server logs
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error creating request: " + e.getMessage());
        }
    }

   
    @PutMapping("/{id}")
    public ResponseEntity<Request> updateRequest(@PathVariable Long id, @RequestBody Request updatedRequest) {
        try {
            Request updated = service.update(id, updatedRequest);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

   /* Test if API is  working */
    @GetMapping("/status")
    public ResponseEntity<String> statusCheck() {
        return ResponseEntity.ok("API is working");
    }
}