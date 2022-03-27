import ChatUtils.ChatIO;
import ChatUtils.MessageLine;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MainMenu extends JDialog {
    private JPanel contentPane;
    private JButton selectFileButton;
    private JButton confirmButton;
    private JTextField searchQuery;
    private JTextPane resultPane;
    private JLabel numberOfMessages;
    private JLabel totalMatches;
    private JButton buttonOK;

    private JFileChooser fc = new JFileChooser();
    private HashMap<String, Integer> hm = new HashMap<>();

    private final static ChatIO cio = new ChatIO();
    private static File file;
    private static List<MessageLine> messages;

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
                if (!verifyForErrors()) {
                    try {
                        messages = cio.readChat(file);
                        displayStatistic();
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "Error opening file", "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public boolean verifyForErrors() {
        String searchQueryText = searchQuery.getText().toLowerCase();
        boolean error = false;

        if (file == null) {
            JOptionPane.showMessageDialog(null, "Please select a file", "Error", JOptionPane.ERROR_MESSAGE);
            error = true;
        } else if (searchQueryText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a search query", "Error", JOptionPane.ERROR_MESSAGE);
            error = true;
        }

        return error;
    }

    public void displayStatistic() {
        String searchQueryText = searchQuery.getText().toLowerCase();

        for (MessageLine msg : messages) {
            if (msg.getContent().toLowerCase().contains(searchQueryText)) {

                if (!hm.containsKey(msg.getAuthor())) {
                    hm.put(msg.getAuthor(), 1);
                } else {
                    int prev = hm.get(msg.getAuthor());
                    hm.replace(msg.getAuthor(), prev + 1);
                }
            }
        }

        StringBuilder results = new StringBuilder();

//                Get the keys inside the hashmap
        Set<String> keys = hm.keySet();

        int total = 0;

//                For every key (author), append the author and the result to the string builder
        for (String key : keys) {
            int numMsg = hm.get(key);

//                    Add numMsg to total
            total += numMsg;
            results.append(
                    String.format(
                            "%s:\t%d\n", key, numMsg
                    )
            );
        }

        resultPane.setText(results.toString());

        numberOfMessages.setText(
                "Total Messages: " + messages.size()
        );

        totalMatches.setText(
                "Total Matches: " + total
        );

//            Clear the hashmap
        hm.clear();
    }

    public static void main(String[] args) {
        MainMenu dialog = new MainMenu();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
