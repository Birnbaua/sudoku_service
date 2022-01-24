import { Component, Input } from '@angular/core';
import {Key} from 'protractor';

@Component({
  selector: 'pic-uploader-component',
  templateUrl: `pic-uploader.component.html`,
  styles: [`h1 { font-family: Lato; }`]
})
export class PicUploaderComponent  {
  @Input() name: string | undefined;

  files: File[] = [];

  onSelect(event: any) {
    console.log(event);
    this.files.push(...event.addedFiles);
  }

  onRemove(event: any) {
    console.log(event);
    this.files.splice(this.files.indexOf(event), 1);
  }
}
