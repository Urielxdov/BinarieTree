package com.mycompany.arboles3;

/**
 * En este metodo damos la platilla para la creacion de nodos en los arboles
 * teniendo la idea de que se un arbol binario, debera trabajar con indices
 * para ser capaz de realizar operaciones como busqueda, eliminacion, entre otros
 * @author Zomber
 */
public class NodoArbol <T> {
    private NodoArbol derecha, izquierda;
    private T dato;
    
    
    public NodoArbol(T dato) {
        this.derecha = null;
        this.izquierda = null;
        this.dato = dato;
    }

    public NodoArbol(NodoArbol<T> derecha, NodoArbol<T> izquierda, T dato) {
        this.derecha = derecha;
        this.izquierda = izquierda;
        this.dato = dato;
    }

    public NodoArbol(T dato, NodoArbol<T> derecha) {
        this(derecha, null, dato);
    }

    public NodoArbol(NodoArbol<T> izquierda, T dato) {
        this(null, izquierda, dato);
    }
    public NodoArbol getDerecha() {
        return derecha;
    }

    public void setDerecha(NodoArbol derecha) {
        this.derecha = derecha;
    }

    public NodoArbol getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(NodoArbol izquierda) {
        this.izquierda = izquierda;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }  
    
    
}
