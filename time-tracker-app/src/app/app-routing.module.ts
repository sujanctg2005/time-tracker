import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
 
import { TimeEntryComponent }   from './time-entry/time-entry.component';

 
const routes: Routes = [
  { path: '', redirectTo: '/timeEntry', pathMatch: 'full' },
  { path: 'timeEntry', component: TimeEntryComponent },
];
 
@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}