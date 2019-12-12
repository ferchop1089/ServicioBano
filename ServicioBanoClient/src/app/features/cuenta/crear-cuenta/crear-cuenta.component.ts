import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-crear-cuenta',
  templateUrl: './crear-cuenta.component.html',
  styleUrls: ['./crear-cuenta.component.css']
})
export class CrearCuentaComponent implements OnInit {

  private id: number;

  constructor(private activateRouter: ActivatedRoute) {
    this.activateRouter.params.subscribe(params => {
      this.id = params.id;
    });
  }

  ngOnInit() {
  }

}
