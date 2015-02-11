package cs555_tm5_project;

import java.util.ArrayList;

/**
 *
 * @author savio_000
 */
public class FamilyInfo {
    private int husband;
    private int wife;
    private ArrayList<Integer> children = new ArrayList<Integer>();

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
}