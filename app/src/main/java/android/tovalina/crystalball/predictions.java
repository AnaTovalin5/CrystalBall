package android.tovalina.crystalball;

import java.util.Random;

public class Predictions {

    private static Predictions predictions; //setting variables
    private String[] answers;

    private Predictions() {
        answers = new String[] { //contains all predictions
                "Your wishes will come true.",
                "Your wishes will NEVER come true."
        };
    }

    public static Predictions get() { //Lets the prediction be able to be used after the intial start of the program
        if(predictions == null) {
            predictions = new Predictions();
        }
        return predictions;
    }

    public String getPrediction() { //determines which fortune will show on the screen
        Random rand = new Random();
        int n = rand.nextInt();
        return answers[1];
    }
}
