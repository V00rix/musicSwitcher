import { Component, OnInit,  Input } from '@angular/core';
import {AudioFile} from "../../domain/audio-file";

@Component({
  selector: 'app-song',
  templateUrl: './song.component.html',
  styleUrls: ['./song.component.scss']
})
export class SongComponent implements OnInit {
  @Input() song: AudioFile;

  constructor() { }

  ngOnInit() {
    if (!this.song) {
      throw new Error("No audio file specified as input for SongComponent");
    }
  }

}
