package cs555_tm5_project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import MemberFunction.HiteshFunction;
import MemberFunction.JiabinFunction;
import MemberFunction.SavioFunction;
import MemberFunction.SiyuanFunction;

/**
 * @author Savio Dcruz, Jiabin Wang, Hitesh Jain, Siyuan Zheng
 */

public class Main {
	
    private static HashMap<Integer, IndividualInfo> individualInfoObjMap = new HashMap<Integer, IndividualInfo>();
    private static HashMap<Integer, FamilyInfo> familyInfoObjMap = new HashMap<Integer, FamilyInfo>();
    private static HashMap<String, Integer> monthMap = new HashMap<String, Integer>();
    
    public static void main(String[] args) {
        try {
        	
        	//INITILIZATION, INFLATE THE FAMILY INFO HASHMAP AND THE INDI INFO HASHMAP 
        	populateMonthMap();
            File dir = new File(".");
            //Clear the contents of output.txt
            String outputFileLoc = dir.getCanonicalPath()+File.separator+"src"+File.separator+"Output"+File.separator+"Output.txt";
            FileWriter fw = new FileWriter(outputFileLoc);
            fw.close();
            //Read from gedcom.ged file
            File fin = new File( dir.getCanonicalPath()+File.separator+"src"+File.separator+"GedComFiles"+File.separator+File.separator+ "Jiabin_Wang_P01.ged");
            readFile(fin);
            
            UserStoryMethods();
            
        } catch(IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void readFile(File fin) throws IOException {
        //List of valid tag names
    	String[] tags = {"INDI", "NAME", "SEX", "BIRT", "DEAT", "FAMC", "FAMS", 
            "FAM", "MARR", "HUSB", "WIFE", "CHIL", "DIV", "DATE", "TRLR", "NOTE"};
        ArrayList<String> tagList = new ArrayList<>(Arrays.asList(tags));
        
        FileInputStream fis = new FileInputStream(fin);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line;
        
        int level = -1;
        String tag = "";
        String tagVal = "";
        String dateCode = "";
        int indi_ID = -1;
        int fam_ID = -1;
        IndividualInfo indiInfo = null;
        FamilyInfo famInfo = null;
        System.out.println("======================Wrong Dates in GedCom File - User Story 03===========================");
        while((line = br.readLine()) != null) {
            writeToFile(line);
            
            int counter = 0;
            boolean invalidTag = false;
            String tagStr = "";
            
            for(String retval: line.split(" ", 3)) {
                counter++;
                
                switch(counter)
                {
                    case 1 :
                        level = Integer.parseInt(retval);
                        writeToFile("Level Number: " + retval);
                        break;
                    case 2 :
                        if(tagList.contains(retval)) {
                            tag = retval;
                            tagStr = "Tag: " + retval + "\n";
                        } else {
                            tagVal = retval;
                            tagStr = "Invalid tag\n";
                            invalidTag = true;
                        }
                        break;
                    case 3 : 
                        if(invalidTag && tagList.contains(retval)) {
                            tag = retval;
                            tagStr = "Tag: " + retval + "\n";
                        } else {
                            tagVal = retval;
                        }
                        break;
                    default :
                        break;
                }
            }
            
            if(level == 0){
                if(tag.equalsIgnoreCase("INDI")) {
                    indiInfo = new IndividualInfo();
                    String temp = tagVal.replace("@", "");
                    temp = temp.replace("I", "");
                    indi_ID = Integer.parseInt(temp);
                }
                if(tag.equalsIgnoreCase("FAM")) {
                    famInfo = new FamilyInfo();
                    String temp = tagVal.replace("@", "");
                    temp = temp.replace("F", "");
                    fam_ID = Integer.parseInt(temp);
                }
            } else {
                if(indi_ID != -1) {
                    String temp = "";
                    switch(tag.toUpperCase())
                    {
                        case "NAME" :
                            indiInfo.setName(tagVal.replace("/", ""));
                            individualInfoObjMap.put(indi_ID, indiInfo);
                            break;
                        case "SEX" :
                            indiInfo.setSex(tagVal);
                            individualInfoObjMap.put(indi_ID, indiInfo);
                            break;
                        case "BIRT" :
                            dateCode = "BH";
                            break;
                        case "DEAT" :
                            dateCode = "DH";
                            break;
                        case "FAMC" :
                            temp = tagVal.replace("@", "");
                            temp = temp.replace("F", "");
                            indiInfo.setChildOfFamPtr(Integer.parseInt(temp));
                            individualInfoObjMap.put(indi_ID, indiInfo);
                            break;
                        case "FAMS" :
                            temp = tagVal.replace("@", "");
                            temp = temp.replace("F", "");
                            indiInfo.setSpouseOfFamPtr(Integer.parseInt(temp));
                            individualInfoObjMap.put(indi_ID, indiInfo);
                            break;    
                        default :
                            break;
                    }
                }
                
                if(fam_ID != -1) {
                    String temp = "";
                    switch(tag.toUpperCase())
                    {
                        case "HUSB" :
                            temp = tagVal.replace("@", "");
                            temp = temp.replace("I", "");
                            famInfo.setHusband(Integer.parseInt(temp));
                            familyInfoObjMap.put(fam_ID, famInfo);
                            break;
                        case "WIFE" :
                            temp = tagVal.replace("@", "");
                            temp = temp.replace("I", "");
                            famInfo.setWife(Integer.parseInt(temp));
                            familyInfoObjMap.put(fam_ID, famInfo);
                            break;
                        case "CHIL" :
                            temp = tagVal.replace("@", "");
                            temp = temp.replace("I", "");
                            famInfo.setChildren(Integer.parseInt(temp));
                            familyInfoObjMap.put(fam_ID, famInfo);
                            break;
                        case "MARR" :
                            dateCode = "MR";
                            break; 
                        case "DIV" :
                            dateCode = "DR";
                            break;    
                        default :
                            break;
                    }
                }
            }
            
            if(!dateCode.isEmpty() && tag.toUpperCase().equals("DATE")) {
       
                switch(dateCode)
                {
                    case "BH" :
                    	
                    	if(convertStrToDate(tagVal)!=null)
                    	{
                    	//============SPRINT ITEM - INVALID DATE VALIDATION- HITESH=========//
                        indiInfo.setBirthDate(convertStrToDate(tagVal));
                        individualInfoObjMap.put(indi_ID, indiInfo);
                    	}
                    	else
                    	{
                    	System.out.println(individualInfoObjMap.get(indi_ID).getName()+" has an Invalid Birth date ");
                    	indiInfo.setBirthDate(null);
                    	individualInfoObjMap.put(indi_ID, indiInfo);
                    	}
                        break;
                    case "DH" :
                    	if(convertStrToDate(tagVal)!=null)
                    	{
                    	//============SPRINT ITEM - INVALID DATE VALIDATION- HITESH=========//
                        indiInfo.setDeathDate(convertStrToDate(tagVal));
                        individualInfoObjMap.put(indi_ID, indiInfo);
                    	}
                    	else
                    	{
                    	System.out.println(individualInfoObjMap.get(indi_ID).getName()+" has an Invalid Death date- "+individualInfoObjMap.get(indi_ID).getDeathDate());
                    	indiInfo.setDeathDate(null);
                    	individualInfoObjMap.put(indi_ID, indiInfo);
                    	}
                        break;
                      
                    case "MR" :
                    	if(convertStrToDate(tagVal)!=null)
                    	{
                    	//============SPRINT ITEM - INVALID DATE VALIDATION- HITESH=========//
                    	famInfo.setMarriageDate(convertStrToDate(tagVal));
                    	familyInfoObjMap.put(fam_ID, famInfo);
                    	}
                    	else
                    	{
                    		String Hus=individualInfoObjMap.get(familyInfoObjMap.get(fam_ID).getHusband()).getName();
                    		String Wife=individualInfoObjMap.get(familyInfoObjMap.get(fam_ID).getWife()).getName();
                    		System.out.println(Hus+" and "+Wife+" have an Invalid Marriage date-" +familyInfoObjMap.get(fam_ID).getMarriageDate());
                    		famInfo.setMarriageDate(null);
                        	familyInfoObjMap.put(fam_ID, famInfo);
                    	}
                    	
                        break;
                    case "DR" :
                    	if(convertStrToDate(tagVal)!=null)
                    	{
                    	//============SPRINT ITEM - INVALID DATE VALIDATION- HITESH=========//
                    	famInfo.setDivorceDate(convertStrToDate(tagVal));
                    	familyInfoObjMap.put(fam_ID, famInfo);
                    	}
                    	else
                    	{
                    		String Hus=individualInfoObjMap.get(familyInfoObjMap.get(fam_ID).getHusband()).getName();
                    		String Wife=individualInfoObjMap.get(familyInfoObjMap.get(fam_ID).getWife()).getName();
                    		System.out.println(Hus+" and "+Wife+" have an Invalid Divorce date-" +familyInfoObjMap.get(fam_ID).getDivorceDate());
                    		famInfo.setDivorceDate(null);
                        	familyInfoObjMap.put(fam_ID, famInfo);
                    	}
                        break;     
                    default :
                        break;
                }
              
                dateCode = "";
           
            }
            
            tag = "";
            tagVal = "";
            
            writeToFile(tagStr);
        }
        
        br.close();
        System.out.print("=========================================================");
    }
    
    private static void writeToFile(String line) throws IOException {
        File dir = new File(".");
        String outputFileLoc = dir.getCanonicalPath() + File.separator + "output.txt";
        FileWriter fw = new FileWriter(outputFileLoc, true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(line);
        bw.newLine();
        bw.close();
        fw.close();
    }
    
    public static Date convertStrToDate(String date) {
    	String dateVal[] = date.split(" ", 3);
    	String formattedDateStr = dateVal[2] + "/" + monthMap.get(dateVal[1]) + "/" + dateVal[0];
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
	    //============SPRINT ITEM - INVALID DATE VALIDATION- HITESH=========//
	    formatter.setLenient(false);
	    //=================================================================//
	    Date utilDate = null;
	    
	    try {
			utilDate = formatter.parse(formattedDateStr);
		} catch (ParseException e) {
			return null;
			
		}
	    
	    return utilDate;
	}
    
    private static void UserStoryMethods(){
      	SavioFunction.deathBeforeBirth(individualInfoObjMap); // User Story 01
        SavioFunction.marriageBeforeBirth(individualInfoObjMap, familyInfoObjMap); // User Story 02
        HiteshFunction.MarriageAfterDeath(individualInfoObjMap, familyInfoObjMap);
        JiabinFunction.marriageBeforeLegalAge(familyInfoObjMap, individualInfoObjMap);
        JiabinFunction.showWhoHasSameBirthday(individualInfoObjMap);
        SiyuanFunction.wrongChildrenBirthday(individualInfoObjMap, familyInfoObjMap);
        SiyuanFunction.wrongMarrigeSex(individualInfoObjMap, familyInfoObjMap);  
        HiteshFunction.DivorceBeforeMarriage(individualInfoObjMap, familyInfoObjMap);
        HiteshFunction.WhoHasLivedLongest(individualInfoObjMap, familyInfoObjMap);
        SavioFunction.displayListOfSingleIndividuals(individualInfoObjMap);
        SavioFunction.displayNoOfChildrenInFamilies(familyInfoObjMap);
        JiabinFunction.marriageToSilbling(familyInfoObjMap, individualInfoObjMap);
        JiabinFunction.showBigamy(familyInfoObjMap, individualInfoObjMap);
        SiyuanFunction.childrenAmountExceed(individualInfoObjMap, familyInfoObjMap, 1);
        SiyuanFunction.genderRatio(individualInfoObjMap, familyInfoObjMap);
        SavioFunction.displayUpcomingBirthdays(individualInfoObjMap, 30);
        SavioFunction.displayUpcomingDeathAnniversaries(individualInfoObjMap, 60);
        HiteshFunction.MarriageAniversaryInNext30Days(individualInfoObjMap, familyInfoObjMap);
        HiteshFunction.WeddingThatOccuredInLast30Days(individualInfoObjMap, familyInfoObjMap);
        JiabinFunction.showPeopleWhoDieAtYoungAge(familyInfoObjMap, individualInfoObjMap);
        JiabinFunction.showPeopleWhoseParentsGetDivorced(familyInfoObjMap, individualInfoObjMap);
        SiyuanFunction.youngMom(familyInfoObjMap, individualInfoObjMap);
        SiyuanFunction.oldMom(familyInfoObjMap, individualInfoObjMap);
        HiteshFunction.DeathInLast30Days(individualInfoObjMap);
        HiteshFunction.Datetodayorprevious(individualInfoObjMap, familyInfoObjMap);
        SavioFunction.displayListOfOrphans(individualInfoObjMap, familyInfoObjMap);
        SavioFunction.displayListOfIndividualsBornOutOfWedlock(individualInfoObjMap, familyInfoObjMap);
        JiabinFunction.showChildrenOfEveryIndi(familyInfoObjMap, individualInfoObjMap);
        JiabinFunction.showSiblingsOfEveryIndi(familyInfoObjMap, individualInfoObjMap);
        SiyuanFunction.checkInvidIdForFam(familyInfoObjMap, individualInfoObjMap);
        SiyuanFunction.checkFamIdForIndiv(familyInfoObjMap, individualInfoObjMap);   
        HiteshFunction.CouplesWithLargeAgeDiff(familyInfoObjMap, individualInfoObjMap, 10);
        HiteshFunction.ChildrenBornAfterFathersDeath(individualInfoObjMap,familyInfoObjMap);
        JiabinFunction.showMothersWhoGaveBirthTooManyChildren(familyInfoObjMap, individualInfoObjMap);
        JiabinFunction.showIndividualWhoLiveTooLong(familyInfoObjMap, individualInfoObjMap);
    	SavioFunction.displayFamilyInfoHavingChildrenBornOnSameDate(individualInfoObjMap, familyInfoObjMap);
        SavioFunction.displayIndividualsWithIncorrectOrMissingLastNames(individualInfoObjMap, familyInfoObjMap);
        SiyuanFunction.genderInfo(individualInfoObjMap);
        SiyuanFunction.youngParents(familyInfoObjMap, individualInfoObjMap);
    }
    
    private static void populateMonthMap() {
    	monthMap.put("JAN", 1);
    	monthMap.put("FEB", 2);
    	monthMap.put("MAR", 3);
    	monthMap.put("APR", 4);
    	monthMap.put("MAY", 5);
    	monthMap.put("JUN", 6);
    	monthMap.put("JUL", 7);
    	monthMap.put("AUG", 8);
    	monthMap.put("SEP", 9);
    	monthMap.put("OCT", 10);
    	monthMap.put("NOV", 11);
    	monthMap.put("DEC", 12);
    }
}