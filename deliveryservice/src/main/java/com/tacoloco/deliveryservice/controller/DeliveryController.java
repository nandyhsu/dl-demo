package com.tacoloco.deliveryservice.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tacoloco.deliveryservice.model.Delivery;
import com.tacoloco.deliveryservice.repository.DeliverySpringDataRepository;

@RestController
public class DeliveryController {
	@Autowired
	private DeliverySpringDataRepository deliveryRepository;
	
	//Get all deliveries
	//Not recommended to do this. Would run into issues with large data sets. Pagination would be the way to go.
	@GetMapping("/delivery")
	public List<Delivery> getAllDelivery(){
		return deliveryRepository.findAll();
	}
	
	//Create new delivery
	@PostMapping("/delivery")
	public ResponseEntity<String> createDelivery(@RequestBody Delivery delivery){
		
		deliveryRepository.save(delivery);
		
		URI loc = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(delivery.getId()).toUri();
		
		return ResponseEntity.created(loc).build();
	}
	
	
	//Update an existing delivery
	@PutMapping("/delivery/{id}")
	public ResponseEntity<String> updateDelivery(@RequestBody Delivery delivery, @PathVariable Long id){
		boolean exists = deliveryRepository.existsById(id);
		if(!exists) {
			return new ResponseEntity<String>("Delivery does not exist!", HttpStatus.CONFLICT);
		}

		delivery.setId(id);
		deliveryRepository.save(delivery);
		
		URI loc = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		
		return ResponseEntity.created(loc).build();
	}
}
