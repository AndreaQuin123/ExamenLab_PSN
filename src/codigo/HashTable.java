package codigo;

public class HashTable {

    private Entry Nodo;

    public HashTable() {
        this.Nodo = null;
    }

    //add(String username, long pos). Agrega un nuevo elemento al final de la lista. 5%
    public boolean add(String username, long posicion) {
        //Revisa si el username esta en blanco para evitar que se agrege un nodo vacio
        if (username.isBlank()) {
            return false;
        }

        posicion = 0;

        //si no hay ninguno nodo al principio entonces lo agrega
        if (Nodo == null) {
            Nodo = new Entry(username, posicion);
            return true;
        } else {
            //Si ya existe entonces le dice que no porque luego se tendra que buscar por username los datos
            if (Nodo.siguiente.getUsername().equals(username)) {
                return false;
            }

            Entry nodeAgregado = this.Nodo;

            //buscando la siguiente posicion libre
            while (nodeAgregado != null) {
                //si alguno de estos tiene el username
                if (nodeAgregado.siguiente.getUsername().contains(username)) {
                    return false;
                }

                nodeAgregado = nodeAgregado.siguiente;
                posicion++;

                //cuando encuentre uno nulo lo llena
                if (nodeAgregado.siguiente == null) {
                    nodeAgregado.siguiente = new Entry(username, posicion);
                    return true;
                }

            }

        }

        return false;

    }

    //remove(String username). Remueve un elemento de la lista que concuerda con dicho código. 5%
    public boolean remove(String username) {
        if (username.isBlank() || Nodo == null) {
            return false;
        }

            //primero se borra el nodo y se referencia al siguiente en vez
            if (Nodo.getUsername().equals(username)) {
                Nodo = Nodo.siguiente;
                return true;
            }

        Entry nodoRemovido = Nodo;

        while (nodoRemovido != null) {
            //referencia al siguiente nodo del nodo borrado
            if (nodoRemovido.siguiente != null && nodoRemovido.siguiente.getUsername().equals(username)) {
                nodoRemovido.siguiente = nodoRemovido.siguiente.siguiente;
                return true;
            }

            nodoRemovido = nodoRemovido.siguiente;

            }

        return false;

    }

    //Search(String username). Recorre la lista y si encuentra un elemento con dicho username, se retorna su atributo posición. 
    public long search(String username) {
        if (username.isBlank() || Nodo == null) {
            return -1;
        }

        if (Nodo.getUsername().equals(username)) {
            return Nodo.getPosicion();
        }

        Entry current = Nodo;
        long position = 0;
        
        while (current != null) {
            if (current.getUsername().equals(username)) {
                return position;
            }
            current = current.siguiente;
            position++;
        }

        return -1;

    }

}
