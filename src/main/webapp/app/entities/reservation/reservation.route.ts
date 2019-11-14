import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Reservation } from 'app/shared/model/reservation.model';
import { ReservationService } from './reservation.service';
import { ReservationComponent } from './reservation.component';
import { ReservationDetailComponent } from './reservation-detail.component';
import { ReservationUpdateComponent } from './reservation-update.component';
import { ReservationDeletePopupComponent } from './reservation-delete-dialog.component';
import { IReservation } from 'app/shared/model/reservation.model';

@Injectable({ providedIn: 'root' })
export class ReservationResolve implements Resolve<IReservation> {
  constructor(private service: ReservationService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReservation> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((reservation: HttpResponse<Reservation>) => reservation.body));
    }
    return of(new Reservation());
  }
}

export const reservationRoute: Routes = [
  {
    path: '',
    component: ReservationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Reservations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ReservationDetailComponent,
    resolve: {
      reservation: ReservationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Reservations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ReservationUpdateComponent,
    resolve: {
      reservation: ReservationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Reservations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ReservationUpdateComponent,
    resolve: {
      reservation: ReservationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Reservations'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const reservationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ReservationDeletePopupComponent,
    resolve: {
      reservation: ReservationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Reservations'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
