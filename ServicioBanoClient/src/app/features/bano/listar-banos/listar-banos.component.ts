import { Component, OnInit } from '@angular/core';
import { ConsultarBanosService, Bano } from '../../servicios/consultar-banos.service';

@Component({
  selector: 'app-listar-banos',
  templateUrl: './listar-banos.component.html',
  styleUrls: ['./listar-banos.component.css']
})
export class ListarBanosComponent implements OnInit {

  listaBanos: Bano2[] = [];


  constructor(private _consultarBanosService: ConsultarBanosService) { }

  ngOnInit() {
    const lista: Bano[] = this._consultarBanosService.getBanos();
    for (let index = 0; index < lista.length; index++) {
      const element: Bano = lista[index];
      let clase: string;
      if (element.estado === 'DISPONIBLE') {
        clase = 'badge badge-primary';
      } else if (element.estado === 'OCUPADO') {
        clase = 'badge badge-danger';
      } else {
        clase = 'badge badge-warning';
      }

      const l: Bano2 = new Bano2(element.id, element.identificador, element.estado, clase);
      this.listaBanos[index] = l;
    }
    console.log(this.listaBanos);
  }

}

class Bano2 {

  id: number;
  identificador: string;
  estado: string;
  clase: string;

  constructor(id: number, identificador: string, estado: string, clase: string) {
    this.id = id;
    this.identificador = identificador;
    this.estado = estado;
    this.clase = clase;
  }
}
