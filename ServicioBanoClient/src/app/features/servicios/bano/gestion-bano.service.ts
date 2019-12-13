import { Injectable } from '@angular/core';
import { Bano } from 'src/app/core/modelo/Bano';
import { EventoEliminarBanoService } from '../../../shared/eventos/evento-eliminar-bano.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { EventoAlertService, Alert } from '../../../shared/eventos/evento-alert.service';
import { ComandoRespuestaBano, ComandoRespuestaBanoLista } from '../../../core/modelo/ComandoRespuesta';
import { map, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class GestionBanoService {

  private listaBanos: Bano[] = [];
  private baseUrl = 'http://192.168.0.5:8080/servicio-bano';

  constructor(private eventEliminar: EventoEliminarBanoService, private eventAlert: EventoAlertService, private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  public getBanosRest(): Observable<ComandoRespuestaBanoLista> {
    return this.http.get<ComandoRespuestaBanoLista>(this.baseUrl + '/consultar', this.httpOptions);
  }

  public getBanoRest(id: number): Observable<ComandoRespuestaBano> {
    return this.http.get<ComandoRespuestaBano>(this.baseUrl + '/consultar/' + id, this.httpOptions);
  }

  public crearBanoRest(bano: Bano): Observable<ComandoRespuestaBano> {
    return this.http.post<number>(this.baseUrl + '/crear', bano, this.httpOptions)
      .pipe(map(idNuevo => {
        return new ComandoRespuestaBano(new Bano(idNuevo, bano.identificador, bano.estado));
      }), catchError(err => this.errorHandl(err)));
  }

  public ActualizarBanoRest(bano: Bano) {
    this.http.put(this.baseUrl + '/actualizar', bano, this.httpOptions).subscribe({
      next: () => {
        const tipoAlerta = 'alert-success';
        const mensaje = 'El registro fue actualizado <strong>exitosamente</strong>';
        this.eventAlert.emitChange(new Alert(tipoAlerta, mensaje));
      },
      error: (err: any) => this.errorHandl(err)
    });
  }

  public eliminarBanoRest(id: number): Observable<Bano> {
    return this.http.delete<Bano>(this.baseUrl + '/eliminar/' + id, this.httpOptions);
  }

  public errorHandl(error: { error: { message: string; }; status: any; }) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\n, Message: ${error.error.message}`;
    }
    console.log(errorMessage);
    this.eventAlert.emitChange(new Alert('alert-danger', error.error.message));
    return throwError(errorMessage);
  }

}
