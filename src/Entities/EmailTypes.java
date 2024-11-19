package Entities;

    //Enum type: Clave-valor. No es una clase, no se instancia. Para acceder simplemente se usa EmailTypes.
    //Se usa en el modelo de db para saber el estado de cada mail y para no comparar strings, sino valores numericos fijos.
    public enum EmailTypes {
        Outbox, //Clave: Outbox, Valor:0
        Sent, //Clave: Sent, Valor:1
        Recieved, //Clave: Recieved, Valor:2
        Pending //Clave: Pending, Valor:3
    }
