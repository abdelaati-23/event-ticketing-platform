import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TicketPurchaseComponent } from './features/ticket-purchase/ticket-purchase';
import {LoginComponent} from "./features/login/login";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,TicketPurchaseComponent,LoginComponent],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = signal('frontend');
}
