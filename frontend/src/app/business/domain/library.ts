import {AudioFile} from "./audioFile";

export class Library {
  constructor(public files: AudioFile[]) {
  }

  /**
   * Update audio files library
   * @param {AudioFile[]} audioFiles
   */
  setOrUpdate(audioFiles: AudioFile[]) {
    if (this.files.length) {
      this.files.forEach((f, index) => {
        if (!f.metadataRetrieved) {
          let newFile = audioFiles.find(af => af.id === f.id);
          if (newFile.metadataRetrieved) {
            f.setData(newFile);
          }
        }
      });
      for (let f of this.files) {
        f = audioFiles.find(af => af.id === f.id);
      }
    } else {
      this.files = audioFiles;
    }
  }
}
