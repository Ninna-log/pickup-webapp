package com.pickupapp.pickupapp;

import com.pickupapp.pickupapp.model.*;
import com.pickupapp.pickupapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootApplication
public class PickupappApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {

		SpringApplication.run(PickupappApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(CustomerRepository customerRepository, OrderRepository orderRepository,
									  ProductRepository productRepository, CustomerProductRepository customerProductRepository) {
		return (args) -> {

			Customer customer1 = new Customer("Gianinna Marquez", passwordEncoder.encode("24"));
			Customer customer2 = new Customer("Pablo Perez", passwordEncoder.encode("24"));
			Customer customer3 = new Customer("Pedro Perez", passwordEncoder.encode("24"));
			Customer customer4 = new Customer("Maria Diaz", passwordEncoder.encode("24"));

			customerRepository.save(customer2);
			customerRepository.save(customer1);
			customerRepository.save(customer3);
			customerRepository.save(customer4);

			Order order1 = new Order(LocalDate.now(), LocalTime.now().plusHours(1), customer1, 5, 1500.50);
			Order order2 = new Order(LocalDate.now(), LocalTime.now().plusHours(2), customer2, 15, 2998.60);
			Order order3 = new Order(LocalDate.now(), LocalTime.now().plusHours(3), customer3, 18, 4590.40);
			Order order4 = new Order(LocalDate.now(), LocalTime.now().plusHours(4), customer4, 9, 1870.30);

			orderRepository.save(order1);
			orderRepository.save(order2);
			orderRepository.save(order3);
			orderRepository.save(order4);

			Product product1 = new Product("Chocolate", 300.00, customer1);
			Product product2 = new Product("Helado", 400.00, customer2);
			Product product3 = new Product("Harina", 180.00, customer3);
			Product product4 = new Product("Gaseosa", 190.00, customer4);

			productRepository.save(product1);
			productRepository.save(product2);
			productRepository.save(product3);
			productRepository.save(product4);

			CustomerProduct customerProduct1 = new CustomerProduct(customer1, product1, order1);
			CustomerProduct customerProduct2 = new CustomerProduct(customer2, product2, order2);
			CustomerProduct customerProduct3 = new CustomerProduct(customer2, product3, order3);
			CustomerProduct customerProduct4 = new CustomerProduct(customer3, product4, order4);

			customerProductRepository.save(customerProduct1);
			customerProductRepository.save(customerProduct2);
			customerProductRepository.save(customerProduct3);
			customerProductRepository.save(customerProduct4);

		};
	}
}
