package cs555_tm5_project;


/**
 *
 * @author savio_000
 */
public class IndividualInfo {
    private String name = "";
    private String sex = "";
    private String birthDate = "";
    private String deathDate = "";
    private String marriageDate = "";
    private String divorceDate = "";
    private int childOfFamPtr;
    private int spouseOfFamPtr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public String getMarriageDate() {
        return marriageDate;
    }

    public void setMarriageDate(String marriageDate) {
        this.marriageDate = marriageDate;
    }

    public String getDivorceDate() {
        return divorceDate;
    }

    public void setDivorceDate(String divorceDate) {
        this.divorceDate = divorceDate;
    }

    public int getChildOfFamPtr() {
        return childOfFamPtr;
    }

    public void setChildOfFamPtr(int childOfFamPtr) {
        this.childOfFamPtr = childOfFamPtr;
    }

    public int getSpouseOfFamPtr() {
        return spouseOfFamPtr;
    }

    public void setSpouseOfFamPtr(int spouseOfFamPtr) {
        this.spouseOfFamPtr = spouseOfFamPtr;
    }
    
    
}