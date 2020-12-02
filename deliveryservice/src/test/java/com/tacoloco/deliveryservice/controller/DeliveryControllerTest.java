package com.tacoloco.deliveryservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.tacoloco.deliveryservice.model.Delivery;
import com.tacoloco.deliveryservice.repository.DeliverySpringDataRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value = DeliveryController.class)
public class DeliveryControllerTest {

	@Autowired
	private MockMvc	mockMvc;
	
	@MockBean
	private DeliverySpringDataRepository deliveryRepo;
	
	@Test
	public void whenGetAllDeliveryfor3_returnAllDelivery() throws Exception{
		
		List<Delivery> mockList = new ArrayList<>();
		mockList.add(new Delivery(1L, "John", "Doe", "21 Rosebank Alley", "Apt 23", "Asheville", "NC", "14234", "US"));
		mockList.add(new Delivery(2L, "Emily", "Rand", "884 Fifth Ave.", "", "Camden", "NJ", "08105", "US"));
		mockList.add(new Delivery(3L, "Molly", "Mason", "8163 2nd Dr.", "Unit 432", "Southfield", "MI", "14234", "US"));
		Mockito.when(deliveryRepo.findAll()).thenReturn(mockList);
	
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/delivery")
				.accept(MediaType.APPLICATION_JSON);
		
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	
		String expected = "[{\"id\" : 1,\r\n" + 
				"	\"firstName\" : \"John\",\r\n" + 
				"	\"lastName\" : \"Doe\",\r\n" + 
				"	\"addressLine1\" : \"21 Rosebank Alley\",\r\n" + 
				"	\"addressLine2\" : \"Apt 23\",\r\n" + 
				"	\"city\" : \"Asheville\",\r\n" + 
				"	\"state\" : \"NC\",\r\n" + 
				"	\"zipcode\" : \"14234\",\r\n" + 
				"	\"country\" : \"US\"},\r\n" + 
				"{\"id\" : 2,\r\n" + 
				"	\"firstName\" : \"Emily\",\r\n" + 
				"	\"lastName\" : \"Rand\",\r\n" + 
				"	\"addressLine1\" : \"884 Fifth Ave.\",\r\n" + 
				"	\"addressLine2\" : \"\",\r\n" + 
				"	\"city\" : \"Camden\",\r\n" + 
				"	\"state\" : \"NJ\",\r\n" + 
				"	\"zipcode\" : \"08105\",\r\n" + 
				"	\"country\" : \"US\"},\r\n" + 
				"{\"id\" : 3,\r\n" + 
				"	\"firstName\" : \"Molly\",\r\n" + 
				"	\"lastName\" : \"Mason\",\r\n" + 
				"	\"addressLine1\" : \"8163 2nd Dr.\",\r\n" + 
				"	\"addressLine2\" : \"Unit 432\",\r\n" + 
				"	\"city\" : \"Southfield\",\r\n" + 
				"	\"state\" : \"MI\",\r\n" + 
				"	\"zipcode\" : \"14234\",\r\n" + 
				"	\"country\" : \"US\"}]";
	
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test 
	public void whenGetAllDeliveryfor0_returnEmptyList() throws Exception {
		Mockito.when(deliveryRepo.findAll()).thenReturn(new ArrayList<>());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/delivery")
				.accept(MediaType.APPLICATION_JSON);
		
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	
		String expected = "[]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);

	}
	
	
}
