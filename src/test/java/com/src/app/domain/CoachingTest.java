package com.src.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.src.app.web.rest.TestUtil;

public class CoachingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coaching.class);
        Coaching coaching1 = new Coaching();
        coaching1.setId(1L);
        Coaching coaching2 = new Coaching();
        coaching2.setId(coaching1.getId());
        assertThat(coaching1).isEqualTo(coaching2);
        coaching2.setId(2L);
        assertThat(coaching1).isNotEqualTo(coaching2);
        coaching1.setId(null);
        assertThat(coaching1).isNotEqualTo(coaching2);
    }
}
