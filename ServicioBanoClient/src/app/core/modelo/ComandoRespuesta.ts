import { Bano } from './Bano';

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