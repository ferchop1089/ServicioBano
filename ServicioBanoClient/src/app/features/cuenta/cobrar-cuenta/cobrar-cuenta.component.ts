import { Component, OnInit } from '@angular/core';
import { GestionCuentaService } from '../../servicios/cuenta/gestion-cuenta.service';
import { ComandoRespuestaCobrar } from '../../../core/modelo/ComandoRespuesta';
import { EventoCobrarCuentaService } from '../../../shared/eventos/evento-cobrar-cuenta.service';
import { Cobrar } from '../../../core/modelo/Cobrar';
import { Cuenta } from 'src/app/core/modelo/Cuenta';

@Component({
  selector: 'app-cobrar-cuenta',
  templateUrl: './cobrar-cuenta.component.html',
  styleUrls: ['./cobrar-cuenta.component.css']
})
export class CobrarCuentaComponent implements OnInit {

  cobrar: Cobrar = new Cobrar(new Cuenta(0, 0, '', '', 0, 0), 0, 0, 0, 0, 0);

  constructor(private gestion: GestionCuentaService, private eventCobrar: EventoCobrarCuentaService) { }

  ngOnInit() {
    this.eventCobrar.changeEmitted$.subscribe(id => {
      this.gestion.cobrarCuenta(id).subscribe({
        next: (comando: ComandoRespuestaCobrar) => {
          this.cobrar = comando.respuesta;
        }
      });
    });
  }

}
