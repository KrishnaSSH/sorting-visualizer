package app;

import app.core.Visualizer;
import app.sort.SortAlgo;
import app.sort.Sorts;
import app.ui.Accent;
import app.ui.Theme;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.nio.file.Files;
import java.nio.file.Path;
public class Main extends Application {
    final Visualizer viz = new Visualizer();
    boolean running = false;
    long lastTick = 0;
    double acc = 0;
    Theme theme = Theme.DARK;
    Accent accent = new Accent("Amber", Color.web("#c49a6c"));
    String fontFamily = "Monospaced";
    BarMode barMode = BarMode.RAINBOW;
    Color barColor = Color.web("#c49a6c");

    Canvas canvas;
    Slider speed;
    Slider size;
    TextField speedField;
    TextField sizeField;
    ChoiceBox<SortAlgo> alg;
    TextField algFilter;
    Label status;
    BorderPane root;
    VBox right;
    ScrollPane rightScroll;
    MenuBar menuBar;
    ChoiceBox<ThemeOption> themeChoice;
    ChoiceBox<Accent> accentChoice;
    ChoiceBox<String> fontChoice;
    ChoiceBox<BarMode> barModeChoice;

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(1000, 520);
        speed = new Slider(10, 2000, 400);
        size = new Slider(16, 256, 128);
        size.setMajorTickUnit(16);
        size.setMinorTickCount(0);
        size.setSnapToTicks(true);
        speedField = new TextField("400");
        speedField.setPrefColumnCount(5);
        sizeField = new TextField("128");
        sizeField.setPrefColumnCount(4);
        alg = new ChoiceBox<>();
        alg.getItems().addAll(Sorts.ALL);
        alg.getSelectionModel().selectFirst();

        Button shuffle = new Button("Shuffle");
        Button run = new Button("Run");
        status = new Label();

        algFilter = new TextField();
        algFilter.setPromptText("Search...");

        themeChoice = new ChoiceBox<>();
        themeChoice.getItems().addAll(
                new ThemeOption("Dark", Theme.DARK),
                new ThemeOption("Light", Theme.LIGHT),
                new ThemeOption("Slate", new Theme(Color.web("#20232b"), Color.web("#2a2f3a"), Color.web("#e0e0e0"))),
                new ThemeOption("Paper", new Theme(Color.web("#f6f1e8"), Color.web("#fbf7ef"), Color.web("#2a241d"))),
                new ThemeOption("Olive", new Theme(Color.web("#1f231c"), Color.web("#2a3026"), Color.web("#e2e6d6")))
        );
        themeChoice.getSelectionModel().selectFirst();

        accentChoice = new ChoiceBox<>();
        accentChoice.getItems().addAll(
                new Accent("Amber", Color.web("#c49a6c")),
                new Accent("Green", Color.web("#7fb069")),
                new Accent("Orange", Color.web("#d38b5d")),
                new Accent("Cyan", Color.web("#6ea7b8")),
                new Accent("Red", Color.web("#b86e6e")),
                new Accent("Indigo", Color.web("#7a6ea8")),
                new Accent("Teal", Color.web("#5fa3a3")),
                new Accent("Lime", Color.web("#9dbb61")),
                new Accent("Sand", Color.web("#b9a26a")),
                new Accent("Mono", Color.web("#b0b0b0"))
        );
        accentChoice.getSelectionModel().selectFirst();

        fontChoice = new ChoiceBox<>();
        fontChoice.getItems().addAll(Font.getFamilies());
        if (!fontChoice.getItems().isEmpty()) {
            fontChoice.getSelectionModel().select(fontFamily);
            if (fontChoice.getSelectionModel().isEmpty()) fontChoice.getSelectionModel().selectFirst();
            fontFamily = fontChoice.getValue();
        }

        barModeChoice = new ChoiceBox<>();
        barModeChoice.getItems().addAll(BarMode.RAINBOW, BarMode.ACCENT, BarMode.CUSTOM);
        barModeChoice.getSelectionModel().selectFirst();

        root = new BorderPane();
        menuBar = buildMenu(stage);
        root.setTop(menuBar);
        StackPane center = new StackPane(canvas);
        right = buildRightPanel(shuffle, run);
        rightScroll = new ScrollPane(right);
        rightScroll.setFitToWidth(true);
        rightScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        SplitPane split = new SplitPane(center, rightScroll);
        split.setDividerPositions(0.75);
        root.setCenter(split);
        Scene scene = new Scene(root, 1100, 600);

        canvas.widthProperty().bind(center.widthProperty());
        canvas.heightProperty().bind(center.heightProperty());
        center.widthProperty().addListener((a, b, c) -> draw());
        center.heightProperty().addListener((a, b, c) -> draw());

        size.valueProperty().addListener((a, b, c) -> {
            if (!running) {
                viz.init(c.intValue());
                status.setText("");
                draw();
            }
            if (!sizeField.isFocused()) sizeField.setText(String.valueOf(c.intValue()));
        });

        speed.valueProperty().addListener((a, b, c) -> {
            if (!speedField.isFocused()) speedField.setText(String.valueOf(c.intValue()));
        });

        sizeField.setOnAction(e -> applySizeField());
        sizeField.focusedProperty().addListener((a, b, c) -> { if (!c) applySizeField(); });
        speedField.setOnAction(e -> applySpeedField());
        speedField.focusedProperty().addListener((a, b, c) -> { if (!c) applySpeedField(); });

        algFilter.textProperty().addListener((a, b, c) -> applyAlgoFilter());
        applyAlgoFilter();

        themeChoice.getSelectionModel().selectedItemProperty().addListener((a, b, c) -> {
            if (c != null) theme = c.theme();
            applyTheme();
            draw();
        });

        accentChoice.getSelectionModel().selectedItemProperty().addListener((a, b, c) -> {
            if (c != null) accent = c;
            barColor = accent.color();
            applyTheme();
            draw();
        });

        fontChoice.getSelectionModel().selectedItemProperty().addListener((a, b, c) -> {
            if (c != null) fontFamily = c;
            applyTheme();
            draw();
        });

        barModeChoice.getSelectionModel().selectedItemProperty().addListener((a, b, c) -> {
            if (c != null) barMode = c;
            draw();
        });

        shuffle.setOnAction(e -> {
            if (!running) {
                viz.shuffle();
                status.setText("Shuffled");
                draw();
            }
        });

        run.setOnAction(e -> {
            if (running) {
                running = false;
                resetTimer();
                status.setText("Paused");
                run.setText("Run");
                return;
            }
            SortAlgo algo = alg.getValue();
            int n = (int) size.getValue();
            if (algo.name().equals("Bitonic Sort") && !isPowerOfTwo(n)) {
                int p = nextPowerOfTwo(n);
                size.setValue(p);
                viz.init(p);
            }
            viz.prepare(algo);
            resetTimer();
            running = true;
            status.setText("Running");
            run.setText("Pause");
        });

        viz.init((int) size.getValue());
        applyTheme();
        draw();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!running) { lastTick = 0; return; }
                if (lastTick == 0) lastTick = now;
                double dt = (now - lastTick) / 1_000_000_000.0;
                lastTick = now;
                acc += dt * speed.getValue();
                int steps = (int) acc;
                acc -= steps;
                if (steps > 0) {
                    boolean done = viz.step(steps);
                    if (done) {
                        running = false;
                        status.setText("Done");
                        run.setText("Run");
                    }
                }
                draw();
            }
        };
        timer.start();

        stage.setTitle("Sorting Visualizer");
        stage.setScene(scene);
        stage.show();
    }

    void resetTimer() {
        lastTick = 0;
        acc = 0;
    }

    void applyTheme() {
        String bg = toHex(theme.background());
        String panel = toHex(theme.panel());
        String text = toHex(theme.text());
        String acc = toHex(accent.color());
        root.setStyle("-fx-background-color:" + bg + "; -fx-base:" + panel + "; -fx-control-inner-background:" + panel + "; -fx-text-fill:" + text + "; -fx-accent:" + acc + "; -fx-focus-color:" + acc + "; -fx-faint-focus-color:" + acc + "33; -fx-font-family: " + fontFamily + "; -fx-font-size: 12px;");
        menuBar.setStyle("-fx-background-color:" + panel + "; -fx-text-fill:" + text + ";");
        right.setStyle("-fx-background-color:" + panel + "; -fx-border-color:" + acc + "; -fx-border-width:1;");
        rightScroll.setStyle("-fx-background:" + panel + "; -fx-background-color:" + panel + ";");
        for (Node n : right.getChildren()) {
            if (n instanceof Label l) l.setTextFill(theme.text());
        }
        for (Node n : menuBar.getChildrenUnmodifiable()) {
            if (n instanceof MenuBar mb) mb.setStyle("-fx-background-color:" + panel + ";");
        }
    }

    void applySizeField() {
        Integer v = parseInt(sizeField.getText());
        if (v == null) { sizeField.setText(String.valueOf((int) size.getValue())); return; }
        int clamped = clamp(v, (int) size.getMin(), (int) size.getMax());
        if (clamped != (int) size.getValue()) size.setValue(clamped);
        sizeField.setText(String.valueOf(clamped));
    }

    void applySpeedField() {
        Integer v = parseInt(speedField.getText());
        if (v == null) { speedField.setText(String.valueOf((int) speed.getValue())); return; }
        int clamped = clamp(v, (int) speed.getMin(), (int) speed.getMax());
        if (clamped != (int) speed.getValue()) speed.setValue(clamped);
        speedField.setText(String.valueOf(clamped));
    }

    void applyAlgoFilter() {
        String q = algFilter.getText();
        alg.getItems().setAll(Sorts.ALL.stream()
                .filter(a -> q == null || q.isBlank() || a.name().toLowerCase().contains(q.toLowerCase()))
                .toList());
        if (alg.getItems().isEmpty()) {
            status.setText("No match");
        } else if (alg.getSelectionModel().isEmpty()) {
            alg.getSelectionModel().selectFirst();
        }
    }

    void draw() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        double w = canvas.getWidth();
        double h = canvas.getHeight();
        g.setFill(theme.background());
        g.fillRect(0, 0, w, h);
        int[] arr = viz.array();
        int n = arr.length;
        if (n == 0) return;
        double barW = w / n;
        for (int i = 0; i < n; i++) {
            double v = arr[i];
            double barH = (v / n) * (h - 20);
            double x = i * barW;
            double y = h - barH;
            Color c;
            if (barMode == BarMode.RAINBOW) {
                double hue = (v / n) * 300;
                c = Color.hsb(hue, 0.75, theme == Theme.DARK ? 0.95 : 0.8);
            } else {
                Color base = barMode == BarMode.CUSTOM ? barColor : accent.color();
                double baseV = theme == Theme.DARK ? 0.35 : 0.55;
                double val = baseV + (v / n) * (theme == Theme.DARK ? 0.55 : 0.35);
                c = Color.hsb(base.getHue(), Math.max(0.25, base.getSaturation()), val);
            }
            g.setFill(c);
            g.fillRect(x, y, barW - 1, barH);
        }
    }

    String toHex(Color c) {
        int r = (int) Math.round(c.getRed() * 255);
        int g = (int) Math.round(c.getGreen() * 255);
        int b = (int) Math.round(c.getBlue() * 255);
        return String.format("#%02x%02x%02x", r, g, b);
    }

    Integer parseInt(String s) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return null; }
    }

    int clamp(int v, int lo, int hi) {
        if (v < lo) return lo;
        if (v > hi) return hi;
        return v;
    }

    MenuBar buildMenu(Stage stage) {
        Menu file = new Menu("File");
        MenuItem settings = new MenuItem("Settings");
        MenuItem exit = new MenuItem("Exit");
        settings.setOnAction(e -> openSettings(stage));
        exit.setOnAction(e -> stage.close());
        file.getItems().addAll(settings, new SeparatorMenuItem(), exit);

        Menu view = new Menu("View");
        Menu themeMenu = new Menu("Theme");
        ToggleGroup tgTheme = new ToggleGroup();
        RadioMenuItem dark = new RadioMenuItem("Dark");
        RadioMenuItem light = new RadioMenuItem("Light");
        dark.setToggleGroup(tgTheme);
        light.setToggleGroup(tgTheme);
        dark.setSelected(true);
        dark.setOnAction(e -> themeChoice.getSelectionModel().select(0));
        light.setOnAction(e -> themeChoice.getSelectionModel().select(1));
        themeMenu.getItems().addAll(dark, light);

        Menu accentMenu = new Menu("Accent");
        ToggleGroup tgAcc = new ToggleGroup();
        for (int i = 0; i < accentChoice.getItems().size(); i++) {
            Accent a = accentChoice.getItems().get(i);
            RadioMenuItem item = new RadioMenuItem(a.name());
            item.setToggleGroup(tgAcc);
            if (i == 0) item.setSelected(true);
            int idx = i;
            item.setOnAction(e -> accentChoice.getSelectionModel().select(idx));
            accentMenu.getItems().add(item);
        }
        view.getItems().addAll(themeMenu, accentMenu);

        Menu bars = new Menu("Bars");
        ToggleGroup tgBars = new ToggleGroup();
        RadioMenuItem rainbow = new RadioMenuItem("Rainbow");
        RadioMenuItem accentBars = new RadioMenuItem("Accent");
        RadioMenuItem customBars = new RadioMenuItem("Custom");
        rainbow.setToggleGroup(tgBars);
        accentBars.setToggleGroup(tgBars);
        customBars.setToggleGroup(tgBars);
        rainbow.setSelected(true);
        rainbow.setOnAction(e -> barModeChoice.getSelectionModel().select(BarMode.RAINBOW));
        accentBars.setOnAction(e -> barModeChoice.getSelectionModel().select(BarMode.ACCENT));
        customBars.setOnAction(e -> barModeChoice.getSelectionModel().select(BarMode.CUSTOM));
        MenuItem barColorItem = new MenuItem("Set Custom Color...");
        barColorItem.setOnAction(e -> openBarColor(stage));
        bars.getItems().addAll(rainbow, accentBars, customBars, new SeparatorMenuItem(), barColorItem);

        Menu ui = new Menu("UI");
        Menu fonts = new Menu("Font");
        ToggleGroup tgFont = new ToggleGroup();
        for (int i = 0; i < fontChoice.getItems().size(); i++) {
            String f = fontChoice.getItems().get(i);
            RadioMenuItem item = new RadioMenuItem(f);
            item.setToggleGroup(tgFont);
            if (i == 0) item.setSelected(true);
            int idx = i;
            item.setOnAction(e -> fontChoice.getSelectionModel().select(idx));
            fonts.getItems().add(item);
        }
        ui.getItems().add(fonts);

        Menu help = new Menu("Help");
        MenuItem about = new MenuItem("About");
        about.setOnAction(e -> openAbout(stage));
        help.getItems().add(about);

        return new MenuBar(file, view, bars, ui, help);
    }

    void openSettings(Stage owner) {
        Stage s = new Stage();
        s.initOwner(owner);
        s.initModality(Modality.APPLICATION_MODAL);
        s.setTitle("Settings");
        javafx.scene.control.ColorPicker barColorPicker = new javafx.scene.control.ColorPicker(barColor);
        barColorPicker.setOnAction(e -> { barColor = barColorPicker.getValue(); });

        Button apply = new Button("Apply");
        Button close = new Button("Close");
        apply.setOnAction(e -> {
            theme = themeChoice.getValue().theme();
            accent = accentChoice.getValue();
            fontFamily = fontChoice.getValue();
            barMode = barModeChoice.getValue();
            if (barMode == BarMode.CUSTOM) barColor = barColorPicker.getValue();
            applyTheme();
            draw();
        });
        close.setOnAction(e -> s.close());

        VBox box = new VBox(10,
                label("Theme"), themeChoice,
                label("Accent"), accentChoice,
                label("Font"), fontChoice,
                label("Bar Mode"), barModeChoice,
                label("Bar Color"), barColorPicker,
                new HBox(8, apply, close)
        );
        box.setPadding(new Insets(12));
        Scene sc = new Scene(box, 300, 360);
        applyDialogTheme(box);
        s.setScene(sc);
        s.showAndWait();
    }

    void openAbout(Stage owner) {
        Stage s = new Stage();
        s.initOwner(owner);
        s.initModality(Modality.APPLICATION_MODAL);
        s.setTitle("About");
        String text = loadLicense();
        javafx.scene.control.TextArea ta = new javafx.scene.control.TextArea(text);
        ta.setEditable(false);
        ta.setWrapText(true);
        ta.setPrefColumnCount(80);
        ta.setPrefRowCount(24);
        ta.setStyle("-fx-control-inner-background:" + toHex(theme.panel()) + "; -fx-text-fill:" + toHex(theme.text()) + "; -fx-font-family:" + fontFamily + ";");
        javafx.scene.control.Hyperlink fsf = new javafx.scene.control.Hyperlink("https://fsf.org");
        javafx.scene.control.Hyperlink gpl = new javafx.scene.control.Hyperlink("https://www.gnu.org/licenses/gpl-3.0.en.html");
        fsf.setOnAction(e -> getHostServices().showDocument(fsf.getText()));
        gpl.setOnAction(e -> getHostServices().showDocument(gpl.getText()));
        Button close = new Button("Close");
        close.setOnAction(e -> s.close());
        VBox box = new VBox(10, new Label("GNU GPL v3"), fsf, gpl, ta, close);
        box.setPadding(new Insets(12));
        Scene sc = new Scene(box, 760, 540);
        applyDialogTheme(box);
        s.setScene(sc);
        s.showAndWait();
    }

    VBox buildRightPanel(Button shuffle, Button run) {
        VBox box = new VBox(8,
                label("Controls"),
                label("Algorithm"), algFilter, alg,
                label("Size"), size, sizeField,
                label("Speed"), speed, speedField,
                shuffle, run,
                label("Status"), status
        );
        box.setPadding(new Insets(12));
        box.setPrefWidth(260);
        box.setMinWidth(220);
        box.setAlignment(Pos.TOP_LEFT);
        return box;
    }

    Label label(String t) {
        Label l = new Label(t);
        return l;
    }

    String loadLicense() {
        try { return Files.readString(Path.of("LICENSE")); }
        catch (Exception e) { return "LICENSE file not found."; }
    }

    record ThemeOption(String name, Theme theme) {
        @Override public String toString() { return name; }
    }

    enum BarMode {RAINBOW, ACCENT, CUSTOM}

    void openBarColor(Stage owner) {
        Stage s = new Stage();
        s.initOwner(owner);
        s.initModality(Modality.APPLICATION_MODAL);
        s.setTitle("Bar Color");
        javafx.scene.control.ColorPicker picker = new javafx.scene.control.ColorPicker(barColor);
        Button apply = new Button("Apply");
        Button close = new Button("Close");
        apply.setOnAction(e -> { barColor = picker.getValue(); barModeChoice.getSelectionModel().select(BarMode.CUSTOM); draw(); });
        close.setOnAction(e -> s.close());
        VBox box = new VBox(10, picker, new HBox(8, apply, close));
        box.setPadding(new Insets(12));
        Scene sc = new Scene(box, 260, 120);
        applyDialogTheme(box);
        s.setScene(sc);
        s.showAndWait();
    }

    void applyDialogTheme(VBox box) {
        String bg = toHex(theme.background());
        String panel = toHex(theme.panel());
        String text = toHex(theme.text());
        String acc = toHex(accent.color());
        box.setStyle("-fx-background-color:" + panel + "; -fx-text-fill:" + text + "; -fx-accent:" + acc + "; -fx-focus-color:" + acc + "; -fx-font-family:" + fontFamily + ";");
        for (Node n : box.getChildren()) {
            if (n instanceof Label l) l.setTextFill(theme.text());
            if (n instanceof javafx.scene.control.Hyperlink h) h.setTextFill(accent.color());
        }
    }

    boolean isPowerOfTwo(int n) { return (n & (n - 1)) == 0; }
    int nextPowerOfTwo(int n) { int p = 1; while (p < n) p <<= 1; return p; }

    public static void main(String[] args) { launch(args); }
}
