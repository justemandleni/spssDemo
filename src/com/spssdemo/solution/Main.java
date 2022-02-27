package com.spssdemo.solution;
import com.ibm.statistics.plugin.*;

import javax.sound.midi.SysexMessage;
import java.util.Calendar;

public class Main {

    public static void main(String[] args) {
        //demoFunction2();
        //demoFunction3();
        //retrievingCases();
        //retrievingCasesByVariable();
        createVariables();
    }

    /**
     * Chapter 2
     * Running IBM SPSS Statistics commands
     */
    public static void demoFunction2(){
        try {
            StatsUtil.start();
            //Express full file path.
            //StatsUtil.submit("GET FILE=’/data/Employee data.sav’.");
            StatsUtil.submit("GET FILE='C:\\Program Files\\IBM\\SPSS Statistics\\Samples\\English\\Employee data.sav'" +
                    ".");

            String[] cmdLines = {
                    "SET PRINTBACK ON MPRINT ON.",
                    "OMS /SELECT TABLES ",
                    "/IF COMMANDS = [’Descriptives’ ’Frequencies’] ",
                    "/DESTINATION FORMAT = HTML ",
                    "IMAGES = NO OUTFILE = ’/output/stats.html’.",
                    "DESCRIPTIVES MINORITY EDUC SALARY SALBEGIN.",
                    "FREQUENCIES SALARY EDUC JOBCAT JOBTIME.",
                    "OMSEND."};
            StatsUtil.submit(cmdLines);
            StatsUtil.stop();
        }
        catch (StatsException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Chapter 3
     * Retrieving dictionary information using Cursor, DataUtil and StatsUtil classes
     */
    public static void demoFunction3(){
        try {
            StatsUtil.start();

            StatsUtil.submit("GET FILE ='C:\\Users\\27736\\Documents\\Employee data2.sav'"+".");

            DataUtil dataUtil = new DataUtil();
            String[] varnames = dataUtil.getVariableNames();
            dataUtil.release();
            StatsUtil.submit("SET PRINTBACK ON MPRINT ON.");
            for (String name:
                 varnames) {

                System.out.println(">> "+name);

                if(name.toLowerCase().equals("salary")){
                    String[] command = {
                            "SORT CASES BY "+name+ ".",
                            "SPLIT FILE LAYERED BY "+name+".",
                            "OMSEND."};
                    StatsUtil.submit(command);
                }
            }
            StatsUtil.stop();
        }
        catch (StatsException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Chapter 4 Working with case data in the active dataset <br/>
     * the ability to: <br/>
     *  - read case data from the active dataset, <br/>
     */
    public static void retrievingCases() {
        try {
            StatsUtil.start();

            StatsUtil.submit("SET PRINTBACK ON MPRINT ON.");

            StatsUtil.submit("GET FILE ='C:\\Users\\27736\\Documents\\demo2.sav'" + ".");
            DataUtil datautil = new DataUtil();
            datautil.setConvertDateTypes(true);
            Case[] data = datautil.fetchCases(false, 0);
            Double numvar;
            String strvar;
            Calendar datevar;

            for (Case onecase : data) {

                String[] data2 = datautil.getVariableNames();
                for(String currentVariable: data2){
                    System.out.print(currentVariable+ ", ");
                }

                System.out.println();

                for (int i = 0; i < onecase.getCellNumber(); i++) {
                    CellValueFormat format = onecase.getCellValueFormat(i);
                    if (format == CellValueFormat.DOUBLE) {
                        numvar = onecase.getDoubleCellValue(i);
                        System.out.print(">n "+numvar+", ");
                    } else if (format == CellValueFormat.STRING) {
                        strvar = onecase.getStringCellValue(i);
                        System.out.print(">s "+strvar+", ");
                    } else if (format == CellValueFormat.DATE) {
                        datevar = onecase.getDateCellValue(i);
                        System.out.print(">d "+datevar+", ");
                    }
                }
            }
            datautil.release();

            StatsUtil.stop();
        } catch (StatsException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void retrievingCasesByVariable(){
        try{
            StatsUtil.start();
            StatsUtil.submit("SET PRINTBACK ON MPRINT ON.");

            StatsUtil.submit("GET FILE ='C:\\Users\\27736\\Documents\\Employee data2.sav'" + ".");
            DataUtil datautil = new DataUtil();
            String[] varNames = new String[]{"id","educ","salary"};
            datautil.setVariableFilter(varNames);
            Case[] data = datautil.fetchCases(false, 0);
            Double numvar;
            String strvar;
            Calendar datevar;

            for (Case onecase : data) {
                System.out.println();

                for(String currentVariable: varNames){
                    System.out.print(currentVariable+ ", ");
                }


                for (int i = 0; i < onecase.getCellNumber(); i++) {
                    System.out.println();
                    CellValueFormat format = onecase.getCellValueFormat(i);
                    if (format == CellValueFormat.DOUBLE) {
                        numvar = onecase.getDoubleCellValue(i);
                        System.out.print(">n "+numvar+", ");
                    } else if (format == CellValueFormat.STRING) {
                        strvar = onecase.getStringCellValue(i);
                        System.out.print(">s "+strvar+", ");
                    } else if (format == CellValueFormat.DATE) {
                        datevar = onecase.getDateCellValue(i);
                        System.out.print(">d "+datevar+", ");
                    }
                }
            }
            datautil.release();

            StatsUtil.stop();
        }
        catch (StatsException e){
            System.out.println(e.getMessage());
        }
    }


    /**
     * Chapter 4 Working with case data in the active dataset <br/>
     * the ability to: <br/>
     *  - create new variables, populate data and save to output file,  <br/>
     */
    public static void createVariables(){
        try {
            StatsUtil.start();
            StatsUtil.submit("SET PRINTBACK ON MPRINT ON.");
            //StatsUtil.submit("GET FILE ='C:\\Users\\27736\\Documents\\Employee data2.sav'"+".");

            String[] command =
                    {
                            "DATA LIST FREE /case (A5).",
                            "BEGIN DATA",
                            "case1",
                            "case2",
                            "case3",
                            "END DATA."
                    };
            StatsUtil.submit(command);
            Variable numVar = new Variable("numvar", 0);
            Variable strVar = new Variable("strvar", 1);
            Variable dateVar = new Variable("datevar", 0);
            dateVar.setFormatType(VariableFormat.DATE);
            double[] numValues = new double[]{1.0, 2.0, 3.0};
            String[] strValues = new String[]{"a", "b", "c"};
            Calendar dateValue = Calendar.getInstance();
            dateValue.set(Calendar.YEAR, 2012);
            dateValue.set(Calendar.MONTH, Calendar.JANUARY);
            dateValue.set(Calendar.DAY_OF_MONTH, 1);
            Calendar[] dateValues = new Calendar[]{dateValue};
            DataUtil datautil = new DataUtil();
            datautil.addVariableWithValue(numVar, numValues, 0);
            datautil.addVariableWithValue(strVar, strValues, 0);
            datautil.addVariableWithValue(dateVar, dateValues, 0);
            datautil.release();

            StatsUtil.submit("SAVE OUTFILE ='C:\\Users\\27736\\Documents\\myData2.sav'"+".");

            StatsUtil.stop();
        }
        catch (StatsException e){
            System.out.println(e.getMessage());
        }
    }
}
