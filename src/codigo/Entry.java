
package codigo;

public class Entry {
    
    
    private String username;
    private long posicion;
    Entry siguiente;
    
    public Entry(String username, long posicion){
        this.username = username;
        this.posicion = posicion;
        this.siguiente = null;
        
    }
    
    public String getUsername(){
        return username;
    }
    
    public long getPosicion(){
        return posicion;
    }
    
    public Entry getSiguiente(){
        return siguiente;
    }
    
    /*
    Crear una clase llamada Entry que tiene como atributo una String para guardar el username de un registro, 
    un long para guardar la posición donde se encuentra el registro de ese usuario en un archivo y un atributo 
    para que apunte al siguiente elemento de una lista. En el constructor se inicializan por parámetro el código y la posición. 
    Por default el siguiente está en null. 5%
    */
    
}
