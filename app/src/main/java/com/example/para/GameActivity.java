package com.example.para;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameActivity extends AppCompatActivity {
    TextView tvQuestion, tvPrize;
    Button btnA, btnB, btnC, btnD, btnNext;
    Connection connect;
    String ConnectionResult="";
    public String correctAnswer="";
    Set<Integer> uniqueNumbers = new HashSet<>();
    public int j=0;
    public String[] prizes = {"500$", "1,000$", "2,000$", "3,000$", "5,000$", "7,000$", "10,000$"
            , "20,000$", "30,000$", "50,000$", "100,000$", "250,000$", "500,000$", "1,000,000$" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_game);
        tvPrize=(TextView)findViewById(R.id.tvPrize);
        Intent intent = getIntent();

            btnNext=(Button) findViewById(R.id.btnNext);
            uniqueNumbers.clear();

            tvPrize.setText(prizes[j]);
            sqlGetText();

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnA.setBackgroundColor(getResources().getColor(R.color.gradient));
                    btnB.setBackgroundColor(getResources().getColor(R.color.gradient));
                    btnC.setBackgroundColor(getResources().getColor(R.color.gradient));
                    btnD.setBackgroundColor(getResources().getColor(R.color.gradient));
                    btnNext.setVisibility(View.INVISIBLE);
                    j++;
                    tvPrize.setText(prizes[j]);
                    sqlGetText();
                }
            });


    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(GameActivity.this, ResultActivity.class);
        GameActivity.this.startActivity(myIntent);
    }

    public void sqlGetText(){
        int rand = 0;
        Random random = new Random();


            int randomNumber = random.nextInt(25) + 1;
            while (uniqueNumbers.contains(randomNumber)) {
                randomNumber = random.nextInt(25) + 1;
            }
            uniqueNumbers.add(randomNumber);
            rand=randomNumber;

        tvQuestion= (TextView) findViewById(R.id.tvQuestion);
        btnA = (Button) findViewById(R.id.btnA);
        btnB = (Button) findViewById(R.id.btnB);
        btnC = (Button) findViewById(R.id.btnC);
        btnD = (Button) findViewById(R.id.btnD);

        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if(connect!=null){
                String query = " Select * from Pitanja where ID='"+rand+"'";
                Statement st=connect.createStatement();
                ResultSet rs= st.executeQuery(query);

                while (rs.next()){
                    tvQuestion.setText(rs.getString(2));
                    btnA.setText(rs.getString(3));
                    btnB.setText(rs.getString(4));
                    btnC.setText(rs.getString(5));
                    btnD.setText(rs.getString(6));
                    correctAnswer=rs.getString(7);

                }
            }
            else{
                ConnectionResult="Check Connection";
            }
        }
        catch (Exception ex){

        }
        gotAnswer(correctAnswer);
    }
    public void gotAnswer(String CORRECT){
        btnA = (Button) findViewById(R.id.btnA);
        btnB = (Button) findViewById(R.id.btnB);
        btnC = (Button) findViewById(R.id.btnC);
        btnD = (Button) findViewById(R.id.btnD);


        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CORRECT.equals("A")){
                    if(j<13) {
                        tvQuestion.setText("Točan odgovor");
                        btnNext.setVisibility(View.VISIBLE);
                        btnA.setBackgroundColor(getResources().getColor(R.color.correct));
                    }
                    else{
                        wongame();
                    }
                }
                else{
                    btnA.setBackgroundColor(getResources().getColor(R.color.incorrect));
                    questionCheck();
                }
            }
        });
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CORRECT.equals("B")){
                    if(j<13) {
                        tvQuestion.setText("Točan odgovor");
                        btnNext.setVisibility(View.VISIBLE);
                        btnB.setBackgroundColor(getResources().getColor(R.color.correct));
                    }
                    else{
                        wongame();
                    }
                }
                else{
                    btnB.setBackgroundColor(getResources().getColor(R.color.incorrect));
                    questionCheck();
                }
            }
        });
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CORRECT.equals("C")){
                    if(j<13) {
                        tvQuestion.setText("Točan odgovor");
                        btnNext.setVisibility(View.VISIBLE);
                        btnC.setBackgroundColor(getResources().getColor(R.color.correct));
                    }
                    else{
                        wongame();
                    }
                }
                else{
                    btnC.setBackgroundColor(getResources().getColor(R.color.incorrect));
                    questionCheck();

                }
            }
        });
        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CORRECT.equals("D")){
                    if(j<13) {
                        tvQuestion.setText("Točan odgovor");
                        btnNext.setVisibility(View.VISIBLE);
                        btnD.setBackgroundColor(getResources().getColor(R.color.correct));
                    }
                    else{
                        wongame();
                    }
                }
                else{
                    btnD.setBackgroundColor(getResources().getColor(R.color.incorrect));
                    questionCheck();

                }
            }
        });

    }
    public void disableClickable(){
        btnA.setClickable(false);
        btnB.setClickable(false);
        btnC.setClickable(false);
        btnD.setClickable(false);
    }
    public void highlightCorrect(String CORRECT){
        if(CORRECT.equals("A")){
            btnA.setBackgroundColor(getResources().getColor(R.color.correct));
            disableClickable();
        }
        else if (CORRECT.equals("B")){
            btnB.setBackgroundColor(getResources().getColor(R.color.correct));
            disableClickable();
        }
        else if (CORRECT.equals("C")){
            btnC.setBackgroundColor(getResources().getColor(R.color.correct));
            disableClickable();
        }
        else if (CORRECT.equals("D")){
            btnD.setBackgroundColor(getResources().getColor(R.color.correct));
            disableClickable();
        }
    }
    public void wongame(){
        tvQuestion.setText("Čestitamo postali ste milijunaš!");
        btnA.setBackgroundColor(getResources().getColor(R.color.gold));
        btnB.setBackgroundColor(getResources().getColor(R.color.gold));
        btnC.setBackgroundColor(getResources().getColor(R.color.gold));
        btnD.setBackgroundColor(getResources().getColor(R.color.gold));
        btnNext.setVisibility(View.INVISIBLE);
        disableClickable();
    }
    public void questionCheck(){
        if(j<4){
            tvQuestion.setText("Netočan odgovor");
            highlightCorrect(correctAnswer);
        }
        else if(4<j && j<9){
            tvQuestion.setText("Čestitamo osvojili ste " + prizes[4]);
            highlightCorrect(correctAnswer);
        }
        else if(9<j && j<13){
            tvQuestion.setText("Čestitamo osvojili ste " + prizes[9]);
            highlightCorrect(correctAnswer);
        }
    }

}