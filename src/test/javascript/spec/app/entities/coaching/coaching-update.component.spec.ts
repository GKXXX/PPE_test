import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestTestModule } from '../../../test.module';
import { CoachingUpdateComponent } from 'app/entities/coaching/coaching-update.component';
import { CoachingService } from 'app/entities/coaching/coaching.service';
import { Coaching } from 'app/shared/model/coaching.model';

describe('Component Tests', () => {
  describe('Coaching Management Update Component', () => {
    let comp: CoachingUpdateComponent;
    let fixture: ComponentFixture<CoachingUpdateComponent>;
    let service: CoachingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestTestModule],
        declarations: [CoachingUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CoachingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CoachingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CoachingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Coaching(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Coaching();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
