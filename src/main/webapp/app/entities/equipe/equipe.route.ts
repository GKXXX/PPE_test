import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Equipe } from 'app/shared/model/equipe.model';
import { EquipeService } from './equipe.service';
import { EquipeComponent } from './equipe.component';
import { EquipeDetailComponent } from './equipe-detail.component';
import { EquipeUpdateComponent } from './equipe-update.component';
import { EquipeDeletePopupComponent } from './equipe-delete-dialog.component';
import { IEquipe } from 'app/shared/model/equipe.model';

@Injectable({ providedIn: 'root' })
export class EquipeResolve implements Resolve<IEquipe> {
  constructor(private service: EquipeService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEquipe> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((equipe: HttpResponse<Equipe>) => equipe.body));
    }
    return of(new Equipe());
  }
}

export const equipeRoute: Routes = [
  {
    path: '',
    component: EquipeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Equipes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EquipeDetailComponent,
    resolve: {
      equipe: EquipeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Equipes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EquipeUpdateComponent,
    resolve: {
      equipe: EquipeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Equipes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EquipeUpdateComponent,
    resolve: {
      equipe: EquipeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Equipes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const equipePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EquipeDeletePopupComponent,
    resolve: {
      equipe: EquipeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Equipes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
