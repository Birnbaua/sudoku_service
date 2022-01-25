import { Injectable } from "@angular/core";
import { DomSanitizer } from '@angular/platform-browser';

@Injectable({
    providedIn: 'root'
})
export class ImgService {
    constructor(
        private sanitizer: DomSanitizer
     ) {}
    

    byteArrayToImage(arr: any){
        let objectURL = 'data:image/png;base64,' + arr 
        return this.sanitizer.bypassSecurityTrustUrl(objectURL)
    }
    
}