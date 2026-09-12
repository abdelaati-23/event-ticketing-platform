import { ComponentFixture, TestBed } from '@angular/core/testing';

import {  TicketPurchaseComponent } from './ticket-purchase';

describe('TicketPurchase', () => {
  let component: TicketPurchaseComponent;
  let fixture: ComponentFixture<TicketPurchaseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketPurchaseComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(TicketPurchaseComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
