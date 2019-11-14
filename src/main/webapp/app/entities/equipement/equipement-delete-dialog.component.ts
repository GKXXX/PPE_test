import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEquipement } from 'app/shared/model/equipement.model';
import { EquipementService } from './equipement.service';

@Component({
  selector: 'jhi-equipement-delete-dialog',
  templateUrl: './equipement-delete-dialog.component.html'
})
export class EquipementDeleteDialogComponent {
  equipement: IEquipement;

  constructor(
    protected equipementService: EquipementService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.equipementService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'equipementListModification',
        content: 'Deleted an equipement'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-equipement-delete-popup',
  template: ''
})
export class EquipementDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ equipement }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EquipementDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.equipement = equipement;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/equipement', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/equipement', { outlets: { popup: null } }]);
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
