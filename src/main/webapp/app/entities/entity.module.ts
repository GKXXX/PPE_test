import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'locaux',
        loadChildren: () => import('./locaux/locaux.module').then(m => m.TestLocauxModule)
      },
      {
        path: 'coach',
        loadChildren: () => import('./coach/coach.module').then(m => m.TestCoachModule)
      },
      {
        path: 'equipement',
        loadChildren: () => import('./equipement/equipement.module').then(m => m.TestEquipementModule)
      },
      {
        path: 'equipe',
        loadChildren: () => import('./equipe/equipe.module').then(m => m.TestEquipeModule)
      },
      {
        path: 'coaching',
        loadChildren: () => import('./coaching/coaching.module').then(m => m.TestCoachingModule)
      },
      {
        path: 'reservation',
        loadChildren: () => import('./reservation/reservation.module').then(m => m.TestReservationModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class TestEntityModule {}
