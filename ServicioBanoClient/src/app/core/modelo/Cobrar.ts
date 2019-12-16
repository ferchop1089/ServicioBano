import { Cuenta } from './Cuenta';

export class Cobrar {
    cuenta: Cuenta;
    tarifaSobreAdicional: number;
    tarifaMinutoAdicional: number;
    minutosPermitidos: number;
    minutosTranscurridos: number;
    minutosAdicionales: number;
    sobresAdicionales: number;
    tarifaMinutosPermitidos: number;
    subtotalMinutosAdicionales: number;
    subtotalSobresAdicionales: number;

    constructor(cuenta: Cuenta, tarifaSobreAdicional: number, tarifaMinutoAdicional: number,
                minutosPermitidos: number, minutosTranscurridos: number, minutosAdicionales: number,
                sobresAdicionales: number, tarifaMinutosPermitidos: number, subtotalMinutosAdicionales: number, 
                subtotalSobresAdicionales: number) {
        this.cuenta = cuenta;
        this.tarifaSobreAdicional = tarifaSobreAdicional;
        this.tarifaMinutoAdicional = tarifaMinutoAdicional;
        this.minutosPermitidos = minutosPermitidos;
        this.minutosTranscurridos = minutosTranscurridos;
        this.minutosAdicionales = minutosAdicionales;
        this.tarifaMinutosPermitidos = tarifaMinutosPermitidos;
        this.subtotalMinutosAdicionales = subtotalMinutosAdicionales;
        this.subtotalSobresAdicionales = subtotalSobresAdicionales;
        this.sobresAdicionales = sobresAdicionales;
    }
}
