package com.ceiba.adn.serviciobano.dominio.servicio.bano;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ceiba.adn.serviciobano.dominio.modelo.Bano;
import com.ceiba.adn.serviciobano.dominio.modelo.EstadoBano;
import com.ceiba.adn.serviciobano.dominio.puerto.repositorio.RepositorioBano;
import com.ceiba.adn.serviciobano.testdatabuilder.BanoTestDataBuilder;

public class ServicioConsultasBanoTest {

	private ServicioConsultasBano servicio;

	@Mock
	private RepositorioBano repositorio;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		servicio = new ServicioConsultasBano(repositorio);
	}

	@Test
	public void cuandoBuscaBanoPorIdEntoncesRetornaBano() {
		// arrange
		Bano bano = new BanoTestDataBuilder().build();
		doReturn(Optional.of(bano)).when(repositorio).buscarPorId(1L);

		// act
		Optional<Bano> encontrado = servicio.buscarBano(1L);

		// assert
		assertTrue(encontrado.isPresent());
		assertThat(encontrado.get().getId(), equalTo(1L));
		assertThat(encontrado.get().getIdentificador(), equalTo(bano.getIdentificador()));
		assertThat(encontrado.get().getEstado(), equalTo(EstadoBano.DISPONIBLE.getEstado()));

		verify(repositorio, times(1)).buscarPorId(anyLong());
		ArgumentCaptor<Long> objCaptor = ArgumentCaptor.forClass(Long.class);
		verify(repositorio).buscarPorId(objCaptor.capture());
		assertThat(objCaptor.getValue(), is(1L));
	}

	@Test
	public void cuandoBuscaBanoPorIdentificadorEntoncesRetornaBano() {
		// arrange
		Bano bano = new BanoTestDataBuilder().build();
		doReturn(Optional.of(bano)).when(repositorio).buscarPorIdentificador(bano.getIdentificador());

		// act
		Optional<Bano> encontrado = servicio.buscarBano(bano.getIdentificador());

		// assert
		assertTrue(encontrado.isPresent());
		assertThat(encontrado.get().getId(), equalTo(1L));
		assertThat(encontrado.get().getIdentificador(), equalTo(bano.getIdentificador()));
		assertThat(encontrado.get().getEstado(), equalTo(EstadoBano.DISPONIBLE.getEstado()));

		verify(repositorio, times(1)).buscarPorIdentificador(anyString());
		ArgumentCaptor<String> objCaptor = ArgumentCaptor.forClass(String.class);
		verify(repositorio).buscarPorIdentificador(objCaptor.capture());
		assertThat(objCaptor.getValue(), is(bano.getIdentificador()));
	}

	@Test
	public void cuandoConsultaEstadoBanoEntoncesRetornaElEstado() {
		// arrange
		Bano bano = new BanoTestDataBuilder().build();
		doReturn(bano.getEstado()).when(repositorio).estadoBano(1L);

		// act
		String estado = servicio.estadoBano(1L);

		// assert
		assertThat(estado, equalTo(EstadoBano.DISPONIBLE.getEstado()));

		verify(repositorio, times(1)).estadoBano(anyLong());
		ArgumentCaptor<Long> objCaptor = ArgumentCaptor.forClass(Long.class);
		verify(repositorio).estadoBano(objCaptor.capture());
		assertThat(objCaptor.getValue(), is(bano.getId()));
	}

	@Test
	public void cuandoExisteBanoEntoncesRetornaTrue() {
		// arrange
		Bano bano = new BanoTestDataBuilder().build();
		doReturn(true).when(repositorio).existePorIdentificador(bano.getIdentificador());

		// act
		boolean existe = servicio.existeBano(bano.getIdentificador());

		// assert
		assertThat(existe, equalTo(true));

		verify(repositorio, times(1)).existePorIdentificador(anyString());
		ArgumentCaptor<String> objCaptor = ArgumentCaptor.forClass(String.class);
		verify(repositorio).existePorIdentificador(objCaptor.capture());
		assertThat(objCaptor.getValue(), is(bano.getIdentificador()));
	}

	@Test
	public void cuandoConsultaBanosEntoncesRetornaLista() {
		// arrange
		List<Bano> lista = new ArrayList<>();
		lista.add(new BanoTestDataBuilder().withId(1L).withIdentificador("Baño 1").build());
		lista.add(new BanoTestDataBuilder().withId(2L).withIdentificador("Baño 2").build());
		lista.add(new BanoTestDataBuilder().withId(3L).withIdentificador("Baño 3").build());
		
		doReturn(lista).when(repositorio).listar();

		// act
		List<Bano> listaSource = servicio.listarBanos();

		// assert
		assertThat(listaSource, is(notNullValue()));
		assertThat(listaSource.size(), equalTo(3));

		verify(repositorio, times(1)).listar();
	}

}
