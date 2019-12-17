import { Injectable, ErrorHandler, Injector, NgZone } from '@angular/core';
import { EventoAlertService, Alert } from '../eventos/evento-alert.service';
import { HttpErrorResponse } from '@angular/common/http';
import { isUndefined, isNull } from 'util';

@Injectable({
  providedIn: 'root'
})
export class ManejadorErroresService implements ErrorHandler {

  private eventAlert: EventoAlertService;
  private zone: NgZone;
  constructor(private injector: Injector) { }

  handleError(error: any) {
    if (isNull(this.eventAlert) || isUndefined(this.eventAlert)) {
      this.eventAlert = this.injector.get(EventoAlertService);
    }
    if (isNull(this.zone) || isUndefined(this.zone)) {
      this.zone = this.injector.get(NgZone);
    }

    let errorMessage;
    if (error instanceof HttpErrorResponse) {
      // Error servidor
      errorMessage = error.error.message;
      if (isUndefined(errorMessage)) {
        errorMessage = error.message;
      }
    } else {
      // Error cliente
      errorMessage = 'Error Code:' + error.status + ' - Message: ' + errorMessage;
    }

    this.zone.run(() => {
      this.eventAlert.emitChange(new Alert('alert-danger', errorMessage));
    });

    console.error(error);
  }

}
