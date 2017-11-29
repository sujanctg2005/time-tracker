import { NgModule }       from '@angular/core';
import { BrowserModule }  from '@angular/platform-browser';
import { FormsModule }    from '@angular/forms';
import { HttpClientModule }    from '@angular/common/http';
import { MomentModule } from 'angular2-moment';



import { AppRoutingModule }     from './app-routing.module';

import { AppComponent }         from './app.component';
import { TimeEntryComponent }   from './time-entry/time-entry.component';
import { TimeTrackerService }          from './services/timetracker.service';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    MomentModule
  ],
  declarations: [
    AppComponent,
    TimeEntryComponent
  ],
  providers: [ TimeTrackerService],
  bootstrap: [ AppComponent ]
})
export class AppModule { }