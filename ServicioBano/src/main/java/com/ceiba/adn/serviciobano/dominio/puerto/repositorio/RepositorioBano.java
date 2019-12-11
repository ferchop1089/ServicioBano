package com.ceiba.adn.serviciobano.dominio.puerto.repositorio;

import java.util.List;
import java.util.Optional;

import com.ceiba.adn.serviciobano.dominio.modelo.Bano;

public interface RepositorioBano {

	public Long crear(Bano bano);

	public void actualizar(Bano bano);

	public void eliminar(Long id);

	public Optional<Bano> buscarPorId(Long id);

	public Optional<Bano> buscarPorIdentificador(String identificador);

	public String estadoBano(Long id);

	public boolean existePorId(Long id);

	public boolean existePorIdentificador(String identificador);

	public List<Bano> listar();

}
