package Helper;

//import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
//import java.util.Locale;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cs555_tm5_project.FamilyInfo;
import cs555_tm5_project.IndividualInfo;

//import java.util.Arrays;
//author Hitesh

public class Global {
	
	public static final int legalMarriageAge = 18;
	
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

	public static void ShowInfo(HashMap<Integer, IndividualInfo> individualInfoObjMap, HashMap<Integer, FamilyInfo> familyInfoObjMap){
		System.out.println("***** Displaying Individual Information *****\n");
	    
	    Object[] indiKey = individualInfoObjMap.keySet().toArray();
	    Arrays.sort(indiKey);
	    for(Object retval: indiKey) {
	        String id =  "@I" + retval.toString() + "@";
	
	        System.out.println(id);
	        System.out.println("NAME: " + individualInfoObjMap.get(retval).getName());
	        System.out.println("SEX: " + individualInfoObjMap.get(retval).getSex());
	        System.out.println("BIRTH: " + individualInfoObjMap.get(retval).getBirthDate());
	        System.out.println("DEATH: " + individualInfoObjMap.get(retval).getDeathDate());
	        System.out.println("");
	    }
	    
	    System.out.println("\n***** Displaying Family Information *****\n");
	    
	    Object[] famKey = familyInfoObjMap.keySet().toArray();
	    Arrays.sort(famKey);
	    for(Object retval: famKey) {
	        String id =  "@F" + retval.toString() + "@";
	        
	        System.out.println(id);
	        System.out.println("HUSBAND: " + individualInfoObjMap.get(familyInfoObjMap.get(retval).getHusband()).getName());
	        System.out.println("WIFE: " + individualInfoObjMap.get(familyInfoObjMap.get(retval).getWife()).getName());
	        System.out.println("MARRIAGE: " + familyInfoObjMap.get(retval).getMarriageDate());
	        System.out.println("DIVORCE: " + familyInfoObjMap.get(retval).getDivorceDate());
	        
	        for(int child: familyInfoObjMap.get(retval).getChildren()) {
	            System.out.println("CHILD: " + individualInfoObjMap.get(child).getName());
	        }
	        
	        
	        System.out.println("");		
	  }
   }
	
   public static int AgeAtSpecificTime(Date birth, Date specificTime){
	   
	   if(birth != null && specificTime != null ){	   
		   if(specificTime.after(birth)){
			   long diff = specificTime.getTime() - birth.getTime();
			   int age = (int) diff/(365*24*3600*1000);
			   return age;
		   }else{
			   return -1;
		   }
	   }   
	   return -1;
   }	
   public static int getAge(Date dateOfBirth) {

	    Calendar today = Calendar.getInstance();
	    Calendar birthDate = Calendar.getInstance();

	    int age = 0;

	    birthDate.setTime(dateOfBirth);
	    if (birthDate.after(today)) {
	        throw new IllegalArgumentException("Can't be born in the future");
	    }

	    age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

	    // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year   
	    if ( (birthDate.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) > 3) ||
	            (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH ))){
	        age--;

	     // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
	    }else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH )) &&
	              (birthDate.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH ))){
	        age--;
	    }

	    return age;
	}

 public  static <K,V extends Comparable<? super V>> List<Entry<K, V>> MapEntriesSortedByValuesDesc(Map<K,V> map) {

List<Entry<K,V>> sortedEntries = new ArrayList<Entry<K,V>>(map.entrySet());

Collections.sort(sortedEntries, 
   new Comparator<Entry<K,V>>() {
       @Override
       public int compare(Entry<K,V> e1, Entry<K,V> e2) {
           return e2.getValue().compareTo(e1.getValue());
       }
   }
);

return sortedEntries;
}
	public static String rebuildIdentifier(String id, char type) {
		String formedID = "";
		
		if(type=='I')
			formedID =  "@I" + id + "@";
		else if(type=='F')
			formedID =  "@F" + id + "@";
		
		return formedID;
	}
}

