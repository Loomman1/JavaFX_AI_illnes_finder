package com.example.opsii_lb1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.Closeable;
import java.io.IOException;
import java.util.LinkedList;

public class ModifyController {

    private LinkedList<Symptom> MasSymptoms = new LinkedList<>();
    private LinkedList<Bolezn> MasBols = new LinkedList<>();

    @FXML
    private TableView<Symptom> SymptomsTable;
    @FXML
    private TableColumn<Symptom, Integer> Symptoms1;
    @FXML
    private TableColumn<Symptom, String> Symptoms2;




    @FXML
    private TableView<Bolezn> DiagsTable;
    @FXML
    private TableColumn<?, ?> Diags1;

    @FXML
    private TableColumn<?, ?> Diags2;

    private ObservableList<Symptom> MasObservableSymptoms = FXCollections.observableArrayList();
    private ObservableList<Bolezn> MasObservableBols = FXCollections.observableArrayList();
    @FXML
    private Button AddSymptomButton;
    @FXML
    private Button RefreshBD;

    public void SetSymptoms(LinkedList<Symptom> s, LinkedList<Bolezn> b)
    {
        MasBols = b;
        MasSymptoms = s;

        //SymptomsTable.setEditable(true);

        Symptoms1.setText("ID");
        Symptoms2.setText("Симптом");
        Diags1.setText("ID");
        Diags2.setText("Диагноз");
        MasObservableSymptoms.clear();
        MasObservableBols.clear();
        MasObservableSymptoms.addAll(MasSymptoms);
        MasObservableBols.addAll(MasBols);

        renew_data();
        //SymptomsTable.getColumns().addAll(Symptoms1, Symptoms2);
    }

    private void renew_data()
    {
        SymptomsTable.setItems(MasObservableSymptoms);
        Symptoms2.setCellValueFactory(new PropertyValueFactory<>("Symp"));
        Symptoms1.setCellValueFactory(new PropertyValueFactory<>("KeySymp"));


        DiagsTable.setItems(MasObservableBols);
        Diags1.setCellValueFactory(new PropertyValueFactory<>("IdBol"));
        Diags2.setCellValueFactory(new PropertyValueFactory<>("NameBol"));
    }

    public void AddSymptomButtonClick()
    {
        Scene scene3;
        FXMLLoader fxmlLoader3 = new FXMLLoader(HelloApplication.class.getResource("addSymp.fxml"));
        try {
            scene3 = new Scene(fxmlLoader3.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AddSymptomController addSymptomController = fxmlLoader3.getController();
        Stage stage = new Stage();
        stage.setScene(scene3);
        stage.show();
        addSymptomController.SetSymptoms(MasSymptoms, MasBols);
    }

    public void DeleteSymptomButtonClick()
    {
        Scene scene3;
        FXMLLoader fxmlLoader3 = new FXMLLoader(HelloApplication.class.getResource("deleteSymp.fxml"));
        try {
            scene3 = new Scene(fxmlLoader3.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DeleteSympController deleteSymptomController = fxmlLoader3.getController();
        Stage stage = new Stage();
        stage.setScene(scene3);
        stage.show();
        deleteSymptomController.SetSymptoms(MasSymptoms, MasBols);
    }

    public void AddBoleznButtonClick()
    {
        Scene scene4;
        FXMLLoader fxmlLoader4 = new FXMLLoader(HelloApplication.class.getResource("addBolezn.fxml"));
        try {
            scene4 = new Scene(fxmlLoader4.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AddBoleznController addBoleznController = fxmlLoader4.getController();
        Stage stage = new Stage();
        stage.setScene(scene4);
        stage.show();
        addBoleznController.SetSymptoms(MasSymptoms, MasBols);
    }


    public void deleteSelectedBolezn()
    {
        //LinkedList<Bolezn> MasExtraBols = new LinkedList<>(MasBols);
        Bolezn b = DiagsTable.getSelectionModel().getSelectedItem();
        //MasExtraBols.remove(b);
        MasBols.remove(b);
        DB_handler db = new DB_handler();
        db.deleteBolezn(b);
        SetSymptoms(MasSymptoms, MasBols);
    }


    public void EditBolezn()
    {
        Scene scene5;
        FXMLLoader fxmlLoader5 = new FXMLLoader(HelloApplication.class.getResource("EditBolezn.fxml"));
        try {
            scene5 = new Scene(fxmlLoader5.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        EditBoleznController editBoleznController = fxmlLoader5.getController();
        Stage stage = new Stage();
        stage.setScene(scene5);
        stage.show();
        Bolezn bolezn = new Bolezn(DiagsTable.getSelectionModel().getSelectedItem());
        editBoleznController.SetSymptoms(MasSymptoms, bolezn, MasBols);
    }

    public void RefreshAllLists()
    {
        DB_handler db_handler = new DB_handler();
        MasSymptoms=db_handler.ReadSymptoms();
        MasBols=db_handler.ReadBolezns();
        MasBols = db_handler.fillBolezns(MasBols, MasSymptoms);

        ComparerBols cmp = new ComparerBols();
        MasBols.sort(cmp);
        ComparerSymps cmp1 = new ComparerSymps();
        MasSymptoms.sort(cmp1);

        SetSymptoms(MasSymptoms, MasBols);


        for(Symptom s: MasSymptoms)
        {
            DB_handler db = new DB_handler();
            db.deleteSymptom(s);
            db.addSymptom(s);
        }
        for(Bolezn b: MasBols)
        {
            DB_handler db = new DB_handler();
            db.deleteBolezn(b);
            db.addBolezn(b);
        }

    }
}
