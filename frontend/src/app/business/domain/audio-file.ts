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
}
