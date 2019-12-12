import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { GestionBanoService } from '../../servicios/bano/gestion-bano.service';
import { Bano } from 'src/app/core/modelo/Bano';

@Component({
  selector: 'app-crear-bano',
  templateUrl: './crear-bano.component.html',
  styleUrls: ['./crear-bano.component.css']
})
export class CrearBanoComponent implements OnInit {

  checkoutForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private _gestionBanoServicio: GestionBanoService) {
    this.checkoutForm = this.formBuilder.group({
      identificador: ''
    });
  }

  ngOnInit() {

  }

  onSubmit(bano) {
    console.warn('Your order has been submitted', bano);
    const b: Bano = new Bano(null, bano.identificador, 'DISPONIBLE');
    this._gestionBanoServicio.crearBano(b);
    this.checkoutForm.reset();
  }

}
