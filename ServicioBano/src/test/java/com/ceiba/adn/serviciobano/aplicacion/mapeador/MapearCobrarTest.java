package com.ceiba.adn.serviciobano.aplicacion.mapeador;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoCobrar;
import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoCuenta;
import com.ceiba.adn.serviciobano.dominio.modelo.Cobrar;
import com.ceiba.adn.serviciobano.dominio.modelo.Cuenta;
import com.ceiba.adn.serviciobano.testdatabuilder.CobrarTestDataBuilder;
import com.ceiba.adn.serviciobano.testdatabuilder.ComandoCobrarTestDataBuilder;
import com.ceiba.adn.serviciobano.testdatabuilder.ComandoCuentaTestDataBuilder;
import com.ceiba.adn.serviciobano.testdatabuilder.CuentaTestDataBuilder;

public class MapearCobrarTest {

	private MapearCobrar mapper;

	@Mock
	private MapearCuenta mapperCuenta;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mapper = new MapearCobrar(mapperCuenta);
	}

	@Test
	public void cuandoComandoCobrarEsNuloEntoncesRetornaNulo() {
		// arrange
		ComandoCobrar comando = null;

		// act
		Cobrar b = mapper.mapearA(comando);

		// assert
		assertNull(b);
		verify(mapperCuenta, never()).mapearA(any(ComandoCuenta.class));
	}

	@Test
	public void cuandoComandoCobrarOkEntoncesRetornaCobrar() {
		// arrange
		ComandoCobrar comando = new ComandoCobrarTestDataBuilder().build();
		Cuenta cuenta = new CuentaTestDataBuilder().build();
		doReturn(cuenta).when(mapperCuenta).mapearA(comando.getCuenta());

		// act
		Cobrar b = mapper.mapearA(comando);

		// assert
		assertNotNull(b);
		assertEquals(b.getMinutosAdicionales(), comando.getMinutosAdicionales());
		assertEquals(b.getMinutosPermitidos(), comando.getMinutosPermitidos());
		assertEquals(b.getMinutosTranscurridos(), comando.getMinutosTranscurridos());
		assertEquals(b.getSobresAdicionales(), comando.getSobresAdicionales());
		assertEquals(b.getSubtotalMinutosAdicionales(), comando.getSubtotalMinutosAdicionales());
		assertEquals(b.getSubtotalSobresAdicionales(), comando.getSubtotalSobresAdicionales());
		assertEquals(b.getTarifaMinutoAdicional(), comando.getTarifaMinutoAdicional());
		assertEquals(b.getTarifaMinutosPermitidos(), comando.getTarifaMinutosPermitidos());
		assertEquals(b.getTarifaSobreAdicional(), comando.getTarifaSobreAdicional());
		assertNotNull(b.getCuenta());
	}

	@Test
	public void cuandoCobrarEsNuloEntoncesRetornaNulo() {
		// arrange
		Cobrar comando = null;

		// act
		ComandoCobrar b = mapper.mapearDesde(comando);

		// assert
		assertNull(b);
	}

	@Test
	public void cuandoCobrarOkEntoncesRetornaComandoCobrar() {
		// arrange
		Cobrar cobrar = new CobrarTestDataBuilder().build();
		ComandoCuenta comando = new ComandoCuentaTestDataBuilder().build();
		doReturn(comando).when(mapperCuenta).mapearDesde(cobrar.getCuenta());

		// act
		ComandoCobrar b = mapper.mapearDesde(cobrar);

		// assert
		assertNotNull(b);
		assertEquals(b.getMinutosAdicionales(), cobrar.getMinutosAdicionales());
		assertEquals(b.getMinutosPermitidos(), cobrar.getMinutosPermitidos());
		assertEquals(b.getMinutosTranscurridos(), cobrar.getMinutosTranscurridos());
		assertEquals(b.getSobresAdicionales(), cobrar.getSobresAdicionales());
		assertEquals(b.getSubtotalMinutosAdicionales(), cobrar.getSubtotalMinutosAdicionales());
		assertEquals(b.getSubtotalSobresAdicionales(), cobrar.getSubtotalSobresAdicionales());
		assertEquals(b.getTarifaMinutoAdicional(), cobrar.getTarifaMinutoAdicional());
		assertEquals(b.getTarifaMinutosPermitidos(), cobrar.getTarifaMinutosPermitidos());
		assertEquals(b.getTarifaSobreAdicional(), cobrar.getTarifaSobreAdicional());
		assertNotNull(b.getCuenta());
	}

}
