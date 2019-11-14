package com.src.app.repository;
import com.src.app.domain.Locaux;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Locaux entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocauxRepository extends JpaRepository<Locaux, Long> {

}
