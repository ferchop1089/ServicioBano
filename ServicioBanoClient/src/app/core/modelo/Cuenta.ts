export class Cuenta {

    id: number;
    idBano: number;
    fechaIngreso: string;
    estado: string;

    constructor(id: number, idBano: number, fechaIngreso: string, estado: string) {
        this.id = id;
        this.idBano = idBano;
        this.fechaIngreso = fechaIngreso;
        this.estado = estado;
    }
}