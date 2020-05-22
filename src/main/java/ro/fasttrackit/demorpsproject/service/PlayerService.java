package ro.fasttrackit.demorpsproject.service;

import org.springframework.stereotype.Service;
import ro.fasttrackit.demorpsproject.domain.Hand;
import ro.fasttrackit.demorpsproject.domain.Player;
import ro.fasttrackit.demorpsproject.exceptions.PlayerLimitException;
import ro.fasttrackit.demorpsproject.exceptions.ResourceNotFoundException;
import ro.fasttrackit.demorpsproject.repository.PlayerRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    public Player addPlayer(Player player) {
        if (playerRepository.count() < 2) {
            Player newPlayer = new Player(player.getName(), player.getHand() == null ? Hand.NONE : player.getHand());
            return playerRepository.save(newPlayer);
        } else {
            throw new PlayerLimitException("Cannot add new player, maximum 2 players allowed.");
        }
    }

    public Player findPlayerById(Integer id) {
        return getOrThrow(id);
    }

    public Player updatePlayer(Integer id, Player player) {
        Player playerToUpdate = getOrThrow(id);
        playerToUpdate.setName(player.getName() == null ? playerToUpdate.getName() : player.getName());
        playerToUpdate.setHand(player.getHand() == null ? Hand.NONE : player.getHand());
        return playerRepository.save(playerToUpdate);
    }

    public Player chooseNewHand(Integer id, Hand hand) {
        Player playerToChoose = getOrThrow(id);
        playerToChoose.setHand(hand);
        return playerRepository.save(playerToChoose);
    }

    public Player replacePlayer(Integer id, Player player) {
        Player playerToReplace = getOrThrow(id);
        playerToReplace.setName(player.getName());
        playerToReplace.setHand(player.getHand() == null ? Hand.NONE : player.getHand());
        playerToReplace.setWins(player.getWins());
        playerToReplace.setLosses(player.getLosses());
        playerToReplace.setDraws(player.getDraws());
        return playerRepository.save(playerToReplace);
    }

    public Player deletePlayerById(Integer id) {
        Player playerToDelete = getOrThrow(id);
        playerRepository.delete(playerToDelete);
        return playerToDelete;
    }

    public void addWinToPlayer(Player player) {
        player.addWin();
        playerRepository.save(player);
    }

    public void addLossToPlayer(Player player) {
        player.addLoss();
        playerRepository.save(player);
    }

    public void addDrawToPlayer(Player player) {
        player.addDraw();
        playerRepository.save(player);
    }

    private Player getOrThrow(Integer id) {
        return playerRepository.findAll().stream()
                .filter(player -> player.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Could not find Player with ID " + id));
    }
}
