package com.ceiba.adn.serviciobano.infraestructura.mapeador;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.ceiba.adn.serviciobano.dominio.modelo.Cuenta;
import com.ceiba.adn.serviciobano.infraestructura.adaptador.repositorio.entidad.CuentaEntidad;
import com.ceiba.adn.serviciobano.testdatabuilder.CuentaEntidadTestDataBuilder;
import com.ceiba.adn.serviciobano.testdatabuilder.CuentaTestDataBuilder;

public class MapearCuentaEntidadTest {

	private MapearCuentaEntidad mapper;

	@Test
	public void cuandoCuentaEntidadEsNuloEntoncesRetornaNulo() {
		// arrange
		mapper = new MapearCuentaEntidad();
		CuentaEntidad comando = null;

		// act
		Cuenta b = mapper.mapearA(comando);

		// assert
		assertNull(b);
	}

	@Test
	public void cuandoCuentaEntidadOkEntoncesRetornaCuenta() {
		// arrange
		mapper = new MapearCuentaEntidad();
		CuentaEntidad comando = new CuentaEntidadTestDataBuilder().build();

		// act
		Cuenta b = mapper.mapearA(comando);

		// assert
		assertNotNull(b);
		assertEquals(b.getId(), comando.getId());
		assertEquals(b.getEstado(), comando.getEstado());
		assertEquals(b.getFechaIngreso(), comando.getFechaIngreso());
		assertEquals(b.getIdBano(), comando.getIdBano());
		assertEquals(b.getSobres(), comando.getSobres());
		assertEquals(b.getTotalCobro(), comando.getTotalCobro());
	}

	@Test
	public void cuandoCuentaEsNuloEntoncesRetornaNulo() {
		// arrange
		mapper = new MapearCuentaEntidad();
		Cuenta comando = null;

		// act
		CuentaEntidad b = mapper.mapearDesde(comando);

		// assert
		assertNull(b);
	}

	@Test
	public void cuandoBanoOkEntoncesRetornaBanoEntidad() {
		// arrange
		mapper = new MapearCuentaEntidad();
		Cuenta comando = new CuentaTestDataBuilder().build();

		// act
		CuentaEntidad b = mapper.mapearDesde(comando);

		// assert
		assertNotNull(b);
		assertEquals(b.getId(), comando.getId());
		assertEquals(b.getEstado(), comando.getEstado());
		assertEquals(b.getFechaIngreso(), comando.getFechaIngreso());
		assertEquals(b.getIdBano(), comando.getIdBano());
		assertEquals(b.getSobres(), comando.getSobres());
		assertEquals(b.getTotalCobro(), comando.getTotalCobro());
	}

}
