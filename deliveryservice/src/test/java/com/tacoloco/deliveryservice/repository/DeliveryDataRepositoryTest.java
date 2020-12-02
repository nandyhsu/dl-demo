package com.tacoloco.deliveryservice.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.tacoloco.deliveryservice.model.Delivery;

@RunWith(SpringRunner.class)
@DataJpaTest
class DeliveryDataRepositoryTest {
	
	@Autowired 
	private TestEntityManager em;
	
	@Autowired
	private DeliverySpringDataRepository deliveryRepo;
	
	@Test
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
	
	@Test
	void whenFindAllDeliveryfor0_returnEmptyList() {
		
		List<Delivery> resultList = deliveryRepo.findAll();
		assertEquals(resultList.isEmpty(), true);

	}

}
