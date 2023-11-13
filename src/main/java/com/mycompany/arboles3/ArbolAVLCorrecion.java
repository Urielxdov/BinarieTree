package com.mycompany.arboles3;

public class ArbolAVLCorrecion <T extends Integer> {
    private NodoArbol<T> raiz;

    public NodoArbol<T> getRaiz(){
        return this.raiz;
    }

    public void add(T i) {
        if(i == null) {
            throw new IllegalArgumentException("No se puede ingresar valores nulos o vacios");
        }
        if(raiz == null)raiz = new NodoArbol<T>(i);
        else add(this.raiz, i);
    }

    private void add (NodoArbol<T> r, T i) {
        NodoArbol<T> nuevo = new NodoArbol<T>(i);

        if(i < r.dato) {
            if(r.izq == null) {
                r.izq = nuevo;
                // conectar nuevo nodo con su padre
                r.izq.padre = r;
                balancear(r.izq);
            } else {
                add(r.izq, i);
            }
        }
        if(i > r.dato) {
            if(r.der == null) {
                r.der = nuevo;
                // conectar nuevo nodo con su padre
                r.der.padre = r;
                balancear(r.der);
            } else {
                add(r.der, i);
            }
        }
    }

    public int altura() {
        if (raiz != null) {
            return 1 + Math.max(altura(raiz.izq), altura(raiz.der));
        } else {
            return 0;
        }
    }

    private int altura(NodoArbol<T> r) {
        if (r != null) {
            return 1 + Math.max(altura(r.izq), altura(r.der));
        } else {
            return 0;
        }
    }

    public int size() {
        if (raiz != null) {
            return 1 + size(raiz.izq) + size(raiz.der);
        } else {
            return 0;
        }
    }

    private int size(NodoArbol<T> r) {
        if (r != null) {
            return 1 + size(r.izq) + size(r.der);
        } else {
            return 0;
        }
    }

    public int FE(NodoArbol<T> r) {
        if (r != null) {
            return altura(r.izq) - altura(r.der);
        } else {
            return 0;
        }
    }

    private void balancear(NodoArbol<T> r) {
        while (r != null) {

            if (FE(r) == -2) {
                // Rotación derecha-derecha (DD)
                if (FE(r.der) == 1) {
                    rotacionDI(r);
                } else {
                    rotacionDD(r);
                }
            } else if (FE(r) == 2) {
                // Rotación izquierda-izquierda (LL)
                if (FE(r.izq) == -1) {
                    rotacionID(r);
                } else {
                    rotacionII(r);
                }
            }

            r = r.padre;
        }
    }

    public void eleminarNodo(T valor) {
        NodoArbol<T> nodoArbolEliminar = buscarNodo(valor, this.raiz);
        if(nodoArbolEliminar == null) {
            System.out.println("Nodo no encontrado");
            return;
        }
        manejarEliminaciones(nodoArbolEliminar);
    }

    public NodoArbol<T> buscarNodo(T valor) {
        NodoArbol<T> nodoRetorno = buscarNodo(valor, this.raiz);
        return nodoRetorno;
    }

    private NodoArbol<T> buscarNodo(T valor, NodoArbol<T> nodo) {
        if (nodo == null)
            return null;
        if (valor == nodo.dato) {
            return nodo;
        }
        NodoArbol<T> nodoEncontrado;
        if (valor < nodo.dato) {
            nodoEncontrado = buscarNodo(valor, nodo.izq);
        } else {
            nodoEncontrado = buscarNodo(valor, nodo.der);
        }
        return nodoEncontrado;
    }

    private void manejarEliminaciones(NodoArbol<T> nodo) {
        NodoArbol<T> nodoEliminado;
        switch (numeroDeHijos(nodo)) {
            case 0:
                nodoEliminado = eliminarNodoHoja(nodo);
                break;
            case 1:
                nodoEliminado = eliminarConUnHijo(nodo);
                break;
            case 2:
                nodoEliminado = eliminarConDosHijos(nodo);
                break;
        }
        balancearRecorrido(nodo); // Posible error
    }

    private int numeroDeHijos(NodoArbol<T> nodo) {
        int hijos = 0;
        hijos = nodo.der != null ? hijos + 1 : hijos;
        hijos = nodo.izq != null ? hijos + 1 : hijos;
        return hijos;
    }

    private NodoArbol<T> eliminarNodoHoja (NodoArbol<T> nodoHoja) {
        
    }

}
