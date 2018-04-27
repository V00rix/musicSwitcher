import {Injectable} from '@angular/core';
import {Subject} from 'rxjs/Subject';
import {HttpClient} from '@angular/common/http';
import 'rxjs/add/operator/map';
import {AudioFile} from "../domain/audio-file";
import {Library} from "../domain/library";

@Injectable()
export class DataService {
  //region Events

  /**
   * Init event
   * @type {Subject<void>}
   */
  onInit = new Subject<void>();

  /**
   * Playlist changed event
   * @type {Subject<any>}
   */
  playlistChanged: Subject<void> = new Subject();

  //endregion

  //region Library

  library = new Library([]);

  //endregion

  //region Current playlist data

  playlist: AudioFile[] = [];
  playing: number = null;

  //endregion

  //region Constructor

  constructor(private http: HttpClient) {
    this.status();
    this.getLibrary();
  }

  //endregion

  //region Public API

  /**
   * Backend synchronization
   */
  status() {
    console.log('checking status');
    this.getStatus();

    // make loop
    setTimeout(() => {
      this.status();
    }, 500);
  }

  /**
   * Set/update playlist
   * @param {AudioFile[]} list
   */
  setPlaylist(list: AudioFile[]) {
    this.playlist = list;
    this.playing = 0;
    this.playlistChanged.next();
    console.log(list);
  }

  //region Http

  /**
   * GET status
   */
  private getStatus() {
    this.http.get("http://localhost:8080/status").subscribe((response) => {
      console.log(response);
    })
  }

  /**
   * GET Library
   */
  private getLibrary() {
    this.http.get("http://localhost:8080/library").subscribe((response: AudioFile[]) => {
      this.library.files = response.map(f => {
        f.title = f.title ? f.title : "Unknown title";
        f.album = f.album ? f.album : "Unknown album";
        f.artist = f.artist ? f.artist : "Unknown artist";
        return f;
      });
      this.onInit.next();
    });
  }

  //endregion

  //endregion

  //region Helpers

  //endregion
}
