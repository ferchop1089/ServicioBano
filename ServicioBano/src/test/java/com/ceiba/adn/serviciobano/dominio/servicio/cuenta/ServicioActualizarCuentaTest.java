package com.ceiba.adn.serviciobano.dominio.servicio.cuenta;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ceiba.adn.serviciobano.comun.FabricaPropiedades;
import com.ceiba.adn.serviciobano.comun.Propiedades;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionSinDatos;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorObligatorio;
import com.ceiba.adn.serviciobano.dominio.modelo.Cuenta;
import com.ceiba.adn.serviciobano.dominio.puerto.repositorio.RepositorioBano;
import com.ceiba.adn.serviciobano.dominio.puerto.repositorio.RepositorioCuenta;
import com.ceiba.adn.serviciobano.testdatabuilder.CuentaTestDataBuilder;

public class ServicioActualizarCuentaTest {

	private static final String MSG_CUENTA_OBLIGATORIO = "msg.err.servicio.actualizar.cuenta.obligatorio";
	private static final String MSG_ID_OBLIGATORIO = "msg.err.servicio.actualizar.cuenta.id.obligatorio";
	private static final String MSG_POR_ID_NO_EXISTE = "msg.err.servicio.actualizar.cuenta.no.encontrada";
	private static final String MSG_CUENTA_SOBRES_NEGATIVO = "msg.err.servicio.actualizar.cuenta.sobres.en.negativo";
	private static final String MSG_CUENTA_ID_BANO_NO_EXISTE = "msg.err.servicio.actualizar.cuenta.idbano.no.existe";
	private static final String MSG_CUENTA_TOTAL_COBRO_NEGATIVO = "msg.err.servicio.actualizar.cuenta.total.cobro.en.negativo";

	private ServicioActualizarCuenta servicio;

	private Propiedades prop;

	@Mock
	private RepositorioBano repositorioBano;

	@Mock
	private RepositorioCuenta repositorioCuenta;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		prop = FabricaPropiedades.propiedadMensajesPorDefecto();
		servicio = new ServicioActualizarCuenta(repositorioCuenta, repositorioBano, prop);
	}

	@Test
	public void cuandoCuentaEsNuloEntoncesLanzarExcepcion() {
		// arrange
		Cuenta cuenta = null;

		try {
			// act
			servicio.actualizarCuenta(cuenta);
			fail();
		} catch (ExcepcionValorObligatorio e) {
			// assert
			assertThat(e.getMessage(), equalTo(prop.getPropiedad(MSG_CUENTA_OBLIGATORIO)));

			verify(repositorioCuenta, never()).existePorId(anyLong());
			verify(repositorioBano, never()).existePorId(anyLong());
			verify(repositorioCuenta, never()).actualizar(any(Cuenta.class));
		}
	}

	@Test
	public void cuandoIdCuentaNuloEntoncesLanzarExcepcion() {
		// arrange
		Cuenta cuenta = new CuentaTestDataBuilder().withId(null).build();

		try {
			// act
			servicio.actualizarCuenta(cuenta);
			fail();
		} catch (ExcepcionValorObligatorio e) {
			// assert
			assertThat(e.getMessage(), equalTo(prop.getPropiedad(MSG_ID_OBLIGATORIO)));

			verify(repositorioCuenta, never()).existePorId(anyLong());
			verify(repositorioBano, never()).existePorId(anyLong());
			verify(repositorioCuenta, never()).actualizar(any(Cuenta.class));
		}
	}

	@Test
	public void cuandoIdCuentaNoExisteEntoncesLanzarExcepcion() {
		// arrange
		Cuenta cuenta = new CuentaTestDataBuilder().build();
		doReturn(false).when(repositorioCuenta).existePorId(cuenta.getId());

		try {
			// act
			servicio.actualizarCuenta(cuenta);
			fail();
		} catch (ExcepcionSinDatos e) {
			// assert
			assertThat(e.getMessage(), equalTo(prop.getPropiedad(MSG_POR_ID_NO_EXISTE)));

			verify(repositorioCuenta, times(1)).existePorId(anyLong());
			verify(repositorioBano, never()).existePorId(anyLong());
			verify(repositorioCuenta, never()).actualizar(any(Cuenta.class));
		}
	}

	@Test
	public void cuandoIdBanoNoExisteEntoncesLanzarExcepcion() {
		// arrange
		Cuenta cuenta = new CuentaTestDataBuilder().build();
		doReturn(true).when(repositorioCuenta).existePorId(cuenta.getId());
		doReturn(false).when(repositorioBano).existePorId(cuenta.getId());

		try {
			// act
			servicio.actualizarCuenta(cuenta);
			fail();
		} catch (ExcepcionValorInvalido e) {
			// assert
			assertThat(e.getMessage(), equalTo(prop.getPropiedad(MSG_CUENTA_ID_BANO_NO_EXISTE)));

			verify(repositorioCuenta, times(1)).existePorId(anyLong());
			verify(repositorioBano, times(1)).existePorId(anyLong());
			verify(repositorioCuenta, never()).actualizar(any(Cuenta.class));
		}
	}

	@Test
	public void cuandoSobresNegativoEntoncesLanzarExcepcion() {
		// arrange
		Cuenta cuenta = new CuentaTestDataBuilder().withSobres(-1).build();
		doReturn(true).when(repositorioCuenta).existePorId(cuenta.getId());
		doReturn(true).when(repositorioBano).existePorId(cuenta.getId());

		try {
			// act
			servicio.actualizarCuenta(cuenta);
			fail();
		} catch (ExcepcionValorInvalido e) {
			// assert
			assertThat(e.getMessage(), equalTo(prop.getPropiedad(MSG_CUENTA_SOBRES_NEGATIVO)));

			verify(repositorioCuenta, times(1)).existePorId(anyLong());
			verify(repositorioBano, times(1)).existePorId(anyLong());
			verify(repositorioCuenta, never()).actualizar(any(Cuenta.class));
		}
	}

	@Test
	public void cuandoTotalPagoNegativoEntoncesLanzarExcepcion() {
		// arrange
		Cuenta cuenta = new CuentaTestDataBuilder().withTotalCobro(new BigDecimal(-1)).build();
		doReturn(true).when(repositorioCuenta).existePorId(cuenta.getId());
		doReturn(true).when(repositorioBano).existePorId(cuenta.getId());

		try {
			// act
			servicio.actualizarCuenta(cuenta);
			fail();
		} catch (ExcepcionValorInvalido e) {
			// assert
			assertThat(e.getMessage(), equalTo(prop.getPropiedad(MSG_CUENTA_TOTAL_COBRO_NEGATIVO)));

			verify(repositorioCuenta, times(1)).existePorId(anyLong());
			verify(repositorioBano, times(1)).existePorId(anyLong());
			verify(repositorioCuenta, never()).actualizar(any(Cuenta.class));
		}
	}

	@Test
	public void cuandoCuentaOkEntoncesDeberiaActualizarla() {
		// arrange
		Cuenta cuenta = new CuentaTestDataBuilder().build();
		doReturn(true).when(repositorioCuenta).existePorId(cuenta.getId());
		doReturn(true).when(repositorioBano).existePorId(cuenta.getId());
		doNothing().when(repositorioCuenta).actualizar(cuenta);

		// act
		servicio.actualizarCuenta(cuenta);

		// assert
		verify(repositorioCuenta, times(1)).existePorId(anyLong());
		verify(repositorioBano, times(1)).existePorId(anyLong());
		verify(repositorioCuenta, times(1)).actualizar(any(Cuenta.class));
	}
	
	@Test
	public void cuandoCuentaOkYIdBanoNuloEntoncesDeberiaActualizarla() {
		// arrange
		Cuenta cuenta = new CuentaTestDataBuilder().withIdBano(null).build();
		doReturn(true).when(repositorioCuenta).existePorId(cuenta.getId());
		doReturn(true).when(repositorioBano).existePorId(cuenta.getId());
		doNothing().when(repositorioCuenta).actualizar(cuenta);

		// act
		servicio.actualizarCuenta(cuenta);

		// assert
		verify(repositorioCuenta, times(1)).existePorId(anyLong());
		verify(repositorioBano, never()).existePorId(anyLong());
		verify(repositorioCuenta, times(1)).actualizar(any(Cuenta.class));
	}

}
