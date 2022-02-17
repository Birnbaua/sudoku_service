import { Component, Input } from '@angular/core';
import {Key} from 'protractor';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {environment} from '../../../../../environments/environment';

@Component({
  selector: 'app-pic-uploader-component',
  templateUrl: `pic-uploader.component.html`,
  styleUrls: ['./pic-uploader.component.css']
})
export class PicUploaderComponent  {
  constructor(
    private http: HttpClient
  ){}

  @Input() name: string | undefined;
  userUrl = environment.serviceDomain + '/user/';
  files: File[] = [];

  onSelect(event: any) {
    console.log(event);
    this.files.push(...event.addedFiles);
  }

  onRemove(event: any) {
    console.log(event);
    this.files.splice(this.files.indexOf(event), 1);
  }

  // tslint:disable-next-line:typedef
  onFilesAdded(event: any, token: string) {

    const httpOptions = {
      headers: new HttpHeaders({
        Authentication: token
      })
    };

    console.log(event);
    this.files.push(...event.addedFiles);

    this.readFile(this.files[0]).then(fileContents => {
      this.http.post( this.userUrl + '{username}/picture', fileContents, httpOptions);
      console.log(fileContents);
    });
  }

  private async readFile(file: File): Promise<string | ArrayBuffer> {
    return new Promise<string | ArrayBuffer>((resolve, reject) => {
      const reader = new FileReader();

      reader.onload = e => {
        // @ts-ignore
        return resolve((e.target as FileReader).result);
      };

      reader.onerror = e => {
        console.error(`FileReader failed on file ${file.name}.`);
        return reject(null);
      };

      if (!file) {
        console.error('No file to read.');
        return reject(null);
      }

      reader.readAsDataURL(file);
    });
  }
}
