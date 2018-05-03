import {Component, Inject, Input, HostBinding, HostListener} from '@angular/core';
import {PlayerComponent} from "../player.component";
import {AudioFile} from "../../../domain/audio-file";

@Component({
  selector: 'app-player-small',
  template: `<div class="song-info pointer" (click)="player.expand()">
                <div class="image small image-note"></div><!-- NoWhiteSpace
                --><div class="description">
                  <div *ngIf="song; else noSong">
                    <label class="title truncate small pointer">{{song.title}}</label>
                    <label class="artist accent truncate tiny light pointer">{{song.artist}}</label>
                  </div>
                  <ng-template #noSong>
                    <label class="title truncate small pointer">Not Playing</label>
                  </ng-template>
                </div>
              </div>`,
  styleUrls: ['./player-small.component.scss']
})
export class PlayerSmallComponent {
  @Input() public song: AudioFile;
  @HostBinding('class') classes = 'app-track-control pointer';

  constructor(@Inject(PlayerComponent) public player: PlayerComponent) {
  }

  @HostListener('click') onclick() {
    this.player.expand();
  };
}
