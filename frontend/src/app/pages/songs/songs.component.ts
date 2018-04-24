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

}
