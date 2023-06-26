import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit{
  username: string = "";
  password: string = "";
  firstname: string = "";
  lastname: string = "";
  email: string = "";
  phoneNumber: string = "";

  constructor(private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
  }

  onContinue(): void {
    this.router.navigateByUrl('/accueil');
  }

  onSubmitForm() {
    this.authService.postRegister(this.username, this.password, this.firstname, this.lastname, this.email, this.phoneNumber).subscribe((data: any) => {
      console.log(data);
      this.authService.setToken(data.token);
      this.onContinue();
    })
  }
}
