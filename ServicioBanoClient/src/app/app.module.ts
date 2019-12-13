import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './shared/pagina-principal/header/header.component';
import { BodyComponent } from './shared/pagina-principal/body/body.component';
import { FooterComponent } from './shared/pagina-principal/footer/footer.component';
import { InicioComponent } from './features/inicio/inicio.component';
import { Pagina404Component } from './shared/pagina-principal/pagina404/pagina404.component';
import { DashboardServicioBanoComponent } from './features/dashboard-servicio-bano/dashboard-servicio-bano.component';
import { ListarBanosComponent } from './features/bano/listar-banos/listar-banos.component';
import { CrearBanoComponent } from './features/bano/crear-bano/crear-bano.component';
import { ActualizarBanoComponent } from './features/bano/actualizar-bano/actualizar-bano.component';
import { EliminarBanoComponent } from './features/bano/eliminar-bano/eliminar-bano.component';
import { CrearCuentaComponent } from './features/cuenta/crear-cuenta/crear-cuenta.component';
import { ActualizarCuentaComponent } from './features/cuenta/actualizar-cuenta/actualizar-cuenta.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { GestionBanoService } from './features/servicios/bano/gestion-bano.service';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    BodyComponent,
    FooterComponent,
    InicioComponent,
    Pagina404Component,
    DashboardServicioBanoComponent,
    ListarBanosComponent,
    CrearBanoComponent,
    ActualizarBanoComponent,
    EliminarBanoComponent,
    CrearCuentaComponent,
    ActualizarCuentaComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FontAwesomeModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [GestionBanoService],
  bootstrap: [AppComponent]
})
export class AppModule { }
