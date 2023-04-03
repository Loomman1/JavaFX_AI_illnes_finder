package com.example.opsii_lb1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;

public class HelloController {

    FXMLLoader fxmlLoader2 = new FXMLLoader(HelloApplication.class.getResource("MedicalTest.fxml"));
    Scene scene2 = new Scene(fxmlLoader2.load());
    FXMLLoader fxmlLoader3 = new FXMLLoader(HelloApplication.class.getResource("ModifyData.fxml"));
    Scene scene3 = new Scene(fxmlLoader3.load());


    @FXML
    private Button TestButton;
    private AnchorPane Tester;


    LinkedList<Bolezn> MasBolezn;
    LinkedList<Symptom> MasGlobalSymptoms;
    LinkedList<Symptom> MasYourSymptoms;

    public HelloController() throws IOException {
    }

    @FXML
    void initialize()
    {
        /*DB_handler db = new DB_handler();
        MasGlobalSymptoms = db.ReadSymptoms();
        MasBolezn = db.ReadBolezns();
        MasBolezn = db.fillBolezns(MasBolezn, MasGlobalSymptoms);
        for (Bolezn b:MasBolezn) {
            b.print();
        }*/
        DB_handler.connectionEstablish();
        DB_handler db_handler = new DB_handler();
        MasGlobalSymptoms=db_handler.ReadSymptoms();
        MasBolezn=db_handler.ReadBolezns();
        MasBolezn = db_handler.fillBolezns(MasBolezn, MasGlobalSymptoms);

        //System.out.println(MasGlobalSymptoms.size());
        //System.out.println(MasBolezn.size());

        /*for(Bolezn b : MasBolezn)
        {
            b.print();
        }*/
    }


    @FXML
    protected void onTestButtonClick () {
        MedicalTestController medicalTestController = fxmlLoader2.getController();
        Stage stage = new Stage();
        stage.setScene(scene2);
        stage.show();
        DB_handler db_handler = new DB_handler();
        MasGlobalSymptoms=db_handler.ReadSymptoms();
        MasBolezn=db_handler.ReadBolezns();
        MasBolezn = db_handler.fillBolezns(MasBolezn, MasGlobalSymptoms);
        medicalTestController.SetSymptoms(MasGlobalSymptoms, MasBolezn);
    }

    @FXML
    protected void onModifyButtonClick () {
        ModifyController modifyController = fxmlLoader3.getController();
        Stage stage = new Stage();
        stage.setScene(scene3);
        stage.show();
        modifyController.SetSymptoms(MasGlobalSymptoms, MasBolezn);
    }
    @FXML
    protected void onCloseButtonClick()
    {
        DB_handler.connectionClose();
        System.exit(0);
    }
}