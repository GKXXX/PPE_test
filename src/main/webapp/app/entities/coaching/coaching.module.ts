import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestSharedModule } from 'app/shared/shared.module';
import { CoachingComponent } from './coaching.component';
import { CoachingDetailComponent } from './coaching-detail.component';
import { CoachingUpdateComponent } from './coaching-update.component';
import { CoachingDeletePopupComponent, CoachingDeleteDialogComponent } from './coaching-delete-dialog.component';
import { coachingRoute, coachingPopupRoute } from './coaching.route';

const ENTITY_STATES = [...coachingRoute, ...coachingPopupRoute];

@NgModule({
  imports: [TestSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CoachingComponent,
    CoachingDetailComponent,
    CoachingUpdateComponent,
    CoachingDeleteDialogComponent,
    CoachingDeletePopupComponent
  ],
  entryComponents: [CoachingDeleteDialogComponent]
})
export class TestCoachingModule {}
