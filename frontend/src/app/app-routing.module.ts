import {NgModule} from "@angular/core";

import {Routes, RouterModule} from '@angular/router';
import {SongsComponent} from "./pages/songs/songs.component";

const appRoutes: Routes = [
  {path: '', component: SongsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(
    appRoutes
  )],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
