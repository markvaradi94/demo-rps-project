package ro.fasttrackit.demorpsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.fasttrackit.demorpsproject.domain.Game;

public interface GameRepository extends JpaRepository<Game, Integer> {
}
