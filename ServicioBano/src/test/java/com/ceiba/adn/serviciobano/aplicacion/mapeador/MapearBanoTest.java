package com.ceiba.adn.serviciobano.aplicacion.mapeador;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoBano;
import com.ceiba.adn.serviciobano.dominio.modelo.Bano;
import com.ceiba.adn.serviciobano.testdatabuilder.BanoTestDataBuilder;
import com.ceiba.adn.serviciobano.testdatabuilder.ComandoBanoTestDataBuilder;

public class MapearBanoTest {

	private MapearBano mapper;

	@Test
	public void cuandoComandoBanoEsNuloEntoncesRetornaNulo() {
		// arrange
		mapper = new MapearBano();
		ComandoBano bano = null;

		// act
		Bano b = mapper.mapearA(bano);

		// assert
		assertNull(b);
	}

	@Test
	public void cuandoComandoBanoOkEntoncesRetornaBano() {
		// arrange
		mapper = new MapearBano();
		ComandoBano bano = new ComandoBanoTestDataBuilder().build();

		// act
		Bano b = mapper.mapearA(bano);

		// assert
		assertNotNull(b);
		assertEquals(b.getId(), bano.getId());
		assertEquals(b.getIdentificador(), bano.getIdentificador());
		assertEquals(b.getEstado(), bano.getEstado());
	}

	@Test
	public void cuandoBanoEsNuloEntoncesRetornaNulo() {
		// arrange
		mapper = new MapearBano();
		Bano bano = null;

		// act
		ComandoBano b = mapper.mapearDesde(bano);

		// assert
		assertNull(b);
	}

	@Test
	public void cuandoBanoOkEntoncesRetornaComandoBano() {
		// arrange
		mapper = new MapearBano();
		Bano bano = new BanoTestDataBuilder().build();

		// act
		ComandoBano b = mapper.mapearDesde(bano);

		// assert
		assertNotNull(b);
		assertEquals(b.getId(), bano.getId());
		assertEquals(b.getIdentificador(), bano.getIdentificador());
		assertEquals(b.getEstado(), bano.getEstado());
	}

}
