package MemberFunction;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import cs555_tm5_project.FamilyInfo;
import cs555_tm5_project.IndividualInfo;

public class SavioFunction {
	public static void deathBeforeBirth(HashMap individualInfo) {
		if(individualInfo != null && !individualInfo.isEmpty()) {
			Object[] indiKey = individualInfo.keySet().toArray();
            Arrays.sort(indiKey);
            
            for(Object retval: indiKey) {
            	IndividualInfo indiInfo = (IndividualInfo)individualInfo.get(retval);
                
            	if(indiInfo.getBirthDate()!=null && indiInfo.getDeathDate()!=null) {
            		if(indiInfo.getDeathDate().before(indiInfo.getBirthDate())) {
            			System.out.println(indiInfo.getName() + " has an incorrect Birth or Death Date.");
            		}
            	}
            }
		}
	}
	
	public static void deathBeforeMarriage(HashMap individualInfo, HashMap familyInfo) {
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
            			
            			if(husbandInfo.getDeathDate()!=null && husbandInfo.getDeathDate().before(marriageDate))
            				System.out.println(husbandInfo.getName() + " has an incorrect Marriage or Death Date.");
            			
            			if(wifeInfo.getDeathDate()!=null && wifeInfo.getDeathDate().before(marriageDate))
            				System.out.println(wifeInfo.getName() + " has an incorrect Marriage or Death Date.");
            		}
            	}
            }
		}
	}
}
