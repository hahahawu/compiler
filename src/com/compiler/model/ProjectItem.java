package com.compiler.model;

public class ProjectItem {
    private String left;
    private String right;
    private int location;

    public ProjectItem(String left, String right, int location) {
        this.left = left;
        this.right = right;
        this.location = location;
    }


    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getNextSymbol(){
        int i = location;
        boolean flag = false;
        while (i<right.length()){
            if (right.charAt(i) == '`') return right.substring(location,i);
            else {
                flag = true;
                i++;
            }
        }
        return flag ? right.charAt(location)+"":"";
    }

    public boolean atLast(){
        return location == right.length();
    }

    @Override
    public String toString() {
        return left+" -> "+right+" $ : "+location;
    }

    @Override
    public boolean equals(Object obj) {
        ProjectItem projectItem = (ProjectItem) obj;
        return left.equalsIgnoreCase(projectItem.left )&& right.equalsIgnoreCase(projectItem.right)&& location == projectItem.location;
    }

    @Override
    public int hashCode() {
        return left.hashCode()*27 + right.hashCode()*11 + ((Integer)location).hashCode()*19;
    }
}
