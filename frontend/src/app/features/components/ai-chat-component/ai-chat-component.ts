import { Component, ElementRef, inject, signal, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { AiAgentService } from '../../../core/services/ai-agent-service';

export interface ChatMessage {
  sender: 'user' | 'agent';
  text: string;
  time: string;
}

@Component({
  selector: 'app-ai-chat',
  standalone: true,
  imports: [CommonModule, FormsModule, ButtonModule, InputTextModule],
  templateUrl: './ai-chat-component.html',
  styleUrl: './ai-chat-component.scss',
})
export class AiChatComponent {
  private aiService = inject(AiAgentService);

  @ViewChild('scrollContainer') private scrollContainer!: ElementRef;

  userInput = signal<string>('');
  isLoading = signal<boolean>(false);
  messages = signal<ChatMessage[]>([
    {
      sender: 'agent',
      text: 'Hello! I can check your tickets, browse available events, or book one for you. How can I help?',
      time: this.getCurrentTime(),
    },
  ]);

  sendMessage(): void {
    const prompt = this.userInput().trim();
    if (!prompt || this.isLoading()) return;

    this.messages.update((prev) => [
      ...prev,
      { sender: 'user', text: prompt, time: this.getCurrentTime() },
    ]);
    this.userInput.set('');
    this.isLoading.set(true);
    this.scrollToBottom();

    this.aiService.sendMessage(prompt).subscribe({
      next: (response) => {
        this.messages.update((prev) => [
          ...prev,
          { sender: 'agent', text: response, time: this.getCurrentTime() },
        ]);
        this.isLoading.set(false);
        this.scrollToBottom();
      },
      error: (err) => {
        const errorText =
          err.status === 403 || err.status === 401
            ? 'Session expired. Please log in again.'
            : 'Sorry, I encountered an error processing your request.';

        this.messages.update((prev) => [
          ...prev,
          { sender: 'agent', text: errorText, time: this.getCurrentTime() },
        ]);
        this.isLoading.set(false);
        this.scrollToBottom();
      },
    });
  }

  private getCurrentTime(): string {
    return new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
  }

  private scrollToBottom(): void {
    setTimeout(() => {
      if (this.scrollContainer) {
        this.scrollContainer.nativeElement.scrollTop =
          this.scrollContainer.nativeElement.scrollHeight;
      }
    }, 50);
  }
}
