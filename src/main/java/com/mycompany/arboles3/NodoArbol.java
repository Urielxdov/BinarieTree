package com.mycompany.arboles3;

public class NodoArbol <T extends Integer>{
    public T dato;
    public NodoArbol<T> izq;
    public NodoArbol<T> der;
    NodoArbol<T> padre;        

    public NodoArbol(T dato) {
        this.dato = dato;
    }               

    @Override
    public String toString() {
        return " " + dato + " ";
    }
}
