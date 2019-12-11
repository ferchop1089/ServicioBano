package com.ceiba.adn.serviciobano.dominio.servicio.bano;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
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

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ceiba.adn.serviciobano.comun.puerto.Propiedades;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorObligatorio;
import com.ceiba.adn.serviciobano.dominio.modelo.Bano;
import com.ceiba.adn.serviciobano.dominio.puerto.repositorio.RepositorioBano;
import com.ceiba.adn.serviciobano.infraestructura.adaptador.propiedades.FabricaPropiedades;
import com.ceiba.adn.serviciobano.testdatabuilder.BanoTestDataBuilder;

public class ServicioCrearBanoTest {

	private static final String MSG_BANO_OBLIGATORIO = "msg.err.servicio.crear.bano.obligatorio";
	private static final String MSG_BANO_POR_ID_DUPLICADO = "msg.err.servicio.crear.bano.id.duplicado";
	private static final String MSG_BANO_POR_IDENTIFICADOR_DUPLICADO = "msg.err.servicio.crear.bano.identificador.duplicado";

	private ServicioCrearBano servicio;

	private Propiedades prop;

	@Mock
	private RepositorioBano repositorio;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		prop = FabricaPropiedades.propiedadMensajesPorDefecto();
		servicio = new ServicioCrearBano(repositorio, prop);
	}

	@Test
	public void cuandoBanoEsNuloEntoncesLanzarExcepcion() {
		// arrange
		Bano bano = null;

		try {
			// act
			servicio.crearBano(bano);
			fail();
		} catch (ExcepcionValorObligatorio e) {
			// assert
			assertThat(e.getMessage(), equalTo(prop.getPropiedad(MSG_BANO_OBLIGATORIO)));

			verify(repositorio, never()).existePorId(anyLong());
			verify(repositorio, never()).existePorIdentificador(anyString());
			verify(repositorio, never()).actualizar(any(Bano.class));
		}
	}

	@Test
	public void cuandoBuscarBanoPorIdDuplicadoEntoncesLanzarExcepcion() {
		// arrange
		Bano bano = new BanoTestDataBuilder().build();
		doReturn(true).when(repositorio).existePorId(bano.getId());

		try {
			// act
			servicio.crearBano(bano);
			fail();
		} catch (ExcepcionDuplicidad e) {
			// assert
			assertThat(e.getMessage(), equalTo(prop.getPropiedad(MSG_BANO_POR_ID_DUPLICADO)));

			verify(repositorio, times(1)).existePorId(anyLong());
			verify(repositorio, never()).existePorIdentificador(anyString());
			verify(repositorio, never()).crear(any(Bano.class));

			ArgumentCaptor<Long> objCaptor = ArgumentCaptor.forClass(Long.class);
			verify(repositorio).existePorId(objCaptor.capture());
			assertThat(objCaptor.getValue(), is(bano.getId()));
		}
	}

	@Test
	public void cuandoBuscarBanoPorIdentificadorDuplicadoEntoncesLanzarExcepcion() {
		// arrange
		Bano bano = new BanoTestDataBuilder().build();
		doReturn(false).when(repositorio).existePorId(bano.getId());
		doReturn(true).when(repositorio).existePorIdentificador(bano.getIdentificador());

		try {
			// act
			servicio.crearBano(bano);
			fail();
		} catch (ExcepcionDuplicidad e) {
			// assert
			assertThat(e.getMessage(), equalTo(prop.getPropiedad(MSG_BANO_POR_IDENTIFICADOR_DUPLICADO)));

			verify(repositorio, times(1)).existePorId(anyLong());
			verify(repositorio, times(1)).existePorIdentificador(anyString());
			verify(repositorio, never()).crear(any(Bano.class));

			ArgumentCaptor<String> objCaptor = ArgumentCaptor.forClass(String.class);
			verify(repositorio).existePorIdentificador(objCaptor.capture());
			assertThat(objCaptor.getValue(), is(bano.getIdentificador()));
		}
	}

	@Test
	public void cuandoBanoOkYIdNuloEntoncesDeberiaCrear() {
		// arrange
		Bano bano = new BanoTestDataBuilder().build();
		bano.setId(null);
		doReturn(false).when(repositorio).existePorId(bano.getId());
		doReturn(false).when(repositorio).existePorIdentificador(bano.getIdentificador());
		doAnswer(t -> {
			bano.setId(10L);
			return bano.getId();
		}).when(repositorio).crear(bano);

		// act
		Long id = servicio.crearBano(bano);

		// assert
		verify(repositorio, never()).existePorId(anyLong());
		pruebasBanoOk(bano, id);
	}

	@Test
	public void cuandoBanoOkYIdNoNuloEntoncesDeberiaCrear() {
		// arrange
		Bano bano = new BanoTestDataBuilder().build();
		doReturn(false).when(repositorio).existePorId(bano.getId());
		doReturn(false).when(repositorio).existePorIdentificador(bano.getIdentificador());
		doReturn(bano.getId()).when(repositorio).crear(bano);

		// act
		Long id = servicio.crearBano(bano);

		// assert
		assertThat(id, equalTo(bano.getId()));
		verify(repositorio, times(1)).existePorId(anyLong());
		pruebasBanoOk(bano, id);
	}

	private void pruebasBanoOk(Bano bano, Long id) {
		assertThat(id, is(notNullValue()));

		verify(repositorio, times(1)).existePorIdentificador(anyString());
		verify(repositorio, times(1)).crear(any(Bano.class));

		ArgumentCaptor<Bano> objCaptor = ArgumentCaptor.forClass(Bano.class);
		verify(repositorio).crear(objCaptor.capture());
		assertThat(objCaptor.getValue(), is(bano));
		assertThat(objCaptor.getValue().getId(), equalTo(bano.getId()));
	}

}
