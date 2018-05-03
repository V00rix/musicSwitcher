import {Injectable} from '@angular/core';
import {Subject} from 'rxjs/Subject';
import {HttpClient} from '@angular/common/http';
import 'rxjs/add/operator/map';
import {AudioFile} from "../domain/audio-file";
import {Library} from "../domain/library";
import {HttpResponse} from "../domain/http-response";

@Injectable()
export class DataService {
  private readonly baseUrl = 'http://192.168.0.192:8080/api';

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

  playStatusUpdated: Subject<any> = new Subject();

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
    // console.log('checking status');
    this.getStatus();

    // make loop
    setTimeout(() => {
      this.status();
    }, 500);
  }

  /**
   * Set/update playlist
   * @param {number} id Index
   */
  setPlaylist(id: number, noPlay = false) {
    const list = this.library.files.slice(id);
    this.playlist = list;
    this.playing = 0;
    this.playlistChanged.next();
    this.setHttpPlaylist();
    console.log(list);
  }

  //region Http

  /**
   * GET status
   */
  private getStatus() {
    this.http.get(`${this.baseUrl}/status`).subscribe((response) => {
      // console.log(response);
    })
  }

  /**
   * GET Library
   */
  private getLibrary() {
    this.http.get(`${this.baseUrl}/library`).subscribe((response: HttpResponse<AudioFile[]>) => {
      this.library.files = response.data.map(f => {

        f.dateChanged = new Date(f.dateChanged);

        if (f.metadataRetrieved) {
          f.title = f.title ? f.title : "Unknown title";
          f.album = f.album ? f.album : "Unknown album";
          f.artist = f.artist ? f.artist : "Unknown artist";
        }
        return f;
      });
      this.onInit.next();
    });
  }

  /**
   * POST set playlist
   */
  public setHttpPlaylist() {
    this.http.post(`${this.baseUrl}/playlist`, this.playlist.map(x => x.id)).subscribe((response) => {
      console.log(response);

      // todo: based on response!

      this.playStatusUpdated.next({playing: true});
    });
  }

  /**
   * POST set selected song index
   */
  public setHttpSelected() {
    this.http.post(`${this.baseUrl}/playlist/selected`, this.playlist).subscribe((response) => {
      console.log(response);
      this.playStatusUpdated.next(response);
    })
  }

  /**
   * POST toggle play
   */
  public togglePlay() {
    this.http.post(`${this.baseUrl}/play`, this.playlist).subscribe((response) => {
      console.log(response);
    })
  }

  /**
   * POST play next
   */
  public playNext() {
    this.http.post(`${this.baseUrl}/play/next`, null).subscribe((response) => {
      console.log(response);
      // this.playStatusUpdated.next(response);
    })
  }

  /**
   * POST play previous
   */
  public playPrevious() {
    this.http.post(`${this.baseUrl}/play/previous`, null).subscribe((response) => {
      console.log(response);
      // this.playStatusUpdated.next(response);
    })
  }

  //endregion

  //endregion

  //region Helpers

  //endregion
}
