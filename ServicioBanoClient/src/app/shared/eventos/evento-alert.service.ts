import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EventoAlertService {

  private emitChangeSource = new Subject<Alert>();
  changeEmitted$ = this.emitChangeSource.asObservable();

  constructor() { }

  public emitChange(change: Alert) {
    this.emitChangeSource.next(change);
  }

}

export class Alert {

  tipoAlerta: string;
  mensaje: string;
  showAlert: boolean;

  constructor(tipoAlerta: string, mensaje: string) {
    this.tipoAlerta = tipoAlerta;
    this.mensaje = mensaje;
  }

}
