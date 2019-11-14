import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestTestModule } from '../../../test.module';
import { EquipementComponent } from 'app/entities/equipement/equipement.component';
import { EquipementService } from 'app/entities/equipement/equipement.service';
import { Equipement } from 'app/shared/model/equipement.model';

describe('Component Tests', () => {
  describe('Equipement Management Component', () => {
    let comp: EquipementComponent;
    let fixture: ComponentFixture<EquipementComponent>;
    let service: EquipementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestTestModule],
        declarations: [EquipementComponent],
        providers: []
      })
        .overrideTemplate(EquipementComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EquipementComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EquipementService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Equipement(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.equipements[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
