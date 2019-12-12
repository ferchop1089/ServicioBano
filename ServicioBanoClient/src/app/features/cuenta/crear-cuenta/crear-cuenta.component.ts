import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ConsultarBanosService } from '../../servicios/consultar-banos.service';

@Component({
  selector: 'app-crear-cuenta',
  templateUrl: './crear-cuenta.component.html',
  styleUrls: ['./crear-cuenta.component.css']
})
export class CrearCuentaComponent implements OnInit {

  private id: number;

  constructor(private activateRouter: ActivatedRoute, private _consultarBanosService: ConsultarBanosService) {
    this.activateRouter.params.subscribe(params => {
      this.id = params.id;
    });
  }

  ngOnInit() {
  }

}
