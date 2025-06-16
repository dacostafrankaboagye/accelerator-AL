package org.example.prjstockprice;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

public class App extends Application {

    private Tile stockTile;

    @Override
    public void start(Stage stage) {
        stockTile = TileBuilder.create()
                .skinType(Tile.SkinType.NUMBER)
                .title("IBM Stock Price")
                .unit("$")
                .build();

        StackPane root = new StackPane(stockTile);
        root.getStyleClass().add("root");

        Scene scene = new Scene(root, 500, 300);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        stage.setTitle("📈 Reactive Stock Dashboard");
        stage.setScene(scene);
        stage.show();

        StockPriceFetcher.fetch("IBM")
                .subscribe(
                        price -> stockTile.setValue(Double.parseDouble(price)),
                        error -> stockTile.setTitle("Error: " + error.getMessage())
                );
    }

    public static void main(String[] args) {
        launch(args);
    }
}