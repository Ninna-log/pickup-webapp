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

			Customer customer1 = new Customer("ninna@gmail.com", "Gianinna", "Márquez", passwordEncoder.encode("24"));
			Customer customer2 = new Customer("pepito@gmail.com", "Pepito", "Pérez",passwordEncoder.encode("24"));
			Customer customer3 = new Customer("carlitos@gmail.com", "Carlos", "Díaz",passwordEncoder.encode("24"));
			Customer customer4 = new Customer("maria@gmail.com", "María", "Díaz",passwordEncoder.encode("24"));
			Customer customer5 = new Customer("nicolas@gmail.com", "Nicolás", "Marquart",passwordEncoder.encode("1234"));

			customerRepository.save(customer2);
			customerRepository.save(customer1);
			customerRepository.save(customer3);
			customerRepository.save(customer4);
			customerRepository.save(customer5);

			Order order1 = new Order(LocalDate.now(), LocalTime.now().plusHours(1), customer1, 5, 1500.50);
			Order order2 = new Order(LocalDate.now(), LocalTime.now().plusHours(2), customer2, 15, 2998.60);
			Order order3 = new Order(LocalDate.now(), LocalTime.now().plusHours(3), customer3, 18, 4590.40);
			Order order4 = new Order(LocalDate.now(), LocalTime.now().plusHours(4), customer4, 9, 1870.30);
			Order order5 = new Order(LocalDate.now(), LocalTime.now().plusHours(4), customer4, 7, 1080.30);

			orderRepository.save(order1);
			orderRepository.save(order2);
			orderRepository.save(order3);
			orderRepository.save(order4);
			orderRepository.save(order5);


			Product product1 = new Product("Leche Larga Vida 3% Clásica La Serenísima 1L", 160.00, "Lacteos", "image/png");
			Product product2 = new Product("Yogur Yogurísimo Firme La Serenísima x1 Frutilla", 120.00, "Lacteos", "image/png");
			Product product3 = new Product("Bebida Láctea Sense Caramel La Serenísima 1L", 180.00, "Lacteos", "image/png");
			Product product4 = new Product("Leche Reducida en Lactosa La Serenísima 1L", 190.00, "Lacteos", "image/png");
			Product product5 = new Product("Dulce de Leche Colonial La Serenísima 400g", 195.00, "Lacteos", "image/png");
			Product product6 = new Product("Dulce de Leche Clásico La Serenísima 400g", 190.00, "Lacteos", "image/png");
			Product product7 = new Product("Finlandia Chef Hebras 4 quesos 130g", 220.00, "Lacteos", "image/png");
			Product product8 = new Product("Finlandia Chef Fetas Danbo 150g", 210.00, "Lacteos", "image/png");


			productRepository.save(product1);
			productRepository.save(product2);
			productRepository.save(product3);
			productRepository.save(product4);
			productRepository.save(product5);
			productRepository.save(product6);
			productRepository.save(product7);
			productRepository.save(product8);

			CustomerProduct customerProduct1 = new CustomerProduct(customer1, product1, order1);
			CustomerProduct customerProduct2 = new CustomerProduct(customer2, product2, order2);
			CustomerProduct customerProduct3 = new CustomerProduct(customer2, product3, order3);
			CustomerProduct customerProduct4 = new CustomerProduct(customer3, product4, order4);
			CustomerProduct customerProduct5 = new CustomerProduct(customer5, product4, order5);
			CustomerProduct customerProduct6 = new CustomerProduct(customer1, product5, order1);
			CustomerProduct customerProduct7 = new CustomerProduct(customer2, product6, order2);
			CustomerProduct customerProduct8 = new CustomerProduct(customer2, product7, order3);
			CustomerProduct customerProduct9 = new CustomerProduct(customer2, product8, order3);

			customerProductRepository.save(customerProduct1);
			customerProductRepository.save(customerProduct2);
			customerProductRepository.save(customerProduct3);
			customerProductRepository.save(customerProduct4);
			customerProductRepository.save(customerProduct5);
			customerProductRepository.save(customerProduct6);
			customerProductRepository.save(customerProduct7);
			customerProductRepository.save(customerProduct8);
			customerProductRepository.save(customerProduct9);

		};
	}
}
