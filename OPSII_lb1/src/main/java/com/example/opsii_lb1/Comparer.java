package com.example.opsii_lb1;

import java.util.Comparator;

class ComparerBols implements Comparator<Bolezn>
{
    @Override
    public int compare(Bolezn o1, Bolezn o2) {
        if(o1.getIdBol()>o2.getIdBol())
        {
            return 1;
        }
        else if(o1.getIdBol()<o2.getIdBol())
        {return -1;}
        else return 0;
    }
}
    class ComparerSymps implements Comparator<Symptom>
{
    @Override
    public int compare(Symptom o1, Symptom o2) {
        if(o1.getKeySymp()>o2.getKeySymp())
        {
            return 1;
        }
        else if(o1.getKeySymp()<o2.getKeySymp())
        {return -1;}
        else return 0;
    }
}