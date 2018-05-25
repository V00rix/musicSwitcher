import { Component, OnInit,  Input, HostBinding } from '@angular/core';
import {AudioFile} from "../../domain/audioFile";

@Component({
  selector: 'app-song',
  templateUrl: './song.component.html',
  styleUrls: ['./song.component.scss']
})
export class SongComponent implements OnInit {
  @Input() song: AudioFile;
  @HostBinding('class') classes = 'song pointer app-song';

  constructor() { }

  ngOnInit() {
    if (!this.song) {
      throw new Error("No audio file specified as input for SongComponent");
    }
  }
}
