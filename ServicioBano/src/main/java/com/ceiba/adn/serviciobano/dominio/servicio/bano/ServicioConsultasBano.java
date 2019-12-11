package com.ceiba.adn.serviciobano.dominio.servicio.bano;

import java.util.List;
import java.util.Optional;

import com.ceiba.adn.serviciobano.dominio.modelo.Bano;
import com.ceiba.adn.serviciobano.dominio.puerto.repositorio.RepositorioBano;

public class ServicioConsultasBano {

	private RepositorioBano repositorio;

	public ServicioConsultasBano(RepositorioBano repositorio) {
		this.repositorio = repositorio;
	}

	public Optional<Bano> buscarBano(Long id) {
		return repositorio.buscarPorId(id);
	}

	public Optional<Bano> buscarBano(String identificador) {
		return repositorio.buscarPorIdentificador(identificador);
	}

	public String estadoBano(Long id) {
		return repositorio.estadoBano(id);
	}

	public boolean existeBano(String identificador) {
		return repositorio.existePorIdentificador(identificador);
	}

	public List<Bano> listarBanos() {
		return repositorio.listar();
	}

}
