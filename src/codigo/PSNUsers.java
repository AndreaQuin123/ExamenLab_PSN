package codigo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;

public class PSNUsers {

    private RandomAccessFile accessFile;
    private HashTable users;
    private File psn;

    /*
    Crear una clase llamada PSNUsers que tiene un atributo de tipo RandomAccessFile y uno de tipo HashTable 
    llamado users que sirve para llevar el control de los usuarios agregados, todas las búsquedas se hacen con este atributo, 
    no se hacen búsquedas en el archivo de ningún tipo porque es mas rápido hacerlo en memoria que en disco.
    En el constructor se inicializa el atributo RAF para que este conectado con el archivo psn. 
    
        Se llama la función reloadHashTable. 5%

     */
    public PSNUsers() throws FileNotFoundException, IOException {
        users = users != null ? users : new HashTable();

        psn = new File("Usuarios");

        if (!psn.exists()) {
            psn.mkdir();
        }

        reloadHashTable();

    }

    /*
    La función reloadHashTable recarga la lista enlazada. 
    Con todos los registros NO BORRADOS que están en el archivo. 
    Cada registro en el archivo es un elemento en la lista.
    Se guarda el código del registro y la posición DESPUÉS de leer el código del mismo. Hacer esta función 
    privada ya que solo se llama en el constructor. 10%
     */
    private void reloadHashTable() throws FileNotFoundException, IOException {
        String username;

        for (String archivo : psn.list()) {
            accessFile = new RandomAccessFile("Usuarios\\" + archivo, "rw");
            accessFile.seek(0);

            username = accessFile.readUTF();

            if (accessFile.readBoolean()) {
                users.add(username, 0);
            }
        }
    }

    /*
    addUser(String username). Agrega un nuevo registro al archivo, al agregarlo también se le agrega un elemento 
    a la HashTable con los datos del usuario nuevo. Los datos de un usuario (usar dicho orden como formato) son:

    Username que viene de parámetro y que se valida que sea UNICO.
    Por default tiene el acumulador de puntos por trofeos en 0.
    Por default tiene el contador de trofeos en 0.
    Por default el registro está activado. Si un registro tiene el dato activado en false se CONSIDERA BORRADO. 10%
     */
    public boolean addUser(String username) throws FileNotFoundException, IOException {

        if (users.add(username, 0)) {

            File file = new File("Usuarios\\" + username + ".psn");
            file.createNewFile();

            accessFile = new RandomAccessFile(file, "rw");
            accessFile.seek(0);
            accessFile.writeUTF(username);
            accessFile.writeInt(0);
            accessFile.writeInt(0);
            accessFile.writeBoolean(true);

            return true;

        }

        return false;

    }

    /*
    deactivateUser(String username). Buscar un usuario, si se encuentra, 
    se escribe en disco como no activo y además se borra su registro en la HashTable. 10%
     */
    public boolean deactivateUser(String username) throws FileNotFoundException, IOException {
        if (users.remove(username)) {

            File file = new File("Usuarios\\" + username + ".psn");

            accessFile = new RandomAccessFile(file, "rw");
            accessFile.seek(0);
            accessFile.readUTF();
            accessFile.readInt();
            accessFile.readInt();
            accessFile.writeBoolean(false);

            return true;

        }

        return false;

    }

    /*
    addTrophieTo(String username, String trophyGame, String trophyName, Trophy type). Busca un usuario con ese username, 
    si se encuentra se le adiciona un trofeo. Los trofeos se guardan en un archivo llamado psn. El formato que se guarda por 
    trofeo es:
    Código del User que se ganó el trofeo.
    String – tipo del trofeo.
    Nombre del juego al que pertenece trofeo.
    Nombre del trofeo.
    Fecha cuando se lo gano. 15%
     */
    public boolean addTrophieTo(String username, String trophyGame, String trophyName, Trophy type) throws IOException {

        long posicion = users.search(username);

        if (posicion != -1 && type != null) {

            File file = new File("Usuarios\\" + username + ".psn");
            file.createNewFile();

            /*
            USERNAME
            TIPO (PLATINO, ORO, PLATA, BRONCE)
            NOMBRE DEL JUEGO
            PUNTOS
            CONTADOR TROFEO
            BOOLEAN DE ACTIVO O NO 
            FECHA
             */
            accessFile = new RandomAccessFile(file, "rw");
            accessFile.seek(0);
            accessFile.writeUTF(username);
            accessFile.writeUTF(type.tipo);
            accessFile.writeUTF(trophyGame);
            accessFile.writeInt(type.puntos);
            accessFile.writeInt(1);
            accessFile.writeBoolean(true);
            accessFile.writeUTF(Calendar.getInstance().getTime().toString());

            accessFile.close();
            return true;

        }
        return false;
    }

    /*
    playerInfo(String username). Busca un usuario con ese username.
    Si se encuentra, se imprime TODOS sus datos. 
    Al final se imprimen los datos de los trofeos que ha ganado con el formato: 
    FECHA – TIPO - JUEGO – DESCRIPCION. 5%
     */
    public String playerInfo(String username) throws IOException {
        StringBuilder mensaje = new StringBuilder();

        try {
            File file = new File("Usuarios\\" + username + ".psn");
            if (!file.exists()) {
                return "Usuario no encontrado.";
            }

            try (RandomAccessFile accessFile = new RandomAccessFile(file, "r")) {
                accessFile.seek(0);

                String usuario = accessFile.readUTF();
                int acumuladorPuntos = accessFile.readInt();
                int contadorTrofeos = accessFile.readInt();
                boolean isActive = accessFile.readBoolean();

                mensaje.append("Usuario: ").append(usuario).append("\n");
                mensaje.append("Puntos: ").append(acumuladorPuntos).append("\n");
                mensaje.append("Trofeos: ").append(contadorTrofeos).append("\n");

                if (isActive) {
                    while (accessFile.getFilePointer() < accessFile.length()) {
                        String fecha = accessFile.readUTF();
                        String tipo = accessFile.readUTF();
                        String juego = accessFile.readUTF();
                        int puntosTrofeo = accessFile.readInt();
                        String nombreTrofeo = accessFile.readUTF();

                        mensaje.append("Trofeo conseguido el: ").append(fecha)
                                .append("\nTrofeo de: ").append(tipo)
                                .append("\tPts. ").append(puntosTrofeo)
                                .append("\nJuego en el que se consiguió: ")
                                .append(juego).append("\nNombre del trofeo: ")
                                .append(nombreTrofeo).append("\n");
                    }
                } else {
                    return "Usuario desactivado.";
                }
            }
        } catch (FileNotFoundException e) {
            return "Error: Archivo no encontrado.";
        } catch (IOException e) {
            return "Error: Se produjo un error de lectura.";
        }

        return mensaje.toString();
    }
}
