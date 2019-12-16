package com.ceiba.adn.serviciobano.infraestructura.controlador;

import java.util.List;

import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoBano;
import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoRespuesta;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorActualizarBano;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorConsultasBano;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorCrearBano;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorEliminarBano;

@CrossOrigin
@RestController
@RequestMapping("/servicio-bano/bano")
public class ComandoControladorBano {

	private ManejadorCrearBano crearBano;
	private ManejadorActualizarBano actualizarBano;
	private ManejadorEliminarBano eliminarBano;
	private ManejadorConsultasBano consultasBano;

	public ComandoControladorBano(ManejadorCrearBano crearBano, ManejadorActualizarBano actualizarBano,
			ManejadorEliminarBano eliminarBano, ManejadorConsultasBano consultasBano) {
		this.crearBano = crearBano;
		this.actualizarBano = actualizarBano;
		this.eliminarBano = eliminarBano;
		this.consultasBano = consultasBano;
	}

	@PostMapping(produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ComandoRespuesta<Long> crear(@RequestBody ComandoBano bano) {
		return crearBano.ejecutar(bano);
	}

	@PutMapping(produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public void actualizar(@RequestBody ComandoBano bano) {
		actualizarBano.ejecutar(bano);
	}

	@DeleteMapping(value = "/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public void eliminar(@PathVariable Long id) {
		eliminarBano.ejecutar(id);
	}

	@GetMapping(produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ComandoRespuesta<List<ComandoBano>> consultarBanos() {
		return consultasBano.ejecutar();
	}

	@GetMapping(value = "/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ComandoRespuesta<ComandoBano> consultarBano(@PathVariable Long id) {
		return consultasBano.ejecutar(id);
	}

}
