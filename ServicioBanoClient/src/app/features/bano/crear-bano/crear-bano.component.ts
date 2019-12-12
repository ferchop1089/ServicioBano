import { Component, OnInit } from '@angular/core';
import { CrearBanoService } from '../../servicios/crear-bano.service';

@Component({
  selector: 'app-crear-bano',
  templateUrl: './crear-bano.component.html',
  styleUrls: ['./crear-bano.component.css']
})
export class CrearBanoComponent implements OnInit {

  

  constructor(private _crearBanoServicio: CrearBanoService) { }

  ngOnInit() {
    
  }

}
