package cs555_tm5_project;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Savio Dcruz
 */
public class Main {
    private static HashMap<Integer, IndividualInfo> individualInfoObjMap = new HashMap<Integer, IndividualInfo>();
    private static HashMap<Integer, FamilyInfo> familyInfoObjMap = new HashMap<Integer, FamilyInfo>();
    
    public static void main(String[] args) {
        try {
            File dir = new File(".");
            
            //Clear the contents of output.txt
            String outputFileLoc = dir.getCanonicalPath() + File.separator + "output.txt";
            FileWriter fw = new FileWriter(outputFileLoc);
            fw.close();
            
            //Read from gedcom.ged file
            File fin = new File(dir.getCanonicalPath() + File.separator + "Jiabin_Wang_P01.ged");
            readFile(fin);
            
            System.out.println("***** Displaying Individual Information *****\n");
            
            Object[] indiKey = individualInfoObjMap.keySet().toArray();
            Arrays.sort(indiKey);
            for(Object retval: indiKey) {
                String id =  "@I" + retval.toString() + "@";

                System.out.println(id);
                System.out.println("NAME: " + individualInfoObjMap.get(retval).getName());
//                System.out.println("SEX: " + individualInfoObjMap.get(id).getSex());
//                System.out.println("BIRTH: " + individualInfoObjMap.get(id).getBirthDate());
//                System.out.println("DEATH: " + individualInfoObjMap.get(id).getDeathDate());
//                System.out.println("MARRIAGE: " + individualInfoObjMap.get(id).getMarriageDate());
//                System.out.println("DIVORCE: " + individualInfoObjMap.get(id).getDivorceDate());
//                System.out.println("");
            }
            
            System.out.println("\n***** Displaying Family Information *****\n");
            
            Object[] famKey = familyInfoObjMap.keySet().toArray();
            Arrays.sort(famKey);
            for(Object retval: famKey) {
                String id =  "@F" + retval.toString() + "@";
                
                System.out.println(id);
                System.out.println("HUSBAND: " + individualInfoObjMap.get(familyInfoObjMap.get(retval).getHusband()).getName());
                System.out.println("WIFE: " + individualInfoObjMap.get(familyInfoObjMap.get(retval).getWife()).getName());
                
//                for(String child: familyInfoObjMap.get(id).getChildren()) {
//                    System.out.println("CHILD: " + individualInfoObjMap.get(child).getName());
//                }
                
                System.out.println("");
            }
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
                        case "MARR" :
                            dateCode = "MR";
                            break; 
                        case "DIV" :
                            dateCode = "DR";
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
                        default :
                            break;
                    }
                }
            }
            
            if(!dateCode.isEmpty() && tag.toUpperCase().equals("DATE")) {
                switch(dateCode)
                {
                    case "BH" :
                        indiInfo.setBirthDate(tagVal);
                        individualInfoObjMap.put(indi_ID, indiInfo);
                        break;
                    case "DH" :
                        indiInfo.setDeathDate(tagVal);
                        individualInfoObjMap.put(indi_ID, indiInfo);
                        break;
                    case "MR" :
                        indiInfo.setMarriageDate(tagVal);
                        individualInfoObjMap.put(indi_ID, indiInfo);
                        break;
                    case "DR" :
                        indiInfo.setDivorceDate(tagVal);
                        individualInfoObjMap.put(indi_ID, indiInfo);
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
}