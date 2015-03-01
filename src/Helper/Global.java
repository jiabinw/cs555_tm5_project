package Helper;

//import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
//import java.util.Locale;

//import java.util.Arrays;
//author Hitesh
public class Global {
//Implement all re-usable functions here .	
public static int[] BubbleSort(int[] arr)
{
	        int temp;
	        for(int i=0; i < arr.length-1; i++){
	 
	            for(int j=1; j < arr.length-i; j++){
	                if(arr[j-1] > arr[j]){
	                    temp=arr[j-1];
	                    arr[j-1] = arr[j];
	                    arr[j] = temp;
	                }
	            }
	           
	        }
	        return arr;
}
public static Date ParseStringToDate(String YourDate) //Input : 15 MAY 1947 o/p 05-15-1947
{
YourDate=YourDate.replace(' ','-');
SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
try {
   Date varDate=dateFormat.parse(YourDate);
   return varDate;
}catch (Exception e) {
   // TODO: handle exception
   e.printStackTrace();
   return null;
}
}
public static boolean IsDateValid(Date YourDate)//To Check if it is a valid calendar date 
{
	 Calendar cal = Calendar.getInstance();
	 try {
	     cal.setTime(YourDate);
	     return true;
	 }
	 catch (Exception e) {
		 return false;
	   //System.out.println("Invalid date");
	 }
}

}

