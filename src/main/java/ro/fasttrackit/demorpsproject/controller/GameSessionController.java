package ro.fasttrackit.demorpsproject.controller;

import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.demorpsproject.domain.GameSession;
import ro.fasttrackit.demorpsproject.domain.Hand;
import ro.fasttrackit.demorpsproject.service.GameSessionService;

import java.util.List;

@RestController
@RequestMapping("sessions")
public class GameSessionController {

    private final GameSessionService gameSessionService;

    public GameSessionController(GameSessionService gameSessionService) {
        this.gameSessionService = gameSessionService;
    }

    @GetMapping
    public List<GameSession> gameSessions() {
        return gameSessionService.allGameSessions();
    }

    @GetMapping("{id}")
    public GameSession getGameSessionById(@PathVariable Integer id) {
        return gameSessionService.getGameSessionById(id);
    }

    @PostMapping
    public GameSession createNewGameSession(@RequestParam(required = false) Integer firstPlayerId,
                                            @RequestParam(required = false) Integer secondPlayerId) {
        return gameSessionService.createNewSession(firstPlayerId, secondPlayerId);
    }

    @PutMapping("{id}")
    public GameSession addGameToGameSession(@PathVariable Integer id, @RequestParam String player1Hand,
                                            @RequestParam String player2Hand) {
        return gameSessionService.addGameToSession(id, Hand.valueOf(player1Hand.toUpperCase()),
                Hand.valueOf(player2Hand.toUpperCase()));
    }

}
