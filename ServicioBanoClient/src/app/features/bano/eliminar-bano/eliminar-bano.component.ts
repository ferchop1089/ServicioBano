import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { CompartirIdBanoService } from '../../../shared/eventos/compartir-id-bano.service';
import { GestionBanoService } from '../../servicios/bano/gestion-bano.service';
import { EventoEliminarBanoService } from '../../../shared/eventos/evento-eliminar-bano.service';
import { EventoAlertService, Alert } from '../../../shared/eventos/evento-alert.service';
//import * as $ from 'jquery';
import { Bano } from '../../../core/modelo/Bano';

@Component({
  selector: 'app-eliminar-bano',
  templateUrl: './eliminar-bano.component.html',
  styleUrls: ['./eliminar-bano.component.css']
})
export class EliminarBanoComponent implements OnInit {

  idBano: number;

  @ViewChild('mmodalOcultar', { static: false })
  closeModal: ElementRef;

  constructor(private shared: CompartirIdBanoService, private eventEliminar: EventoEliminarBanoService, private eventAlert: EventoAlertService, private gestion: GestionBanoService) {
  }

  ngOnInit() {
    this.shared.changeEmitted$.subscribe(id => {
      this.idBano = id;
    });
    this.eventEliminar.changeEmitted$.subscribe(item => {
      const tipoAlerta = 'alert-success';
      const mensaje = 'El registro fue borrado <strong>exitosamente</strong>';
      this.closeModal.nativeElement.click();
      this.eventAlert.emitChange(new Alert(tipoAlerta, mensaje));
    });
  }

  public eliminar() {
    //this.gestion.eliminarBano(this.idBano);
    this.gestion.eliminarBanoRest(this.idBano).subscribe({
      next: (item: Bano) => {
        const tipoAlerta = 'alert-success';
        const mensaje = 'El registro fue borrado <strong>exitosamente</strong>';
        this.closeModal.nativeElement.click();
        this.eventAlert.emitChange(new Alert(tipoAlerta, mensaje));
      },
      error: (err: any) => this.gestion.errorHandl(err)
    });
  }

}
