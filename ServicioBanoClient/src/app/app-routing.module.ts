import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InicioComponent } from './features/inicio/inicio.component';
import { Pagina404Component } from './shared/pagina-principal/pagina404/pagina404.component';
import { DashboardServicioBanoComponent } from './features/dashboard-servicio-bano/dashboard-servicio-bano.component';

const routes: Routes = [
  { path: 'inicio', component: InicioComponent },
  { path: 'dashboard', component: DashboardServicioBanoComponent },
  { path: '404', component: Pagina404Component },
  { path: '', redirectTo: 'inicio', pathMatch: 'full' },
  { path: '**', redirectTo: '404', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
