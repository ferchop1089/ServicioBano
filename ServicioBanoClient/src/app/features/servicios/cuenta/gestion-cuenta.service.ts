import { Injectable } from '@angular/core';
import { Cuenta } from '../../../core/modelo/Cuenta';
import { ComandoRespuestaCuenta } from '../../../core/modelo/ComandoRespuesta';
import { Observable } from 'rxjs';
import { EventoAlertService } from '../../../shared/eventos/evento-alert.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import { ServicioBase } from '../../../core/servicios/servicio-base';

@Injectable({
  providedIn: 'root'
})
export class GestionCuentaService extends ServicioBase {

  private baseUrl = 'http://localhost:8080/servicio-bano/cuenta';

  constructor(eventAlert: EventoAlertService, private http: HttpClient) {
    super(eventAlert);
  }

  public crearCuentaRest(cuenta: Cuenta): Observable<ComandoRespuestaCuenta> {
    return this.http.post<number>(this.baseUrl + '/crear', cuenta, this.httpOptions)
      .pipe(map(idNuevo => {
        return new ComandoRespuestaCuenta(new Cuenta(idNuevo, cuenta.idBano, cuenta.fechaIngreso,
          cuenta.estado, cuenta.sobres, cuenta.totalCobro));
      }), catchError(err => this.errorHandl(err)));
  }

  public getCuentaPorIdBanoRest(idBano: number): Observable<ComandoRespuestaCuenta> {
    return this.http.get<ComandoRespuestaCuenta>(this.baseUrl + '/consultar/' + idBano, this.httpOptions);
  }

  public actualizarCuentaRest(cuenta: Cuenta): Observable<any> {
    return this.http.put(this.baseUrl + '/actualizar', cuenta, this.httpOptions);
  }

}
