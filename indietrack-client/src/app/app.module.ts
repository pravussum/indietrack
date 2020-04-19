import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TrackviewComponent } from './trackview/trackview.component';
import { TracklistComponent } from './tracklist/tracklist.component';
import {HttpClientModule} from '@angular/common/http';
import { HeightprofileComponent } from './heightprofile/heightprofile.component';
import {ChartsModule} from "ng2-charts";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    TrackviewComponent,
    TracklistComponent,
    HeightprofileComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        ChartsModule,
        FormsModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
