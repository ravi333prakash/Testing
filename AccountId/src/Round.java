import java.text.SimpleDateFormat;
import java.math.BigDecimal;
//import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.DoubleToLongFunction;

import org.joda.time.LocalDateTime;

public class Round {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	//dollarToCents(1);
	String dollar1="100.00";
	BigDecimal rawDollar = new BigDecimal(dollar1);
	double dollar = rawDollar.doubleValue();
	int roundedCent = (int) Math.round(dollar * 100);
	double justCent=dollar*100;
	System.out.println("rawDollar= "+rawDollar+" dollar= "+dollar+" roundedcents ="+roundedCent+" Justcents ="+justCent);
	
	
	//this is also fine only if they dont want rounding
	double doll=Double.parseDouble("100.89");
	double justcents=doll*100;
	System.out.println(String.valueOf(justcents));
	//this is also......
	
	
		//Scanner scan =new Scanner(System.in);
		//System.out.println("enter the amount : ");
		
	/*	double amount=Double.parseDouble(scan.nextLine());
		
		
		double roundedAmount=(double)Math.round(amount*100)/100;
		String roundedAmountStr=""+roundedAmount;
		System.out.println(roundedAmountStr);*/
		/*
		String Ent="aaa,bbb,ccc,d";
		System.out.println("enter the amount : ");
		String req=scan.nextLine();
	
		if(Arrays.asList(Ent.split(",")).contains(req))
			System.out.println("true");
	//	System.out.println(Arrays.asList(Ent.split(",")));
		if(Ent.contains(req))
			System.out.println("string true");
		SimpleDateFormat sf = new SimpleDateFormat("ddMMyyyy HH:mm:ss");
		System.out.println(LocalDateTime.now());
		
		LocalDateTime ldt= new LocalDateTime();
		System.out.println(ldt);*/
		
	}
	public static void dollarToCents(double dollar)
	{
		//int cents = (int) Math.round(100*dollar);
		//System.out.println("dollar= "+dollar+" cents ="+cents);
		//return cents;
		
		 BigDecimal rawDollar = BigDecimal.ZERO;
		    BigDecimal increment = new BigDecimal("0.01");
		    for (int i = 0; i < 300; i++) {
		      rawDollar = rawDollar.add(increment);
		      dollar = rawDollar.doubleValue();
		      Double cents = new Double(dollar * 100);
		      int amount = cents.intValue();
		      int roundedAmount = (int) Math.round(dollar * 100);
		      if (amount != roundedAmount) {
		        System.out.println("dollar = " + dollar + " amount = " + amount
		            + " rounded = " + roundedAmount);
		      }
		    }
	}
		
	

}
