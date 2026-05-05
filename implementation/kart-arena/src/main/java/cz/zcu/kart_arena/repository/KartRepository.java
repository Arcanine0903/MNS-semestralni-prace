package cz.zcu.kart_arena.repository;

import cz.zcu.kart_arena.model.Kart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Kart class.
 */
@Repository
public interface KartRepository extends JpaRepository<Kart, Long> {
}