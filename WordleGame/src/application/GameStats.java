import java.io.IOException;
import java.io.PrintWriter;

public class GameStats {
    private int gamesPlayed;
    private int wins;
    private int[] guessDistribution; // Array to store the number of games won with 1-6 guesses

    public GameStats() {
        this.guessDistribution = new int[6]; // Since there are a maximum of 6 guesses allowed
    }

    public void incrementGamesPlayed() {
        gamesPlayed++;
    }

    public void incrementWins() {
        wins++;
    }

    public void addGuessDistribution(int guesses) {
        if (guesses > 0 && guesses <= guessDistribution.length) {
            guessDistribution[guesses - 1]++;
        }
    }

    public void writeStatsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("./src/application/stats.txt"))) {
            writer.println(gamesPlayed);
            writer.println(wins);
            for (int count : guessDistribution) {
                writer.println(count);
            }
        } catch (IOException e) {
            System.out.println("Error writing to stats file.");
            e.printStackTrace();
        }
    }
}
