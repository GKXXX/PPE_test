import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IEquipe } from 'app/shared/model/equipe.model';
import { EquipeService } from './equipe.service';

@Component({
  selector: 'jhi-equipe',
  templateUrl: './equipe.component.html'
})
export class EquipeComponent implements OnInit, OnDestroy {
  equipes: IEquipe[];
  eventSubscriber: Subscription;

  constructor(protected equipeService: EquipeService, protected eventManager: JhiEventManager) {}

  loadAll() {
    this.equipeService.query().subscribe((res: HttpResponse<IEquipe[]>) => {
      this.equipes = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInEquipes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEquipe) {
    return item.id;
  }

  registerChangeInEquipes() {
    this.eventSubscriber = this.eventManager.subscribe('equipeListModification', () => this.loadAll());
  }
}
