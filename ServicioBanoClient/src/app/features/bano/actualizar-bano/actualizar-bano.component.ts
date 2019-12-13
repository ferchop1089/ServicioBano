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

  checkDiponible: string;
  mostrarCheck: boolean;
  form: FormGroup;
  banoObs: Observable<Bano>;
  idBano: number;

  constructor(private formBuilder: FormBuilder, private route: ActivatedRoute, private gestion: GestionBanoService) {

  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      identificador: ['', Validators.required],
      estado: []
    });

    this.route.paramMap.pipe(map(paramMap => paramMap.get('id'))).subscribe({
      next: (id: string) => {
        this.banoObs = this.gestion.getBanoRest(Number(id))
          .pipe(map(t => t.respuesta), catchError(err => this.gestion.errorHandl(err)))
          .pipe(tap(ba => this.form.patchValue(ba)));
      }
    });

    this.banoObs.subscribe({
      next: (bano) => {
        this.idBano = bano.id;
        if (bano.estado === EstadoBano.DISPONIBLE) {
          this.mostrarCheck = true;
          this.checkDiponible = 'checked';
        } else if (bano.estado === EstadoBano.OCUPADO) {
          this.mostrarCheck = false;
          this.checkDiponible = null;
        } else if (bano.estado === EstadoBano.FUERA_SERVICIO) {
          this.mostrarCheck = true;
          this.checkDiponible = null;
        }
      }
    });
  }

  public submit() {
    if (this.form.valid) {
      const valor: any = this.form.get('estado').value;
      const b: Bano = this.form.value;

      if (isBoolean(valor)) {
        if (valor) {
          b.estado = EstadoBano.DISPONIBLE;
        } else {
          b.estado = EstadoBano.FUERA_SERVICIO;
        }
      }
      const banoUpdate: Bano = new Bano(this.idBano, b.identificador, b.estado);
      this.gestion.ActualizarBanoRest(banoUpdate);
    }
  }

}
