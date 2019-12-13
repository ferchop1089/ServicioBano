import { Injectable } from '@angular/core';
import { Bano } from 'src/app/core/modelo/Bano';
import { EventoEliminarBanoService } from '../../../shared/eventos/evento-eliminar-bano.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { EventoAlertService, Alert } from '../../../shared/eventos/evento-alert.service';
import { ComandoRespuestaBano, ComandoRespuestaBanoLista } from '../../../core/modelo/ComandoRespuesta';

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

  public crearBano(bano: Bano) {
    bano.id = this.listaBanos.length + 1;
    this.listaBanos.push(bano);
    console.log('Se agregó nuevo elemento a la lista, id del elemento: ' + bano.id);
    console.log('Número de elementos: ' + this.listaBanos.length);
  }

  public ActualizarBano(bano: Bano) {
    const index = this.listaBanos.findIndex(x => x.id === bano.id);
    if (index > -1) {
      this.listaBanos[index] = bano;
    }
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

  public errorHandl(error) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\n, Message: ${error.error.message}`;
    }
    console.log(errorMessage);
    this.eventAlert.emitChange(new Alert('alert-danger', errorMessage));
    return throwError(errorMessage);
  }

}
