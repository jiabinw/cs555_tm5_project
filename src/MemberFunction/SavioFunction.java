package MemberFunction;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import Helper.Global;
import cs555_tm5_project.FamilyInfo;
import cs555_tm5_project.IndividualInfo;

public class SavioFunction {
	public static void deathBeforeBirth(HashMap individualInfo) {
		System.out.println("***** Output for US01 *****\n");
		
		if(individualInfo != null && !individualInfo.isEmpty()) {
			Object[] indiKey = individualInfo.keySet().toArray();
            Arrays.sort(indiKey);
            
            for(Object retval: indiKey) {
            	IndividualInfo indiInfo = (IndividualInfo)individualInfo.get(retval);
                
            	if(indiInfo.getBirthDate()!=null && indiInfo.getDeathDate()!=null) {
            		if(indiInfo.getDeathDate().before(indiInfo.getBirthDate())) {
            			String id = Global.rebuildIdentifier(retval.toString(), 'I');
            			String name = indiInfo.getName();
            			String bDay = new SimpleDateFormat("dd MMM yyyy").format(indiInfo.getBirthDate());
            			String dDay = new SimpleDateFormat("dd MMM yyyy").format(indiInfo.getDeathDate());
            					
            			System.out.println("Individual " + id + " (" + name + ") has death date (" + dDay + ") before birth date (" + bDay + ")");
            		}
            	}
            }
		}
		
		System.out.println("\n***** End of Output for US01 *****\n");
	}
	
	public static void marriageBeforeBirth(HashMap individualInfo, HashMap familyInfo) {
		System.out.println("***** Output for US02 *****\n");
		
		if(familyInfo != null && !familyInfo.isEmpty()) {
			Object[] famKey = familyInfo.keySet().toArray();
	        Arrays.sort(famKey);
            
            for(Object retval: famKey) {
            	FamilyInfo famInfo = (FamilyInfo)familyInfo.get(retval);
            	
            	if(famInfo.getMarriageDate()!=null) {
            		Date marriageDate = famInfo.getMarriageDate();
            		String mrDay = new SimpleDateFormat("dd MMM yyyy").format(marriageDate);
            		
            		if(famInfo.getHusband()!=0 && famInfo.getWife()!=0) {
            			IndividualInfo husbandInfo = (IndividualInfo)individualInfo.get(famInfo.getHusband());
            			IndividualInfo wifeInfo = (IndividualInfo)individualInfo.get(famInfo.getWife());
            			
            			String id, name, bDay;
            			
            			if(husbandInfo!=null && husbandInfo.getBirthDate()!=null && marriageDate.before(husbandInfo.getBirthDate())) {
            				id = Global.rebuildIdentifier(Integer.toString(famInfo.getHusband()), 'I');
                			name = husbandInfo.getName();
                			bDay = new SimpleDateFormat("dd MMM yyyy").format(husbandInfo.getBirthDate());
                			
                			System.out.println("Individual " + id + " (" + name + ") has marriage date (" + mrDay + ") before birth date (" + bDay + ")");
            			}
            			
            			if(wifeInfo!=null && wifeInfo.getBirthDate()!=null && marriageDate.before(wifeInfo.getBirthDate())) {
            				id = Global.rebuildIdentifier(Integer.toString(famInfo.getWife()), 'I');
                			name = wifeInfo.getName();
                			bDay = new SimpleDateFormat("dd MMM yyyy").format(wifeInfo.getBirthDate());
                			
                			System.out.println("Individual " + id + " (" + name + ") has marriage date (" + mrDay + ") before birth date (" + bDay + ")");
            			}
            		}
            	}
            }
		}
		
		System.out.println("\n***** End of Output for US02 *****\n");
	}
}
