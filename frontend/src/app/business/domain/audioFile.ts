import {forEach} from "@angular/router/src/utils/collection";
import {hasOwnProperty} from "tslint/lib/utils";

export class AudioFile {
  constructor(public id: number,
              public metadataRetrieved: boolean,
              public album: string,
              public artist: string,
              public path: string,
              public genre: string,
              public title: string,
              public dateChanged: Date,
              public track: number) {
  }

  static from(audioFile: AudioFile): AudioFile {
    return new AudioFile(audioFile.id, audioFile.metadataRetrieved, audioFile.album, audioFile.artist, audioFile.path, audioFile.genre, audioFile.title, audioFile.dateChanged, audioFile.track);
  }

  setData(audioFile: AudioFile) {
    this.id = audioFile.id;
    this.metadataRetrieved = audioFile.metadataRetrieved;
    this.album = audioFile.album;
    this.artist = audioFile.artist;
    this.path = audioFile.path;
    this.genre = audioFile.genre;
    this.title = audioFile.title;
    this.dateChanged = audioFile.dateChanged;
    this.track = audioFile.track;
  }
}
