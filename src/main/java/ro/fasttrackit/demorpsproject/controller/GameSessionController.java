package ro.fasttrackit.demorpsproject.controller;

import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.demorpsproject.domain.Game;
import ro.fasttrackit.demorpsproject.domain.GameSession;
import ro.fasttrackit.demorpsproject.domain.Player;
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
    public GameSession newGameSession(@RequestParam Integer player1,
                                      @RequestParam Integer player2) {
        return gameSessionService.createNewSession(player1, player2);
    }
}
