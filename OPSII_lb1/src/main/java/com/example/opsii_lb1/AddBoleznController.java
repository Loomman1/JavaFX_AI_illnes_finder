package com.example.opsii_lb1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class AddBoleznController implements Initializable {
    private LinkedList<Symptom> EXTRASYMPTOMS = new LinkedList<>();
    private LinkedList<Symptom> MasSymptoms = new LinkedList<>();
    private LinkedList<Symptom> MyMasSymptoms = new LinkedList<>();
    private LinkedList<Bolezn> MasBols = new LinkedList<>();
    private ObservableList<Symptom> MasObservableSymptoms = FXCollections.observableArrayList();
    private ObservableList<Symptom> MasMyObservableSymptoms = FXCollections.observableArrayList();


    @FXML
    private TableView<Symptom> SymptomList;
    @FXML
    private TableColumn<Symptom, Integer> SymptomListID;
    @FXML
    private TableColumn<Symptom, String> SymptomListName;






    @FXML
    private TableView<Symptom> AddedSymptoms;
    @FXML
    private TableColumn<Symptom, Integer> AddedSympID;
    @FXML
    private TableColumn<Symptom, String> AddedSympName;
    @FXML
    private TableColumn<Symptom, String> dov;
    @FXML
    private TableColumn<Symptom, String> nedov;


    @FXML
    private TextField NewBoleznID;

    @FXML
    private TextField NewBoleznName;

    public void SetSymptoms(LinkedList<Symptom> s, LinkedList<Bolezn> b)
    {
        MasBols = b;
        MasSymptoms = s;

        SymptomList.setEditable(true);
        AddedSymptoms.setEditable(true);

        SymptomListID.setText("ID");
        SymptomListName.setText("Симптом");
        AddedSympID.setText("ID");
        AddedSympName.setText("Симптом");
        dov.setText("Доверие");
        nedov.setText("Недоверие");
        MasObservableSymptoms.clear();
        MasMyObservableSymptoms.clear();
        MasObservableSymptoms.addAll(MasSymptoms);
        MasMyObservableSymptoms.addAll(MyMasSymptoms);

        renew_data();
    }

    private void renew_data()
    {
        SymptomList.setItems(MasObservableSymptoms);
        SymptomListID.setCellValueFactory(new PropertyValueFactory<>("KeySymp"));
        SymptomListName.setCellValueFactory(new PropertyValueFactory<>("Symp"));

        AddedSymptoms.setItems(MasMyObservableSymptoms);
        AddedSympID.setCellValueFactory(new PropertyValueFactory<>("KeySymp"));
        AddedSympName.setCellValueFactory(new PropertyValueFactory<>("Symp"));
        dov.setCellValueFactory(new PropertyValueFactory<>("doverie"));
        nedov.setCellValueFactory(new PropertyValueFactory<>("nedoverie"));
    }

    public void addSymptom()
    {
        String[] dovnedov = new String[2];
        ModalDovNedov modalDovNedov = new ModalDovNedov();
        dovnedov = modalDovNedov.newModalDovNedov("Введите коэффициенты доверия и недоверия");
        if(dovnedov[0].length()==0||dovnedov[1].length()==0||!AddBoleznController.isNumeric(dovnedov[0])||!AddBoleznController.isNumeric(dovnedov[1])||Double.parseDouble(dovnedov[0])<=0||Double.parseDouble(dovnedov[0])>=1||Double.parseDouble(dovnedov[1])<=0||Double.parseDouble(dovnedov[1])>=1||Double.parseDouble(dovnedov[1])>=Double.parseDouble(dovnedov[0]))
        {
            ModalWindow.newModalWindow("ОШИБКА!");
        }
        else {
            EXTRASYMPTOMS.addAll(MasSymptoms);
            LinkedList<Symptom> EXTRASYMPTOMS = new LinkedList<>(MasSymptoms);
            String str = SymptomList.getSelectionModel().getSelectedItem().getSymp();
            int id = SymptomList.getSelectionModel().getSelectedItem().getKeySymp();
            Symptom symptom = new Symptom(id, str, dovnedov[0],dovnedov[1]);
            for (Symptom s : MasSymptoms) {
                if (s.getKeySymp() == symptom.getKeySymp())
                    EXTRASYMPTOMS.remove(s);
            }
            MyMasSymptoms.add(symptom);
            ComparerSymps cmp = new ComparerSymps();
            EXTRASYMPTOMS.sort(cmp);
            MyMasSymptoms.sort(cmp);
            SetSymptoms(EXTRASYMPTOMS, MasBols);
        }
    }
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private boolean checkBolsByInd(int ID_bol)
    {
        boolean vz = true;
        for (Bolezn b: MasBols) {
            if(ID_bol==b.getIdBol()) {
            vz=false;
            break;
            }
        }
        if(ID_bol<=0)
            vz=false;
        return vz;
    }

    private boolean checkBolsByName(String NAME_bol)
    {
        boolean vz = true;
        for (Bolezn b: MasBols) {
            if(NAME_bol.equals(b.getNameBol())) {
                vz=false;
                break;
            }
        }
        return vz;
    }

    public void ReadyClick()
    {
        boolean flag=true;

        NewBoleznID.borderProperty().set(Border.stroke(Paint.valueOf("Grey")));
        NewBoleznName.borderProperty().set(Border.stroke(Paint.valueOf("Grey")));
        if(!NewBoleznName.getCharacters().isEmpty()) {
            if(checkBolsByName(NewBoleznName.getCharacters().toString())) {
                if (isNumeric(NewBoleznID.getCharacters().toString()) && !NewBoleznID.getCharacters().isEmpty()) {
                    if (checkBolsByInd(Integer.parseInt(NewBoleznID.getCharacters().toString()))) {
                        for (Bolezn b : MasBols) {
                            if (b.compareVhod(MyMasSymptoms)) {
                                ModalWindow.newModalWindow("ВХОЖДЕНИЕ");
                                System.out.println("ВХОЖДЕНИЕ!");
                                flag=false;
                                break;
                            }
                        }
                    } else {
                        flag=false;
                        NewBoleznID.borderProperty().set(Border.stroke(Paint.valueOf("Red")));
                    }
                } else {
                    flag = false;
                    NewBoleznID.borderProperty().set(Border.stroke(Paint.valueOf("Red")));
                }
            }else {
                flag = false;
                NewBoleznName.borderProperty().set(Border.stroke(Paint.valueOf("Red")));
            }
        }else {
            flag = false;
            NewBoleznName.borderProperty().set(Border.stroke(Paint.valueOf("Red")));
        }
        if(flag)
        {
            Bolezn NewBol = new Bolezn(Integer.parseInt(NewBoleznID.getCharacters().toString()), NewBoleznName.getCharacters().toString());
            NewBol.print();
            for (Symptom s: MyMasSymptoms) {
                NewBol.AddSymp(s);
            }
            MasBols.add(NewBol);

            DB_handler db = new DB_handler();
            db.addBolezn(NewBol);

            ComparerBols cmp = new ComparerBols();
            MasBols.sort(cmp);
            SetSymptoms(MasSymptoms, MasBols);
            flag=true;
        }
        else {
            ModalWindow.newModalWindow("ОШИБКА!");
            System.out.println("ОШИБКА!");
        }
        //ModifyController m = new ModifyController();
        //m.RefreshAllLists();
    }
    public void MoveToRightClick()
    {
        String str = AddedSymptoms.getSelectionModel().getSelectedItem().getSymp();
        int id = AddedSymptoms.getSelectionModel().getSelectedItem().getKeySymp();
        Symptom symptom = new Symptom(id, str, "-1","-1");
        for (Symptom s : MyMasSymptoms) {
            if (s.getKeySymp() == symptom.getKeySymp())
                MyMasSymptoms.remove(s);
        }
        MasSymptoms.add(symptom);
        ComparerSymps cmp = new ComparerSymps();
        MyMasSymptoms.sort(cmp);
        MasSymptoms.sort(cmp);
        SetSymptoms(MasSymptoms, MasBols);
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

    @FXML
    void editDovCancel(TableColumn.CellEditEvent edditedCell) {
        System.out.println("cancel activated");
    }

    @FXML
    void editDovCommit(TableColumn.CellEditEvent edditedCell) {
        System.out.println("commit activated");
        Symptom symptomSelected = AddedSymptoms.getSelectionModel().getSelectedItem();

        String[] strs=new String[2];
        strs[0]=edditedCell.getNewValue().toString();
        strs[1]=symptomSelected.getNedoverie();
        if(validate(strs)) {
            symptomSelected.setDoverie(edditedCell.getNewValue().toString());
        }else {
            ModalWindow.newModalWindow("ОШИБКА!");
            editDovCancel(edditedCell);
            AddedSymptoms.refresh();
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
        Symptom symptomSelected = AddedSymptoms.getSelectionModel().getSelectedItem();

        String[] strs=new String[2];
        strs[0]=symptomSelected.getDoverie();
        strs[1]=edditedCell.getNewValue().toString();
        if(validate(strs)) {
            symptomSelected.setNedoverie(edditedCell.getNewValue().toString());
        }else {
            ModalWindow.newModalWindow("ОШИБКА!");
            editNedovCancel(edditedCell);
            AddedSymptoms.refresh();
            symptomSelected.setNedoverie(symptomSelected.getNedoverie());
        }
    }
    @FXML
    void editNedovFunction(TableColumn.CellEditEvent edditedCell) {
        System.out.println("edit activated");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddedSymptoms.setEditable(true);
        dov.setEditable(true);
        nedov.setEditable(true);
        dov.setCellFactory(TextFieldTableCell.forTableColumn());
        nedov.setCellFactory(TextFieldTableCell.forTableColumn());
    }
}
