package com.ceiba.adn.serviciobano.infraestructura.controlador;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoBano;
import com.ceiba.adn.serviciobano.testdatabuilder.ComandoBanoTestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServicioBanoApplication.class)
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application-test.properties")
public class ComandoControladorBanoIT {

	private static final String URL_BASE = "http://localhost:8080/servicio-bano/bano";

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
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/sql/controlador/actualizar/data-insert-bano.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/sql/controlador/actualizar/data-delete-bano.sql")
	public void cuandoPeticionActualizarBanoCorrectaEntoncesDeberiaActualizar() throws Exception {
		// arrange
		ComandoBano bano = new ComandoBanoTestDataBuilder().build();
		bano.setId(10L);
		bano.setIdentificador("Bano 11");

		// act - assert
		mockMvc.perform(put(URL_BASE).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(bano))).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/sql/controlador/eliminar/data-delete-todos-banos.sql")
	public void cuandoPeticionCrearBanoYIdNuloCorrectaEntoncesDeberiaCrear() throws Exception {
		// arrange
		ComandoBano bano = new ComandoBanoTestDataBuilder().build();
		bano.setId(null);

		// act - assert
		mockMvc.perform(post(URL_BASE).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(bano))).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/sql/controlador/crear/data-delete-bano.sql")
	public void cuandoPeticionCrearBanoYIdNoNuloYNoDuplicadoCorrectaEntoncesDeberiaCrear() throws Exception {
		// arrange
		ComandoBano bano = new ComandoBanoTestDataBuilder().build();
		bano.setId(20L);
		bano.setIdentificador("Bano 20");

		// act - assert
		mockMvc.perform(post(URL_BASE).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(bano))).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/sql/controlador/eliminar/data-insert-bano.sql")
	public void cuandoPeticionEliminarBanoPorIdCorrectaEntoncesDeberiaEliminar() throws Exception {
		// arrange
		Long id = 30L;

		// act - assert
		mockMvc.perform(delete(URL_BASE + "/{id}", id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/sql/controlador/consultar/data-insert-5-banos.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/sql/controlador/consultar/data-delete-5-banos.sql")
	public void cuandoPeticionListarBanosOkEntoncesDeberiaRetornarListar() throws Exception {
		// arrange - act - assert
		mockMvc.perform(get(URL_BASE).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(jsonPath("$.respuesta.*", hasSize(5))).andExpect(status().isOk());
	}

	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/sql/controlador/crear/data-insert-bano.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/sql/controlador/crear/data-delete-bano.sql")
	public void cuandoPeticionConsultarBanoPorIdOkEntoncesDeberiaRetornarBano() throws Exception {
		// arrange
		Long id = 20L;

		// arrange - act - assert
		mockMvc.perform(
				get(URL_BASE + "/{id}", id).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
	}

}
