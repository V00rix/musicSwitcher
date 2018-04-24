import {Component} from '@angular/core';
import {DataService} from "./business/services/data.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  constructor(public data: DataService) {
    data.onInit.subscribe(() => {
      console.log(data.library);
    });
   }
}
