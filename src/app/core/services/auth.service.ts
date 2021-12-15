import { LoginCreds } from './../interfaces/LoginCreds';
import { ResponseToken } from '../interfaces/ResponseToken'
import { HttpClient } from '@angular/common/http';
import { Injectable } from "@angular/core";
import { environment } from 'src/environments/environment';
import * as moment from "moment";
import { Router } from '@angular/router';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    constructor(
        private http: HttpClient,
        private router: Router
    ){}

    authUrl = environment.serviceDomain + "/auth/"

    authenticate(username:string, password:string){
        console.log("Inside AuthService.authenticate with name: " + username + " and password: "+ password)
        const creds: LoginCreds = {
            username: username,
            password: password
        }
        return this.http.post<ResponseToken>(this.authUrl + "authenticate", creds);
    }

    setSession(authResult : ResponseToken) {
        localStorage.setItem('token', authResult.token);
        this.router.navigate(['/']);
        console.log(authResult)
    }

    logout(){
        localStorage.removeItem("token");
    }
}