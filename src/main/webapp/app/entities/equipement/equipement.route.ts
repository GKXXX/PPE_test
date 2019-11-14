import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Equipement } from 'app/shared/model/equipement.model';
import { EquipementService } from './equipement.service';
import { EquipementComponent } from './equipement.component';
import { EquipementDetailComponent } from './equipement-detail.component';
import { EquipementUpdateComponent } from './equipement-update.component';
import { EquipementDeletePopupComponent } from './equipement-delete-dialog.component';
import { IEquipement } from 'app/shared/model/equipement.model';

@Injectable({ providedIn: 'root' })
export class EquipementResolve implements Resolve<IEquipement> {
  constructor(private service: EquipementService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEquipement> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((equipement: HttpResponse<Equipement>) => equipement.body));
    }
    return of(new Equipement());
  }
}

export const equipementRoute: Routes = [
  {
    path: '',
    component: EquipementComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Equipements'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EquipementDetailComponent,
    resolve: {
      equipement: EquipementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Equipements'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EquipementUpdateComponent,
    resolve: {
      equipement: EquipementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Equipements'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EquipementUpdateComponent,
    resolve: {
      equipement: EquipementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Equipements'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const equipementPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EquipementDeletePopupComponent,
    resolve: {
      equipement: EquipementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Equipements'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
