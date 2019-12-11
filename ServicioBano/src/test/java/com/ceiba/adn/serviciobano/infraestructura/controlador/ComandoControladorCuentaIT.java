package com.ceiba.adn.serviciobano.infraestructura.controlador;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ceiba.adn.serviciobano.ServicioBanoApplication;
import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoCuenta;
import com.ceiba.adn.serviciobano.dominio.modelo.EstadoCuenta;
import com.ceiba.adn.serviciobano.testdatabuilder.ComandoCuentaTestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServicioBanoApplication.class)
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application-test.properties")
public class ComandoControladorCuentaIT {

	private static final String URL_BASE = "http://localhost:8080/servicio-bano/cuenta";
	private static final String ENDPOINT_CREAR = URL_BASE + "/crear";
	private static final String ENDPOINT_ACTUALIZAR = URL_BASE + "/actualizar";
	private static final String ENDPOINT_COBRAR = URL_BASE + "/cobrar/{id}";
	private static final String ENDPOINT_PAGAR = URL_BASE + "/pagar";

	@Autowired
	private ObjectMapper objectMapperTest;

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void cuandoCrearYCuentaEsNuloEntoncesLanzarExcepcion() throws Exception {
		// arrange
		ComandoCuenta cuenta = null;

		// act - assert
		mockMvc.perform(post(ENDPOINT_CREAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(cuenta))).andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	public void cuandoCrearYIdBanoNuloEntoncesLanzarExcepcion() throws Exception {
		// arrange
		ComandoCuenta cuenta = new ComandoCuentaTestDataBuilder().withId(null).build();

		// act - assert
		mockMvc.perform(post(ENDPOINT_CREAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(cuenta))).andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/sql/controlador/crear/data-insert-cuenta-duplicada-por-id.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/sql/controlador/crear/data-delete-cuenta-duplicada-por-id.sql")
	public void cuandoCrearYBuscarCuentaPorIdDuplicadoEntoncesLanzarExcepcion() throws Exception {
		// arrange
		ComandoCuenta cuenta = new ComandoCuentaTestDataBuilder().withId(20L).build();

		// act - assert
		mockMvc.perform(post(ENDPOINT_CREAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(cuenta))).andDo(print()).andExpect(status().isConflict());
	}

	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/sql/controlador/crear/data-insert-bano.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/sql/controlador/crear/data-delete-bano.sql")
	public void cuandoCuentaOkEntoncesDeberiaCrearla() throws Exception {
		// arrange
		ComandoCuenta cuenta = new ComandoCuentaTestDataBuilder().withId(20L).withIdBano(20L).build();

		// act - assert
		mockMvc.perform(post(ENDPOINT_CREAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(cuenta))).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void cuandoActualizarYCuentaEsNuloEntoncesLanzarExcepcion() throws Exception {
		// arrange
		ComandoCuenta cuenta = null;

		// act - assert
		mockMvc.perform(put(ENDPOINT_ACTUALIZAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(cuenta))).andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	public void cuandoActualizarYIdCuentaNuloEntoncesLanzarExcepcion() throws Exception {
		// arrange
		ComandoCuenta cuenta = new ComandoCuentaTestDataBuilder().withId(null).build();

		// act - assert
		mockMvc.perform(put(ENDPOINT_ACTUALIZAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(cuenta))).andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/sql/controlador/crear/data-insert-cuenta-bano.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/sql/controlador/crear/data-delete-cuenta-bano.sql")
	public void cuandoCuentaOkEntoncesDeberiaActualizarla() throws Exception {
		// arrange
		ComandoCuenta cuenta = new ComandoCuentaTestDataBuilder().withId(25L).withIdBano(20L)
				.withEstado(EstadoCuenta.CERRADA.getEstado()).build();

		// act - assert
		mockMvc.perform(put(ENDPOINT_ACTUALIZAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(cuenta))).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/sql/controlador/cobrar/data-insert-cuenta-cobrar.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/sql/controlador/cobrar/data-delete-cuenta-cobrar.sql")
	public void cuandoCobrarOkEntoncesDeberiaCobrar() throws Exception {
		// arrange
		Long id = 40L;

		// act - assert
		mockMvc.perform(
				get(ENDPOINT_COBRAR, id).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void cuandoCobrarYIdNoExisteEntoncesLanzarExcepcion() throws Exception {
		// arrange
		Long id = 101L;

		// act - assert
		mockMvc.perform(
				get(ENDPOINT_COBRAR, id).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void cuandoPagarYCuentaEsNulaEntoncesLanzarExcepcion() throws Exception {
		// arrange
		ComandoCuenta cuenta = null;

		// act - assert
		mockMvc.perform(put(ENDPOINT_PAGAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(cuenta))).andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/sql/controlador/crear/data-insert-cuenta-bano.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/sql/controlador/crear/data-delete-cuenta-bano.sql")
	public void cuandoPagarOkEntoncesDeberiaPagar() throws Exception {
		// arrange
		ComandoCuenta cuenta = new ComandoCuentaTestDataBuilder().withId(25L).withIdBano(20L)
				.withEstado(EstadoCuenta.CERRADA.getEstado()).build();

		// act - assert
		mockMvc.perform(put(ENDPOINT_PAGAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(cuenta))).andDo(print()).andExpect(status().isOk());
	}

}
