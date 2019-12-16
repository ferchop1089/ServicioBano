package com.ceiba.adn.serviciobano.infraestructura.adaptador.repositorio;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ceiba.adn.serviciobano.comun.mapeador.Mapeador;
import com.ceiba.adn.serviciobano.dominio.modelo.Bano;
import com.ceiba.adn.serviciobano.dominio.puerto.repositorio.RepositorioBano;
import com.ceiba.adn.serviciobano.infraestructura.adaptador.repositorio.entidad.BanoEntidad;

@Component
public class RespositorioBanoAdaptador implements RepositorioBano {

	private RepositorioBanoJpa jpa;

	private Mapeador<BanoEntidad, Bano> mapper;

	public RespositorioBanoAdaptador(RepositorioBanoJpa jpa, Mapeador<BanoEntidad, Bano> mapper) {
		this.jpa = jpa;
		this.mapper = mapper;
	}

	@Override
	public Long crear(Bano bano) {
		return guardar(bano);
	}

	@Override
	public void actualizar(Bano bano) {
		guardar(bano);
	}

	private Long guardar(Bano bano) {
		BanoEntidad entidad = mapper.mapearDesde(bano);
		if (Objects.isNull(entidad.getId())) {
			entidad.setId(RepositorioHelper.getNuevoId(jpa));
		}
		jpa.saveAndFlush(entidad);
		return entidad.getId();
	}

	@Override
	public void eliminar(Long id) {
		jpa.deleteById(id);
	}

	@Override
	public Optional<Bano> buscarPorId(Long id) {
		return jpa.findById(id).map(mapper::mapearA);
	}

	@Override
	public Optional<Bano> buscarPorIdentificador(String identificador) {
		return jpa.findByIdentificador(identificador).map(mapper::mapearA);
	}

	@Override
	public String estadoBano(Long id) {
		return jpa.findEstadoBanoEntidadById(id);
	}

	@Override
	public boolean existePorIdentificador(String identificador) {
		return jpa.existsByIdentificador(identificador);
	}

	@Override
	public List<Bano> listar() {
		List<BanoEntidad> entidades = jpa.findAll();
		return entidades.stream().map(mapper::mapearA).collect(Collectors.toList());
	}

	@Override
	public boolean existePorId(Long id) {
		return jpa.existsById(id);
	}

}
