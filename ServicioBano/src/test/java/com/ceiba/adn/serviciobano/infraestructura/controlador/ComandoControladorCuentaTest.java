package com.ceiba.adn.serviciobano.infraestructura.controlador;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoCuenta;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorActualizarCuenta;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorCobrarCuenta;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorCrearCuenta;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorPagarCuenta;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionRestriccion;
import com.ceiba.adn.serviciobano.infraestructura.controlador.ComandoControladorCuentaTest.ConfiguracionDependencias;
import com.ceiba.adn.serviciobano.testdatabuilder.ComandoCuentaTestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ComandoControladorCuenta.class, ConfiguracionDependencias.class })
public class ComandoControladorCuentaTest {

	private static final String URL_BASE = "http://localhost:8080/servicio-bano/cuenta";
	private static final String ENDPOINT_CREAR = URL_BASE + "/crear";
	private static final String ENDPOINT_ACTUALIZAR = URL_BASE + "/actualizar";
	private static final String ENDPOINT_COBRAR = URL_BASE + "/cobrar/{id}";
	private static final String ENDPOINT_PAGAR = URL_BASE + "/pagar";

	@Autowired
	private ManejadorCrearCuenta crearCuenta;

	@Autowired
	private ManejadorActualizarCuenta actualizarCuenta;

	@Autowired
	private ManejadorCobrarCuenta cobrarCuenta;

	@Autowired
	private ManejadorPagarCuenta pagarCuenta;

	private ComandoControladorCuenta controlador;

	@Autowired
	private ObjectMapper objectMapperTest;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		controlador = new ComandoControladorCuenta(crearCuenta, actualizarCuenta, cobrarCuenta, pagarCuenta);
		mockMvc = MockMvcBuilders.standaloneSetup(controlador).build();
	}

	@After
	public void tearDown() {
		mockMvc = null;
	}

	@Test
	public void cuandoPeticionCrearCuentaFallaEntoncesLanzarInternalServerError() throws Exception {
		// arrange
		ComandoCuenta cuenta = new ComandoCuentaTestDataBuilder().withId(null).build();
		doThrow(NullPointerException.class).when(crearCuenta).ejecutar(any(ComandoCuenta.class));

		// act - assert
		mockMvc.perform(post(ENDPOINT_CREAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(cuenta))).andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void cuandoPeticionActualizarCuentaFallaEntoncesLanzarInternalServerError() throws Exception {
		// arrange
		ComandoCuenta cuenta = new ComandoCuentaTestDataBuilder().withId(null).build();
		doThrow(NullPointerException.class).when(actualizarCuenta).ejecutar(any(ComandoCuenta.class));

		// act - assert
		mockMvc.perform(put(ENDPOINT_ACTUALIZAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(cuenta))).andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void cuandoPeticionCobrarCuentaFallaEntoncesLanzarInternalServerError() throws Exception {
		// arrange
		Long id = 101L;
		doThrow(NullPointerException.class).when(cobrarCuenta).ejecutar(anyLong());

		// act - assert
		mockMvc.perform(
				get(ENDPOINT_COBRAR, id).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void cuandoPeticionPagarCuentaFallaEntoncesLanzarInternalServerError() throws Exception {
		// arrange
		ComandoCuenta cuenta = new ComandoCuentaTestDataBuilder().withId(null).build();
		doThrow(NullPointerException.class).when(pagarCuenta).ejecutar(any(ComandoCuenta.class));

		// act - assert
		mockMvc.perform(put(ENDPOINT_PAGAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(cuenta))).andDo(print())
				.andExpect(status().isInternalServerError());
	}
	
	@Test
	public void cuandoPeticionPagarCuentaFallaEntoncesLanzarPreconditionFailed() throws Exception {
		// arrange
		ComandoCuenta cuenta = new ComandoCuentaTestDataBuilder().withId(null).build();
		doThrow(ExcepcionRestriccion.class).when(pagarCuenta).ejecutar(any(ComandoCuenta.class));

		// act - assert
		mockMvc.perform(put(ENDPOINT_PAGAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(cuenta))).andDo(print())
				.andExpect(status().isPreconditionFailed());
	}

	@TestConfiguration
	protected static class ConfiguracionDependencias {

		public ConfiguracionDependencias() {
		}

		@Bean
		@Scope("prototype")
		public ManejadorCrearCuenta crearManejadorCrearCuenta() {
			return mock(ManejadorCrearCuenta.class);
		}

		@Bean
		@Scope("prototype")
		public ManejadorActualizarCuenta crearManejadorActualizarCuenta() {
			return mock(ManejadorActualizarCuenta.class);
		}

		@Bean
		@Scope("prototype")
		public ManejadorCobrarCuenta crearManejadorCobrarCuenta() {
			return mock(ManejadorCobrarCuenta.class);
		}

		@Bean
		@Scope("prototype")
		public ManejadorPagarCuenta crearManejadorPagarCuenta() {
			return mock(ManejadorPagarCuenta.class);
		}

		public static final String FORMATO_LOCAL_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
		public static final String FORMATO_LOCAL_DATE = "yyyy-MM-dd";

		@Bean
		public ObjectMapper crearObjectMapper() {
			ObjectMapper objectMapper;

			JavaTimeModule module = new JavaTimeModule();
			LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(
					DateTimeFormatter.ofPattern(FORMATO_LOCAL_DATE_TIME));
			module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);

			objectMapper = Jackson2ObjectMapperBuilder.json().modules(module)
					.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).build();

			return objectMapper;
		}
	}

}
