import {Component, OnInit} from '@angular/core';
import {DataService} from "../../services/data.service";
import {AudioFile} from "../../domain/audio-file";
import enumerate = Reflect.enumerate;

@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.scss']
})
export class PlayerComponent implements OnInit {
  public volume: number = 0;
  public track: number = 0;
  public expanded = !false;
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

  constructor(public data: DataService) {
  }

  ngOnInit() {
    this.data.playlistChanged.subscribe(() => {
      // ??? redundant ???
    });
  }

  public play() {
    this.playing = true;
  }

  public pause() {
    this.playing = false;
  }

  public next() {
  }

  public previous() {
  }

  public setVolume(volume) {
    console.log(volume);
  }

  public setTrack(track) {
    console.log(track);
  }
}
