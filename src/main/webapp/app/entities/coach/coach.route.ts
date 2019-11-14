import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Coach } from 'app/shared/model/coach.model';
import { CoachService } from './coach.service';
import { CoachComponent } from './coach.component';
import { CoachDetailComponent } from './coach-detail.component';
import { CoachUpdateComponent } from './coach-update.component';
import { CoachDeletePopupComponent } from './coach-delete-dialog.component';
import { ICoach } from 'app/shared/model/coach.model';

@Injectable({ providedIn: 'root' })
export class CoachResolve implements Resolve<ICoach> {
  constructor(private service: CoachService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICoach> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((coach: HttpResponse<Coach>) => coach.body));
    }
    return of(new Coach());
  }
}

export const coachRoute: Routes = [
  {
    path: '',
    component: CoachComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Coaches'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CoachDetailComponent,
    resolve: {
      coach: CoachResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Coaches'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CoachUpdateComponent,
    resolve: {
      coach: CoachResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Coaches'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CoachUpdateComponent,
    resolve: {
      coach: CoachResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Coaches'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const coachPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CoachDeletePopupComponent,
    resolve: {
      coach: CoachResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Coaches'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
