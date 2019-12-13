package com.ceiba.adn.serviciobano.infraestructura.controlador;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoCuenta;
import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoRespuesta;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorActualizarCuenta;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorCobrarCuenta;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorConsultasCuenta;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorCrearCuenta;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorPagarCuenta;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionLongitudValor;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionRestriccion;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionSinDatos;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionValorObligatorio;

@CrossOrigin
@RestController
@RequestMapping("/servicio-bano/cuenta")
public class ComandoControladorCuenta {

	private ManejadorCrearCuenta crearCuenta;
	private ManejadorActualizarCuenta actualizarCuenta;
	private ManejadorCobrarCuenta cobrarCuenta;
	private ManejadorPagarCuenta pagarCuenta;
	private ManejadorConsultasCuenta consultarCuenta;

	public ComandoControladorCuenta(ManejadorCrearCuenta crearCuenta, ManejadorActualizarCuenta actualizarCuenta,
			ManejadorCobrarCuenta cobrarCuenta, ManejadorPagarCuenta pagarCuenta, ManejadorConsultasCuenta consultarCuenta) {
		this.crearCuenta = crearCuenta;
		this.actualizarCuenta = actualizarCuenta;
		this.cobrarCuenta = cobrarCuenta;
		this.pagarCuenta = pagarCuenta;
		this.consultarCuenta = consultarCuenta;
	}

	@PostMapping(value = "/crear", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ComandoRespuesta<Long> crear(@RequestBody ComandoCuenta cuenta) {
		try {
			return crearCuenta.ejecutar(cuenta);
		} catch (ExcepcionValorInvalido | ExcepcionValorObligatorio | ExcepcionLongitudValor | ExcepcionSinDatos e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (ExcepcionDuplicidad e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@PutMapping(value = "/actualizar", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public void actualizar(@RequestBody ComandoCuenta cuenta) {
		try {
			actualizarCuenta.ejecutar(cuenta);
		} catch (ExcepcionValorInvalido | ExcepcionValorObligatorio | ExcepcionLongitudValor | ExcepcionSinDatos e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@GetMapping(value = "/cobrar/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ComandoRespuesta<BigDecimal> cobrar(@PathVariable Long id) {
		try {
			return cobrarCuenta.ejecutar(id);
		} catch (ExcepcionValorInvalido | ExcepcionValorObligatorio | ExcepcionLongitudValor | ExcepcionSinDatos e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@GetMapping(value = "/consultar/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ComandoRespuesta<ComandoCuenta> consultar(@PathVariable Long id) {
		try {
			return consultarCuenta.ejecutar(id);
		} catch (ExcepcionValorInvalido | ExcepcionValorObligatorio | ExcepcionLongitudValor | ExcepcionSinDatos e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@PutMapping(value = "/pagar", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public void pagar(@RequestBody ComandoCuenta cuenta) {
		try {
			pagarCuenta.ejecutar(cuenta);
		} catch (ExcepcionValorInvalido | ExcepcionValorObligatorio | ExcepcionLongitudValor | ExcepcionSinDatos e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (ExcepcionRestriccion e) {
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, e.getMessage(), e);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

}
