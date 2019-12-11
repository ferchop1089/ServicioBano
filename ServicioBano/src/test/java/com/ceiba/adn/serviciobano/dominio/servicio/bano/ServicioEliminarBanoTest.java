package com.ceiba.adn.serviciobano.dominio.servicio.bano;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
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
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionRestriccion;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionSinDatos;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorObligatorio;
import com.ceiba.adn.serviciobano.dominio.modelo.EstadoBano;
import com.ceiba.adn.serviciobano.dominio.puerto.repositorio.RepositorioBano;
import com.ceiba.adn.serviciobano.infraestructura.adaptador.propiedades.FabricaPropiedades;

public class ServicioEliminarBanoTest {

	private static final String MSG_ID_OBLIGATORIO = "msg.err.servicio.eliminar.bano.id.obligatorio";
	private static final String MSG_BANO_POR_ID_NO_EXISTE = "msg.err.servicio.eliminar.bano.no.encontrado";
	private static final String MSG_BANO_OCUPADO_NO_ELIMINAR = "msg.err.servicio.eliminar.bano.estado.ocupado";

	private ServicioEliminarBano servicio;

	private Propiedades prop;

	@Mock
	private RepositorioBano repositorio;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		prop = FabricaPropiedades.propiedadMensajesPorDefecto();
		servicio = new ServicioEliminarBano(repositorio, prop);
	}

	@Test
	public void cuandoIdEsNuloEntoncesLanzarExcepcion() {
		// arrange
		Long id = null;

		try {
			// act
			servicio.eliminarBano(id);
			fail();
		} catch (ExcepcionValorObligatorio e) {
			// assert
			assertThat(e.getMessage(), equalTo(prop.getPropiedad(MSG_ID_OBLIGATORIO)));

			verify(repositorio, never()).existePorId(anyLong());
			verify(repositorio, never()).estadoBano(anyLong());
			verify(repositorio, never()).eliminar(anyLong());
		}
	}

	@Test
	public void cuandoIdNoExisteEntoncesLanzarExcepcion() {
		// arrange
		Long id = 1L;
		doReturn(false).when(repositorio).existePorId(id);

		try {
			// act
			servicio.eliminarBano(id);
			fail();
		} catch (ExcepcionSinDatos e) {
			// assert
			assertThat(e.getMessage(), equalTo(prop.getPropiedad(MSG_BANO_POR_ID_NO_EXISTE)));

			verify(repositorio, times(1)).existePorId(anyLong());
			verify(repositorio, never()).estadoBano(anyLong());
			verify(repositorio, never()).eliminar(anyLong());

			ArgumentCaptor<Long> objCaptor = ArgumentCaptor.forClass(Long.class);
			verify(repositorio).existePorId(objCaptor.capture());
			assertThat(objCaptor.getValue(), is(id));
		}
	}

	@Test
	public void cuandoEstadoBanoEsOcupadoEntoncesLanzarExcepcion() {
		// arrange
		Long id = 1L;
		String estado = EstadoBano.OCUPADO.getEstado();
		doReturn(true).when(repositorio).existePorId(id);
		doReturn(estado).when(repositorio).estadoBano(id);

		try {
			// act
			servicio.eliminarBano(id);
			fail();
		} catch (ExcepcionRestriccion e) {
			// assert
			assertThat(e.getMessage(), equalTo(prop.getPropiedad(MSG_BANO_OCUPADO_NO_ELIMINAR)));

			verify(repositorio, times(1)).existePorId(anyLong());
			verify(repositorio, times(1)).estadoBano(anyLong());
			verify(repositorio, never()).eliminar(anyLong());

			ArgumentCaptor<Long> objCaptor = ArgumentCaptor.forClass(Long.class);
			verify(repositorio).estadoBano(objCaptor.capture());
			assertThat(objCaptor.getValue(), is(id));
		}
	}

	@Test
	public void cuandoBanoEliminarOkEntoncesDeberiaEliminar() {
		// arrange
		Long id = 1L;
		String estado = EstadoBano.DISPONIBLE.getEstado();

		doReturn(true).when(repositorio).existePorId(id);
		doReturn(estado).when(repositorio).estadoBano(id);
		doNothing().when(repositorio).eliminar(id);

		// act
		servicio.eliminarBano(id);

		// assert
		verify(repositorio, times(1)).existePorId(anyLong());
		verify(repositorio, times(1)).estadoBano(anyLong());
		verify(repositorio, times(1)).eliminar(anyLong());

		ArgumentCaptor<Long> objCaptor = ArgumentCaptor.forClass(Long.class);
		verify(repositorio).eliminar(objCaptor.capture());
		assertThat(objCaptor.getValue(), is(id));
	}

}
