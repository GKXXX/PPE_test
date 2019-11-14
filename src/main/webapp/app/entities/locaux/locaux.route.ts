import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Locaux } from 'app/shared/model/locaux.model';
import { LocauxService } from './locaux.service';
import { LocauxComponent } from './locaux.component';
import { LocauxDetailComponent } from './locaux-detail.component';
import { LocauxUpdateComponent } from './locaux-update.component';
import { LocauxDeletePopupComponent } from './locaux-delete-dialog.component';
import { ILocaux } from 'app/shared/model/locaux.model';

@Injectable({ providedIn: 'root' })
export class LocauxResolve implements Resolve<ILocaux> {
  constructor(private service: LocauxService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILocaux> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((locaux: HttpResponse<Locaux>) => locaux.body));
    }
    return of(new Locaux());
  }
}

export const locauxRoute: Routes = [
  {
    path: '',
    component: LocauxComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Locauxes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LocauxDetailComponent,
    resolve: {
      locaux: LocauxResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Locauxes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LocauxUpdateComponent,
    resolve: {
      locaux: LocauxResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Locauxes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LocauxUpdateComponent,
    resolve: {
      locaux: LocauxResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Locauxes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const locauxPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LocauxDeletePopupComponent,
    resolve: {
      locaux: LocauxResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Locauxes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
