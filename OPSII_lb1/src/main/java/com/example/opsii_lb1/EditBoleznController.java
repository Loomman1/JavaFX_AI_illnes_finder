package com.example.opsii_lb1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class EditBoleznController implements Initializable {
    private LinkedList<Symptom> MasSymptomsLeft = new LinkedList<>();
    private LinkedList<Symptom> MasSymptomsRight = new LinkedList<>();
    private LinkedList<Bolezn> MasAllBolz = new LinkedList<>();
    private LinkedList<Symptom> MasAllSymptoms = new LinkedList<>();
    private ObservableList<Symptom> MasRightObservableSymptoms = FXCollections.observableArrayList();
    private ObservableList<Symptom> MasLeftObservableSymptoms = FXCollections.observableArrayList();
    Bolezn theBolezn;

    @FXML
    private Label BoleznName;
    @FXML
    private TableView<Symptom> BoleznSympList;//Left
    @FXML
    private TableColumn<Symptom, Integer> BoleznSympID;
    @FXML
    private TableColumn<Symptom, String> BoleznSympName;



    @FXML
    private TableView<Symptom> AllSymps; //Right

    @FXML
    private TableColumn<Symptom, Integer> AllSympsID;

    @FXML
    private TableColumn<Symptom, String> AllSympsName;
    @FXML
    private TableColumn<Symptom, String> dov;
    @FXML
    private TableColumn<Symptom, String > nedov;


    private LinkedList<Symptom> find(LinkedList<Symptom> par1, LinkedList<Symptom> par2)
    {
        LinkedList<Symptom> vz = new LinkedList<>();
        for (Symptom s1:par1) {
            boolean fl = true;
            for (Symptom s2:par2) {
                if(s1.getKeySymp()==s2.getKeySymp())
                {
                    fl = false;
                    break;
                }
            }
            if(fl)
            {
                vz.add(s1);
            }
        }
        return vz;

    }


    public void SetSymptoms(LinkedList<Symptom> s, Bolezn realBolezn, LinkedList<Bolezn> b)
    {
        ComparerSymps cmp = new ComparerSymps();
        MasSymptomsRight.sort(cmp);
        MasSymptomsLeft.sort(cmp);

        BoleznName.setText(realBolezn.getNameBol());
        theBolezn = realBolezn;
        MasAllBolz = b;
        MasAllSymptoms = s;

        BoleznSympList.setEditable(true);
        AllSymps.setEditable(true);

        BoleznSympID.setText("ID");
        BoleznSympName.setText("Симптом");
        AllSympsID.setText("ID");
        AllSympsName.setText("Симптом");
        dov.setText("Доверие");
        nedov.setText("Недоверие");

        MasLeftObservableSymptoms.clear();
        MasRightObservableSymptoms.clear();

        MasSymptomsLeft = find(s, realBolezn.getMasSymptoms());
        MasSymptomsRight = realBolezn.getMasSymptoms();

        MasRightObservableSymptoms.addAll(MasSymptomsRight);
        MasLeftObservableSymptoms.addAll(MasSymptomsLeft);
        renew_data();


    }

    private void renew_data()
    {

        BoleznSympID.setCellValueFactory(new PropertyValueFactory<>("KeySymp"));
        BoleznSympName.setCellValueFactory(new PropertyValueFactory<>("Symp"));
        BoleznSympList.setItems(MasLeftObservableSymptoms);

        AllSympsID.setCellValueFactory(new PropertyValueFactory<>("KeySymp"));
        AllSympsName.setCellValueFactory(new PropertyValueFactory<>("Symp"));
        dov.setCellValueFactory(new PropertyValueFactory<>("doverie"));
        nedov.setCellValueFactory(new PropertyValueFactory<>("nedoverie"));
        AllSymps.setItems(MasRightObservableSymptoms);
    }

    private boolean validate(String[] dovnedov)
    {
        boolean vz = true;
        if(dovnedov[0].length()==0||dovnedov[1].length()==0||!AddBoleznController.isNumeric(dovnedov[0])||!AddBoleznController.isNumeric(dovnedov[1])||Double.parseDouble(dovnedov[0])<=0||Double.parseDouble(dovnedov[0])>=1||Double.parseDouble(dovnedov[1])<=0||Double.parseDouble(dovnedov[1])>=1||Double.parseDouble(dovnedov[1])>=Double.parseDouble(dovnedov[0]))
        {
            vz=false;
        }
        return vz;
    }

    public void addSymptomToRight(Symptom symptom)
    {
        String[] dovnedov = new String[2];
        ModalDovNedov modalDovNedov = new ModalDovNedov();
        dovnedov = modalDovNedov.newModalDovNedov("Введите коэффициенты доверия и недоверия");
        System.out.println(AddBoleznController.isNumeric(dovnedov[0]));
        if(!validate(dovnedov))
        {
            ModalWindow.newModalWindow("ОШИБКА!");
        }
        else {
            symptom.setDoverie(dovnedov[0]);
            symptom.setNedoverie(dovnedov[1]);
            MasSymptomsRight.add(symptom);
            for (Symptom s : MasSymptomsLeft) {
                if (s.getKeySymp() == symptom.getKeySymp())
                    MasSymptomsLeft.remove(s);
            }
            SetSymptoms(MasSymptomsLeft, theBolezn, MasAllBolz);
        }
    }

    public void addSymptomToLeft(Symptom symptom)
    {
        MasSymptomsLeft.add(symptom);
        for (Symptom s: MasSymptomsRight) {
            if(s.getKeySymp()==symptom.getKeySymp())
                MasSymptomsRight.remove(s);
        }
        SetSymptoms(MasSymptomsLeft, theBolezn, MasAllBolz);
    }

    public void LeftShiftButtonClicked()
    {
        if(BoleznSympList.getSelectionModel().isEmpty())
        {
            System.out.println("Слева ничего не выбрано!");
        }
        else {
            MasSymptomsLeft.remove(BoleznSympList.getSelectionModel().getSelectedItem());
            addSymptomToRight(BoleznSympList.getSelectionModel().getSelectedItem());
            theBolezn.setMasSymptoms(MasSymptomsRight);
        }
    }

    public void RightShiftButtonClicked()
    {
        if(AllSymps.getSelectionModel().isEmpty())
        {
            System.out.println("Справа ничего не выбрано!");
        }
        else {
            MasSymptomsRight.remove(AllSymps.getSelectionModel().getSelectedItem());
            addSymptomToLeft(AllSymps.getSelectionModel().getSelectedItem());
            theBolezn.setMasSymptoms(MasSymptomsRight);

        }
    }


    public void SaveChanges()
    {
        boolean flag=true;
        for (Bolezn b : MasAllBolz) {
            if (b.getIdBol() != theBolezn.getIdBol())
            {
                if (b.compareVhod(MasSymptomsRight)) {
                    System.out.println("ВХОЖДЕНИЕ!");
                    ModalWindow.newModalWindow("ВХОЖДЕНИЕ");
                    flag = false;
                    break;
                }
            }
        }
        if(flag)
        {
            DB_handler db = new DB_handler();
            db.deleteBolezn(theBolezn);
            db.addBolezn(theBolezn);
        }
    }

    @FXML
    void editDovCancel(TableColumn.CellEditEvent edditedCell) {
        System.out.println("cancel activated");
    }

    @FXML
    void editDovCommit(TableColumn.CellEditEvent edditedCell) {
        System.out.println("commit activated");
        Symptom symptomSelected = AllSymps.getSelectionModel().getSelectedItem();

        String[] strs=new String[2];
        strs[0]=edditedCell.getNewValue().toString();
        strs[1]=symptomSelected.getNedoverie();
        if(validate(strs)) {
            symptomSelected.setDoverie(edditedCell.getNewValue().toString());
        }else {
            ModalWindow.newModalWindow("ОШИБКА!");
            editDovCancel(edditedCell);
            AllSymps.refresh();
        }
    }

    @FXML
    void editDovFunction(TableColumn.CellEditEvent edditedCell) {
        System.out.println("edit activated");
    }

    @FXML
    void editNedovCancel(TableColumn.CellEditEvent edditedCell) {
        System.out.println("cancel activated");
    }

    @FXML
    void editNedovCommit(TableColumn.CellEditEvent edditedCell) {
        System.out.println("commit activated");
        Symptom symptomSelected = AllSymps.getSelectionModel().getSelectedItem();

        String[] strs=new String[2];
        strs[0]=symptomSelected.getDoverie();
        strs[1]=edditedCell.getNewValue().toString();
        if(validate(strs)) {
            symptomSelected.setNedoverie(edditedCell.getNewValue().toString());
        }else {
            ModalWindow.newModalWindow("ОШИБКА!");
            editNedovCancel(edditedCell);
            AllSymps.refresh();
            symptomSelected.setNedoverie(symptomSelected.getNedoverie());
        }
    }

    @FXML
    void editNedovFunction(TableColumn.CellEditEvent edditedCell) {
        System.out.println("edit activated");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AllSymps.setEditable(true);
        dov.setEditable(true);
        nedov.setEditable(true);
        dov.setCellFactory(TextFieldTableCell.forTableColumn());
        nedov.setCellFactory(TextFieldTableCell.forTableColumn());
    }
}
