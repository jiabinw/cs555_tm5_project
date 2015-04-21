package cs555_tm5_project;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author savio_000
 */
public class IndividualInfo {
    private String name = "";
    private String sex = "";
    private Date birthDate = null;
    private Date deathDate = null;
    private int childOfFamPtr = -1;
    private ArrayList<Integer> spouseOfFamPtr = new ArrayList<Integer>();

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

    public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

	public int getChildOfFamPtr() {
        return childOfFamPtr;
    }

    public void setChildOfFamPtr(int childOfFamPtr) {
        this.childOfFamPtr = childOfFamPtr;
    }

    public ArrayList<Integer> getSpouseOfFamPtr() {
        return spouseOfFamPtr;
    }

    public void setSpouseOfFamPtr(int spouseOfFamPtr) {
        this.spouseOfFamPtr.add(spouseOfFamPtr);
    }
}