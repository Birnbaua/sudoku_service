import { Difficulty } from './../../interfaces/Difficulty';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class DifficultyRequestService {
  constructor(private http: HttpClient) {}

  url = environment.serviceDomain + '/difficulty/'

  getAll(){
      return this.http.get<Array<Difficulty>>(this.url)
  }

}
