package com.example.para;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameActivity extends AppCompatActivity {
    TextView tvQuestion;
    Button btnA, btnB, btnC, btnD, btnNext;
    Connection connect;
    String ConnectionResult="";
    public String correctAnswer="";
    Set<Integer> uniqueNumbers = new HashSet<>();
    public int j;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
            btnNext=(Button) findViewById(R.id.btnNext);
            uniqueNumbers.clear();
            j=1;
            sqlGetText();



            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnNext.setVisibility(View.INVISIBLE);
                    j++;
                    if(j<5){
                        sqlGetText();
                    }
                    else{
                        tvQuestion.setText("you won");
                        btnA.setClickable(false);
                        btnB.setClickable(false);
                        btnC.setClickable(false);
                        btnD.setClickable(false);

                    }
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


            int randomNumber = random.nextInt(10) + 1;
            while (uniqueNumbers.contains(randomNumber)) {
                randomNumber = random.nextInt(10) + 1;
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
                    tvQuestion.setText("CORRECT");
                    btnNext.setVisibility(View.VISIBLE);
                }
                else{
                    tvQuestion.setText("INCORRECT");
                }
            }
        });
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CORRECT.equals("B")){
                    tvQuestion.setText("CORRECT");
                    btnNext.setVisibility(View.VISIBLE);
                }
                else{
                    tvQuestion.setText("INCORRECT");
                }
            }
        });
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CORRECT.equals("C")){
                    tvQuestion.setText("CORRECT");
                    btnNext.setVisibility(View.VISIBLE);
                }
                else{
                    tvQuestion.setText("INCORRECT");
                }
            }
        });
        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CORRECT.equals("D")){
                    tvQuestion.setText("CORRECT");
                    btnNext.setVisibility(View.VISIBLE);
                }
                else{
                    tvQuestion.setText("INCORRECT");
                }
            }
        });

    }

}