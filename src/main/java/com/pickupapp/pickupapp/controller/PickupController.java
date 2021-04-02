package com.pickupapp.pickupapp.controller;

import com.pickupapp.pickupapp.model.*;
import com.pickupapp.pickupapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.*;
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

        if (userName.isEmpty() || password.isEmpty() || first_name.isEmpty() || last_name.isEmpty()) {
            return new ResponseEntity<>(AppMessages.MSG_MISSING_DATA, HttpStatus.FORBIDDEN);
        }

        if (customerRepository.findByUserName(userName) != null) {
            return new ResponseEntity<>(AppMessages.MSG_NAME_ALREADY_USED, HttpStatus.FORBIDDEN);
        }
        customerRepository.save(new Customer(userName, first_name, last_name, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    public Map<String,Object> getProducts(Authentication authentication){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();

        if(isGuest(authentication)){
            dto.put("customer", null);
        }
        else{
            Customer customer = customerRepository.findByUserName(authentication.getName());
            dto.put("customer", makeCustomerDTO(customer));
        }
        dto.put("orders", orderRepository.findAll().stream().map(this::makeOrderDTO).collect(Collectors.toList()));
        return dto;
    }



    @PostMapping("/products/save")
    public RedirectView savingProduct(@RequestParam String product_name,
                                      @RequestParam String category,
                                      @RequestParam Double price,
                                      @RequestParam("image") MultipartFile multipartFile) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        Product product = new Product(product_name, price, category, fileName);

        Product savedProduct = productRepository.save(product);

        String uploadDir = "product-photos/" + savedProduct.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return new RedirectView("/products", true);
    }

    private Map<String, Object> makeOrderDTO(Order order) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();

        dto.put("order_number", order.getId());
        dto.put("first_name", order.getCustomer().getFirst_name());
        dto.put("last_name", order.getCustomer().getLast_name());
        dto.put("total_products", order.getTotal_products());
        dto.put("total_price", order.getTotal_price());
        dto.put("arrival_time", order.getArrival_time());
        dto.put("pickup_date", order.getPickup_date());
        return dto;
    }

    private Map<String, Object> makeCustomerDTO(Customer customer) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>(); //
        dto.put("id", customer.getId());
        dto.put("first_name", customer.getFirst_name());
        dto.put("last_name", customer.getLast_name());
        return dto;
    }

    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
}

