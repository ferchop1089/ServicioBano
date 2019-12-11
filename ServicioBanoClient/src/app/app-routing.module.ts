import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InicioComponent } from './features/inicio/inicio.component';
import { Pagina404Component } from './shared/pagina-principal/pagina404/pagina404.component';
import { DashboardServicioBanoComponent } from './features/dashboard-servicio-bano/dashboard-servicio-bano.component';
import { CrearBanoComponent } from './features/bano/crear-bano/crear-bano.component';
import { ListarBanosComponent } from './features/bano/listar-banos/listar-banos.component';

const routes: Routes = [
  { path: 'inicio', component: InicioComponent },
  {
    path: 'dashboard',
    component: DashboardServicioBanoComponent,
    children: [
      { path: 'listarbano', component: ListarBanosComponent },
      { path: 'crearbano', component: CrearBanoComponent },
      { path: '', redirectTo: 'listarbano', pathMatch: 'full' }
    ]
  },
  { path: '404', component: Pagina404Component },
  { path: '', redirectTo: 'inicio', pathMatch: 'full' },
  { path: '**', redirectTo: '404', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
