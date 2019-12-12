import { Injectable } from '@angular/core';
import { Bano } from 'src/app/core/modelo/Bano';

@Injectable({
  providedIn: 'root'
})
export class ConsultarBanosService {

  private listaBanos: Bano[] = [
    {
      id: 1,
      identificador: 'Baño 1',
      estado: 'FUERA DE SERVICIO'
    },
    {
      id: 2,
      identificador: 'Baño 2',
      estado: 'OCUPADO'
    },
    {
      id: 3,
      identificador: 'Baño 3',
      estado: 'FUERA DE SERVICIO'
    },
    {
      id: 4,
      identificador: 'Baño 4',
      estado: 'DISPONIBLE'
    },
    {
      id: 5,
      identificador: 'Baño 5',
      estado: 'DISPONIBLE'
    }
  ];

  constructor() {
    console.log('Servicio iniciado');
  }

  getBanos(): Bano[] {
    return this.listaBanos;
  }

  public getBano(id: number): Bano {
    return this.listaBanos[id];
  }

}
