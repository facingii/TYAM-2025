package mx.uv.fiee.iinf.tyam.recyclerviewdemo;

import android.content.Context;

import androidx.appcompat.content.res.AppCompatResources;

import java.util.List;

class Utils {

    static List<Employee> GetEmployeeList (Context context)
    {
        var e1 = new Employee (
        "Employee 1",
        "Description 1",
        AppCompatResources.getDrawable (context, R.drawable.pic));

        var e2 = new Employee (
        "Employee 2",
        "Description 2",
        AppCompatResources.getDrawable (context, R.drawable.pic));

        var e3 = new Employee (
        "Employee 3",
        "Description 3",
        AppCompatResources.getDrawable (context, R.drawable.pic));

        var e4 = new Employee (
        "Employee 4",
        "Description 4",
        AppCompatResources.getDrawable (context, R.drawable.pic));

        var e5 = new Employee (
        "Employee 5",
        "Description 5",
        AppCompatResources.getDrawable (context, R.drawable.pic));

        var e6 = new Employee (
        "Employee 6",
        "Description 6",
        AppCompatResources.getDrawable (context, R.drawable.pic));

        var e7 = new Employee (
        "Employee 7",
        "Description 7",
        AppCompatResources.getDrawable (context, R.drawable.pic));

        var e8 = new Employee (
        "Employee 8",
        "Description 8",
        AppCompatResources.getDrawable (context, R.drawable.pic));

        var e9 = new Employee (
        "Employee 9",
        "Description 9",
        AppCompatResources.getDrawable (context, R.drawable.pic));

        var e10 = new Employee (
        "Employee 10",
        "Description 10",
        AppCompatResources.getDrawable (context, R.drawable.pic));

        return List.of (e1, e2, e3, e4, e5, e6, e7, e8, e9, e10);
    }

}
