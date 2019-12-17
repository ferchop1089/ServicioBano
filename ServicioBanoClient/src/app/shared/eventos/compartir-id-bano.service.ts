import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CompartirIdBanoService {

  constructor() { }

  private emitChangeSource = new Subject<any>();
  changeEmitted$ = this.emitChangeSource.asObservable();

  public emitChange(change: any) {
    this.emitChangeSource.next(change);
  }

}
