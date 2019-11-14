import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IReservation } from 'app/shared/model/reservation.model';
import { ReservationService } from './reservation.service';

@Component({
  selector: 'jhi-reservation',
  templateUrl: './reservation.component.html'
})
export class ReservationComponent implements OnInit, OnDestroy {
  reservations: IReservation[];
  eventSubscriber: Subscription;

  constructor(protected reservationService: ReservationService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.reservationService.query().subscribe((res: HttpResponse<IReservation[]>) => {
      this.reservations = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInReservations();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IReservation) {
    return item.id;
  }

  registerChangeInReservations() {
    this.eventSubscriber = this.eventManager.subscribe('reservationListModification', () => this.loadAll());
  }
}
