package ro.fasttrackit.demorpsproject.service;

import org.springframework.stereotype.Service;
import ro.fasttrackit.demorpsproject.domain.Hand;
import ro.fasttrackit.demorpsproject.domain.Player;
import ro.fasttrackit.demorpsproject.exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

@Service
public class PlayerService {

    private final List<Player> players = new ArrayList<>();

    public PlayerService() {
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public Player addPlayer(Player player) {
        Player newPlayer = new Player(fetchLatestId(), player.getName(), player.getHand() == null ? Hand.NONE : player.getHand());
        players.add(newPlayer);
        return newPlayer;
    }

    public Player findPlayerById(Integer id) {
        return getOrThrow(id);
    }

    public Player updatePlayer(Integer id, Player player) {
        Player playerToUpdate = getOrThrow(id);
        playerToUpdate.setName(player.getName() == null ? playerToUpdate.getName() : player.getName());
        playerToUpdate.setHand(player.getHand() == null ? Hand.NONE : player.getHand());
        return playerToUpdate;
    }

    public Player replacePlayer(Integer id, Player player) {
        Player playerToReplace = getOrThrow(id);
        playerToReplace.setName(player.getName());
        playerToReplace.setHand(player.getHand() == null ? Hand.NONE : player.getHand());
        playerToReplace.setWins(player.getWins());
        playerToReplace.setLosses(player.getLosses());
        playerToReplace.setDraws(player.getDraws());
        return playerToReplace;
    }

    public Player deletePlayerById(Integer id) {
        Player playerToDelete = getOrThrow(id);
        players.remove(playerToDelete);
        return playerToDelete;
    }

    public void addWinToPlayer(Player player) {
        player.addWin();
    }

    public void addLossToPlayer(Player player) {
        player.addLoss();
    }

    public void addDrawToPlayer(Player player) {
        player.addDraw();
    }

    private Player getOrThrow(Integer id) {
        return players.stream()
                .filter(player -> player.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Could not find Player with ID " + id));
    }

    private int fetchLatestId() {
        final Set<Integer> existingIds = players.stream()
                .map(Player::getId)
                .collect(toSet());
        return IntStream.iterate(1, i -> i + 1)
                .filter(id -> !existingIds.contains(id))
                .findFirst()
                .orElseThrow();
    }
}
