import { Component, OnInit } from '@angular/core';
import {DataService} from "../../business/services/data.service";

@Component({
  selector: 'app-songs',
  templateUrl: './songs.component.html',
  styleUrls: ['./songs.component.scss']
})
export class SongsComponent implements OnInit {

  constructor(public data: DataService) { }

  ngOnInit() {

  }

  public setPlaylist(id: number) {
    this.data.setPlaylist(this.data.library.files.slice(id))
  }
}
