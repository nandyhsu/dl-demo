package com.tacoloco.deliveryservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tacoloco.deliveryservice.model.Delivery;
import com.tacoloco.deliveryservice.repository.DeliverySpringDataRepository;

@RestController
public class DeliveryController {
	@Autowired
	private DeliverySpringDataRepository deliveryRepository;
	
	@GetMapping("/delivery")
	public List<Delivery> getAllDelivery(){
		return deliveryRepository.findAll();
	}
}
