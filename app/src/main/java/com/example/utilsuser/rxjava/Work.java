package com.example.utilsuser.rxjava;

public class Work {
    private double salary;
    private float height;

    @Override
    public String toString() {
        return "Work{" +
                "salary=" + salary +
                ", height=" + height +
                '}';
    }

    public Work(double salary, float height) {
        this.salary = salary;
        this.height = height;
    }
}
