package dev.hooboolhoo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Choice {
    private String name;
    private int count;

    public void increase() {
        this.count += 1;
    }
}
