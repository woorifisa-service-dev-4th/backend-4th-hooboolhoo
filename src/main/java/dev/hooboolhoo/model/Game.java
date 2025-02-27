package dev.hooboolhoo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Game {
    private String author;
    private String category;
    private String gameThumbnail;
    private String title;
    private String subTitle;
    private int id;
    private List<Comment> comments;
    private List<Choice> choices;

    public Game() {
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void addChoice(Choice choice) {
        this.getChoices().add(choice);
    }
}
