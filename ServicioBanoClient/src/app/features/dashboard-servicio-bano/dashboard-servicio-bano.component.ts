import { Component, OnInit } from '@angular/core';
import { EventoAlertService, Alert } from '../../shared/eventos/evento-alert.service';

@Component({
  selector: 'app-dashboard-servicio-bano',
  templateUrl: './dashboard-servicio-bano.component.html',
  styleUrls: ['./dashboard-servicio-bano.component.css']
})
export class DashboardServicioBanoComponent implements OnInit {

  showMensajeExitoso = false;
  tipoAlert: string;
  mensaje: string;

  constructor(private alert: EventoAlertService) { }

  ngOnInit() {
    this.alert.changeEmitted$.subscribe(alert => {
      this.tipoAlert = alert.tipoAlerta;
      this.mensaje = alert.mensaje;
      this.showMensajeExitoso = true;
    });
  }

  public ocultarMensaje() {
    this.showMensajeExitoso = false;
  }

}
