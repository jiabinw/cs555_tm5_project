package MemberFunction;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Helper.Global;
import cs555_tm5_project.FamilyInfo;
import cs555_tm5_project.IndividualInfo;

public class JiabinFunction {
	
	//ShowWhoHasSameBirthday
	public static HashMap<Date, ArrayList<IndividualInfo>> ShowWhoHasSameBirthday(HashMap<Integer, IndividualInfo> individualInfoObjMap){
		HashMap<Date, ArrayList<IndividualInfo>> hash = new HashMap<Date, ArrayList<IndividualInfo>>();
		
		for(Map.Entry<Integer, IndividualInfo> entry : individualInfoObjMap.entrySet()){
			if(entry.getValue().getBirthDate() != null){
				if(hash.containsKey(entry.getValue().getBirthDate())){
					hash.get(entry.getValue().getBirthDate()).add(entry.getValue());
				}else{
					ArrayList<IndividualInfo> array = new ArrayList<IndividualInfo>();
					array.add(entry.getValue());
					hash.put(entry.getValue().getBirthDate(), array);
				}				
			}							
		}
		
		for(Map.Entry<Date, ArrayList<IndividualInfo>> entry : hash.entrySet()){
			if(entry.getValue().size() == 1){
				hash.remove(entry.getKey());
			}
		}
		
		System.out.println("");
		System.out.println("-----------------------------------");
		System.out.println("-----ShowWhoHasSameBirthday--------");
		System.out.println("-----------------------------------");
		
	    if(hash.isEmpty()){
	    	System.out.println("No people has the same birthday.");
	    }else{
	    	for(Map.Entry<Date, ArrayList<IndividualInfo>> entry : hash.entrySet()){
	    		System.out.println("Birthday: "+ entry.getKey().toString());
	    		for(IndividualInfo indi : entry.getValue()){
	    			System.out.println("Name: "+ indi.getName());
	    		}
	    	}
	    }		
		return hash;
	}
	
	//MarriageBeforeLegalAge 
	public static HashMap<Integer, ArrayList<IndividualInfo>> MarriageBeforeLegalAge(HashMap<Integer, FamilyInfo> familyInfoObjMap,HashMap<Integer, IndividualInfo> individualInfoObjMap){
		HashMap<Integer, ArrayList<IndividualInfo>> errorFamily = new HashMap <Integer, ArrayList<IndividualInfo>>();

		for(Map.Entry<Integer, FamilyInfo> entry : familyInfoObjMap.entrySet()){
			
			ArrayList<IndividualInfo> errorPerson = new ArrayList<IndividualInfo>();

        	int familyCode = entry.getKey();
			FamilyInfo family = entry.getValue();
			
			Date marDate = family.getMarriageDate();
			IndividualInfo husband = individualInfoObjMap.get(family.getHusband());
			IndividualInfo wife = individualInfoObjMap.get(family.getWife());
            
			int husbandMAge = Global.AgeAtSpecificTime(husband.getBirthDate(), marDate);
			int wifeMAge = Global.AgeAtSpecificTime(wife.getBirthDate(), marDate);
			
			if((husbandMAge != -1 )&& (wifeMAge != -1)){
				if(husbandMAge<Global.legalMarriageAge){
					errorPerson.add(husband);
					errorFamily.put(familyCode, errorPerson);
				}
				
				if(wifeMAge<Global.legalMarriageAge){
					if(errorFamily.containsKey(entry.getKey())){
						errorFamily.get(familyCode).add(wife);
					}else{
						errorPerson.add(wife);
						errorFamily.put(familyCode, errorPerson);
					}	
				}
			}
		}
		
		System.out.println("");
		System.out.println("-----------------------------------");
		System.out.println("-----Marriage Before Legal Age-----");
		System.out.println("-----------------------------------");
		
		for(Map.Entry<Integer, ArrayList<IndividualInfo>> entry : errorFamily.entrySet() ){
			 ArrayList<IndividualInfo> errorPerson = entry.getValue();
			System.out.println("Family Code:"+" @F"+entry.getKey()+"@");
			System.out.println("Marriage date: "+ familyInfoObjMap.get(entry.getKey()).getMarriageDate().toString());
			for(IndividualInfo person : errorPerson){
				System.out.println(person.getName()+"'s birth date" +":"+person.getBirthDate().toString());
			}
			System.out.println("");
		}
		return errorFamily;		
	}
}
