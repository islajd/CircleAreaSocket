package view;

import Connection.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Panel extends JFrame {
    private Client serverClient = new Client();
    private JButton button = new JButton("Calculate");
    private JLabel label = new JLabel("Radius: ");
    private JLabel area = new JLabel("Area: ");
    private JLabel response = new JLabel("-");
    private JTextField input = new JTextField();
    private JPanel panel = new JPanel(new GridLayout(2,2));
    public Panel() throws IOException {
        setVisible(true);
        setTitle("AREA");
        setSize(300,150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.add(label);
        panel.add(input);
        panel.add(button);
        panel.add(response);
        this.add(panel, BorderLayout.CENTER);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                serverClient.sendRequest(input.getText());
                try {
                    response.setText(serverClient.getResponse());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
