package com.example.ceghospital;

public class Doctor {
    String name;
    String specilication;
   String age;

    String phnumber;
    String email;

    public Doctor(String name, String specilication, String age,  String phnumber, String email) {
        this.name = name;
        this.specilication = specilication;
        this.age = age;
        this.phnumber = phnumber;
        this.email = email;
    }
    public Doctor()
    {

    }

    @Override
    public String toString() {
        return "Doctor{" +
                "name='" + name + '\'' +
                ", specilication='" + specilication + '\'' +
                ", age=" + age +


                ", phnumber='" + phnumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecilication() {
        return specilication;
    }

    public void setSpecilication(String specilication) {
        this.specilication = specilication;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }



    public String getPhnumber() {
        return phnumber;
    }

    public void setPhnumber(String phnumber) {
        this.phnumber = phnumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
