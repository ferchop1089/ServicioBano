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
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/sql/controlador/cuenta/data-insert-cuenta-ok.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/sql/controlador/cuenta/data-delete-cuenta-ok.sql")
	public void cuandoCuentaOkEntoncesDeberiaCrearla() throws Exception {
		// arrange
		ComandoCuenta cuenta = new ComandoCuentaTestDataBuilder().withId(80L).withIdBano(70L).build();

		// act - assert
		mockMvc.perform(post(URL_BASE).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(cuenta))).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/sql/controlador/cuenta/data-insert-cuenta-id-nulo-ok.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/sql/controlador/cuenta/data-delete-cuenta-id-nulo-ok.sql")
	public void cuandoCuentaConIdNuloOkEntoncesDeberiaCrearla() throws Exception {
		// arrange
		ComandoCuenta cuenta = new ComandoCuentaTestDataBuilder().withId(null).withIdBano(69L).build();

		// act - assert
		mockMvc.perform(post(URL_BASE).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(cuenta))).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/sql/controlador/cuenta/data-insert-actualizar-cuenta-ok.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/sql/controlador/cuenta/data-delete-actualizar-cuenta-ok.sql")
	public void cuandoCuentaOkEntoncesDeberiaActualizarla() throws Exception {
		// arrange
		ComandoCuenta cuenta = new ComandoCuentaTestDataBuilder().withId(79L).withIdBano(68L)
				.withEstado(EstadoCuenta.CERRADA.getEstado()).build();

		// act - assert
		mockMvc.perform(put(URL_BASE).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(cuenta))).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/sql/controlador/cuenta/data-insert-consultar-cuenta-id-bano-ok.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/sql/controlador/cuenta/data-delete-consultar-cuenta-id-bano-ok.sql")
	public void cuandoPeticionConsultarCuentaPorIdBanoOkEntoncesDeberiaRetornarCuenta() throws Exception {
		// arrange
		Long idBano = 67L;

		// arrange - act - assert
		mockMvc.perform(get(URL_BASE + "/{idBano}", idBano).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/sql/controlador/cuenta/data-insert-cobrar-ok.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/sql/controlador/cuenta/data-delete-cobrar-ok.sql")
	public void cuandoCobrarOkEntoncesDeberiaCobrar() throws Exception {
		// arrange
		Long id = 77L;

		// act - assert
		mockMvc.perform(get(URL_BASE + "/cobrar/{id}", id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/sql/controlador/cuenta/data-insert-pagar-ok.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/sql/controlador/cuenta/data-delete-pagar-ok.sql")
	public void cuandoPagarOkEntoncesDeberiaPagar() throws Exception {
		// arrange
		ComandoCuenta cuenta = new ComandoCuentaTestDataBuilder().withId(76L).withIdBano(65L)
				.withEstado(EstadoCuenta.CERRADA.getEstado()).build();

		// act - assert
		mockMvc.perform(put(URL_BASE + "/pagar").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(cuenta))).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/sql/controlador/cuenta/data-insert-cuenta-fecha-ingreso-nulo-ok.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/sql/controlador/cuenta/data-delete-cuenta-fecha-ingreso-nulo-ok.sql")
	public void cuandoCuentaConFechaIngresoNuloOkEntoncesDeberiaCrearla() throws Exception {
		// arrange
		ComandoCuenta cuenta = new ComandoCuentaTestDataBuilder().withId(75L).withFechaIngreso(null).withIdBano(64L)
				.build();

		// act - assert
		mockMvc.perform(post(URL_BASE).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(cuenta))).andDo(print()).andExpect(status().isOk());
	}

}
