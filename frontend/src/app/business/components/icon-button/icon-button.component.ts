import { Component, OnInit, HostBinding } from '@angular/core';

@Component({
  selector: 'app-icon-button',
  templateUrl: './icon-button.component.html',
  styleUrls: ['./icon-button.component.scss']
})
export class IconButtonComponent implements OnInit {
  @HostBinding('class') classes = 'app-icon-button';

  constructor() { }

  ngOnInit() {
  }

}
