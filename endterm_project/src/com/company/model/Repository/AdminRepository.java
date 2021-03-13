package com.company.model.Repository;

import com.company.model.database.IDB;
import com.company.model.entitiies.Flight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class AdminRepository implements InterfaceAdminRepository{

    // database
    private final IDB database;

    // constructor
    public AdminRepository(IDB database) {
        this.database = database;
    }

    //method to get all flights which returns arrayList of Flight

    public ArrayList<Flight> getAllFlights(){

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        ArrayList < Flight > ans = new ArrayList<>();

        try {

            con = database.getConnection();

            //sql query
            st = con.createStatement();
            rs = st.executeQuery("select * from flightstable");

            while(rs.next()){
                //if we had id -1 then we deleted this row
                if(rs.getInt(1) == -1) {
                    continue;
                }
                // add to arraylist
                ans.add(new Flight(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }

            // return in arraylist
            return ans;


        } catch (Exception e){
            System.out.println(e);
        } finally {
            try {
                con.close();
            } catch (Exception e){
                System.out.println(e);
            }
        }
        return null;
    }
    // in this method we should delete flight
    public void deleteFlight(int id){
        Connection con = null;

        try {
            con = database.getConnection();
            // we select id from table flightable and update it value to 1

            String sql = "update flightstable set id=-1 where id=?";
            PreparedStatement st = con.prepareStatement(sql);

            st.setInt(1, id);

            // execute query

            st.execute();



        } catch (Exception e){
            System.out.println(e);
        } finally {
            try {
                con.close();
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }

    // in this table we should add a new flight
    public void addFlight(Flight newFlight){
        Connection con = null;

        try {
            con = database.getConnection();

            // we adding a new flight to our database through prepareStatement
            String sql = "insert into flightstable values (default, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, newFlight.getName());
            st.setString(2, newFlight.getDestination());
            st.setString(3, newFlight.getDate());
            st.setString(4, newFlight.getTime());

            // execute statement

            st.execute();



        } catch (Exception e){
            System.out.println(e);
        } finally {
            try {
                con.close();
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }
}

