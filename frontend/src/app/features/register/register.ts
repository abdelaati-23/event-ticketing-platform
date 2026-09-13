import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { CardModule } from 'primeng/card';
import { MessageModule } from 'primeng/message';
import {AuthService} from '../../core/services/auth';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, ButtonModule, InputTextModule, CardModule, MessageModule],
  template: `
    <div style="display: flex; justify-content: center; margin-top: 5rem;">
      <p-card header="Créer un compte" [style]="{ width: '350px' }">
        <div style="display: flex; flex-direction: column; gap: 1rem;">
          <label>Email</label>
          <input pInputText type="email" [(ngModel)]="email">
          <label>Mot de passe</label>
          <input pInputText type="password" [(ngModel)]="password">

          <p-button label="S'inscrire" (onClick)="onRegister()" [loading]="status() === 'loading'"></p-button>
        </div>
      </p-card>
    </div>
  `
})
export class RegisterComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  email = signal('');
  password = signal('');
  status = signal<'idle' | 'loading' | 'success' | 'error'>('idle');

  onRegister() {
    this.status.set('loading');
    this.authService.register(this.email(), this.password()).subscribe({
      next: () => this.router.navigate(['/login']),
      error: () => this.status.set('error')
    });
  }
}
