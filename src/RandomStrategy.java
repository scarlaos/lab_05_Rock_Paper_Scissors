import java.util.Random;

class RandomStrategy implements Strategy{
    private Random rand = new Random();

    public String getMove(String playerMove){
        int choice = rand.nextInt(3);
        switch(choice){
            case 0: return "Rock";
            case 1: return "Paper";
            case 2: return "Scissors";
            default: return "Rock";
        }
    }
}
