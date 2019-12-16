import { Component, OnInit } from '@angular/core';
import { Bano } from '../../../core/modelo/Bano';
import { EstadoBano } from '../../../core/modelo/EstadoBano';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map, tap, catchError } from 'rxjs/operators';
import { GestionBanoService } from '../../servicios/bano/gestion-bano.service';
import { ComandoRespuestaBano } from '../../../core/modelo/ComandoRespuesta';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { isBoolean } from 'util';

@Component({
  selector: 'app-actualizar-bano',
  templateUrl: './actualizar-bano.component.html',
  styleUrls: ['./actualizar-bano.component.css']
})
export class ActualizarBanoComponent implements OnInit {

  checkDisponible: string;
  mostrarCheck: boolean;
  formulario: FormGroup;
  banoObs: Observable<Bano>;
  idBano: number;

  constructor(private formBuilder: FormBuilder, private route: ActivatedRoute, private gestion: GestionBanoService) {

  }

  ngOnInit() {
    this.formulario = this.formBuilder.group({
      identificador: ['', Validators.required],
      estado: []
    });

    this.route.paramMap.pipe(map(paramMap => paramMap.get('id'))).subscribe({
      next: (id: string) => {
        this.banoObs = this.gestion.listarBano(Number(id))
          .pipe(map(t => t.respuesta), catchError(err => this.gestion.errorHandl(err)))
          .pipe(tap(ba => this.formulario.patchValue(ba)));
      }
    });

    this.banoObs.subscribe({
      next: (bano) => {
        this.idBano = bano.id;
        if (bano.estado === EstadoBano.DISPONIBLE) {
          this.mostrarCheck = true;
          this.checkDisponible = 'checked';
        } else if (bano.estado === EstadoBano.OCUPADO) {
          this.mostrarCheck = false;
          this.checkDisponible = null;
        } else if (bano.estado === EstadoBano.FUERA_SERVICIO) {
          this.mostrarCheck = true;
          this.checkDisponible = null;
        }
      }
    });
  }

  public submit() {
    if (this.formulario.valid) {
      const valor: any = this.formulario.get('estado').value;
      const bano: Bano = this.formulario.value;

      if (isBoolean(valor)) {
        if (valor) {
          bano.estado = EstadoBano.DISPONIBLE;
        } else {
          bano.estado = EstadoBano.FUERA_SERVICIO;
        }
      }
      const banoUpdate: Bano = new Bano(this.idBano, bano.identificador, bano.estado);
      this.gestion.actualizarBano(banoUpdate);
    }
  }

}
