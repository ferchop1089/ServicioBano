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
import { ListarBanosComponent } from './features/listar-banos/listar-banos.component';

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
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
