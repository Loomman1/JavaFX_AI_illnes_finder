package com.example.opsii_lb1;

import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ModalWindow {

    public static void newModalWindow(String title)
    {
        Stage w = new Stage();
        w.initModality(Modality.APPLICATION_MODAL);
        StackPane pane = new StackPane();

        Button b = new Button("ОК");
        b.setOnAction(Event-> w.close());
        pane.getChildren().add(b);
        Scene scene = new Scene(pane, 500, 100);
        w.setScene(scene);
        w.setTitle(title);
        w.showAndWait();
    }
}
