import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { map } from 'rxjs/operators';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { GestionCuentaService } from '../../servicios/cuenta/gestion-cuenta.service';
import { Cuenta } from '../../../core/modelo/Cuenta';
import { ComandoRespuestaCuenta } from '../../../core/modelo/ComandoRespuesta';
import { EstadoCuenta } from '../../../core/modelo/EstadoCuenta';
import { EventoAlertService, Alert } from '../../../shared/eventos/evento-alert.service';

@Component({
  selector: 'app-actualizar-cuenta',
  templateUrl: './actualizar-cuenta.component.html',
  styleUrls: ['./actualizar-cuenta.component.css']
})
export class ActualizarCuentaComponent implements OnInit {

  private idBano: number;
  private cuenta:Cuenta;
  form: FormGroup;

  constructor(private gestion: GestionCuentaService, private activatedRoute: ActivatedRoute, 
    private formBuilder: FormBuilder, private eventAlert: EventoAlertService) { }

  ngOnInit() {
    this.activatedRoute.paramMap.pipe(map(paramMap => paramMap.get('idBano'))).subscribe({
      next: (idBano: string) => {
        this.idBano = Number(idBano);
        this.gestion.getCuentaPorIdBanoRest(this.idBano).subscribe({
          next: (comando: ComandoRespuestaCuenta) => {
            this.cuenta = comando.respuesta;
            this.form = this.formBuilder.group({
              sobres: [comando.respuesta.sobres, Validators.required]
            });
          }
        });
      }
    });
    // Si lo retiro genera error
    this.form = this.formBuilder.group({
      sobres: ['', Validators.required]
    });
  }

  public submit() {
    if (this.form.valid) {
      const b: Cuenta = this.form.value;
      this.cuenta.sobres = b.sobres;
      this.gestion.actualizarCuentaRest(this.cuenta).subscribe({
        next: () => {
          const tipoAlerta = 'alert-success';
          const mensaje = 'El registro fue actualizado <strong>exitosamente</strong>';
          this.eventAlert.emitChange(new Alert(tipoAlerta, mensaje));
        }
      });
    }
  }

}
