import {Injectable} from '@angular/core';
import {Subject} from 'rxjs/Subject';
import {HttpClient} from '@angular/common/http';
import 'rxjs/add/operator/map';
import {AudioFile} from "../domain/audio-file";
import {Library} from "../domain/library";

@Injectable()
export class DataService {
  onInit = new Subject<void>();
  public library = new Library([]);

  constructor(private http: HttpClient) {
    this.getLibrary();
  }


  /**
   * GET Library
   */
  private getLibrary() {
    this.http.get("http://localhost:8080/library").subscribe((response: AudioFile[]) => {
      this.library.files = response.map(f => {
        f.title = f.title ? f.title : "Unknown title";
        f.album = f.album ? f.album : "Unknown album";
        f.artist = f.artist ? f.artist : "Unknown artist";
        return f;
      });
      this.onInit.next();
    })
  }
}
