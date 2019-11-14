import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestSharedModule } from 'app/shared/shared.module';
import { EquipeComponent } from './equipe.component';
import { EquipeDetailComponent } from './equipe-detail.component';
import { EquipeUpdateComponent } from './equipe-update.component';
import { EquipeDeletePopupComponent, EquipeDeleteDialogComponent } from './equipe-delete-dialog.component';
import { equipeRoute, equipePopupRoute } from './equipe.route';

const ENTITY_STATES = [...equipeRoute, ...equipePopupRoute];

@NgModule({
  imports: [TestSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [EquipeComponent, EquipeDetailComponent, EquipeUpdateComponent, EquipeDeleteDialogComponent, EquipeDeletePopupComponent],
  entryComponents: [EquipeDeleteDialogComponent]
})
export class TestEquipeModule {}
