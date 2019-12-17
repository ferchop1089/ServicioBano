import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { GestionBanoService } from '../../servicios/bano/gestion-bano.service';
import { Bano } from 'src/app/core/modelo/Bano';
import { EstadoBano } from '../../../core/modelo/EstadoBano';
import { EventoAlertService, Alert } from '../../../shared/eventos/evento-alert.service';

@Component({
  selector: 'app-crear-bano',
  templateUrl: './crear-bano.component.html',
  styleUrls: ['./crear-bano.component.css']
})
export class CrearBanoComponent implements OnInit {

  formulario: FormGroup;

  constructor(private formBuilder: FormBuilder, private gestion: GestionBanoService, private eventAlert: EventoAlertService) {
  }

  ngOnInit() {
    this.formulario = this.formBuilder.group({
      identificador: ['', Validators.required]
    });
  }

  public submit() {
    if (this.formulario.valid) {
      const bano: Bano = this.formulario.value;
      bano.id = null;
      bano.estado = EstadoBano.DISPONIBLE;

      this.gestion.crearBano(bano).subscribe({
        complete: () => {
          const tipoAlerta = 'alert-success';
          const mensaje = 'El registro fue creado <strong>exitosamente</strong>';
          this.eventAlert.emitChange(new Alert(tipoAlerta, mensaje));
          this.formulario.reset();
        }
      });
    }
  }

}
