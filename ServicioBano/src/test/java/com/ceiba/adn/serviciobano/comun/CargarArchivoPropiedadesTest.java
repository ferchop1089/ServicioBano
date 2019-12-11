package com.ceiba.adn.serviciobano.comun;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ceiba.adn.serviciobano.comun.excepcion.ExcepcionLecturaArchivo;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorObligatorio;

public class CargarArchivoPropiedadesTest {

	private static final String MSG_PATH_OBLIGATORIO = "El path para cargar el archivo de propiedades no debe estar vacío";
	private static final String MSG_VALOR_INVALIDO = "No fue posible cargar el archivo de propiedades indicado en la ruta %s";
	private static final String MSG_LECTURA_FALLIDA = "Se presento un problema al tratar de leer el archivo de propiedades indicado en la ruta %s";

	@Mock
	private Properties prop;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void cuandoPathNuloEntoncesLanzarExcepcion() throws IOException {
		// arrange
		String path = null;

		try {
			// act
			new CargarArchivoPropiedades(path, prop);
			fail();
		} catch (ExcepcionValorObligatorio e) {
			// assert
			assertThat(e.getMessage(), equalTo(MSG_PATH_OBLIGATORIO));
			verify(prop, never()).load(any(InputStream.class));
		}
	}
	
	@Test
	public void cuandoPathEsVacioEntoncesLanzarExcepcion() throws IOException {
		// arrange
		String path = "   ";

		try {
			// act
			new CargarArchivoPropiedades(path, prop);
			fail();
		} catch (ExcepcionValorObligatorio e) {
			// assert
			assertThat(e.getMessage(), equalTo(MSG_PATH_OBLIGATORIO));
			verify(prop, never()).load(any(InputStream.class));
		}
	}

	@Test
	public void cuandoPathIncorrectoEntoncesLanzarExcepcion() throws IOException {
		// arrange
		String path = "/path/incorrecto/archivo.properties";

		try {
			// act
			new CargarArchivoPropiedades(path, prop);
			fail();
		} catch (ExcepcionValorInvalido e) {
			// assert
			assertThat(e.getMessage(), equalTo(String.format(MSG_VALOR_INVALIDO, path)));
			verify(prop, never()).load(any(InputStream.class));
		}
	}

	@Test
	public void cuandoLeerArchivoFallaEntoncesLanzarExcepcion() throws IOException {
		// arrange
		String path = "/dominio/mensajes/mensajesDefault.properties";
		doReturn(prop).when(prop).clone();
		doThrow(new IOException()).when(prop).load(any(InputStream.class));

		try {
			// act
			new CargarArchivoPropiedades(path, prop);
			fail();
		} catch (ExcepcionLecturaArchivo e) {
			// assert
			assertThat(e.getMessage(), equalTo(String.format(MSG_LECTURA_FALLIDA, path)));
			verify(prop, times(1)).load(any(InputStream.class));
		}
	}

}
