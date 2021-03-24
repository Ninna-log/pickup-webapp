package com.pickupapp.pickupapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "customer_table")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String userName;
    private String first_name;
    private String last_name;

    @Autowired
    private String password;

    @OneToMany(mappedBy="customer", fetch=FetchType.EAGER)
    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy="customer", fetch=FetchType.EAGER)
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy="customer", fetch=FetchType.EAGER)
    private Set<CustomerProduct> customerProducts = new HashSet<>();


    public Customer() {}

    public Customer(String userName, String first_name, String last_name, String password){
        this.userName = userName;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
    }

    @JsonIgnore
    public List<Product> getProduct(){
        return customerProducts.stream().map(CustomerProduct::getProduct).collect(toList());
    }

    public Set<CustomerProduct> getCustomerProducts() {
        return customerProducts;
    }

    public void setCustomerProducts(Set<CustomerProduct> customerProducts) {
        this.customerProducts = customerProducts;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id.equals(customer.id) &&
                userName.equals(customer.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName);
    }
}
