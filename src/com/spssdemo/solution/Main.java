package com.spssdemo.solution;
import com.ibm.statistics.plugin.*;

public class Main {

    /*
     * The following is a simple example of using the IBM SPSS Statistics - Integration Plug-in for Java to create
     * a dataset in IBM SPSS Statistics, compute descriptive statistics and generate output. It illustrates the basic
     * features of invoking IBM SPSS Statistics from an external Java application
     *
     */
    public static void main(String[] args) {

        try {
            //Start the IBM SPSS Statistics processor
            StatsUtil.start();
            System.out.println("***started***");
            String[] command =
                    {
                            "OMS",
                            "/DESTINATION FORMAT=HTML OUTFILE=’/output/demo.html’.",
                            "DATA LIST FREE /salary (F).",
                            "BEGIN DATA",
                            "21450", "30000", "57000",
                            "END DATA.",
                            "DESCRIPTIVES salary.",
                            "OMSEND."
                    };

            String[] command2 =
                    {
                            "DATA LIST FREE /salary (F).",
                            "BEGIN DATA",
                            "21450", "30000", "57000",
                            "END DATA.",
                            "DESCRIPTIVES salary.",
                            "OMSEND."
                    };

            StatsUtil.submit(command2);
            System.out.println("***submitted***");
            StatsUtil.stop();
            System.out.println("***stopped***");
        }
        catch (StatsException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
