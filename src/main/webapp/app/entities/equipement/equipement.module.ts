import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestSharedModule } from 'app/shared/shared.module';
import { EquipementComponent } from './equipement.component';
import { EquipementDetailComponent } from './equipement-detail.component';
import { EquipementUpdateComponent } from './equipement-update.component';
import { EquipementDeletePopupComponent, EquipementDeleteDialogComponent } from './equipement-delete-dialog.component';
import { equipementRoute, equipementPopupRoute } from './equipement.route';

const ENTITY_STATES = [...equipementRoute, ...equipementPopupRoute];

@NgModule({
  imports: [TestSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EquipementComponent,
    EquipementDetailComponent,
    EquipementUpdateComponent,
    EquipementDeleteDialogComponent,
    EquipementDeletePopupComponent
  ],
  entryComponents: [EquipementDeleteDialogComponent]
})
export class TestEquipementModule {}
