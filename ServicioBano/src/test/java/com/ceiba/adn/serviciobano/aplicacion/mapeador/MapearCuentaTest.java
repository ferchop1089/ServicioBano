package com.ceiba.adn.serviciobano.aplicacion.mapeador;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoCuenta;
import com.ceiba.adn.serviciobano.dominio.modelo.Cuenta;
import com.ceiba.adn.serviciobano.testdatabuilder.ComandoCuentaTestDataBuilder;
import com.ceiba.adn.serviciobano.testdatabuilder.CuentaTestDataBuilder;

public class MapearCuentaTest {

	private MapearCuenta mapper;

	@Test
	public void cuandoComandoCuentaEsNuloEntoncesRetornaNulo() {
		// arrange
		mapper = new MapearCuenta();
		ComandoCuenta comando = null;

		// act
		Cuenta b = mapper.mapearA(comando);

		// assert
		assertNull(b);
	}

	@Test
	public void cuandoComandoCuentaOkEntoncesRetornaCuenta() {
		// arrange
		mapper = new MapearCuenta();
		ComandoCuenta comando = new ComandoCuentaTestDataBuilder().build();

		// act
		Cuenta b = mapper.mapearA(comando);

		// assert
		assertNotNull(b);
		assertEquals(b.getId(), comando.getId());
		assertEquals(b.getEstado(), comando.getEstado());
		assertEquals(b.getIdBano(), comando.getIdBano());
		assertEquals(b.getSobres(), comando.getSobres());
		assertEquals(b.getTotalCobro(), comando.getTotalCobro());
		assertEquals(b.getFechaIngreso(), comando.getFechaIngreso());
	}

	@Test
	public void cuandoCuentaEsNuloEntoncesRetornaNulo() {
		// arrange
		mapper = new MapearCuenta();
		Cuenta comando = null;

		// act
		ComandoCuenta b = mapper.mapearDesde(comando);

		// assert
		assertNull(b);
	}

	@Test
	public void cuandoCuentaOkEntoncesRetornaComandoCuenta() {
		// arrange
		mapper = new MapearCuenta();
		Cuenta comando = new CuentaTestDataBuilder().build();

		// act
		ComandoCuenta b = mapper.mapearDesde(comando);

		// assert
		assertNotNull(b);
		assertEquals(b.getId(), comando.getId());
		assertEquals(b.getEstado(), comando.getEstado());
		assertEquals(b.getIdBano(), comando.getIdBano());
		assertEquals(b.getSobres(), comando.getSobres());
		assertEquals(b.getTotalCobro(), comando.getTotalCobro());
		assertEquals(b.getFechaIngreso(), comando.getFechaIngreso());
	}

}
