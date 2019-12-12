import { Component, OnInit } from '@angular/core';
import { ConsultarBanosService, Bano } from '../../servicios/consultar-banos.service';
import { Router, ActivatedRoute } from '@angular/router';
import { faCog, faTimes } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-listar-banos',
  templateUrl: './listar-banos.component.html',
  styleUrls: ['./listar-banos.component.css']
})
export class ListarBanosComponent implements OnInit {

  listaBanos: Bano2[] = [];
  ESTADO_DISPONIBLE = 'DISPONIBLE';
  ESTADO_OCUPADO = 'OCUPADO';
  ESTADO_FUERA_SERVICIO = 'FUERA DE SERVICIO';
  faCog = faCog;
  faTimes = faTimes;


  constructor(private router: Router, private activateRoute: ActivatedRoute, private _consultarBanosService: ConsultarBanosService) { }

  ngOnInit() {
    const lista: Bano[] = this._consultarBanosService.getBanos();
    for (let index = 0; index < lista.length; index++) {
      const element: Bano = lista[index];
      let clase: string;
      let habilitar: boolean;
      if (element.estado === this.ESTADO_DISPONIBLE) {
        clase = 'badge badge-primary';
        habilitar = true;
      } else if (element.estado === this.ESTADO_OCUPADO) {
        clase = 'badge badge-danger';
        habilitar = true;
      } else if (element.estado === this.ESTADO_FUERA_SERVICIO) {
        clase = 'badge badge-warning';
        habilitar = false;
      }

      const l: Bano2 = new Bano2(element.id, element.identificador, element.estado, clase, habilitar);
      this.listaBanos[index] = l;
    }
    // console.log(this.listaBanos);
  }

  public redireccionar(estado: string, id: number) {
    if (estado === this.ESTADO_DISPONIBLE) {
      this.router.navigate(['../crearcuenta', id], { relativeTo: this.activateRoute });
    } else if (estado === this.ESTADO_OCUPADO) {
      this.router.navigate(['../actualizarcuenta', id], { relativeTo: this.activateRoute });
    } else if (estado === this.ESTADO_FUERA_SERVICIO) {
      this.router.navigate(['listarbano'], { relativeTo: this.activateRoute });
    }
  }

  public redireccionarConfigBano(id: number) {
    this.router.navigate(['../actualizarbano', id], { relativeTo: this.activateRoute });
  }

}

class Bano2 {

  id: number;
  identificador: string;
  estado: string;
  clase: string;
  habilitar: boolean;

  constructor(id: number, identificador: string, estado: string, clase: string, habilitar: boolean) {
    this.id = id;
    this.identificador = identificador;
    this.estado = estado;
    this.clase = clase;
    this.habilitar = habilitar;
  }
}
