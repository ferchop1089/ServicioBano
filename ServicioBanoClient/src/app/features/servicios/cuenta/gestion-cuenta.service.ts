import { Injectable } from '@angular/core';
import { Cuenta } from '../../../core/modelo/Cuenta';
import { ComandoRespuestaCuenta, ComandoRespuestaCobrar } from '../../../core/modelo/ComandoRespuesta';
import { Observable } from 'rxjs';
import { EventoAlertService } from '../../../shared/eventos/evento-alert.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import { ServicioBase } from '../../../core/servicios/servicio-base';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GestionCuentaService extends ServicioBase {

  private baseUrl = environment.url_cuenta;

  constructor(eventAlert: EventoAlertService, private http: HttpClient) {
    super(eventAlert);
  }

  public crearCuenta(cuenta: Cuenta): Observable<ComandoRespuestaCuenta> {
    return this.http.post<number>(this.baseUrl, cuenta, this.httpOptions)
      .pipe(map(idNuevo => {
        return new ComandoRespuestaCuenta(new Cuenta(idNuevo, cuenta.idBano, cuenta.fechaIngreso,
          cuenta.estado, cuenta.sobres, cuenta.totalCobro));
      }));
  }

  public buscarCuentaPorIdBano(idBano: number): Observable<ComandoRespuestaCuenta> {
    return this.http.get<ComandoRespuestaCuenta>(this.baseUrl + '/bano/' + idBano, this.httpOptions);
  }

  public actualizarCuenta(cuenta: Cuenta): Observable<any> {
    return this.http.patch(this.baseUrl, cuenta, this.httpOptions);
  }

  public cobrarCuenta(id: number): Observable<ComandoRespuestaCobrar> {
    return this.http.get<ComandoRespuestaCobrar>(this.baseUrl + '/' + id, this.httpOptions);
  }

  public pagarCuenta(id: number): Observable<any> {
    return this.http.put(this.baseUrl + '/' + id, this.httpOptions);
  }

}
