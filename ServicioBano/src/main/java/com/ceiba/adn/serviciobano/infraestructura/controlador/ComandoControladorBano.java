package com.ceiba.adn.serviciobano.infraestructura.controlador;

import org.springframework.http.HttpStatus;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoBano;
import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoRespuesta;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorActualizarBano;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorCrearBano;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorEliminarBano;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionLongitudValor;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionRestriccion;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionSinDatos;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorObligatorio;

@RestController
@RequestMapping("/servicio-bano")
public class ComandoControladorBano {

	private ManejadorCrearBano crearBano;
	private ManejadorActualizarBano actualizarBano;
	private ManejadorEliminarBano eliminarBano;

	public ComandoControladorBano(ManejadorCrearBano crearBano,
			ManejadorActualizarBano actualizarBano, ManejadorEliminarBano eliminarBano) {
		this.crearBano = crearBano;
		this.actualizarBano = actualizarBano;
		this.eliminarBano = eliminarBano;
	}

	@PostMapping(value = "/crear", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ComandoRespuesta<Long> crear(@RequestBody ComandoBano bano) {
		try {
			return crearBano.ejecutar(bano);
		} catch (ExcepcionValorInvalido | ExcepcionValorObligatorio | ExcepcionLongitudValor | ExcepcionSinDatos e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (ExcepcionDuplicidad e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@PutMapping(value = "/actualizar", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public void actualizar(@RequestBody ComandoBano bano) {
		try {
			actualizarBano.ejecutar(bano);
		} catch (ExcepcionValorInvalido | ExcepcionValorObligatorio | ExcepcionLongitudValor | ExcepcionSinDatos e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (ExcepcionDuplicidad e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@DeleteMapping(value = "/eliminar/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public void eliminar(@PathVariable Long id) {
		try {
			eliminarBano.ejecutar(id);
		} catch (ExcepcionValorInvalido | ExcepcionValorObligatorio | ExcepcionLongitudValor | ExcepcionSinDatos e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (ExcepcionDuplicidad e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		} catch (ExcepcionRestriccion e) {
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, e.getMessage(), e);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

}
