package com.example.webapplicationwithspring;

public class Person {
    public static String email;
    public static String token;
    public static String firstName;
    public static String lastName;
    public static String gender;

    public static void deleteAll(){
        email = "";
        token = "";
        firstName = "";
        lastName = "";
        gender = "";
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Person.email = email;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Person.token = token;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        Person.firstName = firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        Person.lastName = lastName;
    }

    public static String getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        Person.gender = gender;
    }
}
