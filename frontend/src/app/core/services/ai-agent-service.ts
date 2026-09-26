import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AiAgentService {
  private http = inject(HttpClient);
  private readonly API_URL = 'http://localhost:8080/api/v1/ai/chat';

  sendMessage(message: string): Observable<string> {
    const token = localStorage.getItem('token') || '';
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    });

    return this.http.post(this.API_URL, JSON.stringify(message), {
      headers,
      responseType: 'text',
    });
  }
}
