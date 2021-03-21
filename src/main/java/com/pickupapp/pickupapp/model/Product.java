package com.pickupapp.pickupapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "product_table")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String product_name;
    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy="product", fetch=FetchType.EAGER)
    private Set<CustomerProduct> customerProducts = new HashSet<>();


    public Product (){}

    public Product(String product_name, Double price, Customer customer) {
        this.product_name = product_name;
        this.price = price;
        this.customer = customer;
    }

    @JsonIgnore
    public List<Customer> getCustomers(){
        return customerProducts.stream().map(CustomerProduct::getCustomer).collect(toList());
    }

    public Set<CustomerProduct> getCustomerProducts() {

        return customerProducts;
    }

    public void setCustomerProducts(Set<CustomerProduct> customerProducts) {

        this.customerProducts = customerProducts;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Double getPrice() {

        return price;
    }

    public void setPrice(Double price) {

        this.price = price;
    }

    public String getName() {

        return product_name;
    }

    public void setName(String product_name) {

        this.product_name = product_name;
    }
}
