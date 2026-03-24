package app.ui;

import javafx.scene.paint.Color;

public record Accent(String name, Color color) {
    @Override
    public String toString() { return name; }
}
