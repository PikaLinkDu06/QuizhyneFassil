package com.boyon_armando.quizhynefassil.classes;

// Classe servant à la récuperation des contacts de l'utilisateur
public class Contact {

    private String name ;
    private String phoneNumber ;

    public Contact(String name, String number) {
        this.name = name ;
        this.phoneNumber = number ;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String toString() {
        return name + " : " + phoneNumber ;
    }
}
