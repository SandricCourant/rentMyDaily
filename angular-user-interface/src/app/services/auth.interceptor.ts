import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.authService.getToken();

    if (token) {
      // If we have a token, we set it to the header
      request = request.clone({
         setHeaders: {Authorization: `Bearer ${token}`}
      });
    }
    return next.handle(request).pipe(
      catchError((err) => {
        if (err instanceof HttpErrorResponse) {
            if (err.status === 401) {
            // redirect user to the logout page
            console.log("erreur 401, redirect to logout page...");
         }
         if( err.status === 403){
            // redirect user to login with warning
            console.log("erreur de connexion");
         }
      }
      return throwError(() => err);
    })
     )
  }
}
