import { inject, Injectable, signal } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {tap} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http=inject(HttpClient);
  private readonly API_URL = 'http://localhost:8080/api/v1/auth';
  isAuthenticated=signal<boolean>(!!localStorage.getItem('jwt_token'));

  login(email:string, password:string){
    const payload={email, password};
    return this.http.post<{token:string}>(`${this.API_URL}/authenticate`, payload).pipe(
      tap((response)=>{
        localStorage.setItem('jwt_token', response.token);
        this.isAuthenticated.set(true);
      })
    )
  }
  logout(){
    localStorage.removeItem('jwt_token');
    this.isAuthenticated.set(false);
  }
  register(email:string, password:string){
    const payload={email,password,role:"CUSTOMER"};
    return this.http.post<{token:string}>(`${this.API_URL}/register`,payload).pipe(
      tap((response)=>{
        localStorage.setItem('jwt_token',response.token);
        this.isAuthenticated.set(true);
      })
    )
  }

}
