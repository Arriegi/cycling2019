package eus.jarriaga.cycling.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExcelExpenseExportterAux implements Comparable<ExcelExpenseExportterAux> {
    private String name;

    private Float totalAmmount;

    //private List<Object> list;
    private List<ExcelExpenseExportterAux> list;

    public ExcelExpenseExportterAux() {
        this.totalAmmount = 0f;
        //this.list = new ArrayList<Object>();
        this.list = new ArrayList<ExcelExpenseExportterAux>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getTotalAmmount() {
        return totalAmmount;
    }

    public void setTotalAmmount(Float totalAmmount) {
        this.totalAmmount = totalAmmount;
    }
    public List<ExcelExpenseExportterAux> getList() {
        return list;
    }

    public void setList(List<ExcelExpenseExportterAux> list) {
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
        ExcelExpenseExportterAux that = (ExcelExpenseExportterAux) o;
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
    public int compareTo(ExcelExpenseExportterAux o) {
        return this.getName().compareTo(o.getName());
    }
}
