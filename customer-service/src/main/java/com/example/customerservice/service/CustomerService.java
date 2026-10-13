package com.example.customerservice.service;

import com.example.customerservice.dto.CustomerRequestDTO;
import com.example.customerservice.dto.CustomerResponseDTO;
import com.example.customerservice.entity.Customer;
import com.example.customerservice.exception.ResourceNotFoundException;
import com.example.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // POST: Đăng ký
    public CustomerResponseDTO register(CustomerRequestDTO request) {
        Customer customer = new Customer();
        customer.setFullName(request.fullName());
        customer.setEmail(request.email());
        // Mã hóa mật khẩu
        customer.setPassword(passwordEncoder.encode(request.password()));

        Customer saved = customerRepository.save(customer);
        return new CustomerResponseDTO(saved.getId(), saved.getFullName(), saved.getEmail());
    }

    // GET: Tìm theo ID
    public CustomerResponseDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Khách hàng với ID " + id + " không tồn tại!"));
        return new CustomerResponseDTO(customer.getId(), customer.getFullName(), customer.getEmail());
    }

    // PUT: Đăng nhập
    public CustomerResponseDTO login(String email, String password) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("email or password incorrect"));

        // So sánh mật khẩu nhập vào với mật khẩu đã mã hóa trong DB
        if (!passwordEncoder.matches(password, customer.getPassword())) {
            throw new IllegalArgumentException("email or password incorrect");
        }

        return new CustomerResponseDTO(customer.getId(), customer.getFullName(), customer.getEmail());
    }
}
