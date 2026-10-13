package com.example.customerservice.controller;

import com.example.customerservice.dto.CustomerRequestDTO;
import com.example.customerservice.dto.CustomerResponseDTO;
import com.example.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<CustomerResponseDTO> register(@RequestBody CustomerRequestDTO request) {
        return new ResponseEntity<>(customerService.register(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomer(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PutMapping("/login")
    public ResponseEntity<CustomerResponseDTO> login(@RequestBody CustomerRequestDTO request) {
        // Tận dụng RequestDTO để lấy email và password
        return ResponseEntity.ok(customerService.login(request.email(), request.password()));
    }
}
