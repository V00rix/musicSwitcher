import { Component, OnInit, Inject } from '@angular/core';
import {PlayerComponent} from "../player/player.component";

@Component({
  selector: 'app-player-small',
  templateUrl: './player-small.component.html',
  styleUrls: ['./player-small.component.scss']
})
export class PlayerSmallComponent implements OnInit {

  constructor(@Inject(PlayerComponent) public player: PlayerComponent) { }

  ngOnInit() {
  }

}
