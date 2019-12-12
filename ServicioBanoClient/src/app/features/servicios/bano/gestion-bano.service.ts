import { Injectable } from '@angular/core';
import { Bano } from 'src/app/core/modelo/Bano';
import { EventoEliminarBanoService } from '../../../shared/eventos/evento-eliminar-bano.service';

@Injectable({
  providedIn: 'root'
})
export class GestionBanoService {

  private listaBanos: Bano[] = [];

  constructor(private _eventEliminar: EventoEliminarBanoService) { }

  public getBanos(): Bano[] {
    return this.listaBanos;
  }

  public getBano(id: number): Bano {
    return this.listaBanos[id];
  }

  crearBano(bano: Bano) {
    bano.id = this.listaBanos.length + 1;
    this.listaBanos.push(bano);
    console.log('Se agregó nuevo elemento a la lista, id del elemento: ' + bano.id);
    console.log('Número de elementos: ' + this.listaBanos.length);
  }

  public ActualizarBano(bano: Bano) {
    const index = this.listaBanos.findIndex(x => x.id === bano.id);
    if (index > -1) {
      this.listaBanos[index] = bano;
    }
  }

  public eliminarBano(id: number) {
    const index = this.listaBanos.findIndex(x => x.id === id);
    const banos: Bano[] = this.listaBanos.splice(index, 1);
    console.log('Index eliminado: ' + index);
    this._eventEliminar.emitChange(banos[0]);
    //this.listaBanos = this.listaBanos.filter(obj => obj.id !== id);
  }

}
