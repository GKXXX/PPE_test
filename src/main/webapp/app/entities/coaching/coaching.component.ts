import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ICoaching } from 'app/shared/model/coaching.model';
import { CoachingService } from './coaching.service';

@Component({
  selector: 'jhi-coaching',
  templateUrl: './coaching.component.html'
})
export class CoachingComponent implements OnInit, OnDestroy {
  coachings: ICoaching[];
  eventSubscriber: Subscription;

  constructor(protected coachingService: CoachingService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.coachingService.query().subscribe((res: HttpResponse<ICoaching[]>) => {
      this.coachings = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInCoachings();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICoaching) {
    return item.id;
  }

  registerChangeInCoachings() {
    this.eventSubscriber = this.eventManager.subscribe('coachingListModification', () => this.loadAll());
  }
}
