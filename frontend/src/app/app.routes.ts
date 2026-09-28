import { Routes } from '@angular/router';
import {LoginComponent} from './features/login/login';
import {TicketPurchaseComponent} from './features/ticket-purchase/ticket-purchase';
import {authGuard} from './core/guards/auth-guard';
import { RegisterComponent } from './features/register/register';
import { AiChatComponent } from './features/components/ai-chat-component/ai-chat-component';

export const routes: Routes = [
  {path:'login', component: LoginComponent},
  {path:'register', component: RegisterComponent},
  { path: 'assistant', component: AiChatComponent, canActivate: [authGuard] },
  {path:'purchase', component: TicketPurchaseComponent, canActivate:[authGuard]},
  { path: '', redirectTo: '/login', pathMatch: 'full' }, // Default redirection
  { path: '**', redirectTo: '/login' } // Fallback for unknown URLs
];
