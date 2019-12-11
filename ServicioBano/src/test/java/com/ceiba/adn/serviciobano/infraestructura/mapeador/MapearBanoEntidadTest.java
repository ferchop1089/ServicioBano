package com.ceiba.adn.serviciobano.infraestructura.mapeador;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.ceiba.adn.serviciobano.dominio.modelo.Bano;
import com.ceiba.adn.serviciobano.infraestructura.adaptador.repositorio.entidad.BanoEntidad;
import com.ceiba.adn.serviciobano.testdatabuilder.BanoEntidadTestDataBuilder;
import com.ceiba.adn.serviciobano.testdatabuilder.BanoTestDataBuilder;

public class MapearBanoEntidadTest {

	private MapearBanoEntidad mapper;

	@Test
	public void cuandoBanoEntidadEsNuloEntoncesRetornaNulo() {
		// arrange
		mapper = new MapearBanoEntidad();
		BanoEntidad comando = null;

		// act
		Bano b = mapper.mapearA(comando);

		// assert
		assertNull(b);
	}

	@Test
	public void cuandoBanoEntidadOkEntoncesRetornaBano() {
		// arrange
		mapper = new MapearBanoEntidad();
		BanoEntidad comando = new BanoEntidadTestDataBuilder().build();

		// act
		Bano b = mapper.mapearA(comando);

		// assert
		assertNotNull(b);
		assertEquals(b.getId(), comando.getId());
		assertEquals(b.getEstado(), comando.getEstado());
		assertEquals(b.getIdentificador(), comando.getIdentificador());
	}

	@Test
	public void cuandoBanoEsNuloEntoncesRetornaNulo() {
		// arrange
		mapper = new MapearBanoEntidad();
		Bano comando = null;

		// act
		BanoEntidad b = mapper.mapearDesde(comando);

		// assert
		assertNull(b);
	}

	@Test
	public void cuandoBanoOkEntoncesRetornaBanoEntidad() {
		// arrange
		mapper = new MapearBanoEntidad();
		Bano comando = new BanoTestDataBuilder().build();

		// act
		BanoEntidad b = mapper.mapearDesde(comando);

		// assert
		assertNotNull(b);
		assertEquals(b.getId(), comando.getId());
		assertEquals(b.getEstado(), comando.getEstado());
		assertEquals(b.getIdentificador(), comando.getIdentificador());
	}

}
