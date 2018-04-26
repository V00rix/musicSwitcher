import { Component, OnInit, Inject, Input } from '@angular/core';
import {PlayerComponent} from "../player/player.component";
import {AudioFile} from "../../domain/audio-file";

@Component({
  selector: 'app-player-small',
  templateUrl: './player-small.component.html',
  styleUrls: ['./player-small.component.scss']
})
export class PlayerSmallComponent implements OnInit {
  @Input() public song: AudioFile;

  constructor(@Inject(PlayerComponent) public player: PlayerComponent) { }

  ngOnInit() {
  }

  public expandPlayer() {
    this.player.expanded = true;
  }

}
