package ro.fasttrackit.demorpsproject.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "GameSessions")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"games"})
public class GameSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Game> games = new HashSet<>();

    public GameSession(Set<Game> games) {
        this.games = games;
    }

    public GameSession addGame(Game game) {
        game.setGameSession(this);
        this.games.add(game);
        return this;
    }
}
