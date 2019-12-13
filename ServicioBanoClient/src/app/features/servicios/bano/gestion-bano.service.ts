import { Injectable } from '@angular/core';
import { Bano } from 'src/app/core/modelo/Bano';
import { EventoEliminarBanoService } from '../../../shared/eventos/evento-eliminar-bano.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { RespuestaBano } from 'src/app/core/modelo/RespuestaBano';
import { retry, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class GestionBanoService {

  private listaBanos: Bano[] = [];
  private baseUrl = 'http://localhost:8080/servicio-bano';

  constructor(private _eventEliminar: EventoEliminarBanoService, private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  public getBanosRest(): Observable<Bano[]> {
    let rta:Observable<Bano[]> = this.http.get<Bano[]>(this.baseUrl + '/consultar');
    console.log(rta);
    return rta;
    /*return this.http.get<RespuestaBano>(this.baseUrl + '/consultar')
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    );*/
  }

  public getBanos(): Bano[] {
    return this.listaBanos;
  }

  public getBano(id: number): Bano {
    return this.listaBanos[id];
  }

  crearBano(bano: Bano) {
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

  public eliminarBano(id: number) {
    const index = this.listaBanos.findIndex(x => x.id === id);
    const banos: Bano[] = this.listaBanos.splice(index, 1);
    console.log('Index eliminado: ' + index);
    this._eventEliminar.emitChange(banos[0]);
    //this.listaBanos = this.listaBanos.filter(obj => obj.id !== id);
  }

  public errorHandl(error) {
    let errorMessage = '';
    if(error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(errorMessage);
 }

}
