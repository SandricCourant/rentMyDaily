import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  username: string = "";
  password: string = "";
  login = false;


  constructor(private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
  }

  onContinue(): void {
    this.router.navigateByUrl('/accueil');
  }

  onSubmitForm() {
    this.authService.postAuthorize(this.username, this.password).subscribe((data: any) => {
      console.log(data);
      this.authService.setToken(data.token)
      this.authService.setLogin(true);
      this.onContinue();
    });
    this.login = this.authService.getLogin();
  }
  isLogin(): boolean{
    return this.login;
  }
}
