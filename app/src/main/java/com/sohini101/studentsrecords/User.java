package com.sohini101.studentsrecords;

/**
 * Created by SOHINI PAL on 26-11-2017.
 */

public class User {
    private int id;
    private String name;
    private String password;
    private String confirmPass;
    private String userques;
    private String userans;
/*public User(String name,String password,String userques,String userans){
    this.id=id;
    this.name=name;
    this.password=password;
    this.userques=userques;
    this.userans=userans;
}*/
public int getId(){
    return id;
}
public void setId(int id){
    this.id=id;
}
public String getName(){
    return name;
}
public void setName(String name){
    this.name=name;
}
public String getPassword(){
    return password;
}
public void setPassword(String password){
    this.password=password;
}
public String getConfirmPass(){
    return confirmPass;
}
public void setConfirmPass(String confirmPass){
    this.confirmPass=confirmPass;
}
public String getUserques(){
    return userques;
}
public void setUserques(String userques){
    this.userques=userques;
}
public String getUserans(){
    return userans;
}
public void setUserans(String userans){
    this.userans=userans;
}
}