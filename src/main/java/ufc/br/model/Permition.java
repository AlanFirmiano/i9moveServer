package ufc.br.model;

import javax.persistence.*;

@Entity
public class Permition {
    @Id
    @GeneratedValue
    private int id;
    private boolean locked = false;
    @OneToOne(cascade={CascadeType.MERGE})
    private Grasp grasp;
    @OneToOne(cascade={CascadeType.MERGE})
    private Patient patient;

    public Permition(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Grasp getGrasp() {
        return grasp;
    }

    public void setGrasp(Grasp grasp) {
        this.grasp = grasp;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
