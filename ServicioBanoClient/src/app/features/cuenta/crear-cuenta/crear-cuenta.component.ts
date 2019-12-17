import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { Cuenta } from '../../../core/modelo/Cuenta';
import { EstadoCuenta } from '../../../core/modelo/EstadoCuenta';
import { GestionCuentaService } from '../../servicios/cuenta/gestion-cuenta.service';
import { EventoAlertService, Alert } from '../../../shared/eventos/evento-alert.service';

@Component({
  selector: 'app-crear-cuenta',
  templateUrl: './crear-cuenta.component.html',
  styleUrls: ['./crear-cuenta.component.css']
})
export class CrearCuentaComponent implements OnInit {

  private idBano: number;
  formulario: FormGroup;

  constructor(private formBuilder: FormBuilder, private router: Router,
              private activatedRoute: ActivatedRoute, private gestion: GestionCuentaService,
              private eventAlert: EventoAlertService) {
  }

  ngOnInit() {
    this.activatedRoute.paramMap.pipe(map(paramMap => paramMap.get('idBano'))).subscribe({
      next: (idBano: string) => this.idBano = Number(idBano)
    });

    this.formulario = this.formBuilder.group({
      sobres: ['1', Validators.required]
    });
  }

  public submit() {
    if (this.formulario.valid) {
      const cuenta: Cuenta = this.formulario.value;
      cuenta.id = null;
      cuenta.idBano = this.idBano;
      cuenta.estado = EstadoCuenta.ABIERTA;
      this.gestion.crearCuenta(cuenta).subscribe({
        next: () => {
          const tipoAlerta = 'alert-success';
          const mensaje = 'El registro fue actualizado <strong>exitosamente</strong>';
          this.eventAlert.emitChange(new Alert(tipoAlerta, mensaje));
          this.router.navigate(['../../listarbano'], { relativeTo: this.activatedRoute });
        }
      });
    }
  }

}
