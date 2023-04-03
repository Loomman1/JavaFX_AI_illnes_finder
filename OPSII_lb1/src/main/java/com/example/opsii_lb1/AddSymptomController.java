package com.example.opsii_lb1;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.LinkedList;

public class AddSymptomController {

    //дополнительное окно со вводом симптома
    @FXML
    private TextField EnterID;
    @FXML
    private TextField EnterSymptom;
    int ID;
    String sym;
    private LinkedList<Symptom> MasSymptoms = new LinkedList<>();
    private LinkedList<Bolezn> MasBols = new LinkedList<>();

    public void SetSymptoms(LinkedList<Symptom> s, LinkedList<Bolezn> b) {
        MasBols = b;
        MasSymptoms = s;
    }

    public boolean checkSymptom()
    {
        boolean vz = true;
        ID=Integer.parseInt(EnterID.getCharacters().toString());
        sym = EnterSymptom.getCharacters().toString();
        //System.out.println("ID = "+ID+"S = "+sym);
        //System.out.println(MasSymptoms.size());
        for (Symptom s: MasSymptoms)
        {
            //System.out.println("we compare"+s.getSymp()+"to"+sym);
            if((s.getKeySymp()==ID)||(s.getSymp().equals(sym)))
            {
                vz=false;
                break;
            }
        }
        if(ID<=0)
            vz=false;
        return vz;
    }

    public void submitNewSymptom()
    {
        boolean fl = checkSymptom();
        if(fl)
        {
            System.out.println("Пойдет");
            Symptom symptom = new Symptom(ID, sym, "-1", "-1");
            MasSymptoms.addLast(symptom);
            ComparerSymps cmp = new ComparerSymps();
            MasSymptoms.sort(cmp);
            DB_handler db = new DB_handler();
            db.addSymptom(symptom);

        } else {
            ModalWindow.newModalWindow("Проверьте введенные данные");
            System.out.println("не пойдет");
        }
    }
}
