import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICoach, Coach } from 'app/shared/model/coach.model';
import { CoachService } from './coach.service';

@Component({
  selector: 'jhi-coach-update',
  templateUrl: './coach-update.component.html'
})
export class CoachUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nom: [],
    jeu: [],
    prixJour: [],
    telephone: [null, [Validators.required]],
    dispo: []
  });

  constructor(protected coachService: CoachService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ coach }) => {
      this.updateForm(coach);
    });
  }

  updateForm(coach: ICoach) {
    this.editForm.patchValue({
      id: coach.id,
      nom: coach.nom,
      jeu: coach.jeu,
      prixJour: coach.prixJour,
      telephone: coach.telephone,
      dispo: coach.dispo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const coach = this.createFromForm();
    if (coach.id !== undefined) {
      this.subscribeToSaveResponse(this.coachService.update(coach));
    } else {
      this.subscribeToSaveResponse(this.coachService.create(coach));
    }
  }

  private createFromForm(): ICoach {
    return {
      ...new Coach(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      jeu: this.editForm.get(['jeu']).value,
      prixJour: this.editForm.get(['prixJour']).value,
      telephone: this.editForm.get(['telephone']).value,
      dispo: this.editForm.get(['dispo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICoach>>) {
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
