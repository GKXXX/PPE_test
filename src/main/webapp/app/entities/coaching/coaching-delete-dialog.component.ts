import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICoaching } from 'app/shared/model/coaching.model';
import { CoachingService } from './coaching.service';

@Component({
  selector: 'jhi-coaching-delete-dialog',
  templateUrl: './coaching-delete-dialog.component.html'
})
export class CoachingDeleteDialogComponent {
  coaching: ICoaching;

  constructor(protected coachingService: CoachingService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.coachingService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'coachingListModification',
        content: 'Deleted an coaching'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-coaching-delete-popup',
  template: ''
})
export class CoachingDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ coaching }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CoachingDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.coaching = coaching;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/coaching', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/coaching', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
