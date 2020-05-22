package ro.fasttrackit.demorpsproject.service;

import org.springframework.stereotype.Service;
import ro.fasttrackit.demorpsproject.domain.Game;
import ro.fasttrackit.demorpsproject.domain.GameResult;
import ro.fasttrackit.demorpsproject.domain.Hand;
import ro.fasttrackit.demorpsproject.domain.Player;
import ro.fasttrackit.demorpsproject.exceptions.ResourceNotFoundException;
import ro.fasttrackit.demorpsproject.exceptions.SamePlayerException;
import ro.fasttrackit.demorpsproject.repository.GameRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

@Service
public class GameService {

    private final PlayerService playerService;
    private final GameRepository gameRepository;

    public GameService(PlayerService playerService, GameRepository gameRepository) {
        this.playerService = playerService;
        this.gameRepository = gameRepository;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game addGame(Game game) {
        if (game.getFirstPlayerId().equals(game.getSecondPlayerId())) {
            throw new SamePlayerException("Cannot create game with identical players. ID " + game.getFirstPlayerId() +
                    " is used in both cases.");
        }
        Game gameWithResult = gameWithResult(game);
        return gameRepository.save(gameWithResult);
    }

    public Game getGameById(Integer id) {
        return getOrThrow(id);
    }

    public List<Game> gamesWithPlayerInvolved(Integer playerId) {
        return gameRepository.findAll().stream()
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
        return gameRepository.findAll().stream()
                .filter(game -> game.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Could not find Game with ID " + id));
    }

}
