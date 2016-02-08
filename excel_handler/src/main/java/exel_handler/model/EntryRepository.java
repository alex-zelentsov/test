package exel_handler.model;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author azelentsov
 */
public interface EntryRepository extends JpaRepository<Entry, Long> {
}
