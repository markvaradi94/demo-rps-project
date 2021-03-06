package ro.fasttrackit.demorpsproject.controller;

import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.demorpsproject.domain.Game;
import ro.fasttrackit.demorpsproject.service.GameService;

import java.util.List;

@RestController
@RequestMapping("games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<Game> allGames(@RequestParam(required = false) Integer playerId) {
        if (playerId == null) {
            return gameService.getAllGames();
        } else {
            return gameService.gamesWithPlayerInvolved(playerId);
        }
    }

    @GetMapping("{id}")
    public Game getGameById(@PathVariable Integer id) {
        return gameService.getGameById(id);
    }

    @PostMapping
    public Game addNewGame(@RequestBody Game game) {
        return gameService.addGame(game);
    }

}
