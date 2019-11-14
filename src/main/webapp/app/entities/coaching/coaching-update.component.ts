import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ICoaching, Coaching } from 'app/shared/model/coaching.model';
import { CoachingService } from './coaching.service';
import { ICoach } from 'app/shared/model/coach.model';
import { CoachService } from 'app/entities/coach/coach.service';

@Component({
  selector: 'jhi-coaching-update',
  templateUrl: './coaching-update.component.html'
})
export class CoachingUpdateComponent implements OnInit {
  isSaving: boolean;

  coaches: ICoach[];

  editForm = this.fb.group({
    id: [],
    type: [],
    jeu: [],
    prix: [],
    coach: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected coachingService: CoachingService,
    protected coachService: CoachService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ coaching }) => {
      this.updateForm(coaching);
    });
    this.coachService
      .query()
      .subscribe((res: HttpResponse<ICoach[]>) => (this.coaches = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(coaching: ICoaching) {
    this.editForm.patchValue({
      id: coaching.id,
      type: coaching.type,
      jeu: coaching.jeu,
      prix: coaching.prix,
      coach: coaching.coach
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const coaching = this.createFromForm();
    if (coaching.id !== undefined) {
      this.subscribeToSaveResponse(this.coachingService.update(coaching));
    } else {
      this.subscribeToSaveResponse(this.coachingService.create(coaching));
    }
  }

  private createFromForm(): ICoaching {
    return {
      ...new Coaching(),
      id: this.editForm.get(['id']).value,
      type: this.editForm.get(['type']).value,
      jeu: this.editForm.get(['jeu']).value,
      prix: this.editForm.get(['prix']).value,
      coach: this.editForm.get(['coach']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICoaching>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackCoachById(index: number, item: ICoach) {
    return item.id;
  }
}
