package ro.fasttrackit.demorpsproject.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Player {

    private Integer id;
    private String name;
    private Hand hand;
    private int wins = 0;
    private int losses = 0;
    private int draws = 0;

    public Player(Integer id, String name, Hand hand, int wins, int losses, int draws) {
        this.id = id;
        this.name = name;
        this.hand = hand;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
    }

    public Player(Integer id, String name, Hand hand) {
        this.id = id;
        this.name = name;
        this.hand = hand;
    }

    public Player(String name) {
        this(name, Hand.NONE);
    }

    public Player(String name, Hand hand) {
        this(0, name, hand);
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
