package com.tacoloco.deliveryservice.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.tacoloco.deliveryservice.model.Delivery;

@RunWith(SpringRunner.class)
@DataJpaTest
class DeliveryDataRepositoryTest {
	
	@Autowired 
	private TestEntityManager em;
	
	@Autowired
	private DeliverySpringDataRepository deliveryRepo;
	
	
	/******************* findAll() test cases ***************/
	//When 3 deliveries exist in the database, return all 3
	@Test
	@DirtiesContext
	void whenFindAllDeliveryfor3_returnAllDelivery() {
		Delivery d1 = new Delivery("John", "Doe", "21 Rosebank Alley", "Apt 23", "Asheville", "NC", "14234", "US");
		em.persist(d1);
		Delivery d2 = new Delivery("Emily", "Rand", "884 Fifth Ave.", "", "Camden", "NJ", "08105", "US");
		em.persist(d2);
		Delivery d3 = new Delivery("Molly", "Mason", "8163 2nd Dr.", "Unit 432", "Southfield", "MI", "14234", "US");
		em.persist(d3);
		
		em.flush();
		
		List<Delivery> resultList = deliveryRepo.findAll();
		assertEquals(resultList.size(), 3);
		assertEquals(resultList.get(0).equals(d1), true);
		assertEquals(resultList.get(1).equals(d2), true);
		assertEquals(resultList.get(2).equals(d3), true);
	}
	
	//when no deliveries exist in the database, return an empty list
	@Test
	void whenFindAllDeliveryfor0_returnEmptyList() {
		List<Delivery> resultList = deliveryRepo.findAll();
		assertEquals(resultList.isEmpty(), true);
	}
	
	/******************* findById() test cases ***************/
	//find an existing delivery by, return delivery if it exists
	@Test
	@DirtiesContext
	void whenFindById_returnDelivery() {
		Delivery d3 = new Delivery("Molly", "Mason", "8163 2nd Dr.", "Unit 432", "Southfield", "MI", "14234", "US");
		em.persistAndFlush(d3);
		Optional<Delivery> result = deliveryRepo.findById(d3.getId());
		assertEquals(d3, result.get());
	}
	
	/******************* save() test cases ***************/
	//when a save on a new resource is successful, check if the delivery is in database
	@Test
	@DirtiesContext
	void whenSaveOnNewResource_returnSuccessIfExists() {
		Delivery d1 = new Delivery("John", "Doe", "21 Rosebank Alley", "Apt 23", "Asheville", "NC", "14234", "US");
		deliveryRepo.save(d1);
		
		Delivery actual = em.find(Delivery.class, d1.getId());
		assertEquals(actual.equals(d1), true);
	}
	
	//when a save on an existing delivery is successful, check if delivery is updated in database
	@Test
	@DirtiesContext
	void whenSaveOnExistingResource_returnSuccessIfUpdated() {
		Delivery d1 = new Delivery("John", "Doe", "21 Rosebank Alley", "Apt 23", "Asheville", "NC", "14234", "US");
		em.persistAndFlush(d1);
		
		Delivery d2 = new Delivery(d1.getId(), "UpdatedName", "Doe", "21 Rosebank Alley", "Apt 23", "Asheville", "NC", "14234", "US");
		deliveryRepo.save(d2);
		
		Delivery actual = em.find(Delivery.class, d1.getId());
		assertEquals(actual.equals(d2), true);
	}

	/******************* deleteById() test cases ***************/
	//when a delete on an existing delivery is successful, check if delivery no longer exists
	@Test
	void whenDeleteOnExisting_returnSuccessIfNoLongerExists() {
		Delivery d1 = new Delivery("John", "Doe", "21 Rosebank Alley", "Apt 23", "Asheville", "NC", "14234", "US");
		em.persistAndFlush(d1);
		deliveryRepo.deleteById(d1.getId());
		Delivery actual = em.find(Delivery.class, d1.getId());
		assertEquals(actual == null, true);
	}

}
