package com.ceiba.adn.serviciobano.infraestructura.controlador;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoBano;
import com.ceiba.adn.serviciobano.testdatabuilder.ComandoBanoTestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServicioBanoApplication.class)
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application-test.properties")
public class ComandoControladorBanoIT {

	private static final String URL_BASE = "http://localhost:8080/servicio-bano";
	private static final String ENDPOINT_CREAR = URL_BASE + "/crear";
	private static final String ENDPOINT_ACTUALIZAR = URL_BASE + "/actualizar";
	private static final String ENDPOINT_ELIMINAR = URL_BASE + "/eliminar/{id}";

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
		mockMvc.perform(put(ENDPOINT_ACTUALIZAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(bano))).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void cuandoPeticionActualizarBanoEsNuloEntoncesLanzarBadRequest() throws Exception {
		// arrange
		ComandoBano bano = null;

		// act - assert
		mockMvc.perform(put(ENDPOINT_ACTUALIZAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(bano))).andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void cuandoPeticionActualizarBanoIdNuloEntoncesLanzarBadRequest() throws Exception {
		// arrange
		ComandoBano bano = new ComandoBanoTestDataBuilder().build();
		bano.setId(null);

		// act - assert
		mockMvc.perform(put(ENDPOINT_ACTUALIZAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(bano))).andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void cuandoPeticionActualizarBanoIdNoExisteEntoncesLanzarBadRequest() throws Exception {
		// arrange
		ComandoBano bano = new ComandoBanoTestDataBuilder().build();

		// act - assert
		mockMvc.perform(put(ENDPOINT_ACTUALIZAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(bano))).andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/sql/controlador/actualizar/data-insert-identificador-duplicado.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/sql/controlador/actualizar/data-delete-identificador-duplicado.sql")
	public void cuandoPeticionActualizarBanoPorIdentificadorDuplicadoEntoncesLanzarConflict() throws Exception {
		// arrange
		ComandoBano bano = new ComandoBanoTestDataBuilder().build();
		bano.setId(11L);
		bano.setIdentificador("Bano 12");

		// act - assert
		mockMvc.perform(put(ENDPOINT_ACTUALIZAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(bano))).andDo(print()).andExpect(status().isConflict());
	}

	@Test
	public void cuandoPeticionCrearBanoYIdNuloCorrectaEntoncesDeberiaCrear() throws Exception {
		// arrange
		ComandoBano bano = new ComandoBanoTestDataBuilder().build();
		bano.setId(null);

		// act - assert
		mockMvc.perform(post(ENDPOINT_CREAR).contentType(MediaType.APPLICATION_JSON)
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
		mockMvc.perform(post(ENDPOINT_CREAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(bano))).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/sql/controlador/crear/data-insert-bano.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/sql/controlador/crear/data-delete-bano.sql")
	public void cuandoPeticionCrearBanoPeroIdNoNuloYDuplicadoEntoncesLanzarConflict() throws Exception {
		// arrange
		ComandoBano bano = new ComandoBanoTestDataBuilder().build();
		bano.setId(20L);

		// act - assert
		mockMvc.perform(post(ENDPOINT_CREAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(bano))).andDo(print()).andExpect(status().isConflict());
	}

	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/sql/controlador/crear/data-insert-identificador-duplicado.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/sql/controlador/crear/data-delete-identificador-duplicado.sql")
	public void cuandoPeticionCrearBanoPeroIdentificadorDuplicadoEntoncesLanzarConflict() throws Exception {
		// arrange
		ComandoBano bano = new ComandoBanoTestDataBuilder().build();
		bano.setId(25L);
		bano.setIdentificador("Bano 26");

		// act - assert
		mockMvc.perform(post(ENDPOINT_CREAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(bano))).andDo(print()).andExpect(status().isConflict());
	}

	@Test
	public void cuandoPeticionCrearBanoEsNuloEntoncesLanzarBadRequest() throws Exception {
		// arrange
		ComandoBano bano = null;

		// act - assert
		mockMvc.perform(post(ENDPOINT_CREAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(bano))).andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void cuandoPeticionCrearBanoPeroIdentificadorVacioEntoncesLanzarBadRequest() throws Exception {
		// arrange
		ComandoBano bano = new ComandoBanoTestDataBuilder().build();
		bano.setIdentificador(null);

		// act - assert
		mockMvc.perform(post(ENDPOINT_CREAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(bano))).andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void cuandoPeticionCrearBanoPeroEstadoVacioEntoncesLanzarBadRequest() throws Exception {
		// arrange
		ComandoBano bano = new ComandoBanoTestDataBuilder().build();
		bano.setEstado(null);

		// act - assert
		mockMvc.perform(post(ENDPOINT_CREAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(bano))).andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/sql/controlador/eliminar/data-insert-bano.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/sql/controlador/eliminar/data-delete-bano.sql")
	public void cuandoPeticionEliminarBanoCorrectaEntoncesDeberiaEliminar() throws Exception {
		// arrange
		Long id = 30L;

		// act - assert
		mockMvc.perform(delete(ENDPOINT_ELIMINAR, id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void cuandoPeticionEliminarBanoIdNoExisteEntoncesLanzarBadRequest() throws Exception {
		// arrange
		Long id = 101L;

		// act - assert
		mockMvc.perform(delete(ENDPOINT_ELIMINAR, id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/sql/controlador/eliminar/data-insert-estado-ocupado.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/sql/controlador/eliminar/data-delete-estado-ocupado.sql")
	public void cuandoPeticionEliminarBanoYEstadoOcupadoEntoncesLanzarPreconditionFailed() throws Exception {
		// arrange
		Long id = 35L;

		// act - assert
		mockMvc.perform(delete(ENDPOINT_ELIMINAR, id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isPreconditionFailed());
	}

}
