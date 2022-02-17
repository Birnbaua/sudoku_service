import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Difficulty } from '../../interfaces/Difficulty';
import { Sudoku } from '../../interfaces/Sudoku';

@Injectable({
  providedIn: 'root',
})
export class GeneratorRequestService {
  constructor(private http: HttpClient) {}

  url = environment.serviceDomain + '/maintenance/';
  
  getRandomSudoku(difficulty: Difficulty) {
    let url = this.url + "sudoku/generate?amount=1"
    if(difficulty){
        url = url + "&difficulty=" + difficulty.no.toString()
    }
    console.log("Genereting from url " + url)
    return this.http.get<Array<Sudoku>>(url);
  }
}
