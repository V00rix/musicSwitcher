import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import { FormsModule } from '@angular/forms';
import {HttpClient, HttpClientModule} from '@angular/common/http';

import {AppComponent} from './app.component';
import {DataService} from "./business/services/data.service";
import {SongsComponent} from './pages/songs/songs.component';
import {AlbumsComponent} from './pages/albums/albums.component';
import {PlaylistsComponent} from './pages/playlists/playlists.component';
import {ArtistsComponent} from './pages/artists/artists.component';
import {AppRoutingModule} from "./app-routing.module";
import {SongComponent} from './business/components/song/song.component';
import {ListComponent} from './business/components/list/list.component';
import {PlayerComponent} from './business/components/player/player.component';
import {PlayerSmallComponent} from './business/components/player/player-small/player-small.component';
import {MatSliderModule} from '@angular/material/slider';
import { VolumeControlComponent } from './business/components/player/volume-control/volume-control.component';
import { PlayControlComponent } from './business/components/player/play-control/play-control.component';
import { TrackControlComponent } from './business/components/player/track-control/track-control.component';
import { IconButtonComponent } from './business/components/icon-button/icon-button.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

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
    PlayerSmallComponent,
    VolumeControlComponent,
    PlayControlComponent,
    TrackControlComponent,
    IconButtonComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatSliderModule,
    FormsModule,
    BrowserAnimationsModule,
    HttpClientModule
  ],
  providers: [DataService,
    AppRoutingModule],
  bootstrap: [AppComponent]
})
export class AppModule {
}
