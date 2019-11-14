import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestTestModule } from '../../../test.module';
import { LocauxComponent } from 'app/entities/locaux/locaux.component';
import { LocauxService } from 'app/entities/locaux/locaux.service';
import { Locaux } from 'app/shared/model/locaux.model';

describe('Component Tests', () => {
  describe('Locaux Management Component', () => {
    let comp: LocauxComponent;
    let fixture: ComponentFixture<LocauxComponent>;
    let service: LocauxService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestTestModule],
        declarations: [LocauxComponent],
        providers: []
      })
        .overrideTemplate(LocauxComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LocauxComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocauxService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Locaux(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.locauxes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
