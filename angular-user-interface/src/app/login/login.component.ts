import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  username: string = "";
  password: string = "";

  constructor(private router: Router, private userService: UserService) { }

  ngOnInit(): void {
  }

  onContinue(): void {
    this.router.navigateByUrl('/accueil');
  }

  onSubmitForm() {
    this.userService.postAuthorize(this.username, this.password).subscribe((data: any) => {
      console.log(data);
      localStorage.setItem('token', data.token);
      this.onContinue();
    })
  }
}
