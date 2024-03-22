package codigo;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GUI extends JPanel {

    PSNUsers psn;

    JButton agregarUsuarioBTN = new JButton("Agregar Usuario");
    JButton desactivarUsuarioBTN = new JButton("Desactivar Usuario");
    JButton addTrophyBTN = new JButton("Agregar Trofeo");
    JButton playerInfoBTN = new JButton("Player Info");
    JTextArea area = new JTextArea();

    public GUI() throws IOException {
        this.psn = psn != psn ? psn : new PSNUsers();

        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxLayout);

        area.setPreferredSize(new Dimension(300, 200));
        area.setAlignmentX(Component.LEFT_ALIGNMENT);
        area.setEditable(false);
        add(area);

        agregarUsuarioBTN.setFont(new Font("Tahoma", Font.PLAIN, 18));
        agregarUsuarioBTN.addActionListener(new agregar());
        agregarUsuarioBTN.setBounds(205, 287, 155, 40);
        add(agregarUsuarioBTN);

        desactivarUsuarioBTN.setFont(new Font("Tahoma", Font.PLAIN, 18));
        desactivarUsuarioBTN.addActionListener(new desactivar());
        desactivarUsuarioBTN.setBounds(205, 287, 155, 40);
        add(desactivarUsuarioBTN);

        addTrophyBTN.setFont(new Font("Tahoma", Font.PLAIN, 18));
        addTrophyBTN.addActionListener(new trofeo());
        addTrophyBTN.setBounds(205, 287, 155, 40);
        add(addTrophyBTN);

        playerInfoBTN.setFont(new Font("Tahoma", Font.PLAIN, 18));
        playerInfoBTN.addActionListener(new playerInfo());
        playerInfoBTN.setBounds(205, 287, 155, 40);
        add(playerInfoBTN);

    }

    class agregar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String nuevoUsuario = JOptionPane.showInputDialog("Por favor, elegir el usuario del nuevo miembro.");

            if (!nuevoUsuario.isBlank() || nuevoUsuario != null) {
                try {
                    if (psn.addUser(nuevoUsuario)) {
                        JOptionPane.showMessageDialog(null, "Se agrego con exito el nuevo usuario!");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo agregar el nuevo usuario, por favor intentar de nuevo.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    class desactivar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String usuarioDesactivar = JOptionPane.showInputDialog("Por favor, elegir el usuario del nuevo miembro.");

            if (!usuarioDesactivar.isBlank() || usuarioDesactivar != null) {
                try {
                    if (psn.deactivateUser(usuarioDesactivar)) {
                        JOptionPane.showMessageDialog(null, "Se desactivo el usuario con exito!");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo desactivar el usuario, por favor intentar de nuevo.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }

    }

    class trofeo implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String usuarioTrofeo = JOptionPane.showInputDialog("Por favor, elegir el usuario del nuevo miembro.");
            String trophyGame = JOptionPane.showInputDialog("Por favor, elegir el nombre del nuevo juego.");
            String trophyName = JOptionPane.showInputDialog("Por favor, elegir el nombre del nuevo trofeo.");

            String[] tipos = new String[Trophy.Tipo.values().length];

            int indice = 0;

            for (Trophy.Tipo tipo : Trophy.Tipo.values()) {
                tipos[indice++] = tipo.name();
            }

            String selectedTrophyType = (String) JOptionPane.showInputDialog(null,
                    "Seleccione el tipo de trofeo:", "Tipo de Trofeo",
                    JOptionPane.PLAIN_MESSAGE, null, tipos, tipos[0]);

            Trophy selectedTipo = Trophy.valueOf(selectedTrophyType);

            if (!usuarioTrofeo.isBlank() || usuarioTrofeo != null) {
                try {
                    if (psn.addTrophieTo(usuarioTrofeo, trophyGame, trophyName, selectedTipo)) {
                        JOptionPane.showMessageDialog(null, "Se agrego con exito el trofeo!");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo agregar el trofeo, por favor intentar de nuevo.");
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }

    }

    class playerInfo implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String usuarioBuscado = JOptionPane.showInputDialog("Por favor, elegir el usuario del nuevo miembro.");

            if (!usuarioBuscado.isBlank() || usuarioBuscado != null) {
                try {
                    String mensaje = psn.playerInfo(usuarioBuscado);

                    area.setText(mensaje);

                    area.repaint();
                    area.revalidate();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

}
