package MemberFunction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Helper.Global;
import cs555_tm5_project.FamilyInfo;
import cs555_tm5_project.IndividualInfo;

public class HiteshFunction {

//public static void CheckIsDateValid(HashMap individualInfo, HashMap familyInfo)
//{
//	if(familyInfo != null && !familyInfo.isEmpty()) {
//		Object[] famKey = familyInfo.keySet().toArray();
//        Arrays.sort(famKey);
//        
//        for(Object retval: famKey) {
//        	FamilyInfo famInfo = (FamilyInfo)familyInfo.get(retval);
//        	
//        	if(famInfo.getMarriageDate()!=null) {
//        		Date marriageDate = famInfo.getMarriageDate();
//        		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//        		    Date utilDate = null;
//        		    
//        		    try {
//        		    	formatter.setLenient(false);
//        				utilDate = formatter.parse(marriageDate.toString());
//        			} catch (ParseException e) {
//        				//e.printStackTrace();
//
//                		if(famInfo.getHusband()!=0 && famInfo.getWife()!=0) {
//                			IndividualInfo husbandInfo = (IndividualInfo)individualInfo.get(famInfo.getHusband());
//                			IndividualInfo wifeInfo = (IndividualInfo)individualInfo.get(famInfo.getWife());
//                			System.out.println(husbandInfo.getName()+" - "+wifeInfo.getName()+" have an Invalid Marriage Date");}
//        				
//        				
//        			}
//        		
//        		}
//        	}
//        }
//	if(individualInfo != null && !individualInfo.isEmpty()) {
//		Object[] indiKey = individualInfo.keySet().toArray();
//        Arrays.sort(indiKey);
//        
//        for(Object retval: indiKey) {
//        	IndividualInfo indiInfo = (IndividualInfo)individualInfo.get(retval);
//            
//        	if(indiInfo.getBirthDate()!=null) {
//        		Date BirthDate = indiInfo.getBirthDate();
//        		
//       		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//       		    Date utilDate = null;
//       		    
//       		    try {
//       		    	formatter.setLenient(false);
//       				utilDate = formatter.parse(BirthDate.toString());
//       			} catch (ParseException e) {
//       				//e.printStackTrace();
//
//               		
//               			System.out.println(indiInfo.getName()+" has an Invalid Birth Date");}
//       				
//       				
//       			}
//        	if(indiInfo.getDeathDate()!=null) {
//        		Date deathDate = indiInfo.getDeathDate();
//        		
//       		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//       		    Date utilDate = null;
//       		    
//       		    try {
//       		    	formatter.setLenient(false);
//       				utilDate = formatter.parse(deathDate.toString());
//       			} catch (ParseException e) {
//       				//e.printStackTrace();
//
//               		
//               			System.out.println(indiInfo.getName()+" has an Invalid Death Date");}
//       				
//       				
//       			}
//        		}
//        	}
//        }
//	
	
	


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
	System.out.println("=============Divorce Before Marriage  User Story 36============");
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
	System.out.println("=======================================================================");
}
public static void WhoHasLivedLongest(HashMap individualInfo, HashMap familyInfo)
{
	System.out.println("=============Person Who have lived the longest User Story 43============");
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


	System.out.println("==============================================================================");
}
}

