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

  checkoutForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private gestion: GestionBanoService, private eventAlert: EventoAlertService) {
  }

  ngOnInit() {
    this.checkoutForm = this.formBuilder.group({
      identificador: ['', Validators.required]
    });
  }

  public submit() {
    if (this.checkoutForm.valid) {
      const b: Bano = this.checkoutForm.value;
      b.id = null;
      b.estado = EstadoBano.DISPONIBLE;

      this.gestion.crearBanoRest(b).subscribe({
        next: () => {
          const tipoAlerta = 'alert-success';
          const mensaje = 'El registro fue creado <strong>exitosamente</strong>';
          this.eventAlert.emitChange(new Alert(tipoAlerta, mensaje));
          this.checkoutForm.reset();
        }
      });
    }
  }

}
