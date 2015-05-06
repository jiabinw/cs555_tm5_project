package MemberFunction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	
	public static void displayListOfOrphans(HashMap individualInfo, HashMap familyInfo) {
		System.out.println("\n***** Output for US23 *****\n");
		System.out.println("***** List of Orphaned Individuals *****\n");
		
		if(individualInfo != null && !individualInfo.isEmpty() && familyInfo != null && !familyInfo.isEmpty()) {
			Object[] indiKey = individualInfo.keySet().toArray();
            Arrays.sort(indiKey);
            
            for(Object retval: indiKey) {
            	IndividualInfo indiInfo = (IndividualInfo)individualInfo.get(retval);
            	
            	if(indiInfo.getChildOfFamPtr() > 0) {
            		if(indiInfo.getDeathDate() == null) {
            			FamilyInfo famInfo = (FamilyInfo)familyInfo.get(indiInfo.getChildOfFamPtr());
            			
            			IndividualInfo fatherInfo = (IndividualInfo)individualInfo.get(famInfo.getHusband());
            			IndividualInfo motherInfo = (IndividualInfo)individualInfo.get(famInfo.getWife());
            			
            			if(fatherInfo.getDeathDate() != null && motherInfo.getDeathDate() != null) {
            				System.out.println(indiInfo.getName() + " (with ID: " + Global.rebuildIdentifier(retval.toString(), 'I') + ") is an orphan.");
            			}
            		}
            	} else {
            		System.out.println(indiInfo.getName() + " (with ID: " + Global.rebuildIdentifier(retval.toString(), 'I') + ") may be an orphan since no family information was found.");
            	}
            }
		}
		
		System.out.println("\n***** End of Output for US23 *****\n");
	}
	
	public static void displayListOfIndividualsBornOutOfWedlock(HashMap individualInfo, HashMap familyInfo) {
		System.out.println("\n***** Output for US31 *****\n");
		System.out.println("***** List of Individuals born out of wedlock *****\n");
		
		boolean isFirstRecord = true;
		
		if(individualInfo != null && !individualInfo.isEmpty() && familyInfo != null && !familyInfo.isEmpty()) {
			Object[] indiKey = individualInfo.keySet().toArray();
            Arrays.sort(indiKey);
            
            for(Object retval: indiKey) {
            	IndividualInfo indiInfo = (IndividualInfo)individualInfo.get(retval);
            	
            	if(indiInfo.getChildOfFamPtr() > 0) {
            		if(indiInfo.getBirthDate() != null) {
            			FamilyInfo famInfo = (FamilyInfo)familyInfo.get(indiInfo.getChildOfFamPtr());
            			
            			if(famInfo.getMarriageDate() != null && indiInfo.getBirthDate().before(famInfo.getMarriageDate())) {
            				if(isFirstRecord)
    	        	        	isFirstRecord = false;
    	        	        else
    	        	        	System.out.println("");
            				
            				System.out.println(indiInfo.getName() + " (with ID: " + Global.rebuildIdentifier(retval.toString(), 'I') + ") was born out of wedlock.");
            				System.out.println("Birth Date: " + new SimpleDateFormat("dd-MMM-YYYY").format(indiInfo.getBirthDate()));
            				System.out.println("Parent's Marriage Date: " + new SimpleDateFormat("dd-MMM-YYYY").format(famInfo.getMarriageDate()));
            			}
            		}
            	}
            }
		}
		
		System.out.println("\n***** End of Output for US31 *****\n");
	}
	
	public static void displayFamilyInfoHavingChildrenBornOnSameDate(HashMap individualInfo, HashMap familyInfo) {
		System.out.println("***** Output for US27 *****\n");
		System.out.println("***** List the all families who had children born at the same time (i.e twins, triplets, etc) *****");
		
		if(familyInfo != null && !familyInfo.isEmpty()) {
			Object[] famKey = familyInfo.keySet().toArray();
	        Arrays.sort(famKey);
	        
	        for(Object retval: famKey) {
            	FamilyInfo famInfo = (FamilyInfo)familyInfo.get(retval);
            	
            	IndividualInfo husbandInfo = (IndividualInfo)individualInfo.get(famInfo.getHusband());
    			IndividualInfo wifeInfo = (IndividualInfo)individualInfo.get(famInfo.getWife());
            	
            	HashMap<Date, ArrayList<String>> childrenWithSameBirthDateMap = new HashMap<Date, ArrayList<String>>();
            	
            	if(famInfo.getChildren().size() > 1) {
            		for(Integer childId: famInfo.getChildren()) {
            			IndividualInfo indiInfo = (IndividualInfo)individualInfo.get(childId);
            			
            			if(indiInfo.getBirthDate() != null) {
	            			if(childrenWithSameBirthDateMap.containsKey(indiInfo.getBirthDate())) {
	            				childrenWithSameBirthDateMap.get(indiInfo.getBirthDate()).add(indiInfo.getName());
	            			} else {
	            				ArrayList<String> nameList = new ArrayList<String>();
	            				nameList.add(indiInfo.getName());	 
	            				childrenWithSameBirthDateMap.put(indiInfo.getBirthDate(), nameList);
	            			}
            			}
            		}
        		 
            		if(childrenWithSameBirthDateMap.size() > 0) {
            			for (Map.Entry<Date, ArrayList<String>> entry : childrenWithSameBirthDateMap.entrySet()) {
            				 ArrayList<String> value = entry.getValue();
            				 
            				 if(value.size() > 1) {
            					 StringBuffer finalOutput = new StringBuffer();
            					 
            					 finalOutput.append(husbandInfo.getName() + " & " + wifeInfo.getName() + " have children ");
            					 
            					 StringBuffer names = new StringBuffer();
            					 
            					 for(int i=0; i<value.size(); i++) {
            						 if(i == value.size()-1) {
            							 names.append(" & " + value.get(i));
            						 } else {
            							 if(i == value.size()-2) {
            								 names.append(value.get(i));
            							 } else {
            								 names.append(value.get(i) + ", ");
            							 }
            						 }
            					 }
            					 
            					 finalOutput.append(names + " born on " + new SimpleDateFormat("dd-MMM-YYYY").format(entry.getKey()));
            					 
            					 System.out.println(finalOutput.toString());
            				 }
            			}
            		}
            	}
	        }
		}
		
		System.out.println("\n***** End of Output for US27 *****\n");
	}
	
	public static void displayIndividualsWithIncorrectOrMissingLastNames(HashMap individualInfo, HashMap familyInfo) {
		System.out.println("***** Output for US29 *****\n");
		System.out.println("***** List all individuals with missing or incorrect last names *****");
		
		if(individualInfo != null && !individualInfo.isEmpty()) {
			Object[] indiKey = individualInfo.keySet().toArray();
            Arrays.sort(indiKey);
            
            for(Object retval: indiKey) {
            	IndividualInfo indiInfo = (IndividualInfo)individualInfo.get(retval);
            	
            	String indiLname = Global.getLastName(indiInfo.getName());
            	
            	if(!indiLname.isEmpty()) {
	            	if(indiInfo.getSpouseOfFamPtr().size() > 0 && indiInfo.getSex().equals("F")) {
            			if(indiInfo.getSpouseOfFamPtr().size() > 1) {
            				HashMap<Date, String> marriedToMap = new HashMap<Date, String>();
            				
            				for(Iterator it = indiInfo.getSpouseOfFamPtr().iterator(); it.hasNext();) {
            					FamilyInfo spousefamilyInfo = (FamilyInfo)familyInfo.get(it.next());
            					
            					if(spousefamilyInfo.getDivorceDate() == null && spousefamilyInfo.getMarriageDate() != null)
            						marriedToMap.put(spousefamilyInfo.getMarriageDate(), ((IndividualInfo)individualInfo.get(spousefamilyInfo.getHusband())).getName());
            				}
            				
            				if(marriedToMap.size() > 0) {
	            				Iterator<Map.Entry<Date, String>> iterator = Global.sortByKeys(marriedToMap).entrySet().iterator();
	        				    Map.Entry<Date, String> lastEntry = null;
	        				    
	        				    while (iterator.hasNext())
	        				    	lastEntry = iterator.next();
	        				        
	            				String recentHusbandLname = Global.getLastName(lastEntry.getValue());
	            				
	            				if(!recentHusbandLname.isEmpty()) {
	            					if(!recentHusbandLname.equals(indiLname)) {
	            						System.out.println(indiInfo.getName() + " does not have the last name of her husband " + lastEntry.getValue());
	            					}
	            				}
            				}
            			} else {
            				FamilyInfo famInfo_F = (FamilyInfo)familyInfo.get(indiInfo.getSpouseOfFamPtr().get(0));
            				
            				if(famInfo_F != null && famInfo_F.getDivorceDate() == null) {
	            				IndividualInfo husbandInfo = (IndividualInfo)individualInfo.get(famInfo_F.getHusband());
	            				
	            				if(husbandInfo != null) {
		            				String husbandLname = Global.getLastName(husbandInfo.getName());
		            				
		            				if(!husbandLname.isEmpty()) {
		            					if(!husbandLname.equals(indiLname)) {
		            						System.out.println(indiInfo.getName() + " does not have the last name of her husband " + husbandInfo.getName());
		            					}
		            				}
	            				}
            				}
            			}
	            	} else if(indiInfo.getChildOfFamPtr() != 0) {
	            		FamilyInfo famInfo_M = (FamilyInfo)familyInfo.get(indiInfo.getChildOfFamPtr());
            			
	            		if(famInfo_M != null) {
	            			IndividualInfo fatherInfo = (IndividualInfo)individualInfo.get(famInfo_M.getHusband());
	            			
	            			if(fatherInfo != null) {
	            				String fatherLname = Global.getLastName(fatherInfo.getName());
	            				
	            				if(!fatherLname.isEmpty()) {
	            					if(!fatherLname.equals(indiLname)) {
	            						System.out.println(indiInfo.getName() + " does not have the last name of his/her father " + fatherInfo.getName());
	            					}
	            				}
            				}
	            		}
            		} else {
//	            		System.out.println(indiInfo.getName() + " does not have suffient information to validate his/her last name");
	            	}
            	} else {
            		System.out.println(indiInfo.getName() + " (with ID: " + Global.rebuildIdentifier(retval.toString(), 'I') + ") does not have a last name.");
            	}
            }
		}
		
		System.out.println("\n***** End of Output for US29 *****\n");
	}
}
