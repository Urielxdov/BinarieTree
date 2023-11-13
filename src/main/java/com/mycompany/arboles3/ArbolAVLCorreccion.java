package com.mycompany.arboles3;

public class ArbolAVLCorreccion <T extends Integer> {
    private NodoArbol<T> raiz;

    /**
     * Obtener la raiz del arbol
     * @return raiz del arbol
     */
    public NodoArbol<T> getRaiz(){
        return this.raiz;
    }

    /**
     * Dado un valor numerico este se agregara a su lugar correspondiente en el arbol
     * @param i Valor numerico
     */
    public void add(T i) { 
        if(i == null) { 
            throw new IllegalArgumentException("No se puede ingresar valores nulos o vacios");
        }
        if(raiz == null)raiz = new NodoArbol<T>(i);
        else add(this.raiz, i);
    }

    /**
     * De forma recursiva se determina si el valor debe de ser insertado en la derecha o
     * izquierda dependiendo de si es mas grande o mas chico que la raiz. Se agrega el nuevo
     * valor una vez la raiz ya no contenga hijo en izquierda o derecha segun corresponda
     * @param r raiz del sub-arbol actual
     * @param i valor numerico a agregar
     */
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

    /**
     * Se cuentan las iteraciones necesarias de la rama izquierda y derecha para llegar a las hojas
     * y se compara cual es mas grande, izquierda o derecha, se retorna el mas grande
     * @return altura del arbol
     */
    public int altura() {
        if (raiz != null) {
            return 1 + Math.max(altura(raiz.izq), altura(raiz.der));
        } else {
            return 0;
        }
    }

    /**
     * Se divide en sub-arboles cada rama del arbol para contar cuantos hijos tienen en cada
     * rama y retornar el mas grande, esto para cada nodo y regresar hasta la raiz origen para comparar
     * que rama tuvo mas hijos o niveles
     * @param r raiz del sub-arbol
     * @return altura del sub-arbol
     */
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

    /**
     * Se obtiene el nivel de la rama derecha e izquierda, se le resta la derecha a la izquierda
     * y este resultado nos da el factor de equilibrio (FE)
     * @param r raiz del sub-arbol
     * @return factor de equilibrio del nodo raiz o r
     */
    public int FE(NodoArbol<T> r) {
        if (r != null) {
            return altura(r.izq) - altura(r.der);
        } else {
            return 0;
        }
    }

    /**
     * En funcion del factor de equilibrio se determina si el nodo r sufrira rotaciones y en caso de 
     * tener que sufrirlas, determina cual se ocupara para su caso conforme su factor de equilibrio.
     * Cuando el padre tiene un factor de equilibrio de 2 se decide que rotacion tendra en funcion del 
     * factor de equilibrio del hijo, si el hijo tiene un factor de equilibrio de 1 se le da una rotacion
     * derecha-derecha, por el contrario si posee -1 se le da rotacion derecha-izquierda.
     * Cuando el padre tiene un factor de equilibrio de -2 se decide que rotacion tendra en funcion del
     * factor de equilibrio del hijo, si el hijo tiene un factor de equilibrio de -1 se le da izquierda-izquierda,
     * por el contrario si posee un factor de equilibrio de 1 se le da una rotacion izquierda-derecha
     * @param r nodo raiz del sub-arbol
     */
    private void balancear(NodoArbol<T> r) {
        while (r != null) {

            if (FE(r) == -2) {
                if (FE(r.der) == 1) { // Rotación derecha-izquierda (DI)
                    rotacionDI(r);
                } else { // Rotacion derecha-derecha(DD)
                    rotacionDD(r);
                }
            } else if (FE(r) == 2) {
                if (FE(r.izq) == -1) { // Rotacion izquierda-derecha (ID)
                    rotacionID(r);
                } else { // Rotación izquierda-izquierda (II)
                    rotacionII(r);
                }
            }

            r = r.padre;
        }
    }

    /**
     * Dado un valor este se comprueba que exista, en caso de existir se comienza con su analisis para
     * saber como tratar la eliminacion de este
     * @param valor valor del nodo a eliminar
     */
    public void eleminarNodo(T valor) {
        NodoArbol<T> nodoArbolEliminar = buscarNodo(valor, this.raiz); // Pretende encontrar el nodo
        if(nodoArbolEliminar == null) { // Si no existe el nodo, lo informamos y detenemos el proceso
            System.out.println("Nodo no encontrado"); 
            return;
        }
        manejarEliminaciones(nodoArbolEliminar); // Maneja como debe de ser tratada la eliminacion del nodo
    }

    /**
     * Se compara desde la raiz que el valor recibido sea igual al valor de algun nodo para poder retornarlo
     * @param valor valor numerico del nodo
     * @return nodo correspondiente
     */
    public NodoArbol<T> buscarNodo(T valor) { 
        NodoArbol<T> nodoRetorno = buscarNodo(valor, this.raiz); // Se envia una llamada al metodo buscarNodo desde la raiz
        return nodoRetorno; 
    }

    /**
     * Se compara si el nodo actual es el correspondiente, de lo contrario de evalua si es menor o mayor al
     * nodo actual en el stack para decir si mandar en la siguiente llamada nodo que sigue en la parte
     * derecha o izquierda para continuar buscando
     * Se compra si el nodo no es nulo ya que de ocurrir esto no se pudo encontrar en el arbol el 
     * nodo buscado.
     * En caso de coincidir el dato se regresa el nodo correspondiente con dicho dato
     * @param valor valor numerico del nodo
     * @param nodo nodo de la llamada actual en el stack
     * @return nodo correspondiente
     */
    private NodoArbol<T> buscarNodo(T valor, NodoArbol<T> nodo) {
        if (nodo == null) return null; // Nodo no encontrado
        if (valor == nodo.dato) return nodo; // Nodo encontrado

        NodoArbol<T> nodoEncontrado;
        if (valor < nodo.dato) nodoEncontrado = buscarNodo(valor, nodo.izq); // Nodo actualizado al hijo izquierdo
        else nodoEncontrado = buscarNodo(valor, nodo.der); // Nodo actualizado al hijo derecho
        
        return nodoEncontrado;
    }

    /**
     * En funcion al numero de hijos del nodo recibido se evalua que tipo de eliminacion sera, teniendo 3 casos.
     * Caso 1: El nodo no posee hijos por ende es una hoja, eliminacion tipo eliminarNodoHoja
     * Caso 2: El nodo posee un solo hijo por ende no es una hoja, eliminacion tipo eliminarConUnHijo
     * Caso 3: El nodo posee 2 hijos por ende no es una hoja, eliminacion tipo eliminarConDosHijos
     * @param nodo nodo a eliminar
     */
    private void manejarEliminaciones(NodoArbol<T> nodo) {
        NodoArbol<T> nodoEliminado;
        switch (numeroDeHijos(nodo)) {
            case 0:
                System.out.println("Es hoja " + nodo.dato);
                nodoEliminado = eliminarNodoHoja(nodo);
                break;
            case 1:
                System.out.println("Un solo hijo " + nodo.dato);
                nodoEliminado = eliminarConUnHijo(nodo);
                break;
            case 2:
                nodoEliminado = eliminarConDosHijos(nodo);
                break;
            default:
                nodoEliminado = null;
        }
        balancear(nodoEliminado);
    }

    /**
     * Al tratarse de una hoja solo se elimina la hoja y las referencias sobre ella
     * @param nodoHoja nodo a eliminar
     * @return regresamos el padre de nodo eliminado para su posterior balanceo
     */
    private NodoArbol<T> eliminarNodoHoja (NodoArbol<T> nodoHoja) {
        if(nodoHoja.padre == null) { // En este caso la hoja se trata de la raiz
            this.raiz = null;
            return null;
        }

        // Evaluamos la ubicacion de la hoja
        if (nodoHoja.padre.dato < nodoHoja.dato) nodoHoja.padre.der = null;
        else nodoHoja.padre.izq = null;
        // Tomamos la referencia del ultimo nodo afectado por la eliminacion
        NodoArbol<T> referenciaDeEliminacion = nodoHoja.padre;
        nodoHoja = null;
        return referenciaDeEliminacion;
    }

    /**
     * Al poseer unicamente un hijo se busca remplazar al padre con el nodo hijo, siendo este el unico sucesor del padre. 
     * Con ello se actualizan todas las referencias que estaban situadas sobre el padre hacia el hijo
     * @param nodoPadre Nodo a eliminar
     * @return Nuevo padre o nodo sucesor (hijo)
     */
    private NodoArbol<T> eliminarConUnHijo(NodoArbol<T> nodoPadre) {
        NodoArbol<T> hijo = (nodoPadre.izq != null) ? nodoPadre.izq : nodoPadre.der;
    
        if (nodoPadre.padre != null) {
            if (nodoPadre.padre.izq == nodoPadre) {
                nodoPadre.padre.izq = hijo;
            } else {
                nodoPadre.padre.der = hijo;
            }
            if (hijo != null) {
                hijo.padre = nodoPadre.padre;
            }
            balancear(nodoPadre.padre);
        } else {
            this.raiz = hijo;
            if (hijo != null) {
                hijo.padre = null;
            }
        }    
        return hijo;
    }

    /**
     * Se sustituyen las referencias sobre la raiz del arbol para pasarlas al sucesor
     * @param nuevoPadre Sucesor de la raiz
     */
    private NodoArbol<T> eliminarRaiz(NodoArbol<T> nuevoPadre) { 
        if (nuevoPadre != null) {
            // Damos las referencias de la anterior raiz a la nueva
            if(this.raiz.izq != nuevoPadre) nuevoPadre.izq = this.raiz.izq;
            nuevoPadre.der = this.raiz.der;

            // Actualizamos las referencias de la anterior raiz a la nueva
            if(this.raiz.izq != null) this.raiz.izq.padre = nuevoPadre;
            if(this.raiz.der != null) this.raiz.der.padre = nuevoPadre;
            
            // Aseguramos la eliminacion y cambio de raices
            this.raiz = null;
            nuevoPadre.padre = null;
            this.raiz = nuevoPadre;
        } else {
            this.raiz = null; // No hay mas elementos en el arbol
        }
        return raiz;
    }


    /**
     * Al poseer dos hijos se busca remplazar al padre con el nodo mas grande que dependa
     * de su hijo en la rama izquierda, siendo este el sucesor del padre. Con ello se actualizan todas las 
     * referencias que estaban situadas sobre el padre hacia el heredero
     * @param nodoPadre Nodo a eliminar
     * @return Referencia al ultimo nodo afectado por la eliminacion
     */
    private NodoArbol<T> eliminarConDosHijos (NodoArbol<T> nodoPadre) {
        System.out.println("\n\n Eliminacion de dos hojas");
        NodoArbol<T> nuevoPadre = quitarMayor(nodoPadre.izq);
        System.out.println(nuevoPadre);
        if(nodoPadre.padre != null) {
            nuevoPadre.der = nodoPadre.der;
            nuevoPadre.izq = nodoPadre.izq;
            nuevoPadre.padre = nodoPadre.padre;

            if(nodoPadre.izq != null) nodoPadre.izq.padre = nuevoPadre;
            if(nodoPadre.der != null) nodoPadre.der.padre = nuevoPadre;

            if(nodoPadre.padre.dato < nodoPadre.dato) {
                nodoPadre.padre.der = nuevoPadre;
            } else {
                nodoPadre.padre.izq = nuevoPadre;
            }
            nodoPadre = null;
        } else {
            nuevoPadre = eliminarRaiz( nuevoPadre );
        }
        balancear(nuevoPadre.izq);

        System.out.println(nuevoPadre + "Termina la elimicacion  \n\n\n\n");
        return nuevoPadre;
    }

    /**
     * Se busca el nodo mas grande dependiendiente de la rama que se dio como parametro.
     * Una vez encontrado es extraido eliminando todas las referencias sobre el para su posterior tratamiento
     * @param nodo Nodo de la rama que se busca
     * @return Nodo mas grande de la rama
     */
    private NodoArbol<T> quitarMayor(NodoArbol<T> nodo) { // No estas teniendo en cuenta la rama izquierda
        if (nodo.der == null) { // Si no existe un hijo a la derecha
            if(nodo.padre.der == nodo){
                nodo.padre.der = nodo.izq;
                
            }; // En caso de que el hijo posea hijos

            if(nodo.padre.izq == nodo) {
                nodo.padre.izq = nodo.izq;
            } // En caso de que sea una hoja el nodo a quitar
            nodo.padre = null; // Eliminamos el padre del nodo
            return nodo; // Nodo sin referencias
        }
        return quitarMayor(nodo.der); // Se continua recorriendo hacia la derecha
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
        return this.inOrden(raiz) + "\n" +this.postOrden(raiz);
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

    public void imprimirIzquierda(){
        imprimirIzquierda(this.raiz);
    }
    public void imprimirIzquierda(NodoArbol<T> raiz) {
        if(raiz == null) return;
        imprimirIzquierda(raiz.izq);
        System.out.println(raiz.dato);
    }

    


}
