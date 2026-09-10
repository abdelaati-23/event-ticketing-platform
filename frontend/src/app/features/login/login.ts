import { Component, inject, signal } from '@angular/core';
import {AuthService} from '../../core/services/auth';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class LoginComponent {
  private authService = inject(AuthService);
  email = signal<string>('');
  password = signal<string>('');
  status = signal<'idle' | 'loading' | 'success' | 'error'>('idle');

  onSubmit() {
    this.status.set('loading');
    this.authService.login(this.email(), this.password()).subscribe({
      next: () => this.status.set('success'),
      error: () => this.status.set('error'),
    });
  }
}
