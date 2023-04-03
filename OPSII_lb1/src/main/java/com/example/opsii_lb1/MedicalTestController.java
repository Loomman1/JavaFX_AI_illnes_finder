package com.example.opsii_lb1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.LinkedList;


public class MedicalTestController {
    @FXML
    private Button NoButton;
    @FXML
    private Button YesButton;
    @FXML
    private Button  BeginTestButton;
    @FXML
    private Label SymptomLabel;
    private int answerCounter=0;
    private LinkedList<Symptom> MasSymptoms = new LinkedList<>();
    private LinkedList<Bolezn> MasBols = new LinkedList<>();
    private LinkedList<Symptom> MasHisSymptoms = new LinkedList<>();

    @FXML
    private ListView<String> List_for_diagnoses;
    private int compare()
    {
        int vz=-1;
        for (Bolezn bol:
                MasBols) {
            if(bol.compare(MasHisSymptoms)){
                vz=MasBols.indexOf(bol);
                break;
            }
        }
        return vz;
    }

    public void SetSymptoms(LinkedList<Symptom> s, LinkedList<Bolezn> b)
    {
        MasBols = b;
        MasSymptoms = s;
    }
    @FXML
    void initialize() {
        MasHisSymptoms = new LinkedList<>();
        answerCounter=0;
    }
    @FXML
    protected int BeginTestButtonClick () {
        MasHisSymptoms = new LinkedList<>();
        answerCounter=0;
        YesButton.setDisable(false);
        NoButton.setDisable(false);
        BeginTestButton.setVisible(false);
        SymptomLabel.setText("Есть ли у Вас "+MasSymptoms.get(answerCounter).getSymp()+"?");
        return 1;
    }
    @FXML
    protected int onYesClick () {
        MasHisSymptoms.addFirst(MasSymptoms.get(answerCounter));
        answerCounter++;
        step();
        return 1;
    }
    @FXML
    protected int onNoClick () {
        answerCounter++;
        step();
        return 0;
    }
    private void step()
    {
        YesButton.setText("ЕСТЬ");
        NoButton.setText("НЕТ");
        if(answerCounter+1<=MasSymptoms.size())
        {
            SymptomLabel.setText("Есть ли у Вас "+MasSymptoms.get(answerCounter).getSymp()+"?");
        }else
        {
            String rezuult = finishTest();
                SymptomLabel.setText("Поздравляем, у Вас скорее всего " + rezuult);
                answerCounter = 0;
                YesButton.setDisable(true);
                NoButton.setDisable(true);
                BeginTestButton.setVisible(true);
                BeginTestButton.setText("Начать тест");
        }
    }

    private String finishTest()
    {
        List_for_diagnoses.getItems().clear();
        String rez="";
        double bufuv=-1;
        for(Bolezn b: MasBols)
        {
            double[] dovnedov;

            dovnedov = b.vichKUV(MasHisSymptoms);
            String kuv=Double.toString(dovnedov[0]-dovnedov[1]);
            /*if(kuv.length()>5) {
                kuv = kuv.substring(0, 5);
            }*/


            List_for_diagnoses.getItems().add(b+" ( Уверенность "+kuv+" )");
            if(Double.parseDouble(kuv)>bufuv) {
                bufuv = Double.parseDouble(kuv);
                rez = b.getNameBol();
            }

        }
        if(bufuv<=0)
        {
            rez=" всё хорошо";
        }
        return rez;

    }

}
