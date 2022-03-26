import ChatUtils.ChatIO;
import ChatUtils.MessageLine;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

public class MainMenu extends JDialog {
    private JPanel contentPane;
    private JButton selectFileButton;
    private JButton confirmButton;
    private JTextField searchQuery;
    private JTextPane resultPane;
    private JLabel numberOfMessages;
    private JButton buttonOK;

    private JFileChooser fc = new JFileChooser();
    private File file;
    private HashMap<String, Integer> hm = new HashMap<>();

    public MainMenu() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Whatsapp Chat Counter");
        getRootPane().setDefaultButton(buttonOK);
        selectFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                fc.showOpenDialog(null);
                file = fc.getSelectedFile();
//                Set the filename
                selectFileButton.setText(file.getName());


            }
        });

        confirmButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                handleClick();
            }
        });
    }

    public void handleClick() {

        String searchQueryText = searchQuery.getText().toLowerCase();
        boolean error = false;

        if (file == null) {
            JOptionPane.showMessageDialog(null, "Please select a file", "Error", JOptionPane.ERROR_MESSAGE);
            error = true;
        } else if (searchQueryText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a search query", "Error", JOptionPane.ERROR_MESSAGE);
            error = true;
        }

        if (!error) {


            try {
                List<MessageLine> chat = ChatIO.readChat(file);

                for (int i = 0; i < chat.size(); i++) {
                    MessageLine msg = chat.get(i);
                    if (msg.getContent().toLowerCase().contains(searchQueryText)) {

                        if (!hm.containsKey(msg.getAuthor())) {
                            hm.put(msg.getAuthor(), 1);
                        } else {
                            int prev = hm.get(msg.getAuthor());
                            hm.replace(msg.getAuthor(), prev + 1);
                        }
                    }
                }

//            Place result text here
                resultPane.setText(
                        hm.toString()
                );


                numberOfMessages.setText(
                       "Messages: " + chat.size()
                );

//            Clear the hashmap
                hm.clear();

            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Error opening file", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MainMenu dialog = new MainMenu();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
