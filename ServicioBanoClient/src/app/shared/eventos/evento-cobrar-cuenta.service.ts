import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EventoCobrarCuentaService {

  private emitChangeSource = new Subject<number>();
  changeEmitted$ = this.emitChangeSource.asObservable();

  constructor() { }

  public emitChange(change: number) {
    this.emitChangeSource.next(change);
  }

}
