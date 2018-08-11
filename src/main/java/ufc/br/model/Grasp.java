package ufc.br.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Grasp {

    @Id
    @GeneratedValue
    private int id;
    @OneToOne(cascade={CascadeType.MERGE})
    private Exercise exercise;
    @OneToOne(cascade={CascadeType.MERGE})
    private Level level;
    @ManyToOne(cascade={CascadeType.ALL})
    private Recommendation recommendation;
    private String tip;
    private Date latestUpdate;
    private int sequence;

    public Grasp() {

    }

    public Grasp(Exercise exercise, Level level, String tip, int sequence){
        this.exercise = exercise;
        this.level = level;
        this.tip = tip;
        this.sequence = sequence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Recommendation getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(Recommendation recommendation) {
        this.recommendation = recommendation;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Date getLatestUpdate() {
        return latestUpdate;
    }

    public void setLatestUpdate(Date latestUpdate) {
        this.latestUpdate = latestUpdate;
    }

    public int getSequence() { return sequence; }

    public void setSequence(int sequence) { this.sequence = sequence; }
}