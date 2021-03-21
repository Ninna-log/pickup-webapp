package com.pickupapp.pickupapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "order_table")
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private LocalDate pickup_date;
    private LocalTime arrival_time;
    private int total_products;
    private double total_price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy="order", fetch=FetchType.EAGER)
    private Set<CustomerProduct> customerProducts = new HashSet<>();


    protected Order() {}

    public Order(LocalDate pickup_date, LocalTime arrival_time, Customer customer, int total_products, Double total_price){
        this.pickup_date = pickup_date;
        this.arrival_time = arrival_time;
        this.customer = customer;
        this.total_products = total_products;
        this.total_price = total_price;
    }

    @JsonIgnore
    public List<Product> getProducts(){
        return customerProducts.stream().map(CustomerProduct::getProduct).collect(toList());
    }

    public Set<CustomerProduct> getCustomerProducts() {
        return customerProducts;
    }

    public void setCustomerProducts(Set<CustomerProduct> customerProducts) {
        this.customerProducts = customerProducts;
    }

    public LocalDate getPickup_date() {

        return pickup_date;
    }

    public void setPickup_date(LocalDate pickup_date) {

        this.pickup_date = pickup_date;
    }

    public LocalTime getArrival_time() {

        return arrival_time;
    }

    public void setArrival_time(LocalTime arrival_time) {

        this.arrival_time = arrival_time;
    }

    public Long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {

        return customer;
    }

    public void setCustomer(Customer customer) {

        this.customer = customer;
    }

    public int getTotal_products() {
        return total_products;
    }

    public void setTotal_products(int total_products) {
        this.total_products = total_products;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }
}
