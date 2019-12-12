package com.ceiba.adn.serviciobano.infraestructura.controlador;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import org.mockito.ArgumentCaptor;
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
import org.springframework.util.MimeTypeUtils;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoBano;
import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoRespuesta;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorActualizarBano;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorConsultasBano;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorCrearBano;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorEliminarBano;
import com.ceiba.adn.serviciobano.infraestructura.controlador.ComandoControladorBanoTest.ConfiguracionDependencias;
import com.ceiba.adn.serviciobano.testdatabuilder.ComandoBanoTestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

/*
 * Se realiza un prueba unitaria al controlador sin necesidad de desplegar un WebApplicationContext completo.
 * Se simulará las dependencias del controlador permitiendo probarlo de manera aislada
 * */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ComandoControladorBano.class, ConfiguracionDependencias.class })
public class ComandoControladorBanoTest {

	private static final String URL_BASE = "http://localhost:8080/servicio-bano";
	private static final String ENDPOINT_CREAR = URL_BASE + "/crear";
	private static final String ENDPOINT_ACTUALIZAR = URL_BASE + "/actualizar";
	private static final String ENDPOINT_ELIMINAR = URL_BASE + "/eliminar/{id}";
	private static final String ENDPOINT_CONSULTAR = URL_BASE + "/consultar";

	@Autowired
	private ManejadorCrearBano crearBano;

	@Autowired
	private ManejadorActualizarBano actualizarBano;

	@Autowired
	private ManejadorEliminarBano eliminarBano;
	
	@Autowired
	private ManejadorConsultasBano consultasBano;

	private ComandoControladorBano controlador;

	@Autowired
	private ObjectMapper objectMapperTest;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		controlador = new ComandoControladorBano(crearBano, actualizarBano, eliminarBano, consultasBano);
		mockMvc = MockMvcBuilders.standaloneSetup(controlador).build();
	}

	@After
	public void tearDown() {
		mockMvc = null;
	}

	@Test
	public void cuandoPeticionActualizarBanoCorrectaEntoncesDeberiaActualizar() throws Exception {
		// arrange
		ComandoBano bano = new ComandoBanoTestDataBuilder().build();
		doNothing().when(actualizarBano).ejecutar(bano);

		// act - assert
		mockMvc.perform(put(ENDPOINT_ACTUALIZAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(bano))).andDo(print()).andExpect(status().isOk());

		// assert
		verify(actualizarBano, times(1)).ejecutar(any(ComandoBano.class));

		ArgumentCaptor<ComandoBano> objCaptor = ArgumentCaptor.forClass(ComandoBano.class);
		verify(actualizarBano).ejecutar(objCaptor.capture());
		assertThat(objCaptor.getValue().getId(), is(bano.getId()));
	}

	@Test
	public void cuandoPeticionActualizarBanoYAcceptHeaderDiferenteAJsonEntoncesDeberiaLanzarNotAcceptable()
			throws Exception {
		// arrange
		String cabeceraAcceptInvalida = MimeTypeUtils.APPLICATION_XML_VALUE;
		doNothing().when(actualizarBano).ejecutar(any(ComandoBano.class));

		// act - assert
		mockMvc.perform(put(ENDPOINT_ACTUALIZAR).accept(cabeceraAcceptInvalida)).andExpect(status().isNotAcceptable());
	}

	@Test
	public void cuandoPeticionActualizarBanoYContentTypeHeaderDiferenteAJsonEntoncesDeberiaLanzarNotAcceptable()
			throws Exception {
		// arrange
		String cabeceraContentTypeInvalida = MimeTypeUtils.APPLICATION_XML_VALUE;
		doNothing().when(actualizarBano).ejecutar(any(ComandoBano.class));

		// act - assert
		mockMvc.perform(put(ENDPOINT_ACTUALIZAR).contentType((cabeceraContentTypeInvalida)))
				.andExpect(status().isUnsupportedMediaType());
	}

	@Test
	public void cuandoPeticionActualizarBanoFallaEntoncesLanzarInternalServerError() throws Exception {
		// arrange
		ComandoBano bano = new ComandoBanoTestDataBuilder().build();
		doThrow(NullPointerException.class).when(actualizarBano).ejecutar(any(ComandoBano.class));

		// act - assert
		mockMvc.perform(put(ENDPOINT_ACTUALIZAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(bano))).andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void cuandoPeticionCrearBanoYIdNuloCorrectaEntoncesDeberiaCrear() throws Exception {
		// arrange
		ComandoBano bano = new ComandoBanoTestDataBuilder().build();
		bano.setId(null);
		ComandoRespuesta<Long> id = new ComandoRespuesta<Long>(2L);
		doReturn(id).when(crearBano).ejecutar(bano);

		// act - assert
		mockMvc.perform(post(ENDPOINT_CREAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(bano))).andDo(print()).andExpect(status().isOk());

		// assert
		verify(crearBano, times(1)).ejecutar(any(ComandoBano.class));

		ArgumentCaptor<ComandoBano> objCaptor = ArgumentCaptor.forClass(ComandoBano.class);
		verify(crearBano).ejecutar(objCaptor.capture());
		assertThat(objCaptor.getValue().getId(), is(bano.getId()));
	}

	@Test
	public void cuandoPeticionCrearBanoYIdNoNuloCorrectaEntoncesDeberiaCrear() throws Exception {
		// arrange
		ComandoBano bano = new ComandoBanoTestDataBuilder().build();
		ComandoRespuesta<Long> id = new ComandoRespuesta<Long>(bano.getId());
		doReturn(id).when(crearBano).ejecutar(bano);

		// act - assert
		mockMvc.perform(post(ENDPOINT_CREAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(bano))).andDo(print()).andExpect(status().isOk());

		// assert
		verify(crearBano, times(1)).ejecutar(any(ComandoBano.class));

		ArgumentCaptor<ComandoBano> objCaptor = ArgumentCaptor.forClass(ComandoBano.class);
		verify(crearBano).ejecutar(objCaptor.capture());
		assertThat(objCaptor.getValue().getId(), is(bano.getId()));
	}

	@Test
	public void cuandoPeticionCrearBanoYAcceptHeaderDiferenteAJsonEntoncesDeberiaLanzarNotAcceptable()
			throws Exception {
		// arrange
		String cabeceraAcceptInvalida = MimeTypeUtils.APPLICATION_XML_VALUE;
		doReturn(null).when(crearBano).ejecutar(any(ComandoBano.class));

		// act - assert
		mockMvc.perform(post(ENDPOINT_CREAR).accept(cabeceraAcceptInvalida)).andExpect(status().isNotAcceptable());
	}

	@Test
	public void cuandoPeticionCrearBanoYContentTypeHeaderDiferenteAJsonEntoncesDeberiaLanzarUnsupportedMediaType()
			throws Exception {
		// arrange
		String cabeceraContentTypeInvalida = MimeTypeUtils.APPLICATION_XML_VALUE;
		doReturn(null).when(crearBano).ejecutar(any(ComandoBano.class));

		// act - assert
		mockMvc.perform(post(ENDPOINT_CREAR).contentType((cabeceraContentTypeInvalida)))
				.andExpect(status().isUnsupportedMediaType());
	}

	@Test
	public void cuandoPeticionCrearBanoFallaEntoncesLanzarInternalServerError() throws Exception {
		// arrange
		ComandoBano bano = new ComandoBanoTestDataBuilder().build();
		doThrow(NullPointerException.class).when(crearBano).ejecutar(any(ComandoBano.class));

		// act - assert
		mockMvc.perform(post(ENDPOINT_CREAR).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapperTest.writeValueAsString(bano))).andDo(print())
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void cuandoPeticionEliminarBanoCorrectaEntoncesDeberiaEliminar() throws Exception {
		// arrange
		Long id = 1L;
		doNothing().when(eliminarBano).ejecutar(id);

		// act - assert
		mockMvc.perform(delete(ENDPOINT_ELIMINAR, id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

		// assert
		verify(eliminarBano, times(1)).ejecutar(anyLong());

		ArgumentCaptor<Long> objCaptor = ArgumentCaptor.forClass(Long.class);
		verify(eliminarBano).ejecutar(objCaptor.capture());
		assertThat(objCaptor.getValue(), is(id));
	}

	@Test
	public void cuandoPeticionEliminarBanoYAcceptHeaderDiferenteAJsonEntoncesDeberiaLanzarNotAcceptable()
			throws Exception {
		// arrange
		Long id = 1L;
		String cabeceraAcceptInvalida = MimeTypeUtils.APPLICATION_XML_VALUE;
		doNothing().when(eliminarBano).ejecutar(anyLong());

		// act - assert
		mockMvc.perform(delete(ENDPOINT_ELIMINAR, id).accept(cabeceraAcceptInvalida))
				.andExpect(status().isNotAcceptable());
	}

	@Test
	public void cuandoPeticionEliminarBanoFallaEntoncesLanzarInternalServerError() throws Exception {
		// arrange
		Long id = 1L;
		doThrow(NullPointerException.class).when(eliminarBano).ejecutar(anyLong());

		// act - assert
		mockMvc.perform(delete(ENDPOINT_ELIMINAR, id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isInternalServerError());
	}
	
	@Test
	public void cuandoPeticionConsultarBanosOkEntoncesDeberiaRetornarConsulta() throws Exception {
		// arrange - act - assert
		mockMvc.perform(
				get(ENDPOINT_CONSULTAR).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
	}

	@TestConfiguration
	protected static class ConfiguracionDependencias {

		public ConfiguracionDependencias() {
		}

		@Bean
		@Scope("prototype")
		public ManejadorCrearBano crearManejadorCrearBano() {
			return mock(ManejadorCrearBano.class);
		}

		@Bean
		@Scope("prototype")
		public ManejadorActualizarBano crearManejadorActualizarBano() {
			return mock(ManejadorActualizarBano.class);
		}

		@Bean
		@Scope("prototype")
		public ManejadorEliminarBano crearManejadorEliminarBano() {
			return mock(ManejadorEliminarBano.class);
		}
		
		@Bean
		@Scope("prototype")
		public ManejadorConsultasBano crearManejadorConsultasBano() {
			return mock(ManejadorConsultasBano.class);
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