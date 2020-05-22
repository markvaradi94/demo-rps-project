package ro.fasttrackit.demorpsproject.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
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
