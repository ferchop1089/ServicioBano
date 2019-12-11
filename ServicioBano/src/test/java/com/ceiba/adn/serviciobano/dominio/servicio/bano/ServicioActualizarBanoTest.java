package com.ceiba.adn.serviciobano.dominio.servicio.bano;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ceiba.adn.serviciobano.comun.FabricaPropiedades;
import com.ceiba.adn.serviciobano.comun.Propiedades;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionSinDatos;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorObligatorio;
import com.ceiba.adn.serviciobano.dominio.modelo.Bano;
import com.ceiba.adn.serviciobano.dominio.modelo.EstadoBano;
import com.ceiba.adn.serviciobano.dominio.puerto.repositorio.RepositorioBano;
import com.ceiba.adn.serviciobano.testdatabuilder.BanoTestDataBuilder;

public class ServicioActualizarBanoTest {

	private static final String MSG_BANO_OBLIGATORIO = "msg.err.servicio.actualizar.bano.obligatorio";
	private static final String MSG_ID_OBLIGATORIO = "msg.err.servicio.actualizar.bano.id.obligatorio";
	private static final String MSG_BANO_POR_ID_NO_EXISTE = "msg.err.servicio.actualizar.bano.no.encontrado";
	private static final String MSG_BANO_POR_IDENTIFICADOR_DUPLICADO = "msg.err.servicio.actualizar.bano.identificador.duplicado";

	private ServicioActualizarBano servicio;

	private Propiedades prop;

	@Mock
	private RepositorioBano repositorio;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		prop = FabricaPropiedades.propiedadMensajesPorDefecto();
		servicio = new ServicioActualizarBano(repositorio, prop);
	}

	@Test
	public void cuandoBanoEsNuloEntoncesLanzarExcepcion() {
		// arrange
		Bano bano = null;

		try {
			// act
			servicio.actualizarBano(bano);
			fail();
		} catch (ExcepcionValorObligatorio e) {
			// assert
			assertThat(e.getMessage(), equalTo(prop.getPropiedad(MSG_BANO_OBLIGATORIO)));

			verify(repositorio, never()).existePorId(anyLong());
			verify(repositorio, never()).buscarPorIdentificador(anyString());
			verify(repositorio, never()).actualizar(any(Bano.class));
		}
	}

	@Test
	public void cuandoCampoIdNuloEntoncesLanzarExcepcion() {
		// arrange
		Bano bano = new BanoTestDataBuilder().build();
		bano.setId(null);

		try {
			// act
			servicio.actualizarBano(bano);
			fail();
		} catch (ExcepcionValorObligatorio e) {
			// assert
			assertThat(e.getMessage(), equalTo(prop.getPropiedad(MSG_ID_OBLIGATORIO)));

			verify(repositorio, never()).existePorId(anyLong());
			verify(repositorio, never()).buscarPorIdentificador(anyString());
			verify(repositorio, never()).actualizar(any(Bano.class));
		}
	}

	@Test
	public void cuandoBuscarBanoPorIdNoExisteEntoncesLanzarExcepcion() {
		// arrange
		Bano bano = new BanoTestDataBuilder().withId(33L).build();
		doReturn(false).when(repositorio).existePorId(bano.getId());

		try {
			// act
			servicio.actualizarBano(bano);
			fail();
		} catch (ExcepcionSinDatos e) {
			// assert
			assertThat(e.getMessage(), equalTo(prop.getPropiedad(MSG_BANO_POR_ID_NO_EXISTE)));

			verify(repositorio, times(1)).existePorId(anyLong());
			verify(repositorio, never()).buscarPorIdentificador(anyString());
			verify(repositorio, never()).actualizar(any(Bano.class));

			ArgumentCaptor<Long> objCaptor = ArgumentCaptor.forClass(Long.class);
			verify(repositorio).existePorId(objCaptor.capture());
			assertThat(objCaptor.getValue(), is(bano.getId()));
		}
	}

	@Test
	public void cuandoBuscarBanoPorIdentificadorDuplicadoEntoncesLanzarExcepcion() {
		// arrange
		Bano bano = new BanoTestDataBuilder().build();
		Bano duplicado = new BanoTestDataBuilder().withId(2L).build();
		doReturn(true).when(repositorio).existePorId(bano.getId());
		doReturn(Optional.of(duplicado)).when(repositorio).buscarPorIdentificador(bano.getIdentificador());

		try {
			// act
			servicio.actualizarBano(bano);
			fail();
		} catch (ExcepcionDuplicidad e) {
			// assert
			assertThat(e.getMessage(), equalTo(prop.getPropiedad(MSG_BANO_POR_IDENTIFICADOR_DUPLICADO)));

			verify(repositorio, times(1)).existePorId(anyLong());
			verify(repositorio, times(1)).buscarPorIdentificador(anyString());
			verify(repositorio, never()).actualizar(bano);

			ArgumentCaptor<String> objCaptor = ArgumentCaptor.forClass(String.class);
			verify(repositorio).buscarPorIdentificador(objCaptor.capture());
			assertThat(objCaptor.getValue(), is(bano.getIdentificador()));
		}
	}

	@Test
	public void cuandoBanoOkEntoncesDeberiaActualizar() {
		// arrange
		Bano bano = new BanoTestDataBuilder().build();
		doReturn(true).when(repositorio).existePorId(bano.getId());
		doReturn(Optional.empty()).when(repositorio).buscarPorIdentificador(bano.getIdentificador());
		doAnswer(t -> {
			bano.setEstado(EstadoBano.OCUPADO.getEstado());
			return bano;
		}).when(repositorio).actualizar(bano);

		// act
		servicio.actualizarBano(bano);

		// assert
		verify(repositorio, times(1)).existePorId(anyLong());
		verify(repositorio, times(1)).buscarPorIdentificador(anyString());
		verify(repositorio, times(1)).actualizar(any(Bano.class));

		ArgumentCaptor<Bano> objCaptor = ArgumentCaptor.forClass(Bano.class);
		verify(repositorio).actualizar(objCaptor.capture());
		assertThat(objCaptor.getValue(), is(bano));
		assertThat(objCaptor.getValue().getEstado(), equalTo(EstadoBano.OCUPADO.getEstado()));
	}

}
