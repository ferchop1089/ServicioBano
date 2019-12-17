import { HttpHeaders } from '@angular/common/http';
import { throwError } from 'rxjs';
import { Alert, EventoAlertService } from '../../shared/eventos/evento-alert.service';
import { isUndefined } from 'util';


export class ServicioBase {

    constructor() {
    }

    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

}

