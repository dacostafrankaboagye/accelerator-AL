module org.example.prjstockprice {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires io.reactivex.rxjava3;
    requires retrofit2;
    requires retrofit2.adapter.rxjava3;
    requires retrofit2.converter.gson;

    opens org.example.prjstockprice to javafx.fxml;
    exports org.example.prjstockprice;
}