
package codigo;

public enum Trophy {
    
    PLATINO(5,"platino"),
    ORO(3, "oro"),
    PLATA(2, "plata"),
    BRONCE(1, "bronce");
    
    final int puntos;
    final String tipo;
    
    Trophy(int puntos, String tipo){
        this.puntos = puntos;
        this.tipo = tipo;
    }
    
    public enum Tipo{
        PLATINO,
        ORO,
        PLATA,
        BRONCE;
    }
    
    /*
    Crear una enumeración llamada Trophy que tiene como elementos PLATINO, ORO, PLATA y BRONCE. 
    Cada uno inicializa un atributo público llamado points con los valores 5, 3, 2 y 1 respectivamente. 5%
    */
    
    
}
