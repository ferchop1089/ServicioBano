package com.ceiba.adn.serviciobano.dominio.servicio.cuenta;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ceiba.adn.serviciobano.comun.FabricaPropiedades;
import com.ceiba.adn.serviciobano.comun.Propiedades;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionSinDatos;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorObligatorio;
import com.ceiba.adn.serviciobano.dominio.modelo.Cuenta;
import com.ceiba.adn.serviciobano.dominio.puerto.repositorio.RepositorioCuenta;
import com.ceiba.adn.serviciobano.testdatabuilder.CuentaTestDataBuilder;

public class ServicioCobrarCuentaTest {

	private static final String MSG_CUENTA_ID_OBLIGATORIO = "msg.err.servicio.cobrar.cuenta.id.obligatorio";
	private static final String MSG_CUENTA_CON_ID_NO_EXISTE = "msg.err.servicio.cobrar.cuenta.id.no.existe";

	private ServicioCobrarCuenta servicio;

	private Propiedades propMsg;

	private Propiedades propConfig;

	@Mock
	private RepositorioCuenta repositorio;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		propMsg = FabricaPropiedades.propiedadMensajesPorDefecto();
		propConfig = FabricaPropiedades.propiedadConfiguracionPorDefecto();
		servicio = new ServicioCobrarCuenta(repositorio, propMsg, propConfig);
	}

	@Test
	public void cuandoIdEsNuloEntoncesLanzarExcepcion() {
		// arrange
		Long id = null;

		try {
			// act
			servicio.cobrar(id);
			fail();
		} catch (ExcepcionValorObligatorio e) {
			// assert
			assertThat(e.getMessage(), equalTo(propMsg.getPropiedad(MSG_CUENTA_ID_OBLIGATORIO)));
			verify(repositorio, never()).buscarPorId(anyLong());
		}
	}

	@Test
	public void cuandoIdNoExisteEntoncesLanzarExcepcion() {
		// arrange
		Long id = 1L;
		doReturn(Optional.empty()).when(repositorio).buscarPorId(id);

		try {
			// act
			servicio.cobrar(id);
			fail();
		} catch (ExcepcionSinDatos e) {
			// assert
			assertThat(e.getMessage(), equalTo(propMsg.getPropiedad(MSG_CUENTA_CON_ID_NO_EXISTE)));
			verify(repositorio, times(1)).buscarPorId(anyLong());

			ArgumentCaptor<Long> objCaptor = ArgumentCaptor.forClass(Long.class);
			verify(repositorio).buscarPorId(objCaptor.capture());
			assertThat(objCaptor.getValue(), is(id));
		}
	}

	@Test
	public void cuandoIdCuentaOkEntoncesDeberiaCalcular() {
		// arrange
		Cuenta cuenta = new CuentaTestDataBuilder().build();
		doReturn(Optional.of(cuenta)).when(repositorio).buscarPorId(cuenta.getId());

		// act
		BigDecimal cobro = servicio.cobrar(cuenta.getId());

		// assert
		verify(repositorio, times(1)).buscarPorId(anyLong());
		assertThat(cobro, is(notNullValue()));
	}

	@Test
	public void cuandoIdCuentaOkYSobresVariosEntoncesDeberiaCalcular() {
		// arrange
		Cuenta cuenta = new CuentaTestDataBuilder().withSobres(6).build();
		doReturn(Optional.of(cuenta)).when(repositorio).buscarPorId(cuenta.getId());

		// act
		BigDecimal cobro = servicio.cobrar(cuenta.getId());

		// assert
		verify(repositorio, times(1)).buscarPorId(anyLong());
		assertThat(cobro, is(notNullValue()));
	}

}
