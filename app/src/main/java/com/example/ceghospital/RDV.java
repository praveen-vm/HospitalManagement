package com.example.ceghospital;


public class RDV {

    private String appointmentId ;
    private String FirstName;
    private String LastName;
    private String dateRDV ;
    private String hour;


    @Override
    public String toString() {
        return "RDV{" +
                "age='" + age + '\'' +
                '}';
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    private String age;
    private String email ;
    private String tel ;
    private String detail;
    private String issue,doc="",spec="";
    private boolean status=false;
    public RDV(){}


    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public RDV(String appointmentId, String nom, String prenom, String dateRDV, String heurRDV, String email, String tel, String is, String det, String age) {

        this.appointmentId = appointmentId;
        this.FirstName = nom;
        this.LastName = prenom;
        this.dateRDV = dateRDV;
        this.hour = heurRDV;
        this.email = email;
        this.tel = tel;
        this.issue=is;
        this.detail=det;
        this.age=age;

    }

    public RDV(RDV d,String doc, String spec, boolean status) {
        this.appointmentId = d.appointmentId;
        this.FirstName = d.FirstName;
        this.LastName = d.getLastName();
        this.dateRDV = d.dateRDV;
        this.hour = d.hour;
        this.email = d.email;
        this.tel = d.tel;
        this.issue=d.issue;
        this.detail=d.detail;
        this.doc = doc;
        this.spec = spec;
        this.status = status;
        this.age=d.age;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getFirstName() {
        return FirstName;
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

    public String getDateRDV() {
        return dateRDV;
    }

    public void setDateRDV(String dateRDV) {
        this.dateRDV = dateRDV;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
