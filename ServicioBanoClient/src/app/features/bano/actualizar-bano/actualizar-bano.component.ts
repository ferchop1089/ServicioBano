import { Component, OnInit } from '@angular/core';
import { Bano } from '../../../core/modelo/Bano';
import { EstadoBano } from '../../../core/modelo/EstadoBano';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { GestionBanoService } from '../../servicios/bano/gestion-bano.service';
import { ComandoRespuestaBano } from '../../../core/modelo/ComandoRespuesta';

@Component({
  selector: 'app-actualizar-bano',
  templateUrl: './actualizar-bano.component.html',
  styleUrls: ['./actualizar-bano.component.css']
})
export class ActualizarBanoComponent implements OnInit {

  bano: Bano;
  checkDiponible: string;
  mostrarCheck: boolean;
  id: Observable<string>;

  constructor(private route: ActivatedRoute, private gestion: GestionBanoService) {

  }

  ngOnInit() {
    this.id = this.route.paramMap.pipe(map(paramMap => paramMap.get('id')));
    this.id.subscribe({
      next: (id) => {
        this.gestion.getBanoRest(Number(id)).subscribe({
          next: (comando: ComandoRespuestaBano) => {
            console.log(comando);
            console.log(comando.respuesta);
          }
        });
      }
    });

    this.bano = new Bano(1, 'mesa', EstadoBano.FUERA_SERVICIO);
    if (this.bano.estado === EstadoBano.DISPONIBLE) {
      this.mostrarCheck = true;
      this.checkDiponible = 'checked';
    } else if (this.bano.estado === EstadoBano.OCUPADO) {
      this.mostrarCheck = false;
      this.checkDiponible = null;
    } else if (this.bano.estado === EstadoBano.FUERA_SERVICIO) {
      this.mostrarCheck = true;
      this.checkDiponible = null;
    }
  }

}
