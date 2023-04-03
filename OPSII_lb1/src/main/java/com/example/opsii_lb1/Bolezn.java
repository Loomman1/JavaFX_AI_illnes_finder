package com.example.opsii_lb1;

import java.util.LinkedList;

public class Bolezn implements Comparable<Bolezn>{
    private int IdBol;
    private String NameBol;

    private LinkedList<Symptom> masSymptoms;

    Bolezn(int k, String s)
    {
        IdBol =k;
        NameBol =s;
        masSymptoms = new LinkedList<>();
    }
    Bolezn (Bolezn o)
    {
        IdBol=o.IdBol;
        NameBol = o.NameBol;
        masSymptoms = o.masSymptoms;
    }
    public void AddSymp(Symptom s)
    {
        masSymptoms.addLast(s);
    }
    public void DeleteSymp(Symptom s)
    {
        for(Symptom symptom: masSymptoms) {
            if (s.getKeySymp() == symptom.getKeySymp()) {
                masSymptoms.remove(symptom);
            }
        }
    }

    public int getIdBol(){
        return IdBol;
    }
    public String getNameBol(){
        return NameBol;
    }
    public LinkedList<Symptom> getMasSymptoms(){
        return masSymptoms;
    }

    public String toString()
    {
        return NameBol;
    }

    public void setMasSymptoms(LinkedList<Symptom> symp) {masSymptoms = symp;}
    public void print()
    {
        System.out.println("  ID:  " + IdBol+"  Болезнь  "+NameBol);
        System.out.println("Симптомы:");
        for (Symptom s: masSymptoms)
        {
            s.print();
        }
    }

    public boolean compareVhod(LinkedList<Symptom> outerSymps)
    {
        int count = 0;
        for (Symptom s:
                masSymptoms) {
            for (Symptom s1:
                    outerSymps) {
                if(s.getKeySymp()==s1.getKeySymp())
                {
                    count++;
                }
            }
        }
        boolean vz=false;
        if((count==masSymptoms.size()&&count<=outerSymps.size())||(count<=masSymptoms.size()&&count==outerSymps.size())) vz=true;
        else vz= false;
        return vz;
    }

    public boolean compare(LinkedList<Symptom> outerSymps)
    {
        int count = 0;
        for (Symptom s:
                masSymptoms) {
            for (Symptom s1:
                    outerSymps) {
                if(s.getKeySymp()==s1.getKeySymp())
                {
                    count++;
                }
            }
        }
        boolean vz=false;
        if(count==masSymptoms.size()&&masSymptoms.size()==outerSymps.size()) vz=true;
        else vz= false;
        return vz;
    }

    private boolean matchSymptom(Symptom symptom, LinkedList<Symptom> masHisSymps)
    {
        boolean fl = false;
        for(Symptom s: masHisSymps)
        {
            if(s.getKeySymp()==symptom.getKeySymp())
            {
                fl = true;
                break;
            }
        }
        return fl;
    }

    public double[] vichKUV(LinkedList<Symptom> masHisSymps)
    {
        double[] vz=new double[2];
        vz[0]=0;
        vz[1]=0;
        int n=0;
        for(Symptom s: masSymptoms)
        {
            if(matchSymptom(s, masHisSymps))
            {
                if(n==0)
                {
                    vz[0]+=Double.parseDouble(s.getDoverie());
                    vz[1]+=Double.parseDouble(s.getNedoverie());
                }else {
                    vz[0]=Double.parseDouble(s.getDoverie())+(vz[0]*(1-Double.parseDouble(s.getDoverie()))); //[0] - доверия
                    vz[1] = Double.parseDouble(s.getNedoverie()) + (vz[1] * (1 - Double.parseDouble((s.getNedoverie()))));  //[1] - недоверия
                }
                n++;

            }

        }
        return vz;
    }

    @Override
    public int compareTo(Bolezn o) {
        if(this.IdBol>o.IdBol)
            return 1;
        if(this.IdBol<o.IdBol)
            return -1;
        return 0;
    }
}
