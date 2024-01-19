package server.database;

import org.springframework.data.jpa.repository.JpaRepository;
import commons.Activity;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    /**
     * Find all activities that match the description.
     *
     * @param description The string pattern to search for.
     * @return The list of all activities matching the string.
     */
    List<Activity> findByDescriptionContaining(String description);
}
