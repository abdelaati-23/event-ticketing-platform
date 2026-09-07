import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketPurchase } from './ticket-purchase';

describe('TicketPurchase', () => {
  let component: TicketPurchase;
  let fixture: ComponentFixture<TicketPurchase>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketPurchase],
    }).compileComponents();

    fixture = TestBed.createComponent(TicketPurchase);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
