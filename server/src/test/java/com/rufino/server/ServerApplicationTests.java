package com.rufino.server;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import com.rufino.server.Database.DatabaseConnection;
import com.rufino.server.model.Order;
import com.rufino.server.services.OrderService;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${server.port}")
	private int serverPort;
	private static Connection conn;

	@Autowired
	private OrderService orderService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MockMvc mockMvc;

	// TEST CONNECTION ON DATABASE
	@BeforeAll
	public static void openConnection() throws SQLException {
		conn = DatabaseConnection.getInstance().getConnection();
		assertNotNull(conn);
	}

	@AfterAll
	public static void closeConnection() throws SQLException {
		assertNotNull(conn);
		conn.close();
		assertEquals(true, conn.isClosed());
	}

	@BeforeEach
	@Test
	void clear() {
		jdbcTemplate.update("DROP TABLE IF EXISTS orders");
		jdbcTemplate.update(
				"CREATE TABLE IF NOT EXISTS orders (id_order VARCHAR(36) NOT NULL PRIMARY KEY,id_client INT NOT NULL,id_parcel INT NOT NULL,total_value FLOAT NOT NULL,order_address VARCHAR(256) NOT NULL);");
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
	void addOrderTest() throws Exception {

		JSONObject my_obj = new JSONObject();
		my_obj.put("idCliente", 1111);
		my_obj.put("idParcel", 2222);
		my_obj.put("totalValue", 15.99);
		my_obj.put("orderAddress", "Rua do meio");

		MvcResult result = mockMvc
				.perform(post("/api/v1/order").contentType(MediaType.APPLICATION_JSON).content(my_obj.toString()))
				.andExpect(status().isOk()).andReturn();

		assertEquals("order added successfully", result.getResponse().getContentAsString());

	}

	@Test
	public void createNewOrder() {
		Order order = new Order();
		order.setIdClient(1111);
		order.setIdParcel(3333);
		order.setTotalValue(0.50f);
		order.setOrderAddress("Rua de cima");
		long countBeforeInsert = jdbcTemplate.queryForObject("select count(*) from orders", Long.class);
		assertEquals(0, countBeforeInsert);
		orderService.addOrder(order);
		long countAfterInsert = jdbcTemplate.queryForObject("select count(*) from orders", Long.class);
		assertEquals(1, countAfterInsert);
	}

	// -----------------TEST DELETING ORDER
	@Test
	public void deleteOrderDAO() {
		Order order = new Order();
		order.setIdClient(1111);
		order.setIdParcel(3333);
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
		order.setIdClient(1111);
		order.setIdParcel(3333);
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
		order.setIdClient(1111);
		order.setIdParcel(3333);
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
