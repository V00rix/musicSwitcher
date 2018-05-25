import {Component, OnInit, OnDestroy, ViewEncapsulation} from '@angular/core';
import {DataService} from "../../services/data.service";
import {AudioFile} from "../../domain/audioFile";
import enumerate = Reflect.enumerate;
import {Subscription} from 'rxjs/Subscription';

@Component({
  selector: 'app-player',
  encapsulation: ViewEncapsulation.None,
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.scss']
})
export class PlayerComponent implements OnInit, OnDestroy {
  public volume: number = 0;
  public track: number = 0;
  public expanded = false;
  public playing = false;
  /**
   * Milliseconds played
   * @type {number}
   */
  public timePassed = 0;

  /**
   * Milliseconds left
   * @type {number}
   */
  public timeLeft = 0;

  private subscriptions: Subscription[] = [];

  constructor(public data: DataService) {
  }

  ngOnInit() {
    this.subscriptions.push(this.data.playerStatusUpdated.subscribe((status) => {
      this.playing = status.playing;
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

  public play() {
    this.playing = true;
    this.data.togglePlay();
  }

  public pause() {
    this.playing = false;
    this.data.togglePlay();
  }

  public next() {
    this.data.playNext();
  }

  public previous() {
    this.data.playPrevious();
  }

  public setVolume(volume) {
    console.log(volume);
  }

  public setTrack(track) {
    console.log(track);
  }
}
