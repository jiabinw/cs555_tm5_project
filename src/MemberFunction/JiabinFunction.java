package MemberFunction;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Helper.Global;
import cs555_tm5_project.FamilyInfo;
import cs555_tm5_project.IndividualInfo;
import cs555_tm5_project.Main;

public class JiabinFunction {
	
	public static void ShowWhoHasSameBirthday(HashMap<Integer, FamilyInfo> familyInfoObjMap,HashMap<Integer, IndividualInfo> individualInfoObjMap){
		HashMap<Date, >
		
		
		
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
