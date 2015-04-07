package MemberFunction;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
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
	
	public static void displayListOfSingleIndividuals(HashMap individualInfo) {
		System.out.println("***** Output for US09 *****\n");
		System.out.println("***** List of Single Individuals *****");
		
		if(individualInfo != null && !individualInfo.isEmpty()) {
			Object[] indiKey = individualInfo.keySet().toArray();
            Arrays.sort(indiKey);
            
            for(Object retval: indiKey) {
            	IndividualInfo indiInfo = (IndividualInfo)individualInfo.get(retval);
            	
            	if(indiInfo.getSpouseOfFamPtr().size() == 0)
            		System.out.println(indiInfo.getName());
            }
		}
		
		System.out.println("\n***** End of Output for US09 *****\n");
	}
	
	public static void displayNoOfChildrenInFamilies(HashMap familyInfo) {
		System.out.println("***** Output for US10 *****\n");
		System.out.println("***** No. of Children in each Family *****");
		
		boolean isFirstRecord = true;
		
		if(familyInfo != null && !familyInfo.isEmpty()) {
			Object[] famKey = familyInfo.keySet().toArray();
	        Arrays.sort(famKey);
	        
	        for(Object retval: famKey) {
            	FamilyInfo famInfo = (FamilyInfo)familyInfo.get(retval);
            	String id = Global.rebuildIdentifier(retval.toString(), 'F');
            	
            	if(isFirstRecord)
    	        	isFirstRecord = false;
    	        else
    	        	System.out.println("");
            	
            	System.out.println("Family: " + id);
            	System.out.println("No. of Children: " + famInfo.getChildren().size());
            }
	    }
		
		System.out.println("\n***** End of Output for US10 *****\n");
	}
	
	public static void displayUpcomingBirthdays(HashMap individualInfo, int noOFDays) {
		System.out.println("***** Output for US35 *****\n");
		System.out.println("***** List of Upcoming Birthdays in the next "+ noOFDays +" days *****");
		
		boolean isFirstRecord = true;
		
		if(individualInfo != null && !individualInfo.isEmpty()) {
			Object[] indiKey = individualInfo.keySet().toArray();
            Arrays.sort(indiKey);
            
            for(Object retval: indiKey) {
            	IndividualInfo indiInfo = (IndividualInfo)individualInfo.get(retval);
            	
            	if(indiInfo.getBirthDate()!= null) {
	            	Calendar currentDate = Calendar.getInstance();
	            	
	            	Calendar futureDate = Calendar.getInstance();
	            	futureDate.add(Calendar.DATE, noOFDays);
	            	
	            	Calendar birthDate = Calendar.getInstance();
	            	birthDate.setTime(indiInfo.getBirthDate());
	            	
	            	if(birthDate.get(Calendar.MONTH) < currentDate.get(Calendar.MONTH)) {
	            		birthDate.set(Calendar.YEAR, currentDate.get(Calendar.YEAR)+1);
	            	} else if(birthDate.get(Calendar.MONTH) > currentDate.get(Calendar.MONTH)) {
	            		birthDate.set(Calendar.YEAR, currentDate.get(Calendar.YEAR));
	            	} else {
	            		if(birthDate.get(Calendar.DATE) < currentDate.get(Calendar.DATE))
	            			birthDate.set(Calendar.YEAR, currentDate.get(Calendar.YEAR)+1);
	            		else
	            			birthDate.set(Calendar.YEAR, currentDate.get(Calendar.YEAR));
	            	}
	            	
	            	if(birthDate.compareTo(currentDate) >= 0 && birthDate.compareTo(futureDate) <=0) {
	            		if(isFirstRecord)
	        	        	isFirstRecord = false;
	        	        else
	        	        	System.out.println("");
	            		
	            		System.out.println("Name: " + indiInfo.getName());
	                	System.out.println("Birth Date: " + new SimpleDateFormat("dd-MMM-YYYY").format(indiInfo.getBirthDate()));
	            	}
            	}
            }
		}
		
		System.out.println("\n***** End of Output for US35 *****\n");
	}
	
	public static void displayUpcomingDeathAnniversaries(HashMap individualInfo, int noOFDays) {
		System.out.println("***** Output for US37 *****\n");
		System.out.println("***** List of Upcoming Death Anniversaries in the next "+ noOFDays +" days *****");
		
		boolean isFirstRecord = true;
		
		if(individualInfo != null && !individualInfo.isEmpty()) {
			Object[] indiKey = individualInfo.keySet().toArray();
            Arrays.sort(indiKey);
            
            for(Object retval: indiKey) {
            	IndividualInfo indiInfo = (IndividualInfo)individualInfo.get(retval);
            	
            	if(indiInfo.getDeathDate()!= null) {
	            	Calendar currentDate = Calendar.getInstance();
	            	
	            	Calendar futureDate = Calendar.getInstance();
	            	futureDate.add(Calendar.DATE, noOFDays);
	            	
	            	Calendar deathDate = Calendar.getInstance();
	            	deathDate.setTime(indiInfo.getDeathDate());
	            	
	            	if(deathDate.get(Calendar.MONTH) < currentDate.get(Calendar.MONTH)) {
	            		deathDate.set(Calendar.YEAR, currentDate.get(Calendar.YEAR)+1);
	            	} else if(deathDate.get(Calendar.MONTH) > currentDate.get(Calendar.MONTH)) {
	            		deathDate.set(Calendar.YEAR, currentDate.get(Calendar.YEAR));
	            	} else {
	            		if(deathDate.get(Calendar.DATE) < currentDate.get(Calendar.DATE))
	            			deathDate.set(Calendar.YEAR, currentDate.get(Calendar.YEAR)+1);
	            		else
	            			deathDate.set(Calendar.YEAR, currentDate.get(Calendar.YEAR));
	            	}
	            	
	            	if(deathDate.compareTo(currentDate) >= 0 && deathDate.compareTo(futureDate) <=0) {
	            		if(isFirstRecord)
	        	        	isFirstRecord = false;
	        	        else
	        	        	System.out.println("");
	            		
	            		System.out.println("Name: " + indiInfo.getName());
	                	System.out.println("Death Date: " + new SimpleDateFormat("dd-MMM-YYYY").format(indiInfo.getDeathDate()));
	            	}
            	}
            }
		}
		
		System.out.println("\n***** End of Output for US37 *****\n");
	}
}
