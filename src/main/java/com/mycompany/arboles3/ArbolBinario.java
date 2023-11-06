package com.mycompany.arboles3;

public class ArbolBinario <T> {
    private NodoArbol<T> raiz = null;
    public ArbolBinario(){}
    
    public void add(T valor) {
        if (valor == null) {
            throw new IllegalArgumentException("El nodo requiere un valor");
        }

        if (raiz == null) {
            raiz = new NodoArbol(valor);
        } else {
            raiz = add(raiz, valor);
        }
    }

    private NodoArbol<T> add(NodoArbol<T> nodo, T valor) {
        if (nodo == null) {
            return new NodoArbol(valor);
        }

        if ((int) valor > (int) nodo.getDato()) {
            nodo.setDerecha(add(nodo.getDerecha(), valor));
        } else if ((int) valor < (int) nodo.getDato()) {
            nodo.setIzquierda(add(nodo.getIzquierda(), valor));
        } else {
            throw new IllegalArgumentException("El valor ya existe");
        }

        // Después de agregar un nodo, verificamos si es necesario realizar rotaciones.
        return manejarRotaciones(nodo);
    }


    private NodoArbol<T> manejarRotaciones (NodoArbol<T> nodoPadre) { // Checando los retornos
        int factorEquilibrioHijo;
        int factorEquilibrioPadre = factorDeEquilibrio(nodoPadre);
        NodoArbol<T> nuevoPadre = nodoPadre;

        if(factorEquilibrioPadre > 1) { // Desbalanceado por la izquierda
            factorEquilibrioHijo = factorDeEquilibrio(nodoPadre.getDerecha());
            if(factorEquilibrioHijo <= -1) { // Rotacion doble ID
                nuevoPadre = rotacionID(nodoPadre);
            } else { // Rotacion simple II
                nuevoPadre = rotacionII(nodoPadre);
            }
        }
        if(factorEquilibrioPadre < -1) { // Desblanceado por la derecha
            factorEquilibrioHijo = factorDeEquilibrio(nodoPadre.getIzquierda());
            if(factorEquilibrioHijo >= 1) { // Rotacion doble DI
                nuevoPadre = rotacionDI(nodoPadre);
            } else { // Rotacion simple DD
                nuevoPadre = rotacionDD(nodoPadre);
                System.out.println(nuevoPadre.getDato());
            }
        }        
        System.out.println("Nodo padre " + nuevoPadre.getDato());
        return nuevoPadre;
    }
    
    //Solo para depurar
    public NodoArbol<T> getRaiz (){
        return this.raiz;
    }
    

    private int factorDeEquilibrio(NodoArbol<T> raiz) { // Funciona, no le muevas
        if (raiz == null) return 0;
        int alturaIzquierda = nivelArbol(raiz.getIzquierda());
        int alturaDerecha = nivelArbol(raiz.getDerecha());
        return alturaIzquierda - alturaDerecha;
    }
    

    public int nivelArbol() { // Bucle infinito relacionado con el cambio de Raiz, posibles causas los metodos add o la devolucion del metodo para balancear
        return nivelArbol(this.raiz);
    }
    
    private int nivelArbol(NodoArbol<T> raiz) { // Funciona, no le muevas
        if(raiz == null) return 0;
        int nivelDerecha = nivelArbol(raiz.getDerecha());
        int nivelIzquierda = nivelArbol(raiz.getIzquierda());
        return Math.max(nivelIzquierda, nivelDerecha) + 1;
    }
    


    //Rotaciones AVL
    //Depuralos

    private NodoArbol<T> rotacionDD(NodoArbol<T> nodo) { // Funciona
        NodoArbol<T> nuevoPadre = nodo.getDerecha();
        nodo.setDerecha(nuevoPadre.getIzquierda());
        nuevoPadre.setIzquierda(nodo);
        
        return nuevoPadre;
    }

    private NodoArbol<T> rotacionII(NodoArbol<T> nodo) {
        NodoArbol<T> nuevoPadre = nodo.getIzquierda();
        nodo.setIzquierda(nuevoPadre.getDerecha());
        nuevoPadre.setDerecha(nodo);
        return nuevoPadre;
    }

    private NodoArbol<T> rotacionID(NodoArbol<T> nodo) {
        nodo.setIzquierda(rotacionDD(nodo.getIzquierda()));
        return rotacionII(nodo);
    }

    private NodoArbol<T> rotacionDI(NodoArbol<T> nodo) {
        nodo.setDerecha(rotacionII(nodo.getDerecha()));
        return rotacionDD(nodo);
    }
    
    @Override
    public String toString() {
        System.out.println(raiz.getDato());
        System.out.println(raiz.getDerecha().getDato());
        System.out.println(raiz.getIzquierda().getDato());

        return "";
    } // toString

    public void impresionInOrden () {
        impresionInOrden(raiz);
    } // impresionInOrden

    private void impresionInOrden(NodoArbol<T> raiz) {
        if(raiz == null) {
            return;
        }
        impresionInOrden(raiz.getIzquierda());
        System.out.println(raiz.getDato());
        impresionInOrden(raiz.getDerecha());
    } // impresionInOrden

    public void impresionPreOrden() {
        impresionPreOrden(raiz);
    } // impresionPreOrden

    private void impresionPreOrden (NodoArbol<T> raiz) {
        if(raiz == null) return;
        System.out.println(raiz.getDato());
        impresionPreOrden(raiz.getDerecha());
        impresionPreOrden(raiz.getIzquierda());
    } // impresionPreOrden

    public void impresionPostOrden () {
        impresionPostOrden(raiz);
    } // impresionPostOrden

    private void impresionPostOrden (NodoArbol<T> raiz) {
        if(raiz == null) return;
        impresionPostOrden(raiz.getDerecha());
        impresionPostOrden(raiz.getIzquierda());
        System.out.println(raiz.getDato());
    } // impresionPostOrden
}
