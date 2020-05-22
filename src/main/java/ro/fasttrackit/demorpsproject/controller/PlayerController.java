package ro.fasttrackit.demorpsproject.controller;

import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.demorpsproject.domain.Player;
import ro.fasttrackit.demorpsproject.service.PlayerService;

import java.util.List;

@RestController
@RequestMapping("players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> allPlayer() {
        return playerService.getPlayers();
    }

    @GetMapping("{id}")
    public Player playerById(@PathVariable Integer id) {
        return playerService.findPlayerById(id);
    }

    @PostMapping
    public Player addNewPlayer(@RequestBody Player player) {
        return playerService.addPlayer(player);
    }

    @PutMapping("{id}/update")
    public Player updatePlayer(@PathVariable Integer id, @RequestBody Player player) {
        return playerService.updatePlayer(id, player);
    }

    @PutMapping("{id}/replace")
    public Player replacePlayer(@PathVariable Integer id, @RequestBody Player player) {
        return playerService.replacePlayer(id, player);
    }

    @DeleteMapping("{id}")
    public Player deletePlayer(@PathVariable Integer id) {
        return playerService.deletePlayerById(id);
    }


}
