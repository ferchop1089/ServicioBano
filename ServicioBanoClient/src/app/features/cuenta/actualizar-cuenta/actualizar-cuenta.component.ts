import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { map } from 'rxjs/operators';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { GestionCuentaService } from '../../servicios/cuenta/gestion-cuenta.service';
import { Cuenta } from '../../../core/modelo/Cuenta';
import { ComandoRespuestaCuenta } from '../../../core/modelo/ComandoRespuesta';
import { EstadoCuenta } from '../../../core/modelo/EstadoCuenta';
import { EventoAlertService, Alert } from '../../../shared/eventos/evento-alert.service';
import { EventoCobrarCuentaService } from '../../../shared/eventos/evento-cobrar-cuenta.service';

@Component({
  selector: 'app-actualizar-cuenta',
  templateUrl: './actualizar-cuenta.component.html',
  styleUrls: ['./actualizar-cuenta.component.css']
})
export class ActualizarCuentaComponent implements OnInit {

  private idBano: number;
  cuenta: Cuenta;
  formulario: FormGroup;

  constructor(private gestion: GestionCuentaService, private activatedRoute: ActivatedRoute,
              private formBuilder: FormBuilder, private eventAlert: EventoAlertService,
              private eventCobrar: EventoCobrarCuentaService) { }

  ngOnInit() {
    this.activatedRoute.paramMap.pipe(map(paramMap => paramMap.get('idBano'))).subscribe({
      next: (idBano: string) => {
        this.idBano = Number(idBano);
        this.gestion.buscarCuentaPorIdBano(this.idBano).subscribe({
          next: (comando: ComandoRespuestaCuenta) => {
            this.cuenta = comando.respuesta;
            this.formulario = this.formBuilder.group({
              sobres: [comando.respuesta.sobres, Validators.required]
            });
          }
        });
      }
    });
    // Si se retira genera error
    this.formulario = this.formBuilder.group({
      sobres: ['', Validators.required]
    });
  }

  public submit() {
    if (this.formulario.valid) {
      const cuentaValor: Cuenta = this.formulario.value;
      this.cuenta.sobres = cuentaValor.sobres;
      this.gestion.actualizarCuenta(this.cuenta).subscribe({
        next: () => {
          const tipoAlerta = 'alert-success';
          const mensaje = 'El registro fue actualizado <strong>exitosamente</strong>';
          this.eventAlert.emitChange(new Alert(tipoAlerta, mensaje));
        }
      });
    }
  }

  public publicarIdCuenta(id: number) {
    this.eventCobrar.emitChange(id);
  }

}
