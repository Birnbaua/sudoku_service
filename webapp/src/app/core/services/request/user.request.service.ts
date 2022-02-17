import { User } from '../../interfaces/User';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from "@angular/core";
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class UserRequestService {
    constructor(
        private http: HttpClient
    ){}
    
    userUrl = environment.serviceDomain + "/user/"
    
    getUserByToken(token : string){
        const httpOptions = {
            headers: new HttpHeaders({
                Authentication: token
            })
        }
        return this.http.get<User>(this.userUrl,httpOptions)
    }

    createUser(username: string, password: string, mail: string, firstname: string | undefined, lastname: string | undefined){
        let url = this.userUrl
        const jsonBody = {
            username: username,
            nickname: username,
            password: password,
            email: mail,
            firstName: firstname,
            lastName: lastname
        }
        return this.http.post<User>(url,jsonBody)
    }
}