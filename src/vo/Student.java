package com.srk.vo;

public class Student {
    private int id;
    private String name;
    private String address;
    private int age;
    private String qualification;
    private double percentage;
    private int yearPassed;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }

    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; }

    public int getYearPassed() { return yearPassed; }
    public void setYearPassed(int yearPassed) { this.yearPassed = yearPassed; }
}
