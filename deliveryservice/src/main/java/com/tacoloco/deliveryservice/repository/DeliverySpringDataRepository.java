package com.tacoloco.deliveryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tacoloco.deliveryservice.model.Delivery;

@Repository
public interface DeliverySpringDataRepository extends JpaRepository<Delivery, Long>{
}
