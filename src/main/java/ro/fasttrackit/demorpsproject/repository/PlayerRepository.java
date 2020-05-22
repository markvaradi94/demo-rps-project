package ro.fasttrackit.demorpsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.fasttrackit.demorpsproject.domain.Player;

public interface PlayerRepository extends JpaRepository<Player, Integer> {
}
