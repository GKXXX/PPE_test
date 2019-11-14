import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IEquipement, Equipement } from 'app/shared/model/equipement.model';
import { EquipementService } from './equipement.service';

@Component({
  selector: 'jhi-equipement-update',
  templateUrl: './equipement-update.component.html'
})
export class EquipementUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    type: [],
    nom: [],
    prixJour: []
  });

  constructor(protected equipementService: EquipementService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ equipement }) => {
      this.updateForm(equipement);
    });
  }

  updateForm(equipement: IEquipement) {
    this.editForm.patchValue({
      id: equipement.id,
      type: equipement.type,
      nom: equipement.nom,
      prixJour: equipement.prixJour
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const equipement = this.createFromForm();
    if (equipement.id !== undefined) {
      this.subscribeToSaveResponse(this.equipementService.update(equipement));
    } else {
      this.subscribeToSaveResponse(this.equipementService.create(equipement));
    }
  }

  private createFromForm(): IEquipement {
    return {
      ...new Equipement(),
      id: this.editForm.get(['id']).value,
      type: this.editForm.get(['type']).value,
      nom: this.editForm.get(['nom']).value,
      prixJour: this.editForm.get(['prixJour']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEquipement>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
