package com.rufino.server;

import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;
import com.rufino.server.model.Order;
import com.rufino.server.services.OrderService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc

class ServerApplicationTests {

	@Autowired
	private OrderService orderService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void clearTable() {
		jdbcTemplate.update("DELETE FROM ORDERS");
	}

	// ---------------------TEST HOME PAGE-------------------
	@Test
	void testHomeHttp() throws Exception {
		MvcResult result = mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andReturn();

		assertEquals("Hello World from Spring Boot", result.getResponse().getContentAsString());

	}

	// ----------------- TESTING CREATED ORDER
	@Test
	public void createNewOrder() {
		Order order = new Order();
		order.setIdClient("abc123");
		order.setIdParcel("abc456");
		order.setTotalValue(0.50f);
		order.setOrderAddress("Rua de cima");
		long countBeforeInsert = jdbcTemplate.queryForObject("select count(*) from orders", Long.class);
		assertEquals(0, countBeforeInsert);
		orderService.addOrder(order);
		long countAfterInsert = jdbcTemplate.queryForObject("select count(*) from orders", Long.class);
		assertEquals(1, countAfterInsert);
	}
	@Test
	void addOrderTestHttp() throws Exception {

		JSONObject my_obj = new JSONObject();
		my_obj.put("idClient", 1111);
		my_obj.put("idParcel", 2222);
		my_obj.put("totalValue", 15.99);
		my_obj.put("orderAddress", "Rua do meio");

		MvcResult result = mockMvc
				.perform(post("/api/v1/order").contentType(MediaType.APPLICATION_JSON).content(my_obj.toString()))
				.andExpect(status().isOk()).andReturn();

		assertEquals("order added successfully", result.getResponse().getContentAsString());

	}

	@Test
	void addOrderTestHttp_ExpectedError() throws Exception {

		JSONObject my_obj = new JSONObject();
		my_obj.put("idCliente", 1111);
		MvcResult result = mockMvc
				.perform(post("/api/v1/order").contentType(MediaType.APPLICATION_JSON).content(my_obj.toString()))
				.andExpect(status().isOk()).andReturn();

		assertEquals("error operation", result.getResponse().getContentAsString());

	}

	
	// -----------------TEST DELETING ORDER
	@Test
	public void deleteOrderDAO() {
		Order order = new Order();
		order.setIdClient("abc123");
		order.setIdParcel("abc456");
		order.setTotalValue(0.50f);
		order.setOrderAddress("Rua de cima");
		long countBeforeInsert = jdbcTemplate.queryForObject("select count(*) from orders", Long.class);
		assertEquals(0, countBeforeInsert);
		orderService.addOrder(order);
		long countAfterInsert = jdbcTemplate.queryForObject("select count(*) from orders", Long.class);
		assertEquals(1, countAfterInsert);

		UUID id = order.getIdOrder();
		orderService.delete(id);
		long countAfterDelete = jdbcTemplate.queryForObject("select count(*) from orders", Long.class);
		assertEquals(0, countAfterDelete);
	}

	@Test
	public void deleteOrderHttp() throws Exception {
		Order order = new Order();
		order.setIdClient("abc123");
		order.setIdParcel("abc456");
		order.setTotalValue(0.50f);
		order.setOrderAddress("Rua de cima");
		long countBeforeInsert = jdbcTemplate.queryForObject("select count(*) from orders", Long.class);
		assertEquals(0, countBeforeInsert);
		orderService.addOrder(order);
		long countAfterInsert = jdbcTemplate.queryForObject("select count(*) from orders", Long.class);
		assertEquals(1, countAfterInsert);

		MvcResult result = mockMvc.perform(delete(String.format("/api/v1/order/%s", order.getIdOrder())))
				.andExpect(status().isOk()).andReturn();

		assertEquals("successfully operation", result.getResponse().getContentAsString());
		long countAfterDelete = jdbcTemplate.queryForObject("select count(*) from orders", Long.class);
		assertEquals(0, countAfterDelete);
	}

	@Test
	public void deleteOrderHttp_ErrorExpected() throws Exception {
		Order order = new Order();
		order.setIdClient("abc123");
		order.setIdParcel("abc456");
		order.setTotalValue(0.50f);
		order.setOrderAddress("Rua de cima");
		long countBeforeInsert = jdbcTemplate.queryForObject("select count(*) from orders", Long.class);
		assertEquals(0, countBeforeInsert);
		orderService.addOrder(order);
		long countAfterInsert = jdbcTemplate.queryForObject("select count(*) from orders", Long.class);
		assertEquals(1, countAfterInsert);

		MvcResult result = mockMvc.perform(delete(String.format("/api/v1/order/1"))).andExpect(status().isOk())
				.andReturn();

		assertEquals("error operation", result.getResponse().getContentAsString());
		long countAfterDelete = jdbcTemplate.queryForObject("select count(*) from orders", Long.class);
		assertEquals(1, countAfterDelete);
	}

}
