package com.mycompany.arboles3;

public class ArbolAVL<T extends Integer> {

    private NodoArbol<T> raiz;

    /**
     * Da la raiz
     * 
     * @return raiz del arbol
     */
    public NodoArbol<T> getRaiz() {
        return raiz;
    }

    public void add(T i) {
        if (i == null) {
            throw new IllegalArgumentException("No se permiten valores nulos o vacios");
        }
        if (raiz == null) {
            raiz = new NodoArbol<T>(i);
        } else {
            add(raiz, i);
        }
    }

    public void add(NodoArbol<T> r, T i) {
        NodoArbol<T> nuevo = new NodoArbol<T>(i);
        // agregar un nodo al arbol
        if (i < r.dato) {// va del lado izquierdo
            if (r.izq == null) {
                r.izq = nuevo;
                // conetar nuevo nodo con su padre
                r.izq.padre = r;
                balancear(r.izq);
            } else {
                add(r.izq, i);
            }
        }
        if (i > r.dato) {// va del lado derecho
            if (r.der == null) {
                r.der = nuevo;
                // conetar nuevo nodo con su padre
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
            System.out.println(r.dato);
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

    // Solo para debugear, eliminalo al terminar
    public int raiz() {
        return raiz.dato;
    }

    private int size(NodoArbol<T> r) {
        if (r != null) {
            return 1 + size(r.izq) + size(r.der);
        } else {
            return 0;
        }
    }

    /**
     * Revisa el factor de equilibrio de un nodo padre, FE = HijosIzquierda -
     * HijosDerecha
     * 
     * @param r es el nodo a revisar
     * @return el factor de equilibrio (-2,-1,0,1,2); 0= perfecto equilibrio
     */
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
        switch (numeroDeHijos(nodo)) {
            case 0:
                eliminarNodoHoja(nodo);
                break;
            case 1:
                eliminarConUnHijo(nodo);
                break;
            case 2:
                eliminarConDosHijos(nodo);
                break;
        }
        balancearRecorrido(nodo); // Posible error
    }

    /**
     * Evalua el numero de hijos de un nodo
     * 
     * @param nodo Nodo a evaluar
     * @return Numero de hijos
     */
    private int numeroDeHijos(NodoArbol<T> nodo) {
        int hijos = 0;
        hijos = nodo.der != null ? hijos + 1 : hijos;
        hijos = nodo.izq != null ? hijos + 1 : hijos;
        return hijos;
    }

    private void eliminarNodoHoja(NodoArbol<T> nodo) { // Funciona
        if (nodo.padre == null) { // En este caso el nodo era la raiz
            nodo = null;
            this.raiz = null;
            return;
        }
        if (nodo.padre.dato < nodo.dato) { // Valor padre menor que valor hijo
            nodo.padre.der = null;
        } else { // Valor padre mayor que valor hijo
            nodo.padre.izq = null;
        }
        nodo.padre = null; // Eliminamos la referencia al padre
        nodo = null; // Eliminamos el nodo
    } // eliminarNodoHoja

    // Posiblemente el error se debe a que usas una copia, intenta no usandola
    private void eliminarConUnHijo(NodoArbol<T> nodoPadre) {
        NodoArbol<T> hijo; // Variable temporal de hijo que remplaza al padre
        if (nodoPadre.der != null) { // El nodo padre posee hijo derecho?
            hijo = nodoPadre.der; // Tomamos la referencia del hijo
            hijo.padre = nodoPadre.padre; // Actualizamos el padre del hijo hacia el abuelo

            if (nodoPadre.padre != null) { // El nodo padre posee padre
                if (nodoPadre.padre.izq == nodoPadre) { // Validar la rama en la que se encuentra el padre
                    nodoPadre.padre.izq = hijo; // Actualizamos el hijo del abuelo en la izquierda
                } else {
                    nodoPadre.padre.der = hijo; // Actualizamos el hijo del abuelo en la derecha
                }
            } else { // Si no posee padre entonces es la raiz
                this.raiz = hijo; // Redefinimos la raiz
            }
        } else { // El nodo padre posee el hijo en el lado izquierdo
            hijo = nodoPadre.izq; // Copia del hijo
            hijo.padre = nodoPadre.padre; // Actualizamos la referencia padre hacia el abuelo

            if (nodoPadre.padre != null) { // Se posee abuelo
                if (nodoPadre.padre.izq == nodoPadre) { // Validamos la ubicacion del padre
                    nodoPadre.padre.izq = hijo; // Actualizamos la rama izquierda del abuelo
                } else {
                    nodoPadre.padre.der = hijo; // Actualizamos la rama derecha del abuelo
                }
            } else { // Si no posee padre es la raiz
                this.raiz = hijo; // Redefinimos la raiz
            }
        }

        //Eliminamos las ultimas referencias del padre
        nodoPadre.padre = null;
        nodoPadre = null;
    } // eliminarConUnHijo

    private void eliminarRaiz(NodoArbol<T> nuevoPadre) { 
        if (nuevoPadre != null) {
            nuevoPadre.padre = null;
            nuevoPadre.izq = raiz.izq;
            nuevoPadre.der = raiz.der;
            raiz = nuevoPadre;
        } else {
            raiz = null; // No hay mas elementos en el arbol
        }
    }

    /**
     * Eliminamos el no
     * 
     * @param nodoPadre
     */
    private void eliminarConDosHijos(NodoArbol<T> nodoPadre) {
        NodoArbol<T> nuevoPadre = quitarMayor(nodoPadre.izq); // Este sera el sustituto del padre
        if (nodoPadre.padre != null) { // Si posee padre no es la raiz
            // Actualizando referrencias del nuevoPadre
            nuevoPadre.der = nodoPadre.der;
            nuevoPadre.izq = nodoPadre.izq;
            nuevoPadre.padre = nodoPadre.padre;
            // Actualizando nodos que hacian referencia al viejo padre para el nuevo
            nodoPadre.izq.padre = nodoPadre;
            nodoPadre.der.padre = nodoPadre;
            // Actualizamos la referencia del padre del antiguo padre
            if (nodoPadre.padre.dato < nodoPadre.dato) {
                nodoPadre.padre.der = nuevoPadre;
            } else {
                nodoPadre.padre.izq = nuevoPadre;
            }
            // Eliminando al viejo padre
            nodoPadre = null;
        } else { // No posee padre por ende es la raiz
            eliminarRaiz( nuevoPadre ); // Actualizamos con el caso especial
        }
        balancearRecorrido(nuevoPadre.izq); // Se balancea el rastro por el cual el se tomo el sucesor del padre
    }

    private NodoArbol<T> quitarMayor(NodoArbol<T> nodo) { // No estas teniendo en cuenta la rama izquierda
        if (nodo.der == null) { // Si no existe un hijo a la derecha
            nodo.padre.der = nodo.izq; // En caso de que el hijo posea hijos
            if (nodo.padre.der != null)
                nodo.izq.padre = nodo.padre; // Esto maneja posibles hojas izquierdas en el nodo a intercambiar
            // balancearRecorrido(nodo.izq); // Balanceamos lo que hayamos cambiado
            nodo.padre = null; // Eliminamos el padre del nodo
            return nodo; // Nodo sin referencias
        }
        return quitarMayor(nodo.der); // Se continua recorriendo hacia la derecha
    }

    private void balancearRecorrido(NodoArbol<T> nodo) { // Se detecto el error
        NodoArbol<T> NodoArbolctual = nodo; // Este nodo se actualizara
        while (NodoArbolctual != null) {
            balancear(NodoArbolctual);
            NodoArbolctual = NodoArbolctual.padre;
        }
    }



































    // Rotaciones

    /**
     * El nodo recibido es tratado como un arbol independientemente de donde se
     * encuentre
     * En este caso el nodo hijo de la rama izquierda de la raiz subira a la raiz
     * dejandole sus hijos
     * de la rama derecha al padre en la rama izquierda, posteriormente el hijo que
     * subio deja al padre
     * como hijo de la rama derecha. Posteriomente se actualiza cada nodo afectado
     * con nuevas referencias
     * 
     * @param nodo raiz del arbol
     */
    private void rotacionII(NodoArbol<T> nodo) { // Funciona
        NodoArbol<T> nuevoPadre = nodo.izq; // Este subira a la raiz
        nodo.izq = nuevoPadre.der; // Se agregan a ña antigua raiz los hijos rama derecha de la nueva raiz

        if (nodo.izq != null)
            nodo.izq.padre = nodo; // Se actualiza el padre de la rama izquierda

        nuevoPadre.der = nodo; // Se coloca la antigua raiz como hijo de la nueva raiz en la rama derecha
        nuevoPadre.padre = nodo.padre; // Se actualiza el padre de la nueva raiz
        nodo.padre = nuevoPadre; // Se actualiza el padre de la antigua raiz

        if (nuevoPadre.padre != null) { // Si la nueva raiz tiene padre entonces se ajusta su posicion
            if (nuevoPadre.dato < nuevoPadre.padre.dato)
                nuevoPadre.padre.izq = nuevoPadre;
            else
                nuevoPadre.padre.der = nuevoPadre;
        } else
            this.raiz = nuevoPadre; // La raiz es la raiz global del arbol
    }

    /**
     * El nodo rrecibido es tratado como abol independientemenre de donde se
     * encuentre
     * En este caso el nodo hijo de la rama derecha de la raiz subira a la raiz
     * dejandole sus hijos
     * de la rama izquierda al padre en la rama derecha, posteriormente el hijo que
     * subio deja al padrre
     * como hijo de la rrama derecha. Posteriormente se actualiza cada nodo afectado
     * con nuevas referencias
     * 
     * @param nodo raiz del arbol
     */
    private void rotacionDD(NodoArbol<T> nodo) { // Funciona
        NodoArbol<T> nuevoPadre = nodo.der; // Este subira a la raiz
        nodo.der = nuevoPadre.izq; // Se agregan a la antigua raiz los hijos rama izquierda de la nueva raiz

        if (nodo.der != null)
            nodo.der.padre = nodo; // Se actualiza el padre de la rama derecha

        nuevoPadre.izq = nodo; // Se coloca la antigua raiz como hijo de la nueva raiz en la rama derecha
        nuevoPadre.padre = nodo.padre; // Se actualiz el padre de la nueva raiz
        nodo.padre = nuevoPadre; // Se actualiza el padre de la antigua raiz

        if (nuevoPadre.padre != null) { // Si la raiz tiene padre entonces se ajusta su posicion
            if (nuevoPadre.dato < nuevoPadre.padre.dato)
                nuevoPadre.padre.izq = nuevoPadre;
            else
                nuevoPadre.padre.der = nuevoPadre;
        } else
            this.raiz = nuevoPadre; // La raiz es la raiz global del abol
    }

    /**
     * Todo nodo recibido es tratado como un arbol
     * En este caso para lograr la rotacion al hijo izquierdo de la raiz le
     * aplicamos una rotacion
     * derecha-derecha, despues de esto el hijo queda balanceado y al padre junto al
     * hijo o nuevo hijo
     * le aplicamos una rotacion izquierda-izquierda
     * 
     * @param nodo raiz del arbol
     */
    private void rotacionID(NodoArbol<T> nodo) { // Funciona
        rotacionDD(nodo.izq);
        rotacionII(nodo.izq);
    }

    /**
     * Todo nodo recibido es tratado como un arbol
     * En este caso para lograr la rotacion al hijo derecho de la raiz le aplicamos
     * una rotacion
     * izquierda-izquierda, despues de esto el hijo queda balanceado y al padre
     * junto al hijo o nuevo hijo
     * le aplicamos una rotacion derecha-derecha
     * 
     * @param nodo
     */
    private void rotacionDI(NodoArbol<T> nodo) { // Funciona
        rotacionII(nodo.der);
        rotacionDD(nodo.der);
    }

    // Metodos de Impresion
    @Override
    public String toString() {
        return this.inOrden(raiz);
    }

    private String inOrden(NodoArbol<T> r) {
        if (r != null) {
            return inOrden(r.izq) + r + inOrden(r.der);
        } else {
            return "";
        }
    }

    private String preOrden(NodoArbol<T> r) {
        if (r != null) {
            return r + preOrden(r.izq) + preOrden(r.der);
        } else {
            return "";
        }
    }

    private String postOrden(NodoArbol<T> r) {
        if (r != null) {
            return postOrden(r.izq) + postOrden(r.der) + r;
        } else {
            return "";
        }
    }
}