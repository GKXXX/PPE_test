package com.src.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.src.app.web.rest.TestUtil;

public class CoachTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coach.class);
        Coach coach1 = new Coach();
        coach1.setId(1L);
        Coach coach2 = new Coach();
        coach2.setId(coach1.getId());
        assertThat(coach1).isEqualTo(coach2);
        coach2.setId(2L);
        assertThat(coach1).isNotEqualTo(coach2);
        coach1.setId(null);
        assertThat(coach1).isNotEqualTo(coach2);
    }
}
