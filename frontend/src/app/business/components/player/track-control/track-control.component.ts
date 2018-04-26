import {Component, OnInit, Inject, HostBinding} from '@angular/core';
import {PlayerComponent} from "../player.component";

@Component({
  selector: 'app-track-control',
  template: `<mat-slider
              class="ib"
              [max]="1000"
              [min]="0"
              (input)="player.setTrack($event.value)"
              [(ngModel)]="volume"></mat-slider>
              <label class="time-passed small">{{player.timePassed}}</label><!--
   NoSpace --><label class="time-left small">{{player.timeLeft}}</label>`,
  styleUrls: ['./track-control.component.scss']
})
export class TrackControlComponent implements OnInit {
  @HostBinding('class') classes = 'text-center app-track-control';

  constructor(@Inject(PlayerComponent) public player: PlayerComponent) {
  }

  ngOnInit() {
  }

}
