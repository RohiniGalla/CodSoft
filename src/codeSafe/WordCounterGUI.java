package codeSafe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordCounterGUI extends JFrame implements ActionListener {

    private JTextField filePathField;
    private JButton browseButton;
    private JTextArea resultArea;

    public WordCounterGUI() {
        setTitle("Word Counter");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JLabel filePathLabel = new JLabel("File Path:");
        topPanel.add(filePathLabel);

        filePathField = new JTextField(20);
        topPanel.add(filePathField);

        browseButton = new JButton("Browse");
        browseButton.addActionListener(this);
        topPanel.add(browseButton);

        add(topPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == browseButton) {
            String filePath = filePathField.getText();
            String text = readFile(filePath);

            // Split the string into an array of words using space or punctuation as delimiters
            String[] words = text.split("[\\s\\p{Punct}]+");

            // Initialize a counter variable to keep track of the number of words
            int wordCount = 0;

            // Set to store common words or stop words
            Set<String> stopWords = new HashSet<>();
            // Adding some common stop words for demonstration
            stopWords.add("the");
            stopWords.add("is");
            stopWords.add("and");
            stopWords.add("in");
            stopWords.add("of");

            // Map to store word frequency
            Map<String, Integer> wordFrequency = new HashMap<>();

            // Iterate through the array of words and increment the counter for each word encountered
            for (String word : words) {
                if (!word.isEmpty()) {
                    word = word.toLowerCase(); // Convert to lowercase
                    if (!stopWords.contains(word)) { // Check if word is not a stop word
                        wordCount++;
                        wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
                    }
                }
            }

            // Display the total count of words to the user
            resultArea.setText("Total word count: " + wordCount + "\n");
            // Display the number of unique words
            resultArea.append("Number of unique words: " + wordFrequency.size() + "\n");
            // Display the frequency of each word
            resultArea.append("Word frequency:\n");
            for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
                resultArea.append(entry.getKey() + ": " + entry.getValue() + "\n");
            }
        }
    }

    private static String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }
        return content.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WordCounterGUI wordCounterGUI = new WordCounterGUI();
            wordCounterGUI.setVisible(true);
        });
    }
}

