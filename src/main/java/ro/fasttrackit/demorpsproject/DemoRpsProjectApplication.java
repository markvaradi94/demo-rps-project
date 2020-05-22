package ro.fasttrackit.demorpsproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ro.fasttrackit.demorpsproject.domain.Game;
import ro.fasttrackit.demorpsproject.domain.Hand;
import ro.fasttrackit.demorpsproject.domain.Player;
import ro.fasttrackit.demorpsproject.service.GameService;
import ro.fasttrackit.demorpsproject.service.PlayerService;

@SpringBootApplication
public class DemoRpsProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoRpsProjectApplication.class, args);
    }

//    @Bean
//    CommandLineRunner atStartup(PlayerService playerService, GameService gameService) {
//
//        return args -> {
//            Player player1 = new Player("Mark", Hand.ROCK);
//            playerService.addPlayer(player1);
//            Player player2 = new Player("Dan", Hand.PAPER);
//            playerService.addPlayer(player2);
//
//            Game game = new Game(1, 2);
//            gameService.addGame(game);
//        };
//    }

}
