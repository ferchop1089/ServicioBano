package com.ceiba.adn.serviciobano.dominio.servicio.cuenta;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionRestriccion;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorObligatorio;
import com.ceiba.adn.serviciobano.dominio.modelo.Cuenta;
import com.ceiba.adn.serviciobano.dominio.modelo.EstadoBano;
import com.ceiba.adn.serviciobano.dominio.puerto.repositorio.RepositorioBano;
import com.ceiba.adn.serviciobano.dominio.puerto.repositorio.RepositorioCuenta;
import com.ceiba.adn.serviciobano.infraestructura.adaptador.propiedades.FabricaPropiedades;
import com.ceiba.adn.serviciobano.testdatabuilder.CuentaTestDataBuilder;

public class ServicioCrearCuentaTest {

	private static final String MSG_CUENTA_OBLIGATORIO = "msg.err.servicio.crear.cuenta.obligatorio";
	private static final String MSG_CUENTA_BANO_ID_OBLIGATORIO = "msg.err.servicio.crear.cuenta.idbano.obligatorio";
	private static final String MSG_CUENTA_POR_ID_DUPLICADO = "msg.err.servicio.crear.cuenta.id.duplicado";
	private static final String MSG_CUENTA_ID_BANO_NO_EXISTE = "msg.err.servicio.crear.cuenta.idbano.no.existe";
	private static final String MSG_CUENTA_BANO_NO_DISPONIBLE = "msg.err.servicio.crear.cuenta.bano.no.disponible";

	private ServicioCrearCuenta servicio;

	private Propiedades prop;

	@Mock
	private RepositorioBano repositorioBano;

	@Mock
	private RepositorioCuenta repositorioCuenta;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		prop = FabricaPropiedades.propiedadMensajesPorDefecto();
		servicio = new ServicioCrearCuenta(repositorioCuenta, repositorioBano, prop);
	}

	@Test
	public void cuandoCuentaEsNuloEntoncesLanzarExcepcion() {
		// arrange
		Cuenta cuenta = null;

		try {
			// act
			servicio.crearCuenta(cuenta);
			fail();
		} catch (ExcepcionValorObligatorio e) {
			// assert
			assertThat(e.getMessage(), equalTo(prop.getPropiedad(MSG_CUENTA_OBLIGATORIO)));

			verify(repositorioCuenta, never()).existePorId(anyLong());
			verify(repositorioBano, never()).existePorId(anyLong());
			verify(repositorioBano, never()).estadoBano(anyLong());
			verify(repositorioCuenta, never()).crear(any(Cuenta.class));
		}
	}
	
	@Test
	public void cuandoIdBanoNuloEntoncesLanzarExcepcion() {
		// arrange
		Cuenta cuenta = new CuentaTestDataBuilder().withIdBano(null).build();

		try {
			// act
			servicio.crearCuenta(cuenta);
			fail();
		} catch (ExcepcionValorObligatorio e) {
			// assert
			assertThat(e.getMessage(), equalTo(prop.getPropiedad(MSG_CUENTA_BANO_ID_OBLIGATORIO)));

			verify(repositorioCuenta, never()).existePorId(anyLong());
			verify(repositorioBano, never()).existePorId(anyLong());
			verify(repositorioBano, never()).estadoBano(anyLong());
			verify(repositorioCuenta, never()).crear(any(Cuenta.class));
		}
	}

	@Test
	public void cuandoBuscarCuentaPorIdDuplicadoEntoncesLanzarExcepcion() {
		// arrange
		Cuenta cuenta = new CuentaTestDataBuilder().build();
		doReturn(true).when(repositorioCuenta).existePorId(cuenta.getId());

		try {
			// act
			servicio.crearCuenta(cuenta);
			fail();
		} catch (ExcepcionDuplicidad e) {
			// assert
			assertThat(e.getMessage(), equalTo(prop.getPropiedad(MSG_CUENTA_POR_ID_DUPLICADO)));

			verify(repositorioCuenta, times(1)).existePorId(anyLong());
			verify(repositorioBano, never()).existePorId(anyLong());
			verify(repositorioBano, never()).estadoBano(anyLong());
			verify(repositorioCuenta, never()).crear(any(Cuenta.class));

			ArgumentCaptor<Long> objCaptor = ArgumentCaptor.forClass(Long.class);
			verify(repositorioCuenta).existePorId(objCaptor.capture());
			assertThat(objCaptor.getValue(), is(cuenta.getId()));
		}
	}

	@Test
	public void cuandoBuscarBanoPorIdBanoNoExisteEntoncesLanzarExcepcion() {
		// arrange
		Cuenta cuenta = new CuentaTestDataBuilder().build();
		doReturn(false).when(repositorioCuenta).existePorId(cuenta.getId());
		doReturn(false).when(repositorioBano).existePorId(cuenta.getIdBano());

		try {
			// act
			servicio.crearCuenta(cuenta);
			fail();
		} catch (ExcepcionValorInvalido e) {
			// assert
			assertThat(e.getMessage(), equalTo(prop.getPropiedad(MSG_CUENTA_ID_BANO_NO_EXISTE)));

			verify(repositorioCuenta, times(1)).existePorId(anyLong());
			verify(repositorioBano, times(1)).existePorId(anyLong());
			verify(repositorioBano, never()).estadoBano(anyLong());
			verify(repositorioCuenta, never()).crear(any(Cuenta.class));

			ArgumentCaptor<Long> objCaptor = ArgumentCaptor.forClass(Long.class);
			verify(repositorioBano).existePorId(objCaptor.capture());
			assertThat(objCaptor.getValue(), is(cuenta.getIdBano()));
		}
	}

	@Test
	public void cuandoEstadoBanoNoDisponibleEntoncesLanzarExcepcion() {
		// arrange
		Cuenta cuenta = new CuentaTestDataBuilder().build();
		doReturn(false).when(repositorioCuenta).existePorId(cuenta.getId());
		doReturn(true).when(repositorioBano).existePorId(cuenta.getIdBano());
		doReturn(EstadoBano.FUERA_DE_SERVICIO.getEstado()).when(repositorioBano).estadoBano(cuenta.getIdBano());

		try {
			// act
			servicio.crearCuenta(cuenta);
			fail();
		} catch (ExcepcionRestriccion e) {
			// assert
			assertThat(e.getMessage(), equalTo(prop.getPropiedad(MSG_CUENTA_BANO_NO_DISPONIBLE)));

			verify(repositorioCuenta, times(1)).existePorId(anyLong());
			verify(repositorioBano, times(1)).existePorId(anyLong());
			verify(repositorioBano, times(1)).estadoBano(anyLong());
			verify(repositorioCuenta, never()).crear(any(Cuenta.class));

			ArgumentCaptor<Long> objCaptor = ArgumentCaptor.forClass(Long.class);
			verify(repositorioBano).estadoBano(objCaptor.capture());
			assertThat(objCaptor.getValue(), is(cuenta.getIdBano()));
		}
	}

	@Test
	public void cuandoCuentaOkEntoncesDeberiaCrearla() {
		// arrange
		Cuenta cuenta = new CuentaTestDataBuilder().build();
		doReturn(false).when(repositorioCuenta).existePorId(cuenta.getId());
		doReturn(true).when(repositorioBano).existePorId(cuenta.getIdBano());
		doReturn(EstadoBano.DISPONIBLE.getEstado()).when(repositorioBano).estadoBano(cuenta.getIdBano());

		// act
		servicio.crearCuenta(cuenta);
		
		// assert
		verify(repositorioCuenta, times(1)).existePorId(anyLong());
		verify(repositorioBano, times(1)).existePorId(anyLong());
		verify(repositorioBano, times(1)).estadoBano(anyLong());
		verify(repositorioCuenta, times(1)).crear(any(Cuenta.class));

		ArgumentCaptor<Cuenta> objCaptor = ArgumentCaptor.forClass(Cuenta.class);
		verify(repositorioCuenta).crear(objCaptor.capture());
		assertThat(objCaptor.getValue(), is(cuenta));

	}

}
