package com.src.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.src.app.web.rest.TestUtil;

public class EquipementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Equipement.class);
        Equipement equipement1 = new Equipement();
        equipement1.setId(1L);
        Equipement equipement2 = new Equipement();
        equipement2.setId(equipement1.getId());
        assertThat(equipement1).isEqualTo(equipement2);
        equipement2.setId(2L);
        assertThat(equipement1).isNotEqualTo(equipement2);
        equipement1.setId(null);
        assertThat(equipement1).isNotEqualTo(equipement2);
    }
}
