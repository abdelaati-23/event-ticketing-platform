import { Component, inject, signal } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import {ToolbarModule} from "primeng/toolbar";
import {ButtonModule} from "primeng/button";
import {AuthService} from "./core/services/auth";


@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink,ToolbarModule,ButtonModule],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected authService=inject(AuthService);
  private router=inject(Router);
  logout(){
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
