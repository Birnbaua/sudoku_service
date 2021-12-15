import { User } from './../interfaces/User';
import { Injectable } from "@angular/core";
import { Subject, BehaviorSubject } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class UserDataService{
    constructor() { }

    public userDataDetails: User = {
        username: "empty"
    };

    public subject = new Subject<User>();

    private userSource = new BehaviorSubject(this.userDataDetails);

    currentUser = this.userSource.asObservable();

    setUser(user: User){
        this.userSource.next(user);
    }

    getUser(){
        return this.currentUser;
    }
}