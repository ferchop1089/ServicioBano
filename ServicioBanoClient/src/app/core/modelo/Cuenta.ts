export class Cuenta {
    id: number;
    idBano: number;
    fechaIngreso: string;
    estado: string;
    sobres: number;
    totalCobro: number;

    constructor(id: number, idBano: number, fechaIngreso: string, estado: string, sobres: number, totalCobro: number) {
        this.id = id;
        this.idBano = idBano;
        this.fechaIngreso = fechaIngreso;
        this.estado = estado;
        this.sobres = sobres;
        this.totalCobro = totalCobro;
    }
}