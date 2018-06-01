import {Injectable} from '@angular/core';
import {Subject} from 'rxjs/Subject';
import {HttpClient} from '@angular/common/http';
import 'rxjs/add/operator/map';
import {AudioFile} from "../domain/audioFile";
import {Library} from "../domain/library";
import {HttpResponse} from "../domain/httpResponse";
import {PlayerStatus} from "../domain/playerStatus";

@Injectable()
export class DataService {
  private readonly baseUrl;

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
  playerStatusUpdated: Subject<void> = new Subject();
  //endregion

  //region Library
  library = new Library([]);
  //endregion

  //region Current playlist data
  public playerStatus = new PlayerStatus(null, null, [], null, false);
  //endregion

  //region Constructor
  constructor(private http: HttpClient) {
    // this.baseUrl = `http://192.168.0.248:8190/api`;
    this.baseUrl = `http://${window.location.host}/api`;
    this.startSynchronization();
  }

  //endregion

  //region Public API
  /**
   * Backend synchronization
   */
  startSynchronization() {
    this.updatePlayer();
    this.updateLibrary();
  }

  /**
   * Set/update playlist
   * @param {number} id Index
   * @param noPlay
   */
  setPlaylist(id: number, noPlay = false) {
    this.playerStatus.song = 0;
    this.setHttpPlaylist(this.playerStatus.playlist = this.library.files.map(f => f.id), id);
  }

  //region Http
  //region Synchronization
  /**
   * GET startSynchronization
   */
  private updatePlayer() {
    this.http.get(`${this.baseUrl}/playerStatus`).subscribe((response: HttpResponse<PlayerStatus>) => {
      this.playerStatus = response.data;
      this.playerStatusUpdated.next();
    });

    setTimeout(() => {
      this.updatePlayer();
    }, 500);
  }

  /**
   * GET Library
   */
  private updateLibrary() {
    let updateTime = 1000;

    this.http.get(`${this.baseUrl}/library`).subscribe((response: HttpResponse<{ key: AudioFile[], value: boolean }>) => {

      updateTime = response.data.value ? 30000 : 1000;

      this.library.setOrUpdate(response.data.key.map(f => {
        f = AudioFile.from(f);
        f.dateChanged = new Date(f.dateChanged);

        if (f.metadataRetrieved) {
          f.title = f.title ? f.title : "Unknown title";
          f.album = f.album ? f.album : "Unknown album";
          f.artist = f.artist ? f.artist : "Unknown artist";
        }
        return f;
      }));
      this.onInit.next();

      setTimeout(() => {
        this.updateLibrary();
      }, updateTime);
    }, (error) => {

      setTimeout(() => {
        this.updateLibrary();
      }, updateTime);
    });

  }

  //endregion

  /**
   * POST set playlist
   */
  private setHttpPlaylist(list: number[], id: number) {
    this.http.post(`${this.baseUrl}/playlist`, list).subscribe((response) => {
      this.setHttpSelected(id);
    });
  }

  /**
   * POST set selected song index
   */
  public setHttpSelected(id: number) {
    this.http.post(`${this.baseUrl}/playlist/selected`, id).subscribe((response) => {
    })
  }

  //region Player Controls
  /**
   * POST toggle play
   */
  public togglePlay() {
    this.playerStatus.isPlaying = !this.playerStatus.isPlaying;
    this.http.post(`${this.baseUrl}/play`, null).subscribe((response) => {
      console.log(response);
    })
  }

  /**
   * POST play next
   */
  public playNext() {
    this.http.post(`${this.baseUrl}/play/next`, null).subscribe((response) => {
    })
  }

  /**
   * POST play previous
   */
  public playPrevious() {
    this.http.post(`${this.baseUrl}/play/previous`, null).subscribe((response) => {
    })
  }

  /**
   * POST change volume
   * @param {number} volume from 0 to 1
   */
  public setVolume(volume: number) {
    console.log(volume);
    this.http.post(`${this.baseUrl}/play/volume`, volume).subscribe((response) => {
      console.log(response);
    })
  }

  /**
   * POST Slightly increase or decrease the volume
   * @param decrement If the volume should be incremented of decremented
   */
  public setVolumeInc(decrement = false) {
    if (decrement) {
      this.http.post(`${this.baseUrl}/play/volume/decrement`, null).subscribe((response) => {
        console.log(response);
      })
    } else {
      this.http.post(`${this.baseUrl}/play/volume/increment`, null).subscribe((response) => {
        console.log(response);
      })
    }
  }

  /**
   * POST Set seek position
   * @param passed seconds from beginning
   */
  public setSeek(passed: number) {
    this.http.post(`${this.baseUrl}/play/seek`, passed).subscribe((response) => {
      console.log(response);
    });
  }
  //endregion
  //endregion
  //endregion

  //region Helpers

  //endregion
}
