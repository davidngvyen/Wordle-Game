package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import javafx.scene.input.KeyCode;
import java.util.Arrays;


public class SampleController {
    @FXML
    public Label label1,label2,label3,label4,label5,
    label6, label7, label8, label9, label10,
    label11, label12, label13, label14, label15,
    label16, label17, label18, label19, label20,
    label21, label22, label23, label24, label25,
    label26, label27, label28, label29, label30,
    guess1,guess2,guess3,guess4,guess5,guess6,
    gamesPlay,winPerc;
    public Button Q,W,E,R,T,Y,U,I,O,P,A,S,D,F,G,H,J,K,L,Z,X,C,V,B,N,M;
    
    private int gamesPlayed =0;
    private int winP = 0;
    private int distOne = 0;
    private int distTwo = 0;
    private int distThree = 0;
    private int distFour = 0;
    private int distFive = 0;
    private int distSix = 0;
    @FXML
	private BorderPane gameBoard;
    @FXML
	private AnchorPane gameb;
	@FXML
	private Pane board;
	@FXML
	private Button exBut,b,colorButton,resetButton,save,load;
	
    private Label[][] labels;
    
    public int currentIndex = 0;
    
    public List<String> words = new ArrayList<>();
    
    public String word = "";
	public int row = 0;
	public int guesses = 0;
	@FXML
	private TextField text;
	int reset;
	String textC = "white";
    @FXML
    private void initialize() {
        // Initialize the labels array
    	Platform.runLater(() -> b.requestFocus());
    	b.setVisible(true);
    	text.setDisable(true);
    	board.setVisible(false);
        labels = new Label[][]{
            {label1, label2, label3, label4, label5},
            {label6, label7, label8, label9, label10},
            {label11, label12, label13, label14, label15},
            {label16, label17, label18, label19, label20},
            {label21, label22, label23, label24, label25},
            {label26, label27, label28, label29, label30}
        };
        
        Scanner infile = null;
        Scanner inStats = null;
		try
		{
		      infile = new Scanner(new FileReader("./src/application/words.txt"));
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("File not found");
			e.printStackTrace();
			System.exit(0);
		}

        while(infile.hasNextLine()) {
        	String line = infile.nextLine();
			StringTokenizer tokenizer = new StringTokenizer(line);
        	while(tokenizer.hasMoreTokens()) 
        	{word = tokenizer.nextToken();
			words.add(word);}

        	
        }
        infile.close( );
        chooseRandomWord();
        
        try
		{
		      inStats = new Scanner(new FileReader("./src/application/stats.txt"));
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("File not found");
			e.printStackTrace(); // prints error(s)
			System.exit(0); // Exits entire program
		}
        if (inStats.hasNextLine()) {
            gamesPlayed = Integer.parseInt(inStats.nextLine());
        }

        if (inStats.hasNextLine()) {
            winP = Integer.parseInt(inStats.nextLine());
        }

        if (inStats.hasNextLine()) {
            distOne = Integer.parseInt(inStats.nextLine());
        }
        if (inStats.hasNextLine()) {
            distTwo = Integer.parseInt(inStats.nextLine());
        }
        if (inStats.hasNextLine()) {
            distThree = Integer.parseInt(inStats.nextLine());
        }
        if (inStats.hasNextLine()) {
            distFour = Integer.parseInt(inStats.nextLine());
        }
        if (inStats.hasNextLine()) {
            distFive = Integer.parseInt(inStats.nextLine());
        }
        if (inStats.hasNextLine()) {
            distSix = Integer.parseInt(inStats.nextLine());
        }
        inStats.close( );
        gamesPlayed++;
    }
    

    @FXML
    public void input(KeyEvent event) {
        char character = event.getCharacter().charAt(0);
        if (Character.isLetter(character)) {
            // Make sure currentIndex doesn't exceed 30
            if (currentIndex < 30) {
                labels[currentIndex / 5][currentIndex % 5].setText(String.valueOf(Character.toUpperCase(character)));
                currentIndex++;
            }
        }
            
        else if(event.getCharacter().getBytes()[0] == '\n' || event.getCharacter().getBytes()[0] == '\r') {
        	System.out.println(character);
        	String currentText = getCurrentText().toLowerCase();
            boolean wordGuessed = false;
            // Check if the guessed word is in the list
            for (String officialWord : words) {
                if (officialWord.equals(currentText)) {
                    wordGuessed = true;
                    break;
                }
            }
            if (!wordGuessed){
            	text.setVisible(true);
                text.setText("word is not in word list");
            }
            else if(wordGuessed){
            	text.setVisible(false);
        		check();
            }
        }
        else if (event.getCharacter().getBytes()[0] == '\b'){ // Handle backspace
        	int currentRowStartIndex = row * 5;
            if (currentIndex > currentRowStartIndex) {
                currentIndex--; // Move back one index
                labels[currentIndex / 5][currentIndex % 5].setText(""); // Clear the text of the current label
                labels[currentIndex / 5][currentIndex % 5].requestLayout();
            }
        }
    }



    private void check() {
    	String currentText = getCurrentText().toLowerCase();
        guesses++;
        int correct = 0;
        for (int col = 0; col < 5; col++) {
            char c = currentText.charAt(col);
            Label label = labels[row][col];
            if (word.contains(String.valueOf(c))) {
                if (c == word.charAt(col)) {
                    label.setStyle("-fx-background-color: green;" + "-fx-text-fill: " + textC + ";" );
                    correct++;// Correct character in the correct spot\
                    updateButtonStyle(Character.toUpperCase(c), 1);
                } else {
                    label.setStyle("-fx-background-color: yellow;" + "-fx-text-fill: " + textC + ";"); // Correct character in the wrong spot
                    updateButtonStyle(Character.toUpperCase(c), 2);
                }
            } else {
                label.setStyle("-fx-background-color: grey;" + "-fx-text-fill: " + textC + ";"); // Character not in the word
                updateButtonStyle(Character.toUpperCase(c), 3);
            }
            label.setDisable(true);
            label.setOpacity(1);
        }
        
        if(guesses == 6 && correct != 5) {
        	text.setVisible(true);
            text.setText("Correct word was: " + word);
            endGame(true);
        }
        if(correct == 5) {
        	if(row == 0) {
        		distOne++;
        	}
        	if(row == 1) {
        		distTwo++;
        	}
        	if(row == 2) {
        		distThree++;
        	}
        	if(row == 3) {
        		distFour++;
        	}
        	if(row == 4) {
        		distFive++;
        	}
        	if(row == 5) {
        		distSix++;
        	}
        	winP++;
        	endGame(true);
        }
        row++;
        
    }
    
    public void resetBoard(ActionEvent event) {
    	// Clear all labels and reset their styles and interactivity
    	b.requestFocus();
    	currentIndex = 0;
        row = 0;
        guesses = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                labels[i][j].setText("");
                labels[i][j].setStyle("-fx-text-fill: " + textC + "; -fx-background-color: transparent; -fx-border-color: grey; -fx-border-width: 3px;");
            }
        }
        chooseRandomWord();
        gamesPlayed++;
        resetAllButtonStyles();
        
    }
    
    private String getCurrentText() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            builder.append(labels[row][i].getText());
        }
        return builder.toString();
    }
    
    private void chooseRandomWord() {
        Random random = new Random();
        word = words.get(random.nextInt(words.size()));
        System.out.println("Randomly chosen word: " + word);
    }
    
    private void endGame(boolean isWin) {
        updateStatsFile();
        float percent = (float)winP/gamesPlayed * 100;
        guess1.setText(Integer.toString(distOne));
        guess2.setText(Integer.toString(distTwo));
        guess3.setText(Integer.toString(distThree));
        guess4.setText(Integer.toString(distFour));
        guess5.setText(Integer.toString(distFive));
        guess6.setText(Integer.toString(distSix));
        gamesPlay.setText(Integer.toString(gamesPlayed));
        winPerc.setText(Float.toString(percent));
        board.setVisible(true);
    }
    
    public void exit(ActionEvent event) {
    	resetBoard(event);
        board.setVisible(false);
        
    }
    
    private void updateStatsFile() {
        try (FileWriter writer = new FileWriter("./src/application/stats.txt")) {
            // Write the updated statistics to the file
            writer.write(String.valueOf(gamesPlayed) + "\n");
            writer.write(String.valueOf(winP) + "\n");
            writer.write(String.valueOf(distOne) + "\n");
            writer.write(String.valueOf(distTwo) + "\n");
            writer.write(String.valueOf(distThree) + "\n");
            writer.write(String.valueOf(distFour) + "\n");
            writer.write(String.valueOf(distFive) + "\n");
            writer.write(String.valueOf(distSix) + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void switchColor(ActionEvent event) {
    	b.requestFocus();
        String currentStyle = gameBoard.getStyle();
        boolean isDark = currentStyle.contains("black");

        // Toggle the overall background and text color without affecting label backgrounds
        if (isDark) {
            // Switch to light theme
            gameBoard.setStyle("-fx-background-color: white;");
            gameb.setStyle("-fx-background-color: white;");
            updateLabelColors("black");  // Ensure this only updates text color
        } else {
            // Switch to dark theme
            gameBoard.setStyle("-fx-background-color: black;");
            gameb.setStyle("-fx-background-color: black;");
            updateLabelColors("white");  // Ensure this only updates text color
        }
    }

    private void updateLabelColors(String textColor) {
        // Update text color without affecting the background color used for feedback
    	textC = textColor;
        String style = "-fx-text-fill: " + textColor + ";";
        for (Label[] row : labels) {
            for (Label label : row) {
                // Preserve existing background color by not overwriting it
                label.setStyle(label.getStyle() + style);
            }
        }
    }

    @FXML
    private void nothing(ActionEvent event) {
        // Determine the current background color
    	b.setVisible(true);
    	b.requestFocus();
    }

    @FXML
    public void save(ActionEvent event) {
        try (FileWriter saveInfo = new FileWriter("./src/application/data.txt")) {
            // Save currentIndex
            saveInfo.write(String.valueOf(currentIndex));
            saveInfo.write(",");
            // Save the word being guessed
            saveInfo.write(word);
            saveInfo.write(",");
            // Save guesses
            saveInfo.write(String.valueOf(guesses));
            saveInfo.write(",");
            // Save the current row
            saveInfo.write(String.valueOf(row));
            saveInfo.write(",");
            // Save label inputs and checked status
            for (Label[] row : labels) {
                boolean isRowFilled = Arrays.stream(row)
                        .filter(label -> !label.getText().isEmpty())
                        .count() == 5; // Check if the row is filled with 5 letters
                for (Label label : row) {
                    String labelText = label.getText();
                    boolean isChecked = isRowFilled; // Set isChecked to true only if the row is filled with 5 letters
                    saveInfo.write(labelText + ":" + isChecked + ","); // Save label text and checked status
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void load(ActionEvent event) {
        b.requestFocus();
        try (Scanner loadInfo = new Scanner(new FileReader("./src/application/data.txt"))) {
            // Read the saved data from the file
            String[] savedData = loadInfo.nextLine().split(",");
            currentIndex = Integer.parseInt(savedData[0]);
            word = savedData[1];
            guesses = Integer.parseInt(savedData[2]); // Load the guesses
            row = Integer.parseInt(savedData[3]); // Load the row
            String[] labelInputs = Arrays.copyOfRange(savedData, 4, savedData.length);
            // Update the labels with the loaded inputs
            int index = 0;
            for (int i = 0; i < labels.length; i++) {
                StringBuilder checkedLabels = new StringBuilder();
                for (int j = 0; j < labels[i].length; j++) {
                    if (index < labelInputs.length) {
                        String[] labelData = labelInputs[index].split(":");
                        String labelText = labelData[0];
                        boolean isChecked = Boolean.parseBoolean(labelData[1]);

                        labels[i][j].setText(labelText);
                        // Set the label's checked status based on the loaded data
                        if (isChecked) {
                            checkedLabels.append(labelText.toLowerCase()).append("");
                        }
                        index++;
                    }
                }
                if (checkedLabels.length() == 5) {
                    for (int l = 0; l < 5; l++) {
                        char c = checkedLabels.charAt(l);
                        Label label = labels[i][l];
                        if (word.contains(String.valueOf(c))) {
                            if (c == word.charAt(l)) {
                                label.setStyle("-fx-background-color: green;" + "-fx-text-fill: " + textC + ";");
                            } else {
                                label.setStyle("-fx-background-color: yellow;" + "-fx-text-fill: " + textC + ";"); // Correct character in the wrong spot
                            }
                        } else {
                            label.setStyle("-fx-background-color: grey;" + "-fx-text-fill: " + textC + ";"); // Character not in the word
                        }
                        label.setDisable(true);
                        label.setOpacity(1);
                    }
                }
            }
            labels[row][currentIndex % 5].requestFocus();
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    private void simulateKeyPress(char character) {
        KeyEvent keyEvent = null;
        if (character == '\n' || character == '\r') {
            // Simulate Enter key press
            keyEvent = new KeyEvent(
                null,                          // Source
                null,                          // Target
                KeyEvent.KEY_PRESSED,          // Event type
                "",                            // Text is empty
                "",                            // Character is empty
                KeyCode.ENTER,                 // KeyCode for the Enter key
                false, false, false, false     // No modifier keys
            );
        } else {
            // Simulate other character key presses
            KeyCode keyCode = charToKeyCode(character);
            if (keyCode != KeyCode.UNDEFINED) {
                keyEvent = new KeyEvent(
                    null,                        // Source
                    null,                        // Target
                    KeyEvent.KEY_TYPED,
                    String.valueOf(character),   // Text
                    String.valueOf(character),   // Character
                    KeyCode.UNDEFINED,           // KEY_TYPED events have no KeyCode
                    false, false, false, false   // No modifier keys
                );
            }
        }
        input(keyEvent);  // Pass the event to the input method
    }
    private KeyCode charToKeyCode(char character) {
        switch (Character.toUpperCase(character)) {
            case 'A': return KeyCode.A;
            case 'B': return KeyCode.B;
            case 'C': return KeyCode.C;
            case 'D': return KeyCode.D;
            case 'E': return KeyCode.E;
            case 'F': return KeyCode.F;
            case 'G': return KeyCode.G;
            case 'H': return KeyCode.H;
            case 'I': return KeyCode.I;
            case 'J': return KeyCode.J;
            case 'K': return KeyCode.K;
            case 'L': return KeyCode.L;
            case 'M': return KeyCode.M;
            case 'N': return KeyCode.N;
            case 'O': return KeyCode.O;
            case 'P': return KeyCode.P;
            case 'Q': return KeyCode.Q;
            case 'R': return KeyCode.R;
            case 'S': return KeyCode.S;
            case 'T': return KeyCode.T;
            case 'U': return KeyCode.U;
            case 'V': return KeyCode.V;
            case 'W': return KeyCode.W;
            case 'X': return KeyCode.X;
            case 'Y': return KeyCode.Y;
            case 'Z': return KeyCode.Z;
            default: return KeyCode.UNDEFINED;
        }
    }
    @FXML
    public void handleAButton(ActionEvent event) {
        simulateKeyPress('A');
    }
    @FXML
    public void handleBButton(ActionEvent event) {
        simulateKeyPress('B');
    }
    @FXML
    public void handleCButton(ActionEvent event) {
        simulateKeyPress('C');
    }
    @FXML
    public void handleDButton(ActionEvent event) {
        simulateKeyPress('D');
    }
    @FXML
    public void handleEButton(ActionEvent event) {
        simulateKeyPress('E');
    }
    @FXML
    public void handleFButton(ActionEvent event) {
        simulateKeyPress('F');
    }
    @FXML
    public void handleGButton(ActionEvent event) {
        simulateKeyPress('G');
    }
    @FXML
    public void handleHButton(ActionEvent event) {
        simulateKeyPress('H');
    }
    @FXML
    public void handleIButton(ActionEvent event) {
        simulateKeyPress('I');
    }
    @FXML
    public void handleJButton(ActionEvent event) {
        simulateKeyPress('J');
    }
    @FXML
    public void handleKButton(ActionEvent event) {
        simulateKeyPress('K');
    }
    @FXML
    public void handleLButton(ActionEvent event) {
        simulateKeyPress('L');
    }
    @FXML
    public void handleMButton(ActionEvent event) {
        simulateKeyPress('M');
    }
    @FXML
    public void handleNButton(ActionEvent event) {
        simulateKeyPress('N');
    }
    @FXML
    public void handleOButton(ActionEvent event) {
        simulateKeyPress('O');
    }
    @FXML
    public void handlePButton(ActionEvent event) {
        simulateKeyPress('P');
    }
    @FXML
    public void handleQButton(ActionEvent event) {
        simulateKeyPress('Q');
    }
    @FXML
    public void handleRButton(ActionEvent event) {
        simulateKeyPress('R');
    }
    @FXML
    public void handleSButton(ActionEvent event) {
        simulateKeyPress('S');
    }
    @FXML
    public void handleTButton(ActionEvent event) {
        simulateKeyPress('T');
    }
    @FXML
    public void handleUButton(ActionEvent event) {
        simulateKeyPress('U');
    }
    @FXML
    public void handleVButton(ActionEvent event) {
        simulateKeyPress('V');
    }
    @FXML
    public void handleWButton(ActionEvent event) {
        simulateKeyPress('W');
    }
    @FXML
    public void handleXButton(ActionEvent event) {
        simulateKeyPress('X');
    }
    @FXML
    public void handleYButton(ActionEvent event) {
        simulateKeyPress('Y');
    }
    @FXML
    public void handleZButton(ActionEvent event) {
        simulateKeyPress('Z');
    }
    
    private void updateButtonStyle(char character, int isInWord) {
        Button button = getButtonByChar(character);
        if (button != null) {
        	if (isInWord == 1) {
        		button.setStyle("-fx-background-color: green;" + "-fx-text-fill: " + textC + ";");
        	}
            if (isInWord == 2) {
                button.setStyle("-fx-background-color: yellow;" + "-fx-text-fill: " + textC + ";");
            } 
            else if(isInWord == 3) {
                button.setStyle("-fx-background-color: #424242;" + "-fx-text-fill: " + textC + ";");  // Reset style or set to another style if needed
            }
        }
    }

    private Button getButtonByChar(char character) {
        switch (character) {
            case 'A': return A;
            case 'B': return B;
            case 'C': return C;
            case 'D': return D;
            case 'E': return E;
            case 'F': return F;
            case 'G': return G;
            case 'H': return H;
            case 'I': return I;
            case 'J': return J;
            case 'K': return K;
            case 'L': return L;
            case 'M': return M;
            case 'N': return N;
            case 'O': return O;
            case 'P': return P;
            case 'Q': return Q;
            case 'R': return R;
            case 'S': return S;
            case 'T': return T;
            case 'U': return U;
            case 'V': return V;
            case 'W': return W;
            case 'X': return X;
            case 'Y': return Y;
            case 'Z': return Z;
            default: return null; // No button for non-alphabet character
        }
    }

    private void resetAllButtonStyles() {
        Button[] buttons = {A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z};
        for (Button button : buttons) {
            button.setStyle("-fx-background-color: #A4A4A4; -fx-text-fill: " + textC + ";");
        }
    }

}

