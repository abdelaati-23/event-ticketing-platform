import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TicketPurchaseComponent } from './features/ticket-purchase/ticket-purchase';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,TicketPurchaseComponent],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = signal('frontend');
}
