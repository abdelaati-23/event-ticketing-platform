import { Component, inject, signal } from '@angular/core';
import {AuthService} from '../../core/services/auth';
import {FormsModule} from '@angular/forms';
import {Router} from "@angular/router";
import {ButtonModule} from "primeng/button";
import {InputTextModule} from "primeng/inputtext";
import {CardModule} from "primeng/card";
import {MessageModule} from "primeng/message";

@Component({
  selector: 'app-login',
  imports: [FormsModule, ButtonModule,InputTextModule,CardModule,MessageModule ],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class LoginComponent {
  private authService = inject(AuthService);
  private router= inject(Router);
  email = signal<string>('');
  password = signal<string>('');
  status = signal<'idle' | 'loading' | 'success' | 'error'>('idle');

  onSubmit() {
    this.status.set('loading');
    this.authService.login(this.email(), this.password()).subscribe({
      next: () => {
        this.status.set('success');
        this.router.navigate( ['/purchase'])
      },
      error: () => this.status.set('error'),
    });
  }
}
