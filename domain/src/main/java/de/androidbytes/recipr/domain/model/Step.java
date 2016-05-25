package de.androidbytes.recipr.domain.model;

public class Step {

    private long id;
    private int number;
    private String instruction;

    public Step(long id, int number, String instruction) {
        this.id = id;
        this.number = number;
        this.instruction = instruction;
    }

    public long getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public String getInstruction() {
        return instruction;
    }
}
