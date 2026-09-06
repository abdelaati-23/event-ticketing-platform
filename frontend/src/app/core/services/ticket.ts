import { inject, Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TicketService {
  private http=inject(HttpClient);
  private readonly API_URL = 'http://localhost:8080/api/v1/tickets';
  purchaseTicket(eventId:string, userId: string, price: number):Observable<any> {
    const payload= {eventId, userId, price};
    return this.http.post(`${this.API_URL}/purchase`, payload);
  }
}
