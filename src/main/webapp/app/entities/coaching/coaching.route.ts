import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Coaching } from 'app/shared/model/coaching.model';
import { CoachingService } from './coaching.service';
import { CoachingComponent } from './coaching.component';
import { CoachingDetailComponent } from './coaching-detail.component';
import { CoachingUpdateComponent } from './coaching-update.component';
import { CoachingDeletePopupComponent } from './coaching-delete-dialog.component';
import { ICoaching } from 'app/shared/model/coaching.model';

@Injectable({ providedIn: 'root' })
export class CoachingResolve implements Resolve<ICoaching> {
  constructor(private service: CoachingService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICoaching> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((coaching: HttpResponse<Coaching>) => coaching.body));
    }
    return of(new Coaching());
  }
}

export const coachingRoute: Routes = [
  {
    path: '',
    component: CoachingComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Coachings'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CoachingDetailComponent,
    resolve: {
      coaching: CoachingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Coachings'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CoachingUpdateComponent,
    resolve: {
      coaching: CoachingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Coachings'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CoachingUpdateComponent,
    resolve: {
      coaching: CoachingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Coachings'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const coachingPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CoachingDeletePopupComponent,
    resolve: {
      coaching: CoachingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Coachings'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
