import {Component, OnInit, Inject, HostBinding, Input} from '@angular/core';
import {PlayerComponent} from "../player.component";

@Component({
  selector: 'app-track-control',
  template: `
    <mat-slider
      class="ib"
      [max]="player.seek.value"
      [min]="0"
      [disabled]="disabled"
      (input)="player.setSeek($event.value)"
      [(ngModel)]="player.seek.key"></mat-slider>
    <label class="time-passed small">{{player.seek.key | minutes}}</label><!--
   NoSpace --><label class="time-left small">{{player.seek.key - player.seek.value | minutes}}</label>`,
  styleUrls: ['./track-control.component.scss']
})
export class TrackControlComponent implements OnInit {
  @HostBinding('class') classes = 'text-center app-track-control';
  @Input() disabled = false;

  constructor(@Inject(PlayerComponent) public player: PlayerComponent) {
  }

  ngOnInit() {
  }

}
