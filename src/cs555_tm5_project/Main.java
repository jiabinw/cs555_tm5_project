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

import MemberFunction.SavioFunction;

/**
 * @author Savio Dcruz
 */
public class Main {
    private static HashMap<Integer, IndividualInfo> individualInfoObjMap = new HashMap<Integer, IndividualInfo>();
    private static HashMap<Integer, FamilyInfo> familyInfoObjMap = new HashMap<Integer, FamilyInfo>();
    private static HashMap<String, Integer> monthMap = new HashMap<String, Integer>();
    
    public static void main(String[] args) {
        try {
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
            
//            System.out.println("***** Displaying Individual Information *****\n");
//            
//            Object[] indiKey = individualInfoObjMap.keySet().toArray();
//            Arrays.sort(indiKey);
//            for(Object retval: indiKey) {
//                String id =  "@I" + retval.toString() + "@";
//
//                System.out.println(id);
//                System.out.println("NAME: " + individualInfoObjMap.get(retval).getName());
//                System.out.println("SEX: " + individualInfoObjMap.get(retval).getSex());
//                System.out.println("BIRTH: " + individualInfoObjMap.get(retval).getBirthDate());
//                System.out.println("DEATH: " + individualInfoObjMap.get(retval).getDeathDate());
//                System.out.println("");
//            }
//            
//            System.out.println("\n***** Displaying Family Information *****\n");
//            
//            Object[] famKey = familyInfoObjMap.keySet().toArray();
//            Arrays.sort(famKey);
//            for(Object retval: famKey) {
//                String id =  "@F" + retval.toString() + "@";
//                
//                System.out.println(id);
//                System.out.println("HUSBAND: " + individualInfoObjMap.get(familyInfoObjMap.get(retval).getHusband()).getName());
//                System.out.println("WIFE: " + individualInfoObjMap.get(familyInfoObjMap.get(retval).getWife()).getName());
//                System.out.println("MARRIAGE: " + familyInfoObjMap.get(retval).getMarriageDate());
//                System.out.println("DIVORCE: " + familyInfoObjMap.get(retval).getDivorceDate());
//                
//                for(int child: familyInfoObjMap.get(retval).getChildren()) {
//                    System.out.println("CHILD: " + individualInfoObjMap.get(child).getName());
//                }
//                
//                System.out.println("");
//            }
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
                            famInfo.getChildren().add(Integer.parseInt(temp));
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
                        indiInfo.setBirthDate(convertStrToDate(tagVal));
                        individualInfoObjMap.put(indi_ID, indiInfo);
                        break;
                    case "DH" :
                        indiInfo.setDeathDate(convertStrToDate(tagVal));
                        individualInfoObjMap.put(indi_ID, indiInfo);
                        break;
                    case "MR" :
                    	famInfo.setMarriageDate(convertStrToDate(tagVal));
                    	familyInfoObjMap.put(fam_ID, famInfo);
                        break;
                    case "DR" :
                    	famInfo.setDivorceDate(convertStrToDate(tagVal));
                    	familyInfoObjMap.put(fam_ID, famInfo);
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
    
    private static Date convertStrToDate(String date) {
    	String dateVal[] = date.split(" ", 3);
    	String formattedDateStr = dateVal[2] + "/" + monthMap.get(dateVal[1]) + "/" + dateVal[0];
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
	    Date utilDate = null;
	    
	    try {
			utilDate = formatter.parse(formattedDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    
	    return utilDate;
	}
    
    private static void UserStoryMethods() {
    	SavioFunction.deathBeforeBirth(individualInfoObjMap); // User Story 01
        SavioFunction.marriageBeforeBirth(individualInfoObjMap, familyInfoObjMap); // User Story 02
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