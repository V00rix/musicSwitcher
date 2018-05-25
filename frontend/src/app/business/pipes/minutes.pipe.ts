import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'minutes'
})
export class MinutesPipe implements PipeTransform {

  transform(seconds: number): string {
    let sign = (seconds < 0 ? '-' : '');
    seconds = Math.abs(seconds);
    let m = Math.floor(seconds/60);
    let s = seconds % 60;
    let ss = (s < 10 ? '0' : '') + s;
    return  sign + m + ':' + ss;
  }

}
