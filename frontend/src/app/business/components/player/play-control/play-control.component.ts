import {Component, OnInit, Inject, HostBinding} from '@angular/core';
import {PlayerComponent} from "../player.component";

@Component({
  selector: 'app-play-control',
  template: `
    <div class="image medium image-previous ib no-shadow hover-up" (click)="player.previous()"></div>
    <div *ngIf="!player.data.playerStatus.isPlaying" class="image medium image-play ib no-shadow hover-up" (click)="player.togglePlay()"></div>
    <div *ngIf="player.data.playerStatus.isPlaying" class="image medium image-pause ib no-shadow hover-up" (click)="player.togglePlay()"></div>
    <div class="image medium image-next ib no-shadow hover-up" (click)="player.next() "></div>`,
  styleUrls: ['./play-control.component.scss']
})
export class PlayControlComponent implements OnInit {
  @HostBinding('class') classes = 'text-center app-play-control';

  constructor(@Inject(PlayerComponent) public player: PlayerComponent) {
  }

  ngOnInit() {
  }

}
