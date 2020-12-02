package com.tacoloco.deliveryservice.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
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
	
	/******************* HTTP GET /delivery test cases ***************/
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
	
	//when there are no deliveries, return an empty list
	@Test 
	public void whenGetAllDeliveryfor0_returnEmptyList() throws Exception {
		Mockito.when(deliveryRepo.findAll()).thenReturn(new ArrayList<>());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/delivery")
				.accept(MediaType.APPLICATION_JSON);
		
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	
		String expected = "[]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);

	}
	
	/******************* HTTP GET /delivery/{id} test cases ***************/
	//when a delivery is found by id, return delivery
	@Test
	public void whenGetDeliveryById_returnDelivery() throws Exception{
		Delivery d3 = new Delivery(3L,"Molly", "Mason", "8163 2nd Dr.", "Unit 432", "Southfield", "MI", "14234", "US");
		
		Mockito.when(deliveryRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(d3));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/delivery/3")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		String expected = "{\"id\" : 3,\r\n" + 
				"	\"firstName\" : \"Molly\",\r\n" + 
				"	\"lastName\" : \"Mason\",\r\n" + 
				"	\"addressLine1\" : \"8163 2nd Dr.\",\r\n" + 
				"	\"addressLine2\" : \"Unit 432\",\r\n" + 
				"	\"city\" : \"Southfield\",\r\n" + 
				"	\"state\" : \"MI\",\r\n" + 
				"	\"zipcode\" : \"14234\",\r\n" + 
				"	\"country\" : \"US\"}";
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, response.getContentAsString(), true);
		
	}
	
	
	/******************* HTTP POST /delivery test cases ***************/
	//when a new delivery is created, return the URI to the resource
	@Test
	public void whenCreateDelivery_returnCreatedResponse() throws Exception {
		Delivery mockDelivery = new Delivery(1L, "John", "Doe", "21 Rosebank Alley", "Apt 23", "Asheville", "NC", "14234", "US");
		Mockito.when(deliveryRepo.save(Mockito.any(Delivery.class))).thenReturn(mockDelivery);
		
		String jsonStr = "{\"id\" : 1,\r\n" + 
				"	\"firstName\" : \"John\",\r\n" + 
				"	\"lastName\" : \"Doe\",\r\n" + 
				"	\"addressLine1\" : \"21 Rosebank Alley\",\r\n" + 
				"	\"addressLine2\" : \"Apt 23\",\r\n" + 
				"	\"city\" : \"Asheville\",\r\n" + 
				"	\"state\" : \"NC\",\r\n" + 
				"	\"zipcode\" : \"14234\",\r\n" + 
				"	\"country\" : \"US\"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/delivery")
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonStr)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response= result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals("http://localhost/delivery/1", response.getHeader(HttpHeaders.LOCATION));
	}	

	/******************* HTTP PUT /delivery/{id} test cases ***************/
	//when a delivery is successfully updated, return the URI to the resource
	@Test
	public void whenUpdateDelivery_returnCreatedResponse() throws Exception{
		Delivery mockDelivery = new Delivery(1L, "John", "Doe", "21 Rosebank Alley", "Apt 23", "Asheville", "NC", "14234", "US");
		
		Mockito.when(deliveryRepo.existsById(Mockito.any(Long.class))).thenReturn(true);
		Mockito.when(deliveryRepo.save(Mockito.any(Delivery.class))).thenReturn(mockDelivery);
		
		String jsonStr = "{\"id\" : 1,\r\n" + 
				"	\"firstName\" : \"John\",\r\n" + 
				"	\"lastName\" : \"Doe\",\r\n" + 
				"	\"addressLine1\" : \"21 Rosebank Alley\",\r\n" + 
				"	\"addressLine2\" : \"Apt 23\",\r\n" + 
				"	\"city\" : \"Asheville\",\r\n" + 
				"	\"state\" : \"NC\",\r\n" + 
				"	\"zipcode\" : \"14234\",\r\n" + 
				"	\"country\" : \"US\"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/delivery/1")
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonStr)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response= result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals("http://localhost/delivery/1", response.getHeader(HttpHeaders.LOCATION));
	}
	
	// when a delivery doesn't exist for update, return http conflict response
	@Test
	public void whenUpdateDelivery_returnConflictResponse() throws Exception{
		Delivery mockDelivery = new Delivery(1L, "John", "Doe", "21 Rosebank Alley", "Apt 23", "Asheville", "NC", "14234", "US");
		
		Mockito.when(deliveryRepo.existsById(Mockito.any(Long.class))).thenReturn(false);
		Mockito.when(deliveryRepo.save(Mockito.any(Delivery.class))).thenReturn(mockDelivery);
		
		String jsonStr = "{\"id\" : 1,\r\n" + 
				"	\"firstName\" : \"John\",\r\n" + 
				"	\"lastName\" : \"Doe\",\r\n" + 
				"	\"addressLine1\" : \"21 Rosebank Alley\",\r\n" + 
				"	\"addressLine2\" : \"Apt 23\",\r\n" + 
				"	\"city\" : \"Asheville\",\r\n" + 
				"	\"state\" : \"NC\",\r\n" + 
				"	\"zipcode\" : \"14234\",\r\n" + 
				"	\"country\" : \"US\"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/delivery/1")
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonStr)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response= result.getResponse();
		assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
	}
	
	/******************* HTTP DELETE /delivery/{id} test cases ***************/
	// when a delivery is deleted, return http no content response
	@Test
	public void whenDeleteDeliverySuccess_returnNoContent() throws Exception{		
		Mockito.when(deliveryRepo.existsById(Mockito.any(Long.class))).thenReturn(true);
		Mockito.doNothing().when(deliveryRepo).deleteById(Mockito.any(Long.class));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/delivery/1")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
	}
	
	// when a delivery does not exist to be deleted, return http conflict
	@Test
	public void whenDeliveryDoesNotExist_returnConflict() throws Exception{		
		Mockito.when(deliveryRepo.existsById(Mockito.any(Long.class))).thenReturn(false);
		Mockito.doNothing().when(deliveryRepo).deleteById(Mockito.any(Long.class));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/delivery/1")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
	}
	
}
