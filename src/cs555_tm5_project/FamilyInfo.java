package cs555_tm5_project;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author savio_000
 */
public class FamilyInfo {
    private int husband;
    private int wife;
    private ArrayList<Integer> children = new ArrayList<Integer>();
    private Date marriageDate = null;
    private Date divorceDate = null;

    public int getHusband() {
        return husband;
    }

    public void setHusband(int husband) {
        this.husband = husband;
    }

    public int getWife() {
        return wife;
    }

    public void setWife(int wife) {
        this.wife = wife;
    }

    public ArrayList<Integer> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Integer> children) {
        this.children = children;
    }

	public Date getMarriageDate() {
		return marriageDate;
	}

	public void setMarriageDate(Date marriageDate) {
		this.marriageDate = marriageDate;
	}

	public Date getDivorceDate() {
		return divorceDate;
	}

	public void setDivorceDate(Date divorceDate) {
		this.divorceDate = divorceDate;
	}
}