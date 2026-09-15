import { Component, inject, signal } from '@angular/core';
import {TicketService} from '../../core/services/ticket';
import {ButtonModule} from "primeng/button";
import {MessageModule} from "primeng/message";

@Component({
  selector: 'app-ticket-purchase',
  imports: [ButtonModule, MessageModule],
  templateUrl: './ticket-purchase.html',
  styleUrl: './ticket-purchase.scss',
  standalone: true,
})
export class TicketPurchaseComponent {
  private ticketService = inject(TicketService);
  lastTicketId = signal<string>('');
  status = signal<'idle' | 'loading' | 'success' | 'error'>('idle');
  message = signal<string>('');
  eventId = '33333333-3333-3333-3333-333333333333';
  userId = '22222222-2222-2222-2222-222222222222';
  purchase() {
    this.status.set('loading');
    this.ticketService.purchaseTicket(this.eventId, this.userId, 50.0).subscribe({
      next: (response) => {
        this.status.set('success');
        this.message.set(`Billet confirmé ! Réf: ${response.id}`);
        this.lastTicketId.set(response.id);
      },
      error: (err) => {
        this.status.set('error');
        this.message.set('Erreur lors de la réservation du billet.');
      },
    });
  }
  downloadPdf() {
    this.ticketService.downloadTicketPdf(this.lastTicketId()).subscribe((blob) => {
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = `ticket-${this.lastTicketId()}.pdf`;
      link.click();
      window.URL.revokeObjectURL(url);
    });
  }
}
