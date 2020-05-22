package ro.fasttrackit.demorpsproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.fasttrackit.demorpsproject.service.GameService;
import ro.fasttrackit.demorpsproject.service.PlayerService;

@Controller
public class IndexController {
    private final PlayerService playerService;
    private final GameService gameService;

    public IndexController(PlayerService playerService, GameService gameService) {
        this.playerService = playerService;
        this.gameService = gameService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        model.addAttribute("players", playerService.getPlayers());
        return "index";
    }
}
