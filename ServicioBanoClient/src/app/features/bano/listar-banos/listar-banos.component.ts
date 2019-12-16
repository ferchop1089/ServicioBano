import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { faCog, faTimes } from '@fortawesome/free-solid-svg-icons';
import { GestionBanoService } from '../../servicios/bano/gestion-bano.service';
import { Bano } from '../../../core/modelo/Bano';
import { EventoEliminarBanoService } from '../../../shared/eventos/evento-eliminar-bano.service';
import { CompartirIdBanoService } from 'src/app/shared/eventos/compartir-id-bano.service';
import { ComandoRespuestaBanoLista } from '../../../core/modelo/ComandoRespuesta';
import { EstadoBano } from '../../../core/modelo/EstadoBano';

@Component({
  selector: 'app-listar-banos',
  templateUrl: './listar-banos.component.html',
  styleUrls: ['./listar-banos.component.css']
})
export class ListarBanosComponent implements OnInit {

  listaBanos: Bano2[] = [];
  faCog = faCog;
  faTimes = faTimes;

  constructor(private router: Router, private activateRoute: ActivatedRoute,
              private gestion: GestionBanoService,
              private shared: CompartirIdBanoService,
              private eventEliminar: EventoEliminarBanoService) {

  }

  ngOnInit() {
    this.eventEliminar.changeEmitted$.subscribe(idDeleted => {
      const index = this.listaBanos.findIndex(x => x.id === idDeleted);
      this.listaBanos.splice(index, 1);
    });

    this.gestion.listarBanos().subscribe({
      next: (rta: ComandoRespuestaBanoLista) => {
        const lista: Bano[] = rta.respuesta;
        for (let index = 0; index < lista.length; index++) {
          const element: Bano = lista[index];
          let clase: string;
          let habilitarEliminar: boolean;
          let habilitarModificar: boolean;
          if (element.estado === EstadoBano.DISPONIBLE) {
            clase = 'badge badge-primary';
            habilitarEliminar = true;
            habilitarModificar = true;
          } else if (element.estado === EstadoBano.OCUPADO) {
            clase = 'badge badge-danger';
            habilitarEliminar = false;
            habilitarModificar = true;
          } else if (element.estado === EstadoBano.FUERA_SERVICIO) {
            clase = 'badge badge-warning';
            habilitarEliminar = true;
            habilitarModificar = false;
          }

          const l: Bano2 = new Bano2(element.id, element.identificador, element.estado, clase, habilitarEliminar, habilitarModificar);
          this.listaBanos[index] = l;
        }
      },
      error: (err: any) => this.gestion.errorHandl(err)
    });
    console.log('Finaliza el ngOnInit del listar baños. Elementos listados: ' + this.listaBanos.length);
  }

  public redireccionar(estado: string, idBano: number) {
    if (estado === EstadoBano.DISPONIBLE) {
      this.router.navigate(['../crearcuenta', idBano], { relativeTo: this.activateRoute });
    } else if (estado === EstadoBano.OCUPADO) {
      this.router.navigate(['../actualizarcuenta', idBano], { relativeTo: this.activateRoute });
    } else if (estado === EstadoBano.FUERA_SERVICIO) {
      this.router.navigate(['listarbano'], { relativeTo: this.activateRoute });
    }
  }

  public redireccionarConfigBano(id: number) {
    this.router.navigate(['../actualizarbano', id], { relativeTo: this.activateRoute });
  }

  public publicarIdBano(id: number) {
    this.shared.emitChange(id);
  }

}

class Bano2 {

  id: number;
  identificador: string;
  estado: string;
  clase: string;
  habilitar: boolean;
  habilitarEliminar: boolean;
  habilitarModificar: boolean;

  constructor(id: number, identificador: string, estado: string, clase: string, habilitarEliminar: boolean, habilitarModificar: boolean) {
    this.id = id;
    this.identificador = identificador;
    this.estado = estado;
    this.clase = clase;
    this.habilitarEliminar = habilitarEliminar;
    this.habilitarModificar = habilitarModificar;
  }
}
