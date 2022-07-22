package com.example.ceghospital;



public class Patient {

    private String patientId ;
    private String FirstName;
    private String LastName;
    private String Address;
    private String Phone;
    private String email;
    private String Sex;
    private String age;
    private String cin ;

    public Patient() {
        this.patientId = "patientId" ;
        this.FirstName = "nom";
        this.LastName = "prenom";
        this.Address = "adress";
        this.Phone = "tel";
        this.email = "email";
        this.age = "age";
        this.cin = "cin";
    }


    public Patient(String patientId, String nom, String prenom, String adress, String tel, String email, String age, String cin) {

        this.patientId = patientId ;
        this.FirstName = nom;
        this.LastName = prenom;
        this.Address = adress;
        this.Phone = tel;
        this.email = email;
        this.age = age;
        this.cin = cin;

    }

    public String getPatientid() {
        return patientId;
    }

    public void setPatientid(String patientId) {
        this.patientId = patientId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        this.Sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
