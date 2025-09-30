import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Random;

public class RockPaperScissorsFrame extends JFrame {
    JPanel mainPanel = new JPanel();

    JPanel buttonPanel = new JPanel();
    JLabel titleLabel;
    JButton rockButton, paperButton, scissorsButton, quitButton;

    JPanel statsPanel = new JPanel();

    JLabel playerWinsLabel;
    JTextField playerWinsField;

    JLabel computerWinsLabel;
    JTextField computerWinsField;

    JLabel tiesLabel;
    JTextField tiesField;

    JPanel resultPanel = new JPanel();
    JTextArea resultField;
    JScrollPane scroller;

    int playerWins = 0;
    int computerWins = 0;
    int ties = 0;

    Random rand = new Random();

    public RockPaperScissorsFrame() {
        mainPanel = new JPanel();

        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        createStatsPanel();
        mainPanel.add(statsPanel, BorderLayout.CENTER);

        createResultsPanel();
        mainPanel.add(resultPanel, BorderLayout.SOUTH);

        setSize(600, 400);
        setTitle("Rock, Paper, Scissors Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,6));
        buttonPanel.setBorder(new EtchedBorder());

        rockButton = new JButton(new ImageIcon("src/rockimage.jpg"));
        rockButton.addActionListener(e -> playGame("Rock"));

        paperButton = new JButton(new ImageIcon("src/paperimage.jpg"));
        paperButton.addActionListener(e -> playGame("Paper"));

        scissorsButton = new JButton(new ImageIcon("src/scissorsimage.jpg"));
        scissorsButton.addActionListener(e -> playGame("Scissors"));

        quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(rockButton);
        buttonPanel.add(paperButton);
        buttonPanel.add(scissorsButton);
        buttonPanel.add(quitButton);


        titleLabel = new JLabel("Rock Paper Scissors");
        titleLabel.setFont(new Font(Font.SERIF, Font.BOLD, 28));
        buttonPanel.add(titleLabel);
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

    }

    private void createResultsPanel() {
        resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        resultPanel.setBorder((new TitledBorder(new EtchedBorder(),"Results: ")));

        resultField = new JTextArea(4,60);
        resultField.setEditable(false);
        resultField.setFont(new Font(Font.SERIF, Font.BOLD, 18));

        scroller = new JScrollPane(resultField);
        resultPanel.add(scroller, BorderLayout.CENTER);


    }

    Strategy strategy = new CheatStrategyDemo.Cheat();
    public void playGame(String playersChoice) {
        String computerChoice = strategy.getMove(playersChoice);

        String result;
        if(playersChoice.equals(computerChoice)) {
            result = "Tie!";
            ties++;

        }
        else if(playersChoice.equals("Rock") && computerChoice.equals("Scissors") || playersChoice.equals("Paper") && computerChoice.equals("Rock") || playersChoice.equals("Scissors") && computerChoice.equals("Paper")) {
            result = "You win! " + playersChoice + " beats " + computerChoice;
            playerWins++;
        }else{
            result = "You lost! Computer Wins ";
            computerWins++;
        }

        resultField.append(result + "\n");
        playerWinsField.setText(String.valueOf(playerWins));
        computerWinsField.setText(String.valueOf(computerWins));
        tiesField.setText(String.valueOf(ties));
    }
}
