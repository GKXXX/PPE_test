import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICoaching } from 'app/shared/model/coaching.model';

@Component({
  selector: 'jhi-coaching-detail',
  templateUrl: './coaching-detail.component.html'
})
export class CoachingDetailComponent implements OnInit {
  coaching: ICoaching;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ coaching }) => {
      this.coaching = coaching;
    });
  }

  previousState() {
    window.history.back();
  }
}
