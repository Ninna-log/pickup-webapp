package com.pickupapp.pickupapp.repositories;

import com.pickupapp.pickupapp.model.CustomerProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CustomerProductRepository extends JpaRepository<CustomerProduct, Long> {
}
