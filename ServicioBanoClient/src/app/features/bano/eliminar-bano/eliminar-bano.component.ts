import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { CompartirIdBanoService } from '../../../shared/eventos/compartir-id-bano.service';
import { GestionBanoService } from '../../servicios/bano/gestion-bano.service';
import { EventoEliminarBanoService } from '../../../shared/eventos/evento-eliminar-bano.service';
import { EventoAlertService, Alert } from '../../../shared/eventos/evento-alert.service';

@Component({
  selector: 'app-eliminar-bano',
  templateUrl: './eliminar-bano.component.html',
  styleUrls: ['./eliminar-bano.component.css']
})
export class EliminarBanoComponent implements OnInit {

  idBano: number;

  @ViewChild('ocultarModalEliminarBano', { static: false })
  closeModal: ElementRef;

  constructor(private compartirIdBano: CompartirIdBanoService, private eventEliminar: EventoEliminarBanoService,
              private eventAlert: EventoAlertService, private gestion: GestionBanoService) {
  }

  ngOnInit() {
    this.compartirIdBano.changeEmitted$.subscribe(id => {
      this.idBano = id;
    });
  }

  public eliminar() {
    this.gestion.eliminarBano(this.idBano).subscribe({
      complete: () => {
        const tipoAlerta = 'alert-success';
        const mensaje = 'El registro fue borrado <strong>exitosamente</strong>';
        this.closeModal.nativeElement.click();
        this.eventEliminar.emitChange(this.idBano);
        this.eventAlert.emitChange(new Alert(tipoAlerta, mensaje));
      }
    });
  }

}
