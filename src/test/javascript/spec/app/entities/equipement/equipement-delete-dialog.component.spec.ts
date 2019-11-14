import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TestTestModule } from '../../../test.module';
import { EquipementDeleteDialogComponent } from 'app/entities/equipement/equipement-delete-dialog.component';
import { EquipementService } from 'app/entities/equipement/equipement.service';

describe('Component Tests', () => {
  describe('Equipement Management Delete Component', () => {
    let comp: EquipementDeleteDialogComponent;
    let fixture: ComponentFixture<EquipementDeleteDialogComponent>;
    let service: EquipementService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestTestModule],
        declarations: [EquipementDeleteDialogComponent]
      })
        .overrideTemplate(EquipementDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EquipementDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EquipementService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
