import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/** Rock Paper Scissors game using strategies, includes track of cheats and least/most used
 * @author Scarlaos
 * Computer Programming II lab 5 assignment
 */

public class RockPaperScissorsFrame extends JFrame {
    JPanel mainPanel = new JPanel();
    JLabel titleLabel;

    //buttons
    JPanel buttonPanel = new JPanel();
    JButton rockButton, paperButton, scissorsButton, quitButton;

    JPanel statsPanel = new JPanel();
    //player's choices
    JLabel playerWinsLabel;
    JTextField playerWinsField;
    //computer's choices
    JLabel computerWinsLabel;
    JTextField computerWinsField;
    //matched games
    JLabel tiesLabel;
    JTextField tiesField;
    //results
    JPanel resultPanel = new JPanel();
    JTextArea resultField;
    JScrollPane scroller;

    int playerWins = 0;
    int computerWins = 0;
    int ties = 0;

    //keeps track of the moves played
    private Map<String,Integer> moveCounts = new HashMap<>(); //https://www.w3schools.com/java/java_hashmap.asp
    private String lastMove = null;

    Random rand = new Random();

    public RockPaperScissorsFrame() {
        moveCounts.put("Rock",0);
        moveCounts.put("Paper",0);
        moveCounts.put("Scissors",0);


        mainPanel = new JPanel();

        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        createStatsPanel();
        mainPanel.add(statsPanel, BorderLayout.CENTER);

        createResultsPanel();
        mainPanel.add(resultPanel, BorderLayout.SOUTH);

        setSize(700, 600);
        setTitle("Rock, Paper, Scissors Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(new EtchedBorder());

        titleLabel = new JLabel("Rock Paper Scissors Game");
        titleLabel.setFont(new Font(Font.SERIF, Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(titleLabel);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(Box.createVerticalGlue());


        JPanel buttonRow = new JPanel();
        buttonRow.setLayout(new  BoxLayout(buttonRow, BoxLayout.X_AXIS));
        buttonRow.setAlignmentX(Component.CENTER_ALIGNMENT);


        rockButton = new JButton(new ImageIcon("src/rockimage.jpg"));
        rockButton.addActionListener(e -> playGame("Rock"));



        paperButton = new JButton(new ImageIcon("src/paperimage.jpg"));
        paperButton.addActionListener(e -> playGame("Paper"));



        scissorsButton = new JButton(new ImageIcon("src/scissorsimage.jpg"));
        scissorsButton.addActionListener(e -> playGame("Scissors"));


        quitButton = new JButton(new ImageIcon("src/QUITimage.png"));
        quitButton.addActionListener(e -> System.exit(0));
        quitButton.setPreferredSize(new Dimension(500,500)); //trying to make button reasonable size

        Dimension buttonSize = new Dimension(450, 360);
        rockButton.setPreferredSize(buttonSize);
        paperButton.setPreferredSize(buttonSize);
        scissorsButton.setPreferredSize(buttonSize);




        buttonRow.add(rockButton);
        buttonRow.add(paperButton);
        buttonRow.add(scissorsButton);
        buttonRow.add(quitButton);

        buttonPanel.add(buttonRow);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

    }

    private void createStatsPanel() {
        statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(1,4));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Stats"));

        playerWinsLabel = new JLabel("Player Wins:");
        playerWinsField = new JTextField("0");

        computerWinsLabel = new JLabel("Computer Wins:");
        computerWinsField = new JTextField("0");

        tiesLabel = new JLabel("Ties:");
        tiesField = new JTextField("0");

        playerWinsField.setEditable(false);
        computerWinsField.setEditable(false);
        tiesField.setEditable(false);

        statsPanel.add(playerWinsLabel);
        statsPanel.add(playerWinsField);
        statsPanel.add(computerWinsLabel);
        statsPanel.add(computerWinsField);
        statsPanel.add(tiesLabel);
        statsPanel.add(tiesField);

        mainPanel.add(statsPanel, BorderLayout.CENTER);
    }

    private void createResultsPanel() {
        resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        resultPanel.setBorder((new TitledBorder(new EtchedBorder(),"Results: ")));

        resultField = new JTextArea(10,60);
        resultField.setEditable(false);
        resultField.setFont(new Font(Font.SERIF, Font.BOLD, 18));

        scroller = new JScrollPane(resultField);
        resultPanel.add(scroller, BorderLayout.CENTER);

        mainPanel.add(resultPanel, BorderLayout.SOUTH);
    }

    Strategy strategy = new RandomStrategy();
    public void playGame(String playersChoice) {
        moveCounts.put(playersChoice, moveCounts.get(playersChoice) + 1);

        int computerChoiceofPlay = rand.nextInt(100) + 1;
        Strategy strategy;
        String strategyName;

        if (computerChoiceofPlay <= 10) { // 10% Cheat
            strategy = new CheatStrategyDemo.Cheat();
            strategyName = "Computer Cheated";

        } else if (computerChoiceofPlay <= 30) { // 20% LeastUsed
            strategy = new LeastUsed();
            strategyName = "Least Used Play";

        } else if (computerChoiceofPlay <= 50) { // 20% MostUsed
            strategy = new MostUsed();
            strategyName = "Most Used Play";

        } else if (computerChoiceofPlay <= 70) { // 20% LastUsed
            strategy = new LastUsed();
            strategyName = "Last Used Play";

        } else { // 30% Random
            strategy = new RandomStrategy();
            strategyName = "Random";
        }

        String computerChoice = strategy.getMove(playersChoice);

        String result;
        if (playersChoice.equals(computerChoice)) {
            result = "Tie! You picked: " + playersChoice + ". The computer picked: " + computerChoice;
            ties++;
        } else if ((playersChoice.equals("Rock") && computerChoice.equals("Scissors")) ||
                (playersChoice.equals("Paper") && computerChoice.equals("Rock")) ||
                (playersChoice.equals("Scissors") && computerChoice.equals("Paper"))) {
            result = playersChoice + " beats " + computerChoice + " (Player Wins)";
            playerWins++;
        } else {
            result = computerChoice + " beats " + playersChoice + " (Computer Wins)";
            computerWins++;
        }

        lastMove = playersChoice;

        resultField.append(result + "\n Computer Strategy used: " + strategyName + "\n");
        playerWinsField.setText(String.valueOf(playerWins));
        computerWinsField.setText(String.valueOf(computerWins));
        tiesField.setText(String.valueOf(ties));
    }



    //Strategies
    private class LeastUsed implements Strategy {
        @Override
        public String getMove(String playersChoice) {
            String least = "Rock";
            int min = Integer.MAX_VALUE;
            for (String move : moveCounts.keySet()) {
                if (moveCounts.get(move) < min) {
                    min = moveCounts.get(move);
                    least = move;
                }
            }
            switch (least) {
                case "Rock": return "Paper"; //game logic
                case "Paper": return "Scissors";
                case "Scissors": return "Rock";
                default: return "Rock";
            }
        }
    }

    private class MostUsed implements Strategy {
        @Override
        public String getMove(String playersChoice) {
            String most = "Rock";
            int max = -1;
            for (String move : moveCounts.keySet()) {
                if (moveCounts.get(move) > max) {
                    max = moveCounts.get(move);
                    most = move;
                }
            }
            switch (most) {
                case "Rock": return "Paper"; //game logic
                case "Paper": return "Scissors";
                case "Scissors": return "Rock";
                default: return "Rock";
            }
        }
    }

    private class LastUsed implements Strategy {
        @Override
        public String getMove(String playersChoice) {
            if (lastMove == null) {
                return new RandomStrategy().getMove(playersChoice);
            }
            switch (lastMove) {
                case "Rock": return "Paper"; //game logic
                case "Paper": return "Scissors";
                case "Scissors": return "Rock";
                default: return "Rock";
            }
        }
    }
}

