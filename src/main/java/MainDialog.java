import model.Data;
import server.Server;
import server.ServerListeners;
import server.ServerThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainDialog extends JDialog {
    private JPanel contentPane;
    private JTextArea textArea1;
    private JButton startServerButton;
    private JTextField portText;
    private JButton buttonOK;

    private Server server;
    private ServerThread serverThread;

    public MainDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        startServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                startServer();


            }
        });


    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    private void startServer(){
        int port = Integer.parseInt(portText.getText());
         server  = new Server(port);
         serverThread = new ServerThread(server);
         server.setServerListeners(new ServerListeners() {
             @Override
             public void onError(String message) {
                 String pMessage = textArea1.getText()+"Error: "+message+"\n";
                 textArea1.setText(pMessage);
             }

             @Override
             public void onStart(String message) {
                 String pMessage = textArea1.getText()+message+"\n";
                 textArea1.setText(pMessage);
             }

             @Override
             public void onStop(String message) {
                 String pMessage = textArea1.getText()+message+"\n";
                 textArea1.setText(pMessage);

             }

             @Override
             public void onReceivedMessage(Data data) {
                 String pMessage = textArea1.getText()+ data.toString()+"\n";
                 textArea1.setText(pMessage);

             }

             @Override
             public void newUserJoin(String username) {
                 String pMessage = textArea1.getText()+username+ " is join\n";
                 textArea1.setText(pMessage);
             }
         });
         serverThread.startServer();


    }
    private void stopServer(){
        serverThread.stopServer();
    }

    public static void main(String[] args) {


        MainDialog dialog = new MainDialog();
        dialog.setPreferredSize(new Dimension(500,500));
        dialog.pack();
        dialog.setVisible(true);


        System.exit(0);
    }
}
