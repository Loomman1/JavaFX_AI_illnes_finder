package com.example.opsii_lb1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.LinkedList;

public class DB_handler {
    static String connect = "jdbc:postgresql://localhost:5432/testdb";
    static Connection con;
    private String SQL = null;
    private String SQL1 = null;
    private String buf1 = null;
    private String buf2 = null;

    public static void connectionEstablish()
    {
        try{
        con = DriverManager.getConnection(connect, "postgres", "qWe159$dF");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void connectionClose()
    {
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBolezn(Bolezn bolezn){
        try{
            SQL = "delete from Illnesses where illness_ID="+bolezn.getIdBol()+";";
            SQL1 = "delete from Sovpads where fk_illness_ID="+bolezn.getIdBol()+";";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(SQL1);
            stmt.executeUpdate(SQL);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addBolezn(Bolezn bolezn) {
        try {
                SQL = "insert into Illnesses(illness_ID, illness_NAME) values("+bolezn.getIdBol()+", "+"'"+bolezn.getNameBol()+"');";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(SQL);

                LinkedList<Symptom> symps = new LinkedList<>(bolezn.getMasSymptoms());
                for (Symptom s: symps) {
                    SQL1 = "insert into Sovpads(fk_illness_ID, fk_symp_ID, doverie, nedoverie) values("+bolezn.getIdBol()+", "+
                            "'"+s.getKeySymp()+"'"+","+s.getDoverie()+","+s.getNedoverie()+");";
                    stmt.executeUpdate(SQL1);
                }
                stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSymptom(Symptom symptom) {
        try {
                SQL = "delete from Symptoms where symp_ID="+symptom.getKeySymp()+";";
                SQL1 = "delete from Sovpads where fk_symp_ID="+symptom.getKeySymp()+";";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(SQL1);
                stmt.executeUpdate(SQL);
                stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addSymptom(Symptom symptom) {
        try {
                SQL = "insert into Symptoms(symp_ID, symp_NAME) values("+symptom.getKeySymp()+", "+"'"+symptom.getSymp()+"');";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(SQL);
                stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public LinkedList<Bolezn> fillBolezns(LinkedList<Bolezn> BolsIn, LinkedList<Symptom> SympsIn) {
        try {
                SQL = "SELECT * FROM testdb.public.Sovpads ORDER BY fk_illness_id ASC, fk_symp_id ASC LIMIT 100;";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(SQL);

                while (rs.next()) {
                    int buf3 = Integer.parseInt(rs.getString("fk_symp_ID"));
                    int buf4 = Integer.parseInt(rs.getString("fk_illness_ID"));
                    String buf5 = rs.getString("doverie");
                    String buf6 = rs.getString("nedoverie");
                    for (Bolezn b: BolsIn) {
                        if(b.getIdBol()== buf4) {
                            for (Symptom s: SympsIn)
                            {
                                if(s.getKeySymp()== buf3) {
                                    s.setDoverie(buf5);
                                    s.setNedoverie(buf6);
                                    Symptom smpt = new Symptom(s);
                                    b.AddSymp(smpt);
                                }
                            }
                        }
                    }
                }
                rs.close();
                stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BolsIn;
    }


    public LinkedList<Bolezn> ReadBolezns() {
        final LinkedList<Bolezn> MasBolezns = new LinkedList<>();
        try {
                SQL = "SELECT * FROM testdb.public.Illnesses;";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(SQL);

                while (rs.next()) {
                    buf1 = rs.getString("illness_ID");
                    buf2 = rs.getString("illness_NAME");
                    Bolezn B = new Bolezn(Integer.parseInt(buf1), buf2);
                    MasBolezns.add(B);
                }
                rs.close();
                stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MasBolezns;
    }

    public LinkedList<Symptom> ReadSymptoms() {
        final LinkedList<Symptom> MasSymptoms = new LinkedList<>();
        try {
                SQL = "SELECT * FROM testdb.public.Symptoms;";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(SQL);

                while (rs.next()) {
                    buf1 = rs.getString("symp_ID");
                    buf2 = rs.getString("symp_NAME");
                    Symptom sympt = new Symptom(Integer.parseInt(buf1), buf2, "-1", "-1");
                    MasSymptoms.add(sympt);
                }
                rs.close();
                stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MasSymptoms;
    }
}

