package ro.fasttrackit.demorpsproject.service;

import org.springframework.stereotype.Service;
import ro.fasttrackit.demorpsproject.domain.Game;
import ro.fasttrackit.demorpsproject.domain.GameSession;
import ro.fasttrackit.demorpsproject.domain.Player;
import ro.fasttrackit.demorpsproject.exceptions.ResourceNotFoundException;
import ro.fasttrackit.demorpsproject.repository.GameSessionRepository;

@Service
public class GameSessionService {

    private final GameSessionRepository gameSessionRepository;
    private final GameService gameService;
    private final PlayerService playerService;

    public GameSessionService(GameSessionRepository gameSessionRepository, GameService gameService, PlayerService playerService) {
        this.gameSessionRepository = gameSessionRepository;
        this.gameService = gameService;
        this.playerService = playerService;
    }

    public GameSession createNewSession(Integer player1Id, Integer player2Id) {
        Game game = createGame(player1Id, player2Id);
        GameSession gameSession = new GameSession();
        gameSession.addGame(game);
        return gameSessionRepository.save(gameSession);
    }

    public GameSession getGameSessionById(Integer id) {
        return getOrThrow(id);
    }

//    public GameSession addGameToSession(Integer sessionId, Game game) {
//        GameSession session = getOrThrow(sessionId);
//
//    }

    private Game createGame(Integer player1Id, Integer player2Id) {
        Player player1 = playerService.findPlayerById(player1Id);
        Player player2 = playerService.findPlayerById(player2Id);
        Game initialGame = new Game(player1.getId(), player2.getId());
        return gameService.addGame(initialGame);
    }

    private GameSession getOrThrow(Integer id) {
        return gameSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find session with ID " + id));
    }

}
