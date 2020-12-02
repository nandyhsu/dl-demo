package com.tacoloco.deliveryservice.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	
	//get all deliveries
	//not recommended to do this. Would run into issues with large data sets. Pagination would be the way to go for large applications.
	@GetMapping("/delivery")
	public List<Delivery> getAllDelivery(){
		return deliveryRepository.findAll();
	}
	
	//get one delivery by id
	@GetMapping("/delivery/{id}")
	public ResponseEntity<Delivery> getDelivery(@PathVariable Long id){
		
		Optional<Delivery> delivery = deliveryRepository.findById(id);
		if(!delivery.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(delivery.get());
	}
	
	//create new delivery
	@PostMapping("/delivery")
	public ResponseEntity<String> createDelivery(@RequestBody Delivery delivery){
		
		deliveryRepository.save(delivery);
		
		URI loc = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(delivery.getId()).toUri();
		
		return ResponseEntity.created(loc).build();
	}
	
	
	//update an existing delivery
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
	
	//delete an existing delivery
	@DeleteMapping("/delivery/{id}")
	public ResponseEntity<String> deleteDelivery(@PathVariable Long id){
		boolean exists = deliveryRepository.existsById(id);
		if(!exists) {
			return new ResponseEntity<String>("Delivery does not exist!", HttpStatus.CONFLICT);
		}
		
		deliveryRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
