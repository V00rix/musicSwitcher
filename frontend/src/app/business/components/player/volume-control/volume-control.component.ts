import { Component, OnInit, HostBinding, Inject } from '@angular/core';
import {PlayerComponent} from "../player.component";

@Component({
  selector: 'app-volume-control',
  template: `<div class="image tiny image-volume-down ib hover-up" (click)="player.setVolumeInc(true)"></div><!-- NoSpace
            --><mat-slider
              class="ib"
              [max]="100"
              [min]="0"
              [step]="1"
              [thumb-label]="true"
              [tick-interval]="10"
              (input)="player.setVolume($event.value)"
              [(ngModel)]="player.volume"></mat-slider><!-- NoSpace
            --><div class="image tiny image-volume-up ib hover-up" (click)="player.setVolumeInc()"></div>`,
  styleUrls: ['./volume-control.component.scss']
})
export class VolumeControlComponent implements OnInit {
  @HostBinding('class') classes = 'standard-padding app-volume-control';

  constructor(@Inject(PlayerComponent) public player: PlayerComponent) { }

  ngOnInit() {

  }
}
