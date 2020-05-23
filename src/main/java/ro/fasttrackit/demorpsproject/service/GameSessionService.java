package ro.fasttrackit.demorpsproject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.fasttrackit.demorpsproject.domain.Game;
import ro.fasttrackit.demorpsproject.domain.GameSession;
import ro.fasttrackit.demorpsproject.domain.Hand;
import ro.fasttrackit.demorpsproject.domain.Player;
import ro.fasttrackit.demorpsproject.exceptions.ResourceNotFoundException;
import ro.fasttrackit.demorpsproject.repository.GameSessionRepository;

import java.util.ArrayList;
import java.util.List;

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

    public List<GameSession> allGameSessions() {
        return gameSessionRepository.findAll();
    }

    public GameSession createNewSession(Integer player1Id, Integer player2Id) {
        Game game = createGame(player1Id, player2Id);
        GameSession gameSession = new GameSession();
        gameSession.addGame(game);
        game.setGameSession(gameSession);
        Player player1 = playerService.findPlayerById(game.getFirstPlayerId());
        Player player2 = playerService.findPlayerById(game.getSecondPlayerId());
        gameSession.addPlayer(player1);
        gameSession.addPlayer(player2);
        player1.setGameSession(gameSession);
        player2.setGameSession(gameSession);
        return gameSessionRepository.save(gameSession);
    }

    public GameSession getGameSessionById(Integer id) {
        return getOrThrow(id);
    }

    public GameSession addGameToSession(Integer sessionId, Hand player1Hand, Hand player2Hand) {
        GameSession session = getOrThrow(sessionId);
        List<Player> sessionPlayers = new ArrayList<>(session.getSessionPlayers());
        Player player1 = sessionPlayers.get(0);
        Player player2 = sessionPlayers.get(1);
        playerService.chooseNewHand(player1.getId(), player1Hand);
        playerService.chooseNewHand(player2.getId(), player2Hand);
        Game newGame = gameService.addGame(new Game(player1.getId(), player2.getId()));
        session.addGame(newGame);
        return gameSessionRepository.save(session);
    }

    private Game createGame(Integer player1Id, Integer player2Id) {
        Player player1 = playerService.findPlayerById(player1Id);
        Player player2 = playerService.findPlayerById(player2Id);
        Game initialGame = new Game(player1.getId(), player2.getId());
        return gameService.addGame(initialGame);
    }

    private GameSession getOrThrow(Integer id) {
        return gameSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find Session with ID " + id));
    }

}
