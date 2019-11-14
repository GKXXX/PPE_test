package com.src.app.repository;
import com.src.app.domain.Equipement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Equipement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipementRepository extends JpaRepository<Equipement, Long> {

}
