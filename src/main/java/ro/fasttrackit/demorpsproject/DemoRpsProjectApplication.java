package ro.fasttrackit.demorpsproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ro.fasttrackit.demorpsproject.domain.Game;
import ro.fasttrackit.demorpsproject.domain.Hand;
import ro.fasttrackit.demorpsproject.domain.Player;
import ro.fasttrackit.demorpsproject.service.GameService;
import ro.fasttrackit.demorpsproject.service.GameSessionService;
import ro.fasttrackit.demorpsproject.service.PlayerService;

@SpringBootApplication
public class DemoRpsProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoRpsProjectApplication.class, args);
    }

    @Bean
    CommandLineRunner atStartup(PlayerService playerService, GameService gameService, GameSessionService gameSessionService) {

        return args -> {
            Player player1 = new Player("Mark", Hand.ROCK);
            playerService.addPlayer(player1);
            Player player2 = new Player("Dan", Hand.PAPER);
            playerService.addPlayer(player2);
            Player player3 = new Player("Dana", Hand.SCISSORS);
            playerService.addPlayer(player3);
            playerService.getPlayers().forEach(System.out::println);
            System.out.println();

//            Game game = new Game(1, 2);
//            gameService.addGame(game);

            gameSessionService.createNewSession(1, 2);
            gameSessionService.createNewSession(1, 3);
            gameSessionService.createNewSession(2, 3);
            System.out.println();
           gameSessionService.allGameSessions().forEach(System.out::println);

            gameSessionService.addGameToSession(3, Hand.SCISSORS, Hand.SCISSORS);
        };
    }

}
