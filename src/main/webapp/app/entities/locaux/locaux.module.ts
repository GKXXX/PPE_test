import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestSharedModule } from 'app/shared/shared.module';
import { LocauxComponent } from './locaux.component';
import { LocauxDetailComponent } from './locaux-detail.component';
import { LocauxUpdateComponent } from './locaux-update.component';
import { LocauxDeletePopupComponent, LocauxDeleteDialogComponent } from './locaux-delete-dialog.component';
import { locauxRoute, locauxPopupRoute } from './locaux.route';

const ENTITY_STATES = [...locauxRoute, ...locauxPopupRoute];

@NgModule({
  imports: [TestSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [LocauxComponent, LocauxDetailComponent, LocauxUpdateComponent, LocauxDeleteDialogComponent, LocauxDeletePopupComponent],
  entryComponents: [LocauxDeleteDialogComponent]
})
export class TestLocauxModule {}
