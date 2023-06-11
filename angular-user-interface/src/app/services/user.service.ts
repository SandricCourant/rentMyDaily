import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrlAuthorize = "http://localhost:8443/api/v1/authorize";
  private apiUrlRegister = "http://localhost:8443/api/v1/register";

  constructor(private http: HttpClient) {}

  postAuthorize(username: string, password: string){
    return this.http.post(this.apiUrlAuthorize, {username, password});
  }
  postRegister(username: string, password: string){
    return this.http.post(this.apiUrlRegister, {username, password});
  }

  clear(){
    localStorage.clear();
  }
}
