import {Component, OnInit, Inject, HostBinding} from '@angular/core';
import {PlayerComponent} from "../player.component";

@Component({
  selector: 'app-play-control',
  template: `<div class="image medium image-previous ib no-shadow hover-up" (click)="player.previous()"></div>
              <div *ngIf="!player.playing" class="image medium image-play ib no-shadow hover-up" (click)="player.play()"></div>
              <div *ngIf="player.playing" class="image medium image-pause ib no-shadow hover-up" (click)="player.pause()"></div>
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
