package org.gravityclasses.gavitytestseries;

public class User {
    String firstName,lastName,name,picPath,phone,email,gender,dob;
    int classId,studentId,packageId;
    ClassInfo ci;

    public User(ClassInfo ci,String name,String picPath,String phone,String email,String gender,String dob,int packageId,int classId,int studentId)
    {
        this.ci=ci;
        this.name=name;
        this.picPath=picPath;
        this.phone=phone;
        this.email=email;
        this.gender=gender;
        this.dob=dob;
        this.packageId=packageId;
        this.classId=classId;
        this.studentId=studentId;
    }

}
