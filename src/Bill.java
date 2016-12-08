import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Created by nera_gatta on 26.10.2016.
 */
public class Bill implements Serializable{

   private static final long serialVersionUID = 1L;

   private int numberBuilding;
   static final String P_numberBuilding = "Building number";
   private int numberFlat;
   static final String P_numberFlat = "Flat number";
   private String adress;
   static final String P_adress = "Adress";
   private String FIO="";
   static final String P_FIO = "FIO";
   private LocalDate dateOfPayment;
   static final String P_DateOfPayment = "Payment date";
   private double sumOfPayment;
   static final String P_SumOfPayment = "Payment sum";
   private double percent;
   static final String P_percent = "Penalty percent";
   private int daysExpired;
   static final String P_daysExpired = "Days expired";

   int getNumberBuilding() {
       return numberBuilding;
   }

   int getNumberFlat() {
       return numberFlat;
   }

   String getFIO() {
       return FIO;
   }

   LocalDate getDateOfPayment() {
       return dateOfPayment;
   }

   static Boolean nextRead (Scanner fin, PrintStream out) {
       return nextRead(P_numberBuilding, fin, out);
   }

   private static Boolean nextRead( final String prompt, Scanner fin, PrintStream out) {
       out.print(prompt);
       out.print(": ");
       return fin.hasNextLine();
   }


   public static Bill read (Scanner fin, PrintStream out) throws IOException {
       Bill bill = new Bill();

       try {
           bill.numberBuilding = Integer.parseInt(fin.nextLine());
           if (! nextRead(P_numberFlat, fin, out)) {
               return null;
           }

           bill.numberFlat = Integer.parseInt(fin.nextLine());
           if (! nextRead(P_adress, fin, out)) {
               return null;
           }

           bill.adress = fin.nextLine();
           if (! nextRead(P_FIO, fin, out)) {
               return null;
           }

           //bill.FIO = fin.nextLine();
           String s = fin.nextLine();
           String[] strings = s.split(" ");
           StringBuilder SB = new StringBuilder();
           if (strings.length == 3) {
               bill.FIO = SB.append(strings[0]).append(" ").append(strings[1]).append(" ").append(strings[2]).toString();
           } else {
               throw new IllegalArgumentException("Invalid fall name date");
           }
           if (! nextRead(P_DateOfPayment, fin, out)) {
               return null;
           }

           bill.dateOfPayment = LocalDate.parse(fin.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
           if (! nextRead(P_SumOfPayment, fin, out)) {
               return null;
           }

           bill.sumOfPayment = Double.parseDouble(fin.nextLine());
           if (! nextRead(P_percent, fin, out)) {
               return null;
           }

           strings = fin.nextLine().split("%");
           if (strings.length == 1) {
               bill.percent = Double.parseDouble(strings[0]);
           } else {
               throw new IllegalArgumentException("Invalid pecent data");
           }
           if (! nextRead(P_daysExpired, fin, out)) {
               return null;
           }

           bill.daysExpired = Integer.parseInt(fin.nextLine());
       }
       catch ( NumberFormatException e ) {
           System.out.println(e.getMessage());
       }

       return bill;
   }

   public Bill(String FIO, int numberFlat, int numberBuilding, String adress, LocalDate dateOfPayment,
               double sumOfPayment, double percent, int daysExpired) {
       this.FIO = FIO;
       this.numberFlat = numberFlat;
       this.numberBuilding = numberBuilding;
       this.adress = adress;
       this.dateOfPayment = dateOfPayment;
       this.sumOfPayment = sumOfPayment;
       this.percent = percent;
       this.daysExpired = daysExpired;
   }
   
   public  Bill() {
   }

   @Override
   public String toString() {
        return numberBuilding + "|" + numberFlat + "|" + adress + "|" +
                FIO + "|" + dateOfPayment + "|" + sumOfPayment + "|" +
                percent + "|" + daysExpired;
   }

}

