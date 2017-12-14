import { NgModule }       from '@angular/core';
import { BrowserModule }  from '@angular/platform-browser';
import { FormsModule }    from '@angular/forms';
import { HttpClientModule }    from '@angular/common/http';
import { MomentModule } from 'angular2-moment';
import { Ng2DeviceDetectorModule } from 'ng2-device-detector';


import { AppRoutingModule }     from './app-routing.module';

import { AppComponent }         from './app.component';
import { TimeEntryComponent }   from './time-entry/time-entry.component';
import { TimeTrackerService }          from './services/timetracker.service';
import { MessageService }          from './services/message.service';
import { Ng2AutoCompleteModule } from 'ng2-auto-complete';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    MomentModule,
    Ng2AutoCompleteModule,
    Ng2DeviceDetectorModule.forRoot(),
    NgbModule.forRoot()
  ],
  declarations: [
    AppComponent,
    TimeEntryComponent
  ],
  providers: [ TimeTrackerService,MessageService],
  bootstrap: [ AppComponent ]
})
export class AppModule { }