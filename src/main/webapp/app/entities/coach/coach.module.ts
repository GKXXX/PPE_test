import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestSharedModule } from 'app/shared/shared.module';
import { CoachComponent } from './coach.component';
import { CoachDetailComponent } from './coach-detail.component';
import { CoachUpdateComponent } from './coach-update.component';
import { CoachDeletePopupComponent, CoachDeleteDialogComponent } from './coach-delete-dialog.component';
import { coachRoute, coachPopupRoute } from './coach.route';

const ENTITY_STATES = [...coachRoute, ...coachPopupRoute];

@NgModule({
  imports: [TestSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [CoachComponent, CoachDetailComponent, CoachUpdateComponent, CoachDeleteDialogComponent, CoachDeletePopupComponent],
  entryComponents: [CoachDeleteDialogComponent]
})
export class TestCoachModule {}
