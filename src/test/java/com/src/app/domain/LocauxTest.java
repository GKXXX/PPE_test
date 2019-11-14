package com.src.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.src.app.web.rest.TestUtil;

public class LocauxTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Locaux.class);
        Locaux locaux1 = new Locaux();
        locaux1.setId(1L);
        Locaux locaux2 = new Locaux();
        locaux2.setId(locaux1.getId());
        assertThat(locaux1).isEqualTo(locaux2);
        locaux2.setId(2L);
        assertThat(locaux1).isNotEqualTo(locaux2);
        locaux1.setId(null);
        assertThat(locaux1).isNotEqualTo(locaux2);
    }
}
