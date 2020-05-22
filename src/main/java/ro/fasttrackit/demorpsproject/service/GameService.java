package ro.fasttrackit.demorpsproject.service;

import org.springframework.stereotype.Service;
import ro.fasttrackit.demorpsproject.domain.Game;
import ro.fasttrackit.demorpsproject.domain.GameResult;
import ro.fasttrackit.demorpsproject.domain.Hand;
import ro.fasttrackit.demorpsproject.domain.Player;
import ro.fasttrackit.demorpsproject.exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

@Service
public class GameService {

    private final List<Game> games = new ArrayList<>();
    private final PlayerService playerService;

    public GameService(PlayerService playerService) {
        this.playerService = playerService;
    }

    public List<Game> getAllGames() {
        return Collections.unmodifiableList(games);
    }

    public Game addGame(Game game) {
        Game gameWithResult = gameWithResult(game);
        Game gameToAdd = new Game(fetchLatestId(), gameWithResult.getFirstPlayerId(), gameWithResult.getSecondPlayerId(),
                gameWithResult.getGameResult());
        games.add(gameToAdd);
        return gameToAdd;
    }

    public Game getGameById(Integer id) {
        return getOrThrow(id);
    }

    public List<Game> gamesWithPlayerInvolved(Integer playerId) {
        return games.stream()
                .filter(game -> game.getFirstPlayerId().equals(playerId) || game.getSecondPlayerId().equals(playerId))
                .collect(Collectors.toList());
    }

    private Game gameWithResult(Game game) {
        Player player1 = playerService.findPlayerById(game.getFirstPlayerId());
        Player player2 = playerService.findPlayerById(game.getSecondPlayerId());
        GameResult result = gameResult(player1, player2);
        return new Game(player1.getId(), player2.getId(), result);
    }

    private GameResult gameResult(Player player1, Player player2) {
        if (player1.getHand() == null) {
            throw new ResourceNotFoundException("Player with ID " + player1.getId() + " has no Hand.");
        }
        if (player2.getHand() == null) {
            throw new ResourceNotFoundException("Player with ID " + player2.getId() + " has no Hand.");
        }
        if (player1.getHand().equals(Hand.ROCK) && player2.getHand().equals(Hand.SCISSORS) ||
                player1.getHand().equals(Hand.PAPER) && player2.getHand().equals(Hand.ROCK) ||
                player1.getHand().equals(Hand.SCISSORS) && player2.getHand().equals(Hand.PAPER)) {
            playerService.addWinToPlayer(player1);
            playerService.addLossToPlayer(player2);
            return GameResult.PLAYER_1_WINS;
        } else if (player1.getHand().equals(player2.getHand()) && !player1.getHand().equals(Hand.NONE)
                && !player2.getHand().equals(Hand.NONE)) {
            playerService.addDrawToPlayer(player1);
            playerService.addDrawToPlayer(player2);
            return GameResult.DRAW;
        } else if (player1.getHand().equals(Hand.NONE) || player2.getHand().equals(Hand.NONE)) {
            return GameResult.WAITING_FOR_PLAYER;
        } else {
            playerService.addWinToPlayer(player2);
            playerService.addLossToPlayer(player1);
            return GameResult.PLAYER_2_WINS;
        }
    }

    private Game getOrThrow(Integer id) {
        return games.stream()
                .filter(game -> game.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Could not find Game with ID " + id));
    }

    private int fetchLatestId() {
        final Set<Integer> existingIds = games.stream()
                .map(Game::getId)
                .collect(toSet());
        return IntStream.iterate(1, i -> i + 1)
                .filter(id -> !existingIds.contains(id))
                .findFirst()
                .orElseThrow();
    }
}
