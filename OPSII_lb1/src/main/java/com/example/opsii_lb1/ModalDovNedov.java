package com.example.opsii_lb1;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalDovNedov {
    TextField txt1;
    TextField txt2;
    public String[] newModalDovNedov(String title)
    {
        String[] vz = new String[2];
        Stage w = new Stage();
        w.initModality(Modality.APPLICATION_MODAL);
        FlowPane pane = new FlowPane();

        txt1 = new TextField();
        txt2 = new TextField();
        Button b = new Button("ОК");
        txt1.setText("<Введите коэффициент доверия>");
        txt2.setText("<Введите коэффициент недоверия>");
        txt1.setMinWidth(300);
        txt2.setMinWidth(300);

        b.setOnMouseClicked(Event-> w.close());


        pane.getChildren().add(txt1);
        pane.getChildren().add(txt2);
        pane.getChildren().add(b);

        Scene scene = new Scene(pane, 400, 200);
        w.setScene(scene);
        w.setTitle(title);
        w.showAndWait();
        vz[0]=txt1.getCharacters().toString();
        vz[1]=txt2.getCharacters().toString();
        return vz;
    }

}
