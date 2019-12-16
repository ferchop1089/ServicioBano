import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { GestionCuentaService } from '../../servicios/cuenta/gestion-cuenta.service';
import { ComandoRespuestaCobrar } from '../../../core/modelo/ComandoRespuesta';
import { EventoCobrarCuentaService } from '../../../shared/eventos/evento-cobrar-cuenta.service';
import { Cobrar } from '../../../core/modelo/Cobrar';
import { Cuenta } from 'src/app/core/modelo/Cuenta';
import { EventoAlertService, Alert } from '../../../shared/eventos/evento-alert.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-cobrar-cuenta',
  templateUrl: './cobrar-cuenta.component.html',
  styleUrls: ['./cobrar-cuenta.component.css']
})
export class CobrarCuentaComponent implements OnInit {

  @ViewChild('ocultarModalCobrar', { static: false })
  closeModal: ElementRef;
  cobrar: Cobrar = new Cobrar(new Cuenta(0, 0, '', '', 0, 0), 0, 0, 0, 0, 0, 0, 0, 0, 0);

  constructor(private gestion: GestionCuentaService, private eventCobrar: EventoCobrarCuentaService,
              private eventAlert: EventoAlertService, private router: Router, private activateRoute: ActivatedRoute) { }

  ngOnInit() {
    this.eventCobrar.changeEmitted$.subscribe(id => {
      this.gestion.cobrarCuenta(id).subscribe({
        next: (comando: ComandoRespuestaCobrar) => {
          this.cobrar = comando.respuesta;
        }
      });
    });
  }

  public pagar(id: number) {
    this.gestion.pagarCuenta(id).subscribe({
      next: () => { },
      error: (err: any) => {
        this.closeModal.nativeElement.click();
        this.gestion.errorHandl(err);
      },
      complete: () => {
        const tipoAlerta = 'alert-success';
        const mensaje = 'La cuenta fue pagadada <strong>exitosamente</strong>';
        this.closeModal.nativeElement.click();
        this.eventAlert.emitChange(new Alert(tipoAlerta, mensaje));
        this.router.navigate(['listarbano'], { relativeTo: this.activateRoute });
      }
    });
  }

}
