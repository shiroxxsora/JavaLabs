package ru.bsu.webdev.agario.Client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ConnectionScreen extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField addressField;
    private JTextField usernameField;

    public ConnectionScreen() {
        setTitle("Connection");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("server address:"));
        addressField = new JTextField();
        panel.add(addressField);

        panel.add(new JLabel("username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        JButton connectionnButton = new JButton("Connection");
        connectionnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connect();
            }
        });

        panel.add(connectionnButton);
        add(panel);
    }

    private void connect() {
    	String address = addressField.getText();
        String username = usernameField.getText();

        // Закрываем окно логина и запускаем игру
        this.setVisible(false);
        Client.instance.startGame(address, username);
    }
}
