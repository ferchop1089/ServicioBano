import { Bano } from './Bano';
import { Cuenta } from './Cuenta';
import { Cobrar } from './Cobrar';

export class ComandoRespuestaBano {
    respuesta: Bano;

    constructor(respuesta: Bano) {
        this.respuesta = respuesta;
    }
}

export class ComandoRespuestaBanoLista {
    respuesta: Bano[];

    constructor(respuesta: Bano[]) {
        this.respuesta = respuesta;
    }
}

export class ComandoRespuestaCuenta {
    respuesta: Cuenta;

    constructor(respuesta: Cuenta) {
        this.respuesta = respuesta;
    }
}

export class ComandoRespuestaCobrar {
    respuesta: Cobrar;

    constructor(respuesta: Cobrar) {
        this.respuesta = respuesta;
    }
}