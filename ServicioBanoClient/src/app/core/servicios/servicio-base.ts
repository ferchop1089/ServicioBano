import { HttpHeaders } from '@angular/common/http';
import { throwError } from 'rxjs';
import { Alert, EventoAlertService } from '../../shared/eventos/evento-alert.service';
import { isUndefined } from 'util';


export class ServicioBase {

    eventAlert1: EventoAlertService;

    constructor(private eventAlert: EventoAlertService) {
        this.eventAlert1 = eventAlert;
    }

    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    public errorHandl0(error) {
        let errorMessage = '';
        if (error.error instanceof ErrorEvent) {
            // Get client-side error
            errorMessage = error.error.message;
        } else {
            // Get server-side error
            errorMessage = error.error.message;
            if (isUndefined(errorMessage )) {
                errorMessage = error.message;
            }
        }
        console.log(`Error Code: ${error.status}\n, Message: ${errorMessage}`);
        this.eventAlert.emitChange(new Alert('alert-danger', errorMessage));
        return throwError(errorMessage);
    }
}

