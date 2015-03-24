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
	public static HashMap<Date, ArrayList<IndividualInfo>> showWhoHasSameBirthday(HashMap<Integer, IndividualInfo> individualInfoObjMap){
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
		
		
		Global.printTitle("Show Who Has Same Birthday");
		
	    if(hash.isEmpty()){
	    	System.out.println("No people has the same birthday.");
	    }else{
	    	for(Map.Entry<Date, ArrayList<IndividualInfo>> entry : hash.entrySet()){
	    		if(entry.getValue().size() != 1){
		    		System.out.println("Birthday: "+ entry.getKey().toString());
		    		for(IndividualInfo indi : entry.getValue()){
		    			System.out.println("Name: "+ indi.getName());
		    		}
	    		}
	    	}
	    }		
	    System.out.println("");
	    
		return hash;
	}
	
	//MarriageBeforeLegalAge 
	public static HashMap<Integer, ArrayList<IndividualInfo>> marriageBeforeLegalAge(HashMap<Integer, FamilyInfo> familyInfoObjMap,HashMap<Integer, IndividualInfo> individualInfoObjMap){
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
		
		Global.printTitle("Marriage Before Legal Age");
		
		for(Map.Entry<Integer, ArrayList<IndividualInfo>> entry : errorFamily.entrySet() ){
			ArrayList<IndividualInfo> errorPerson = entry.getValue();
			System.out.println("Family Code:"+" @F"+entry.getKey()+"@");
			System.out.println("Marriage date: "+ familyInfoObjMap.get(entry.getKey()).getMarriageDate().toString());
			for(IndividualInfo person : errorPerson){
				System.out.println(person.getName()+"'s birth date" +":"+person.getBirthDate().toString());
			}
			System.out.println("");
		}
		
		System.out.println("");
		return errorFamily;		
	}
    
	//MarriageToSilbling
	public static HashMap<Integer, FamilyInfo> marriageToSilbling(HashMap<Integer, FamilyInfo> familyInfoObjMap,HashMap<Integer, IndividualInfo> individualInfoObjMap){
		HashMap<Integer, FamilyInfo> result = new HashMap<Integer, FamilyInfo>();
		for(Map.Entry<Integer, FamilyInfo> entry : familyInfoObjMap.entrySet()){
			int husband = entry.getValue().getHusband();
			int wife = entry.getValue().getWife();
			int husbandAsChildFamilyCode = individualInfoObjMap.get(husband).getChildOfFamPtr();
			FamilyInfo husbandAsChildFamily = familyInfoObjMap.get(husbandAsChildFamilyCode);
			if(husbandAsChildFamily != null){
				if(husbandAsChildFamily.getChildren().contains(wife)){
					result.put(entry.getKey(), entry.getValue());
				}
			}
		}
		
		Global.printTitle("Marriage To Silbling");
		System.out.println("The husband and wife are siblings in the following families:");
		if(!result.isEmpty()){
			for(Map.Entry<Integer, FamilyInfo> entry : result.entrySet()){
				System.out.println(Global.rebuildIdentifier(entry.getKey(), 'F'));
				int husband = entry.getValue().getHusband();
				int wife = entry.getValue().getWife();
				System.out.println("Husband: "+individualInfoObjMap.get(husband).getName()+"\n"+"Wife: "+individualInfoObjMap.get(wife).getName());
				System.out.println("");
			}
		}else{
			System.out.println("None.");
		}
		System.out.println("");
		
		return result;
	}
	
	//Bigamy 
	@SuppressWarnings("deprecation")
	public static ArrayList<Integer> showBigamy(HashMap<Integer, FamilyInfo> familyInfoObjMap,HashMap<Integer, IndividualInfo> individualInfoObjMap){
		ArrayList<Integer> bigamyPerson = new ArrayList<Integer>();
		for(Map.Entry<Integer, IndividualInfo> entry : individualInfoObjMap.entrySet()){
			IndividualInfo indi = entry.getValue();
			if(indi.getSpouseOfFamPtr().size()>1){
				ArrayList<ArrayList<Date>> marriagePeriod = new ArrayList<ArrayList<Date>>();
 				ArrayList<Integer> spouseFamilyList = indi.getSpouseOfFamPtr();
				for(Integer i : spouseFamilyList){
					ArrayList<Date> singlePersonMarriagePeriod = new ArrayList<Date>();
					if(familyInfoObjMap.get(i).getMarriageDate() != null){
						//add the marriage starting date 
						singlePersonMarriagePeriod.add(familyInfoObjMap.get(i).getMarriageDate());
						//add the marriage ending date 
						if(familyInfoObjMap.get(i).getDivorceDate() != null){
							singlePersonMarriagePeriod.add(familyInfoObjMap.get(i).getDivorceDate());
							marriagePeriod.add(singlePersonMarriagePeriod);
						}else{
							if(indi.getDeathDate() != null){
								singlePersonMarriagePeriod.add(indi.getDeathDate());
							    marriagePeriod.add(singlePersonMarriagePeriod);
							}else{
								singlePersonMarriagePeriod.add(new Date(9999, 1, 1));
							    marriagePeriod.add(singlePersonMarriagePeriod);
							}						
						}
					}										
				}
				
				if(marriagePeriod.size() == indi.getSpouseOfFamPtr().size()){
					for(ArrayList<Date> period : marriagePeriod){
						Date beginningM = period.get(0);
						Date endM = period.get(1);
						
						for(int i = 0; i < marriagePeriod.size(); i++){
							if(marriagePeriod.indexOf(period) != i){
								
								Date otherBeginningM = marriagePeriod.get(i).get(0);
								Date otherEndM = marriagePeriod.get(i).get(1);
								
								if(otherBeginningM.after(beginningM) && otherBeginningM.before(endM) 
									|| 
								   otherEndM.after(beginningM) && otherEndM.before(endM)){
									bigamyPerson.add(entry.getKey());
								}
							}
						}
					}
				}								
			}
		}
		
		Global.printTitle("Show Bigamy");
		System.out.println("The below people may have bigamy:");
		if(bigamyPerson.size() != 0){
	        for(Integer person : bigamyPerson){
	        	System.out.println(Global.rebuildIdentifier(person,'I')+" "+individualInfoObjMap.get(person).getName());
	        }
        }else{
			System.out.println("None.");
		}
		System.out.println("");
		
		return bigamyPerson;
	}		

}




