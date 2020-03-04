import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {TrackviewComponent} from "./trackview/trackview.component";
import {TracklistComponent} from "./tracklist/tracklist.component";

const routes: Routes = [
  { path: '', component: TracklistComponent},
  { path: 'track/:trackId', component: TrackviewComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
