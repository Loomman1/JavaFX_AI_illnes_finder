package com.example.opsii_lb1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PopupControl;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Window;

import java.util.LinkedList;

public class DeleteSympController {
    @FXML
    private Button AcceptDeleteSympButton;
    @FXML
    private TextField IDDelSymp;
    private LinkedList<Symptom> MasSymptoms = new LinkedList<>();
    private LinkedList<Bolezn> MasBols = new LinkedList<>();
    int SymptomToDeleteID;
    String symp;
    public void SetSymptoms(LinkedList<Symptom> s, LinkedList<Bolezn> b) {
        MasBols = b;
        MasSymptoms = s;
    }

    public int checkSymptom()
    {
        int vz = 0;
        for (Symptom s: MasSymptoms)
        {
            if(s.getKeySymp()==SymptomToDeleteID)
            {
                symp = s.getSymp();
                vz=1;
                break;
            }
        }
        return vz;
    }

    private boolean chekAfterDeleteCollision(Symptom symptom)
    {
        boolean fl =false;
        LinkedList<Bolezn> BufferMasBols = new LinkedList<>(MasBols);
        LinkedList<Bolezn> BufferMasBols1 = new LinkedList<>(MasBols);
        for (Bolezn b: BufferMasBols) {
            b.DeleteSymp(symptom);
        }
        for (Bolezn b: BufferMasBols1) {
            b.DeleteSymp(symptom);
        }
        for(Bolezn b: BufferMasBols)
            b.print();
        System.out.println("and-------------------");
        for(Bolezn b: BufferMasBols1)
            b.print();

        System.out.println("Мы удалили " +symptom.getSymp());

        for (Bolezn b: BufferMasBols) {
            for (Bolezn b1 : BufferMasBols1) {
                if(b.getIdBol()!=b1.getIdBol()) {
                    fl = b1.compareVhod(b.getMasSymptoms()); //возвращает true если вхождение
                    if (fl) {
                        b.print();
                        System.out.println("===========");
                        b1.print();
                        break;
                    }
                }
            }
            if (fl) break;
        }

        if(fl) //fl=true значит вхождение
        { ModalWindow.newModalWindow("ВХОЖДЕНИЕ");
            //JOptionPane.showMessageDialog(frame, "Eggs are not supposed to be green."); //свинг
            System.out.println("ВХОЖДЕНИЕ!");

        }
        else { //fl = false - вхождения нет, удаляем
            for (Bolezn b: MasBols) {
                b.DeleteSymp(symptom);  //удалить симптом из каждой болезни основного массива
            }
        }
        System.out.println("Флаг удаления симптома fl =  "+fl+" (true = удалять низя; false = удалять можно)");
        return fl;
    }


    public void submitDeleteSymp()
    {
        SymptomToDeleteID = Integer.parseInt(IDDelSymp.getCharacters().toString());
        int fl=0;
        fl = checkSymptom();
        if(fl==1) {
            boolean fl1=false;
            Symptom symptom = new Symptom(SymptomToDeleteID, symp, "-1", "-1");
            fl1=chekAfterDeleteCollision(symptom); //false - удаляем
            if (!fl1)
            {
                MasSymptoms.remove(symptom);
                DB_handler db = new DB_handler();
                db.deleteSymptom(symptom);
            }

        } else
            System.out.println("Симптом не найден");
    }


}
