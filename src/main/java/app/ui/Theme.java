package app.ui;

import javafx.scene.paint.Color;

public record Theme(Color background, Color panel, Color text) {
    public static final Theme DARK = new Theme(Color.web("#1a1a1a"), Color.web("#232323"), Color.web("#e6e6e6"));
    public static final Theme LIGHT = new Theme(Color.web("#f2f2f2"), Color.web("#fafafa"), Color.web("#222222"));
}
