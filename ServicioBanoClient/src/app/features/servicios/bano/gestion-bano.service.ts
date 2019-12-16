import { Injectable } from '@angular/core';
import { Bano } from 'src/app/core/modelo/Bano';
import { EventoEliminarBanoService } from '../../../shared/eventos/evento-eliminar-bano.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { EventoAlertService, Alert } from '../../../shared/eventos/evento-alert.service';
import { ComandoRespuestaBano, ComandoRespuestaBanoLista } from '../../../core/modelo/ComandoRespuesta';
import { map, catchError } from 'rxjs/operators';
import { ServicioBase } from '../../../core/servicios/servicio-base';

@Injectable({
  providedIn: 'root'
})
export class GestionBanoService extends ServicioBase {

  private baseUrl = 'http://localhost:8080/servicio-bano/bano';

  constructor(private http: HttpClient, eventAlert: EventoAlertService) {
    super(eventAlert);
  }

  public listarBanos(): Observable<ComandoRespuestaBanoLista> {
    return this.http.get<ComandoRespuestaBanoLista>(this.baseUrl, this.httpOptions);
  }

  public consultarBano(id: number): Observable<ComandoRespuestaBano> {
    return this.http.get<ComandoRespuestaBano>(this.baseUrl + '/' + id, this.httpOptions);
  }

  public crearBano(bano: Bano): Observable<ComandoRespuestaBano> {
    return this.http.post<number>(this.baseUrl, bano, this.httpOptions)
      .pipe(map(idNuevo => {
        return new ComandoRespuestaBano(new Bano(idNuevo, bano.identificador, bano.estado));
      }), catchError(err => this.errorHandl(err)));
  }

  public actualizarBano(bano: Bano) {
    this.http.put(this.baseUrl, bano, this.httpOptions).subscribe({
      next: () => {
        const tipoAlerta = 'alert-success';
        const mensaje = 'El registro fue actualizado <strong>exitosamente</strong>';
        this.eventAlert1.emitChange(new Alert(tipoAlerta, mensaje));
      },
      error: (err: any) => this.errorHandl(err)
    });
  }

  public eliminarBano(id: number): Observable<Bano> {
    return this.http.delete<Bano>(this.baseUrl + '/' + id, this.httpOptions);
  }

}
