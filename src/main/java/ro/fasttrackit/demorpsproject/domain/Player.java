package ro.fasttrackit.demorpsproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = {"gameSession"})
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"gameSession"})
@Table(name = "Players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Hand hand;
    private int wins = 0;
    private int losses = 0;
    private int draws = 0;

    @JsonIgnore
    @ManyToOne
    private GameSession gameSession;

    public Player(String name, Hand hand, int wins, int losses, int draws, GameSession gameSession) {
        this.name = name;
        this.hand = hand;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
        this.gameSession = gameSession;
    }

    public Player(String name, Hand hand, int wins, int losses, int draws) {
        this.name = name;
        this.hand = hand;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
    }

    public Player(String name, Hand hand) {
        this.name = name;
        this.hand = hand;
    }

    public Player(String name) {
        this(name, Hand.NONE);
    }

    public void addWin() {
        this.wins++;
    }

    public void addLoss() {
        this.losses++;
    }

    public void addDraw() {
        this.draws++;
    }
}
