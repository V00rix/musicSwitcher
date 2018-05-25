import {Component, OnInit, OnDestroy, ViewEncapsulation} from '@angular/core';
import {DataService} from "../../services/data.service";
import {AudioFile} from "../../domain/audioFile";
import enumerate = Reflect.enumerate;
import {Subscription} from 'rxjs/Subscription';
import {PlayerStatus} from "../../domain/playerStatus";

@Component({
  selector: 'app-player',
  encapsulation: ViewEncapsulation.None,
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.scss']
})
export class PlayerComponent implements OnInit, OnDestroy {
  /**
   * True for full screen control, else small bar in the bottom of the screen
   * @type {boolean}
   */
  public expanded = false;

  /**
   * Current song playing/selected
   */
  public song: AudioFile = null;

  private subscriptions: Subscription[] = [];

  /**
   * Volume level (from 0 to 100)
   */
  public volume: number = 100;

  /**
   * Seconds the song has played and total duration
   */
  public seek = {key: 0, value: 0};

  public seekDisabled = true;

  constructor(public data: DataService) {
  }

  ngOnInit() {
    this.subscriptions.push(this.data.playerStatusUpdated.subscribe(() => {
      if (this.data.playerStatus) {
        let newStatus = this.data.playerStatus;
        if (newStatus.playlist) {

          this.song = this.data.library.getFileById(newStatus.playlist[newStatus.song]);
        }
        this.volume = Math.floor(newStatus.volume * 100);
        if (newStatus.seek) {
          this.seekDisabled = false;
          this.seek = newStatus.seek;
        } else {
          this.seekDisabled = true;
          this.seek = {key: 0, value: 0};
        }
      }
    }));
  }

  ngOnDestroy() {
    this.subscriptions.forEach(s => s.unsubscribe());
  }

  public expand() {
    let body = document.getElementsByTagName('body')[0];
    body.classList.add("no-scroll");
    this.expanded = true;
  }

  public collapse() {
    let body = document.getElementsByTagName('body')[0];
    body.classList.remove("no-scroll");
    this.expanded = false;
  }

  public togglePlay() {
    this.data.togglePlay();
  }

  public next() {
    this.data.playNext();
  }

  public previous() {
    this.data.playPrevious();
  }

  public setVolume(volume) {
    this.data.setVolume(volume / 100);
  }

  public setVolumeInc(decrement = false) {
    this.data.setVolumeInc(decrement);
  }

  public setSeek(seek) {
    this.data.setSeek(seek);
  }

  /**
   * Move to selected song in playlist
   * @param id
   */
  public setCurrentSong(id) {
    this.data.setPlaylist(this.data.playerStatus.song + id + 1);
  }
}
