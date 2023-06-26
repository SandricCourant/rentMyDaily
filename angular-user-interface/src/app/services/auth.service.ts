import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


const API_URL = "http://localhost:8443/api/v1/account";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  login: boolean = false;

  constructor(private http: HttpClient) {}

  postAuthorize(username: string, password: string){
    return this.http.post(API_URL + "/authorize", {username, password});
  }
  postRegister(username: string, password: string, firstname: string, lastname: string, email: string, phoneNumber: string){
    return this.http.post(API_URL + "/register", {username, password, firstname, lastname, email, phoneNumber});
  }
  getToken(): any {
    return localStorage.getItem('token');
  }
  setToken(token: string){
    localStorage.setItem('token', token);
  }
  clear(){
    localStorage.clear();
  }
  
  setLogin(login: boolean){
    this.login = login;
  }
  getLogin(): boolean{
    return this.login;
  }
}
