package com.example.opsii_lb1;

public class Symptom implements Comparable<Symptom>{
    private int KeySymp;
    private String Symp;
    private String doverie;
    private String nedoverie;

    Symptom(int k, String s, String dov, String nedov)
    {
        KeySymp = k;
        Symp = s;
        doverie = dov;
        nedoverie = nedov;
    }
    Symptom (Symptom o)
    {
        KeySymp=o.KeySymp;
        Symp = o.Symp;
        doverie=o.doverie;
        nedoverie=o.nedoverie;
    }
    public int getKeySymp(){
        return KeySymp;
    }

    public String getSymp(){
        return Symp;
    }
    public String getDoverie(){return doverie;}
    public String getNedoverie(){return nedoverie;}
    public void setSymp(String smp){ Symp=smp;}
    public void setDoverie(String dov){ doverie=dov;}
    public void setNedoverie(String nedov){nedoverie=nedov;}
    public void print()
    {
        System.out.println("-----ID: "+KeySymp+"   Симптом:   "+Symp+" Доверие: "+doverie+"  Недоверие: "+ nedoverie);
    }
    @Override
    public int compareTo(Symptom o) {
        if(this.KeySymp>o.KeySymp)
            return 1;
        if(this.KeySymp<o.KeySymp)
            return -1;
        return 0;
    }
}
