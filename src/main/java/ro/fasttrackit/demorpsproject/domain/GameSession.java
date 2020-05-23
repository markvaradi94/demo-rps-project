package ro.fasttrackit.demorpsproject.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "GameSessions")
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class GameSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_session_id", referencedColumnName = "id")
    private Set<Game> games = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_session_id", referencedColumnName = "id")
    private Set<Player> sessionPlayers = new HashSet<>();

    public GameSession(Set<Game> games, Set<Player> sessionPlayers) {
        this.games = games;
        this.sessionPlayers = sessionPlayers;
    }

    public GameSession addGame(Game game) {
        game.setGameSession(this);
        this.games.add(game);
        return this;
    }

    public GameSession addPlayers(Player player1, Player player2) {
        player1.setGameSession(this);
        player2.setGameSession(this);
        this.sessionPlayers.add(player1);
        this.sessionPlayers.add(player2);
        return this;
    }


}
