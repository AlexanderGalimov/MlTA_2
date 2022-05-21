package ru.vsu.cs.galimov.tasks;

import java.util.Objects;

public class InputArgs {
    private String input;
    private String output;

    public InputArgs() {
    }

    public InputArgs(String input, String output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InputArgs inputArgs = (InputArgs) o;
        return Objects.equals(input, inputArgs.input) && Objects.equals(output, inputArgs.output);
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}