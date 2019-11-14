import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestTestModule } from '../../../test.module';
import { LocauxUpdateComponent } from 'app/entities/locaux/locaux-update.component';
import { LocauxService } from 'app/entities/locaux/locaux.service';
import { Locaux } from 'app/shared/model/locaux.model';

describe('Component Tests', () => {
  describe('Locaux Management Update Component', () => {
    let comp: LocauxUpdateComponent;
    let fixture: ComponentFixture<LocauxUpdateComponent>;
    let service: LocauxService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestTestModule],
        declarations: [LocauxUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LocauxUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LocauxUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocauxService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Locaux(123);
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
        const entity = new Locaux();
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
