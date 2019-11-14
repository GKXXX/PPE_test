import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ICoach } from 'app/shared/model/coach.model';
import { CoachService } from './coach.service';

@Component({
  selector: 'jhi-coach',
  templateUrl: './coach.component.html'
})
export class CoachComponent implements OnInit, OnDestroy {
  coaches: ICoach[];
  eventSubscriber: Subscription;

  constructor(protected coachService: CoachService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.coachService.query().subscribe((res: HttpResponse<ICoach[]>) => {
      this.coaches = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInCoaches();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICoach) {
    return item.id;
  }

  registerChangeInCoaches() {
    this.eventSubscriber = this.eventManager.subscribe('coachListModification', () => this.loadAll());
  }
}
