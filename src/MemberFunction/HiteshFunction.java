package MemberFunction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

import Helper.Global;
import cs555_tm5_project.FamilyInfo;
import cs555_tm5_project.IndividualInfo;

public class HiteshFunction {
public static void MarriageAfterDeath(HashMap individualInfo, HashMap familyInfo) {
	System.out.println("=============Marriage After Death Cases User Story 04============");
	if(familyInfo != null && !familyInfo.isEmpty()) {
		Object[] famKey = familyInfo.keySet().toArray();
        Arrays.sort(famKey);
        
        for(Object retval: famKey) {
        	FamilyInfo famInfo = (FamilyInfo)familyInfo.get(retval);
        	
        	if(famInfo.getMarriageDate()!=null) {
        		Date marriageDate = famInfo.getMarriageDate();
        		
        		if(famInfo.getHusband()!=0 && famInfo.getWife()!=0) {
        			IndividualInfo husbandInfo = (IndividualInfo)individualInfo.get(famInfo.getHusband());
        			IndividualInfo wifeInfo = (IndividualInfo)individualInfo.get(famInfo.getWife());
        			
        			if(husbandInfo!=null && husbandInfo.getDeathDate()!=null && husbandInfo.getDeathDate().before(marriageDate))
        				System.out.println(husbandInfo.getName() + " has an incorrect Marriage or Death Date.\n Marriage Date: "+marriageDate+"\n Death Date: "+husbandInfo.getDeathDate());
        			
        			if(wifeInfo!=null && wifeInfo.getDeathDate()!=null && wifeInfo.getDeathDate().before(marriageDate))
        				System.out.println(wifeInfo.getName() + " has an incorrect Marriage or Death Date.\n Marriage Date: "+marriageDate+"\n Death Date: "+wifeInfo.getDeathDate());
        		}
        	}
        }
	}
	System.out.println("===================================================");
}
public static void DivorceBeforeMarriage(HashMap individualInfo, HashMap familyInfo) {
	//System.out.println("=============Divorce Before Marriage  User Story 11============");
	Global.printTitle("Divorce Before Marriage  User Story 11");
	if(familyInfo != null && !familyInfo.isEmpty()) {
		Object[] famKey = familyInfo.keySet().toArray();
        Arrays.sort(famKey);
        
        for(Object retval: famKey) {
        	FamilyInfo famInfo = (FamilyInfo)familyInfo.get(retval);
        	
        	if(famInfo.getMarriageDate()!=null) {
        		Date marriageDate = famInfo.getMarriageDate();
        		if(famInfo.getDivorceDate()!=null)
        			if(marriageDate.after(famInfo.getDivorceDate()))
        			{
        				if(famInfo.getHusband()!=0 && famInfo.getWife()!=0) {
        					IndividualInfo husbandInfo = (IndividualInfo)individualInfo.get(famInfo.getHusband());
        					IndividualInfo wifeInfo = (IndividualInfo)individualInfo.get(famInfo.getWife());
        			
        				System.out.println(wifeInfo.getName()+" and "+husbandInfo.getName()+  " has Divorce Date ("+famInfo.getDivorceDate() +") before Marriage Date ("+famInfo.getMarriageDate()+")");
        				}
        		}
        	}
        }
	}
	System.out.println();
	//System.out.println("=======================================================================");
}
public static void WhoHasLivedLongest(HashMap individualInfo, HashMap familyInfo)
{
	//System.out.println("=============Person Who have lived the longest User Story 12============");
Global.printTitle("Person(s) Who have lived the longest User Story 12");
	Map<String, Integer> map = new HashMap<String, Integer>();
	if(individualInfo != null && !individualInfo.isEmpty()) {
		Object[] indiKey = individualInfo.keySet().toArray();
        Arrays.sort(indiKey);
        
        for(Object retval: indiKey) {
        	IndividualInfo indiInfo = (IndividualInfo)individualInfo.get(retval);
            
        	if(indiInfo.getBirthDate()!=null) {
      		Date BirthDate = indiInfo.getBirthDate();
      	
      		map.put(indiInfo.getName(), Global.getAge(BirthDate));
        	}
        }
       
	}
	List<Map.Entry<String, Integer>>sortedMap= Global.MapEntriesSortedByValuesDesc(map);
	
	
	//System.out.println((sortedMap.get(0)+"years").replace('=', ':'));
	int highestage=sortedMap.get(0).getValue();

	for (Map.Entry<String, Integer> PMap : sortedMap) {
	   if(PMap.getValue()==highestage)
		   System.out.println(PMap.getKey()+" : "+PMap.getValue()+" Years");
		  
		  
		   
	   
	}


	System.out.println();
}
public static void WeddingThatOccuredInLast30Days(HashMap individualInfo, HashMap familyInfo)
{
	Global.printTitle("Marriage in Past 30 Days ");
	if(familyInfo != null && !familyInfo.isEmpty()) {
		Object[] famKey = familyInfo.keySet().toArray();
        Arrays.sort(famKey);
        
        for(Object retval: famKey) {
        	FamilyInfo famInfo = (FamilyInfo)familyInfo.get(retval);
        	
        	if(famInfo.getMarriageDate()!=null) {
        		Date marriageDate = famInfo.getMarriageDate();
        	
        	
        			if(datebetweenRange(marriageDate,-30))
        			{
        				if(famInfo.getHusband()!=0 && famInfo.getWife()!=0) {
        					IndividualInfo husbandInfo = (IndividualInfo)individualInfo.get(famInfo.getHusband());
        					IndividualInfo wifeInfo = (IndividualInfo)individualInfo.get(famInfo.getWife());
        			
        				System.out.println(wifeInfo.getName()+" and "+husbandInfo.getName()+  " had marriage in Last 30 days ("+famInfo.getMarriageDate()+")");
        				}
        		}
        	}
        }
	}
	System.out.println();
	//System.out.println("=======================================================================");
}
public static void MarriageAniversaryInNext30Days(HashMap individualInfo, HashMap familyInfo) 
{
	Global.printTitle("Marriage aniversary in next 30 Days ");
	if(familyInfo != null && !familyInfo.isEmpty()) {
		Object[] famKey = familyInfo.keySet().toArray();
        Arrays.sort(famKey);
        
        for(Object retval: famKey) {
        	FamilyInfo famInfo = (FamilyInfo)familyInfo.get(retval);
        	
        	if(famInfo.getMarriageDate()!=null) {
        		Date marriageDate = famInfo.getMarriageDate();
        	
        	
        			if(Global.CalculateAniversaryDaysLeft(marriageDate)<=30)
        		
        		{
        			
        				if(famInfo.getHusband()!=0 && famInfo.getWife()!=0) {
        					IndividualInfo husbandInfo = (IndividualInfo)individualInfo.get(famInfo.getHusband());
        					IndividualInfo wifeInfo = (IndividualInfo)individualInfo.get(famInfo.getWife());
        			
        				System.out.println(wifeInfo.getName()+" and "+husbandInfo.getName()+  " has marriage aniversary in next 30 days ("+famInfo.getMarriageDate()+")");
        			
        				}
        		}
        	}
        }
	}
	System.out.println();
	//System.out.println("=======================================================================");
}
public static boolean datebetweenRange(Date d,int range)
{
	Calendar c = Calendar.getInstance();
	c.setTime(new Date()); // Now use today date.
	c.add(Calendar.DATE, range); // Adding 5 days
	Date current=new Date();//current
	Date Min=c.getTime();//5 days ago
	if(d.before(current) && d.after(Min))
		return true;
	else
	return false;
}
public static void DeathInLast30Days(HashMap individualInfo)
{
	Global.printTitle("Person(s) Who have died in past 30 days User Story 40");
	Map<String, Integer> map = new HashMap<String, Integer>();
	if(individualInfo != null && !individualInfo.isEmpty()) {
		Object[] indiKey = individualInfo.keySet().toArray();
        Arrays.sort(indiKey);
        
        for(Object retval: indiKey) {
        	IndividualInfo indiInfo = (IndividualInfo)individualInfo.get(retval);
            
        	if(indiInfo.getDeathDate()!=null) {
        		if(datebetweenRange(indiInfo.getDeathDate(),-30))
        		{
        			System.out.print("Death of "+indiInfo.getName()+" occured in last 30 days ("+indiInfo.getDeathDate()+")");
        		}
        	}
        }
       
	}
}
public static void Datetodayorprevious(HashMap individualInfo, HashMap familyInfo)
{
	Global.printTitle("Dates that are Invalid i.e not before today or today User Story 30");
	Map<String, Integer> map = new HashMap<String, Integer>();
	 Date today=new Date();
	if(individualInfo != null && !individualInfo.isEmpty()) {
		Object[] indiKey = individualInfo.keySet().toArray();
        Arrays.sort(indiKey);
        
        for(Object retval: indiKey) {
        	IndividualInfo indiInfo = (IndividualInfo)individualInfo.get(retval);
           
        	if(indiInfo.getBirthDate()!=null) {
        		if(indiInfo.getBirthDate().after(today))
        		System.out.println(indiInfo.getName()+"has an Invalid Birth Date:"+indiInfo.getBirthDate()+" i.e. the Date is not today or before today but after");
        		
        		
        	}
        	if(indiInfo.getDeathDate()!=null) {
        	if(indiInfo.getDeathDate().after(today))
            	System.out.println(indiInfo.getName()+"has an Invalid Death Date:"+indiInfo.getDeathDate()+" i.e. the Date is not today or before today but after");
        	}
        }
       
	}
	if(familyInfo != null && !familyInfo.isEmpty()) {
		Object[] famKey = familyInfo.keySet().toArray();
        Arrays.sort(famKey);
        
        for(Object retval: famKey) {
        	FamilyInfo famInfo = (FamilyInfo)familyInfo.get(retval);
        	
        	if(famInfo.getMarriageDate()!=null) {
        		Date marriageDate = famInfo.getMarriageDate();
        		
        		if(famInfo.getHusband()!=0 && famInfo.getWife()!=0) {
        			IndividualInfo husbandInfo = (IndividualInfo)individualInfo.get(famInfo.getHusband());
        			IndividualInfo wifeInfo = (IndividualInfo)individualInfo.get(famInfo.getWife());
        			
        			if(husbandInfo!=null && wifeInfo!=null  && marriageDate.after(today))
        				System.out.println("husband:"+husbandInfo.getName()+" and wife:"+wifeInfo.getName() + " has an incorrect Marriage Date:"+marriageDate);
        			
        			
        		}
        	}
        	if(famInfo.getDivorceDate()!=null) {
        		Date divdate = famInfo.getDivorceDate();
        		
        		if(famInfo.getHusband()!=0 && famInfo.getWife()!=0) {
        			IndividualInfo husbandInfo = (IndividualInfo)individualInfo.get(famInfo.getHusband());
        			IndividualInfo wifeInfo = (IndividualInfo)individualInfo.get(famInfo.getWife());
        			
        			if(husbandInfo!=null && wifeInfo!=null  && divdate.after(today))
        				System.out.println("husband:"+husbandInfo.getName()+" and wife:"+wifeInfo.getName() + " has an incorrect divorce Date:"+divdate);
        			
        			
        		}
        	}
        }
}
	
}
public static void ChildrenBornAfterFathersDeath(HashMap individualInfo, HashMap familyInfo)
{
	System.out.println("==========USER STORY 24========");
	if(individualInfo != null && !individualInfo.isEmpty() && familyInfo != null && !familyInfo.isEmpty()) {
		Object[] indiKey = individualInfo.keySet().toArray();
        Arrays.sort(indiKey);
        
        for(Object retval: indiKey) {
        	IndividualInfo indiInfo = (IndividualInfo)individualInfo.get(retval);
        	
        	if(indiInfo.getChildOfFamPtr() > 0) {
        		if(indiInfo.getBirthDate() != null) {
        			FamilyInfo famInfo = (FamilyInfo)familyInfo.get(indiInfo.getChildOfFamPtr());
        			
        			IndividualInfo fatherInfo = (IndividualInfo)individualInfo.get(famInfo.getHusband());
        			
        			if(fatherInfo.getDeathDate()!=null)
        			{
        			if(fatherInfo.getDeathDate().before(indiInfo.getBirthDate())) {
        				System.out.println("Individual-"+indiInfo.getName()+" was born on ("+indiInfo.getBirthDate()+") after their father-:"+fatherInfo.getName()+"'s death("+fatherInfo.getDeathDate()+")");
        			}}
        		}
        	} 
        }
	}
	System.out.println("=============================");
}
public static void CouplesWithLargeAgeDiff(HashMap<Integer, FamilyInfo> familyInfoObjMap,HashMap<Integer, IndividualInfo> individualInfoObjMap,int ageDifference)
{
	
System.out.println("==========USER STORY 22========");
	for(Map.Entry<Integer, FamilyInfo> entry : familyInfoObjMap.entrySet()){
		
	

    	
		FamilyInfo family = entry.getValue();
		
		Date marDate = family.getMarriageDate();
		IndividualInfo husband = individualInfoObjMap.get(family.getHusband());
		IndividualInfo wife = individualInfoObjMap.get(family.getWife());
       if(husband.getBirthDate()!=null && wife.getBirthDate()!=null)
       {
		int husbandMAge = Global.getAge(husband.getBirthDate());
       
		int wifeMAge = Global.getAge(wife.getBirthDate());
		//System.out.println(Math.abs(husbandMAge-wifeMAge));
		//System.out.println();

		if(Math.abs(husbandMAge-wifeMAge) >ageDifference)
		{
			System.out.println("Couple:"+husband.getName()+" and "+wife.getName()+" has an age difference-:"+Math.abs(husbandMAge-wifeMAge)+" years, (greater than "+ageDifference+" years)");
		}
       }
	} 

System.out.println("=============================");
}
}




