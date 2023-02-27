import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { NovaTranferenciaComponent } from './nova-transferencia/nova-transferencia.componente';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    NovaTranferenciaComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
