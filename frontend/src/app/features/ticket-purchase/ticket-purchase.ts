import { Component, inject, signal } from '@angular/core';
import {TicketService} from '../../core/services/ticket';

@Component({
  selector: 'app-ticket-purchase',
  imports: [],
  templateUrl: './ticket-purchase.html',
  styleUrl: './ticket-purchase.scss',
})
export class TicketPurchaseComponent {
  private ticketService= inject(TicketService);
  status = signal<'idle' | 'loading' | 'success' | 'error'>('idle');
  message= signal<string>('');
  eventId = '33333333-3333-3333-3333-333333333333';
  userId = '22222222-2222-2222-2222-222222222222';
  purchase(){
    this.status.set("loading");
    this.ticketService.purchaseTicket(this.eventId,this.userId,50.00).subscribe({
      next:(response)=>{
        this.status.set("success");
        this.message.set(`Billet confirmé ! Réf: ${response.id}`);
      },
      error:(err)=>{
        this.status.set("error");
        this.message.set("Erreur lors de la réservation du billet.");
      }
    });
  }
}
