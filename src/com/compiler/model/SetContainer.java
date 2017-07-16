package com.compiler.model;

import java.util.HashSet;

public class SetContainer {
    private HashSet<ProjectItem> hashSet;

    public HashSet<ProjectItem> getHashSet() {
        return hashSet;
    }

    public void setHashSet(HashSet<ProjectItem> hashSet) {
        this.hashSet = hashSet;
    }

    public SetContainer(HashSet<ProjectItem> hashSet) {
        this.hashSet = hashSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SetContainer that = (SetContainer) o;

        for (ProjectItem projectItem : that.hashSet){
            if (!this.hashSet.contains(projectItem)) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hashcode = 0;
        for (ProjectItem projectItem : hashSet){
            hashcode += projectItem.hashCode()*11;
        }
        return hashcode;
    }
}
