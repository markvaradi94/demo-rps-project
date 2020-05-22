package ro.fasttrackit.demorpsproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Games")
@EqualsAndHashCode(exclude = {"gameSession"})
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer firstPlayerId;
    private Integer secondPlayerId;

    @Enumerated(EnumType.STRING)
    private GameResult gameResult;

    @JsonIgnore
    @ManyToOne
    private GameSession gameSession;

    public Game(Integer firstPlayerId, Integer secondPlayerId, GameResult gameResult, GameSession gameSession) {
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerId = secondPlayerId;
        this.gameResult = gameResult;
        this.gameSession = gameSession;
    }

    public Game(Integer firstPlayerId, Integer secondPlayerId, GameResult gameResult) {
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerId = secondPlayerId;
        this.gameResult = gameResult;
    }

    public Game(Integer firstPlayerId, Integer secondPlayerId) {
        this(firstPlayerId, secondPlayerId, GameResult.WAITING_FOR_PLAYER);
    }
}
