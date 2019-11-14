import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IEquipe, Equipe } from 'app/shared/model/equipe.model';
import { EquipeService } from './equipe.service';

@Component({
  selector: 'jhi-equipe-update',
  templateUrl: './equipe-update.component.html'
})
export class EquipeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nom: [],
    jeu: [],
    taille: [],
    telephone: [null, [Validators.required]]
  });

  constructor(protected equipeService: EquipeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ equipe }) => {
      this.updateForm(equipe);
    });
  }

  updateForm(equipe: IEquipe) {
    this.editForm.patchValue({
      id: equipe.id,
      nom: equipe.nom,
      jeu: equipe.jeu,
      taille: equipe.taille,
      telephone: equipe.telephone
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const equipe = this.createFromForm();
    if (equipe.id !== undefined) {
      this.subscribeToSaveResponse(this.equipeService.update(equipe));
    } else {
      this.subscribeToSaveResponse(this.equipeService.create(equipe));
    }
  }

  private createFromForm(): IEquipe {
    return {
      ...new Equipe(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      jeu: this.editForm.get(['jeu']).value,
      taille: this.editForm.get(['taille']).value,
      telephone: this.editForm.get(['telephone']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEquipe>>) {
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
