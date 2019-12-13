import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Bano } from '../../core/modelo/Bano';

@Injectable({
  providedIn: 'root'
})
export class EventoCrearBanoService {

  private emitChangeSource = new Subject<Bano>();
  changeEmitted$ = this.emitChangeSource.asObservable();

  constructor() { }

  public emitChange(change: Bano) {
    this.emitChangeSource.next(change);
  }

}
