package android.tovalina.crystalball;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.Random;

public class Predictions {

    private static Predictions predictions; //setting variables
    private String[] answers;

    private Predictions() {
        answers = new String[] { //contains all predictions
                "Your wishes will come true.",
                "Your wishes will NEVER come true.",
                "SHARKISHA SAYS NO",
                "Your wish is my command",
                "MEGAN IS LAME",
                "Try Again Later",
                "Why you got to be so rude?",
                "I don't know"
        };
    }

    public static Predictions get() { //Lets the prediction be able to be used after the intial start of the program
        if(predictions == null) {
            predictions = new Predictions();
        }
        return predictions;
    }

    public String getPrediction() { //determines which fortune will show on the screen
        int idx = new Random().nextInt(answers.length);
        String random = (answers[idx]);
        return random;

    }
}