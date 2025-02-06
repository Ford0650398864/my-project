import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public class UserScoreTable {
    private Map<String, List<Integer>> userScores;
    private JTable table;

    public UserScoreTable() {
        userScores = new TreeMap<>();
        initializeTable();
    }

    private void initializeTable() {
        // Create columns and an empty data model for the JTable
        String[] columns = {"Username", "Score (WPM)"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        table = new JTable(model);

        // Set up the table to be scrollable
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Create a JFrame to hold the JTable
        JFrame frame = new JFrame("User Scores");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.getContentPane().add(scrollPane);
        frame.setVisible(true);
    }

    public void addUserScore(String username, int score) {
        userScores.computeIfAbsent(username, k -> new ArrayList<>()).add(score);
        updateTable();
    }

    private void updateTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        // Create a custom comparator to sort scores in descending order
        Comparator<Map.Entry<String, List<Integer>>> scoreComparator =
                Comparator.comparingInt(entry -> entry.getValue().stream().max(Integer::compareTo).orElse(0));

        // Sort user scores by the highest score in descending order
        List<Map.Entry<String, List<Integer>>> sortedUserScores = new ArrayList<>(userScores.entrySet());
        sortedUserScores.sort(scoreComparator.reversed());

        // Update the table with sorted scores
        for (Map.Entry<String, List<Integer>> entry : sortedUserScores) {
            String username = entry.getKey();
            List<Integer> scores = entry.getValue();

            // Get the highest score for each user
            int highestScore = scores.stream().max(Integer::compareTo).orElse(0);

            // Display the user only once with the highest score
            Object[] rowData = {username, highestScore};
            model.addRow(rowData);
        }
    }

    public void displayUserScores() {
        StringBuilder message = new StringBuilder("User Scores (Sorted by Score in Descending Order):\n");

        // Create a custom comparator to sort scores in descending order
        Comparator<Map.Entry<String, List<Integer>>> scoreComparator =
                Comparator.comparingInt(entry -> entry.getValue().stream().max(Integer::compareTo).orElse(0));

        // Sort user scores by the highest score in descending order
        List<Map.Entry<String, List<Integer>>> sortedUserScores = new ArrayList<>(userScores.entrySet());
        sortedUserScores.sort(scoreComparator.reversed());

        for (Map.Entry<String, List<Integer>> entry : sortedUserScores) {
            String username = entry.getKey();
            List<Integer> scores = entry.getValue();

            // Get the highest score for each user
            int highestScore = scores.stream().max(Integer::compareTo).orElse(0);

            message.append("Username: ").append(username).append(", Score: ").append(highestScore).append(" WPM\n");
        }
        System.out.println(message.toString());
    }

    public void displayUserScores(String username) {
    }

    public void displayAllUserScores() {

    }

}