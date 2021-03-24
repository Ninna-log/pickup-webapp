package com.pickupapp.pickupapp.controller;

import com.pickupapp.pickupapp.model.*;
import com.pickupapp.pickupapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class PickupController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerProductRepository customerProductRepository;

    @GetMapping("/customers")
    public List<Customer> getCustomers(){

        return customerRepository.findAll();
    }

    /*@GetMapping("/products")
    public List<Product> getProducts(){
        return productRepository.findAll();
    }*/

    /*@GetMapping("/orders")
    public List<Order> getOrders(){
        return orderRepository.findAll();
    }*/

    /*@GetMapping("/customerProducts")
    public List<CustomerProduct> getCustomerProducts(){
        return customerProductRepository.findAll();
    }*/

    @PostMapping("/users")
    public ResponseEntity<Object> register(@RequestParam String userName,
                                           @RequestParam String first_name,
                                           @RequestParam String last_name,
                                           @RequestParam String password) {

        if (userName.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>(AppMessages.MSG_MISSING_DATA, HttpStatus.FORBIDDEN);
        }

        if (customerRepository.findByUserName(userName) != null) {
            return new ResponseEntity<>(AppMessages.MSG_NAME_ALREADY_USED, HttpStatus.FORBIDDEN);
        }
        customerRepository.save(new Customer(userName, first_name, last_name, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    public List<Map<String, Object>> getProducts(){
        return orderRepository.findAll().stream().map(this::makeOrderDTO).collect(Collectors.toList());
    }

    private Map<String, Object> makeOrderDTO(Order order) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();

        dto.put("order_number", order.getId());
        dto.put("client", order.getCustomer().getFirst_name() + order.getCustomer().getLast_name());
        dto.put("total_products", order.getTotal_products());
        dto.put("total_price", order.getTotal_price());
        dto.put("arrival_time", order.getArrival_time());
        dto.put("pickup_date", order.getPickup_date());
        return dto;
    }
}

