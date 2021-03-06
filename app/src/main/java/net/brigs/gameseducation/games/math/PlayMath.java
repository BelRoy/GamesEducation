package net.brigs.gameseducation.games.math;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import net.brigs.gameseducation.R;
import net.brigs.gameseducation.games.math.Score;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PlayMath extends Activity implements View.OnClickListener {

    private int level = 0, answer = 0, operator = 0, operand1 = 0, operand2 = 0;
    private final int ADD = 0, SUBTRACT= 1, MULTIPLY = 2, DIVIDE = 3;
    private String[] operators = { "+", "-", "x", "/" };
    private int[][] levelMin = { { 1, 11, 21 }, { 1, 5, 10 }, { 2, 5, 10 }, { 2, 3, 5 } };
    private int[][] levelMax = { { 10, 25, 50 }, { 10, 20, 30 }, { 5, 10, 15 }, { 10, 50, 100 } };
    private Random random;
    private TextView question, answerTxt, scoreTxt;
    private ImageView response;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0,
            enterBtn, clearBtn;

    private SharedPreferences gamePrefs;
    public static final String GAME_PREFS = "ArithmeticFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_play_math);

        gamePrefs = getSharedPreferences(GAME_PREFS, 0);

        question = findViewById(R.id.question);
        answerTxt = findViewById(R.id.answer);
        response = findViewById(R.id.response);
        scoreTxt = findViewById(R.id.score);
        response.setVisibility(View.INVISIBLE);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn0 = findViewById(R.id.btn0);
        enterBtn = findViewById(R.id.enter);
        clearBtn = findViewById(R.id.clear);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn0.setOnClickListener(this);
        enterBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int passedLevel = extras.getInt("level", -1);
            if (passedLevel >= 0)
                level = passedLevel;
        }

        random = new Random();
        chooseQuestion();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.enter) {

            String answerContent = answerTxt.getText().toString();
            if(!answerContent.endsWith("?"))
            {
                //we have an answer
                int enteredAnswer = Integer.parseInt(answerContent.substring(2));
                int exScore = getScore();
                if(enteredAnswer==answer){
                    //correct
                    scoreTxt.setText("Score: "+(exScore+1));
                    response.setImageResource(R.drawable.ok);
                    response.setVisibility(View.VISIBLE);
                }else{
                    //incorrect
                    scoreTxt.setText("Score: 0");
                    response.setImageResource(R.drawable.error);
                    response.setVisibility(View.VISIBLE);
                }
                chooseQuestion();
            }
        } else if (v.getId() == R.id.clear) {
            // clear button
            answerTxt.setText("= ?");
        } else {
            // number button
            response.setVisibility(View.INVISIBLE);
            int enteredNum = Integer.parseInt(v.getTag().toString());
            if (answerTxt.getText().toString().endsWith("?"))
                answerTxt.setText("= " + enteredNum);
            else
                answerTxt.append("" + enteredNum);
        }
    }

    private void chooseQuestion() {
        // get a question
        answerTxt.setText("= ?");
        operator = random.nextInt(operators.length);
        operand1 = getOperand();
        operand2 = getOperand();

        if (operator == SUBTRACT) {
            while (operand2 > operand1) {
                operand1 = getOperand();
                operand2 = getOperand();
            }
        } else if (operator == DIVIDE) {
            while ((((double) operand1 / (double) operand2) % 1 > 0)
                    || (operand1 == operand2)) {
                operand1 = getOperand();
                operand2 = getOperand();
            }
        }

        switch (operator) {
            case ADD:
                answer = operand1 + operand2;
                break;
            case SUBTRACT:
                answer = operand1 - operand2;
                break;
            case MULTIPLY:
                answer = operand1 * operand2;
                break;
            case DIVIDE:
                answer = operand1 / operand2;
                break;
            default:
                break;
        }

        question.setText(operand1 + " " + operators[operator] + " " + operand2);
    }

    private int getOperand() {
        // return operand number
        return random.nextInt(levelMax[operator][level]
                - levelMin[operator][level] + 1)
                + levelMin[operator][level];
    }

    private int getScore(){
        String scoreStr = scoreTxt.getText().toString();
        return Integer.parseInt(scoreStr.substring(scoreStr.lastIndexOf(" ")+1));
    }

    private void setHighScore(){
        int exScore = getScore();
        if(exScore>0){
            //we have a valid score
            SharedPreferences.Editor scoreEdit = gamePrefs.edit();
            DateFormat dateForm = new SimpleDateFormat("dd MMMM yyyy");
            String dateOutput = dateForm.format(new Date());
            //get existing scores
            String scores = gamePrefs.getString("highScores", "");
            //check for scores
            if(scores.length()>0){
                //we have existing scores
                List<Score> scoreStrings = new ArrayList<Score>();
                //split scores
                String[] exScores = scores.split("\\|");
                //add score object for each
                for(String eSc : exScores){
                    String[] parts = eSc.split(" - ");
                    scoreStrings.add(new Score(parts[0], Integer.parseInt(parts[1])));
                }
                //new score
                Score newScore = new Score(dateOutput, exScore);
                scoreStrings.add(newScore);
                //sort
                Collections.sort(scoreStrings);
                //get top ten
                StringBuilder scoreBuild = new StringBuilder("");
                for(int s=0; s<scoreStrings.size(); s++){
                    if(s>=10) break;
                    if(s>0) scoreBuild.append("|");
                    scoreBuild.append(scoreStrings.get(s).getScoreText());
                }
                //write to prefs
                scoreEdit.putString("highScores", scoreBuild.toString());
                scoreEdit.commit();

            }
            else{
                //no existing scores
                scoreEdit.putString("highScores", ""+dateOutput+" - "+exScore);
                scoreEdit.commit();
            }
        }
    }

    //set high score if activity destroyed
    protected void onDestroy(){
        setHighScore();
        super.onDestroy();
    }

    //save instance state
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //save score and level
        int exScore = getScore();
        savedInstanceState.putInt("score", exScore);
        savedInstanceState.putInt("level", level);
        //superclass method
        super.onSaveInstanceState(savedInstanceState);
    }


}
