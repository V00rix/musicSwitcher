import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClient, HttpClientModule} from '@angular/common/http';

import {AppComponent} from './app.component';
import {DataService} from "./business/services/data.service";
import {SongsComponent} from './pages/songs/songs.component';
import {AlbumsComponent} from './pages/albums/albums.component';
import {PlaylistsComponent} from './pages/playlists/playlists.component';
import {ArtistsComponent} from './pages/artists/artists.component';
import {AppRoutingModule} from "./app-routing.module";
import { SongComponent } from './business/components/song/song.component';
import { ListComponent } from './business/components/list/list.component';
import { PlayerComponent } from './business/components/player/player.component';
import { PlayerSmallComponent } from './business/components/player-small/player-small.component';


@NgModule({
  declarations: [
    AppComponent,
    SongsComponent,
    AlbumsComponent,
    PlaylistsComponent,
    ArtistsComponent,
    SongComponent,
    ListComponent,
    PlayerComponent,
    PlayerSmallComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [DataService,
    AppRoutingModule],
  bootstrap: [AppComponent]
})
export class AppModule {
}
