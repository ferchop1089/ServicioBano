package com.ceiba.adn.serviciobano.dominio.servicio.cuenta;

import static com.ceiba.adn.serviciobano.dominio.comun.ValidadorArgumento.validarObligatorio;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import com.ceiba.adn.serviciobano.comun.puerto.Propiedades;
import com.ceiba.adn.serviciobano.dominio.excepcion.ExcepcionSinDatos;
import com.ceiba.adn.serviciobano.dominio.modelo.Cobrar;
import com.ceiba.adn.serviciobano.dominio.modelo.Cuenta;
import com.ceiba.adn.serviciobano.dominio.puerto.repositorio.RepositorioCuenta;

public class ServicioCobrarCuenta {

	private static final String MSG_CUENTA_ID_OBLIGATORIO = "msg.err.servicio.cobrar.cuenta.id.obligatorio";
	private static final String MSG_CUENTA_CON_ID_NO_EXISTE = "msg.err.servicio.cobrar.cuenta.id.no.existe";

	private static final String CONFIG_MINUTOS_PERMITIDOS = "prop.cobro.minutos.permitidos";
	private static final String CONFIG_TARIFA_MINUTO_ADICIONAL = "prop.cobro.tarifa.minuto.adicional";
	private static final String CONFIG_TARIFA_SOBRE_ADICIONAL = "prop.cobro.tarifa.sobre.adicional";
	private static final String CONFIG_TARIFA_INICIAL = "prop.cobro.tarifa.inicial";

	private RepositorioCuenta repositorio;
	private Propiedades propMsg;
	private Propiedades propConfig;

	public ServicioCobrarCuenta(RepositorioCuenta repositorio, Propiedades propMsg, Propiedades propConfig) {
		this.repositorio = repositorio;
		this.propMsg = propMsg;
		this.propConfig = propConfig;
	}

	public Cobrar cobrar(Long id) {
		validarObligatorio(id, propMsg.getPropiedad(MSG_CUENTA_ID_OBLIGATORIO));

		Optional<Cuenta> cuentaOpt = repositorio.buscarPorId(id);
		if (!cuentaOpt.isPresent()) {
			throw new ExcepcionSinDatos(propMsg.getPropiedad(MSG_CUENTA_CON_ID_NO_EXISTE));
		}

		Cuenta cuenta = cuentaOpt.get();
		return calcularCuenta(cuenta);
	}

	private Cobrar calcularCuenta(Cuenta cuenta) {
		Integer sobres = cuenta.getSobres();
		LocalDateTime fechaIngreso = cuenta.getFechaIngreso();
		LocalDateTime ahora = LocalDateTime.now();
		
		Long minutosAdicionales = 0L;
		Long sobresAdicionales = 0L;

		Long minutosPermitidos = Long.parseLong(propConfig.getPropiedad(CONFIG_MINUTOS_PERMITIDOS)); // 15 min
		BigDecimal tarifaSobreAdicional = new BigDecimal(propConfig.getPropiedad(CONFIG_TARIFA_SOBRE_ADICIONAL)); // $200
		BigDecimal tarifaMinutoAdicional = new BigDecimal(propConfig.getPropiedad(CONFIG_TARIFA_MINUTO_ADICIONAL)); // $200
		BigDecimal tarifaMinutosPermitidos = new BigDecimal(propConfig.getPropiedad(CONFIG_TARIFA_INICIAL)); // $1000

		BigDecimal subtotalMinutosAdicionales = new BigDecimal(0);
		BigDecimal subtotalSobresAdicionales = new BigDecimal(0);
		Long minutosTranscurridos = Duration.between(fechaIngreso, ahora).toMinutes();

		if (minutosTranscurridos > minutosPermitidos) {
			minutosAdicionales = minutosTranscurridos - minutosPermitidos;
			subtotalMinutosAdicionales = new BigDecimal(minutosAdicionales).multiply(tarifaMinutoAdicional);
		}
		if (Objects.nonNull(sobres) && sobres > 1) {
			sobresAdicionales = sobres - 1L;
			subtotalSobresAdicionales = new BigDecimal(sobresAdicionales).multiply(tarifaSobreAdicional);
		}

		BigDecimal valorTotal = tarifaMinutosPermitidos.add(subtotalMinutosAdicionales).add(subtotalSobresAdicionales);
		cuenta.setTotalCobro(valorTotal);

		return new Cobrar(cuenta, tarifaSobreAdicional, tarifaMinutoAdicional, minutosPermitidos, minutosTranscurridos,
				minutosAdicionales, tarifaMinutosPermitidos, subtotalMinutosAdicionales, subtotalSobresAdicionales,
				sobresAdicionales);
	}

}
