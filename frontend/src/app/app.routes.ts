import { Routes } from '@angular/router';
import {LoginComponent} from './features/login/login';
import {TicketPurchaseComponent} from './features/ticket-purchase/ticket-purchase';
import {authGuard} from './core/guards/auth-guard';

export const routes: Routes = [
  {path:'login', component: LoginComponent},
  {path:'purshase', component: TicketPurchaseComponent, canActivate:[authGuard]},
  { path: '', redirectTo: '/login', pathMatch: 'full' }, // Default redirection
  { path: '**', redirectTo: '/login' } // Fallback for unknown URLs
];
