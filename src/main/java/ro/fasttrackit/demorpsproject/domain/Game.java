package ro.fasttrackit.demorpsproject.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Game {

    private Integer id;

    private Integer firstPlayerId;
    private Integer secondPlayerId;
    private GameResult gameResult;

    public Game(Integer id, Integer firstPlayerId, Integer secondPlayerId, GameResult gameResult) {
        this.id = id;
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerId = secondPlayerId;
        this.gameResult = gameResult;
    }

    public Game(Integer firstPlayerId, Integer secondPlayerId, GameResult gameResult) {
        this(0, firstPlayerId, secondPlayerId, gameResult);
    }

    public Game(Integer firstPlayerId, Integer secondPlayerId) {
        this(0, firstPlayerId, secondPlayerId, GameResult.WAITING_FOR_PLAYER);
    }
}
