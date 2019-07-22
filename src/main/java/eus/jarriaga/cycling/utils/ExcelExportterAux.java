package eus.jarriaga.cycling.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExcelExportterAux implements Comparable< ExcelExportterAux > {
    private String name;

    private Float totalSeconds;

    //private List<Object> list;
    private List<ExcelExportterAux> list;

    public ExcelExportterAux() {
        this.totalSeconds = 0f;
        //this.list = new ArrayList<Object>();
        this.list = new ArrayList<ExcelExportterAux>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getTotalSeconds() {
        return totalSeconds;
    }

    public void setTotalSeconds(Float totalSeconds) {
        this.totalSeconds = totalSeconds;
    }
    public List<ExcelExportterAux> getList() {
        return list;
    }

    public void setList(List<ExcelExportterAux> list) {
        this.list = list;
    }

    /*
    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExcelExportterAux that = (ExcelExportterAux) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "ExcelExportterAux{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(ExcelExportterAux o) {
        return this.getName().compareTo(o.getName());
    }
}
