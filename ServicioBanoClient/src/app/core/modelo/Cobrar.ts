import { Cuenta } from './Cuenta';

export class Cobrar {
    cuenta: Cuenta;
    valorSobreAdicional: number;
    valorMinutoAdicional: number;
    minutosPermitidos: number;
    minutosTranscurridos: number;
    minutosAdicionales: number;

    constructor(cuenta: Cuenta, valorSobreAdicional: number, valorMinutoAdicional: number,
                minutosPermitidos: number, minutosTranscurridos: number, minutosAdicionales: number) {
        this.cuenta = cuenta;
        this.valorSobreAdicional = valorSobreAdicional;
        this.valorMinutoAdicional = valorMinutoAdicional;
        this.minutosPermitidos = minutosPermitidos;
        this.minutosTranscurridos = minutosTranscurridos;
        this.minutosAdicionales = minutosAdicionales;
    }
}