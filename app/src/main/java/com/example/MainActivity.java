package com.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    private int[] numericButtonsID = {R.id.btnZero, R.id.btnOne, R.id.btnTwo, R.id.btnThree, R.id.btnFour, R.id.btnFive, R.id.btnSix, R.id.btnSeven, R.id.btnEight, R.id.btnNine};
    private int[] operatorButtonsID = {R.id.btnAdd, R.id.btnMultiply, R.id.btnDivide};
    private TextView txtScreen;
    boolean isEqualPressed;
    boolean isNumberPressed;
    boolean isDotPressed;
    boolean isOperatorPressed;
    boolean isSubtractPressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resetAllFlags();
        this.txtScreen = (TextView) findViewById(R.id.resutlTxt);
        setNumbersOnclickListener();
        setOperatorsOnclickListener();
        setDotClearEqualOnclickListener();

    }

    private void setDotClearEqualOnclickListener(){

        findViewById(R.id.btnDot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isEqualPressed)
                    txtScreen.setText("");
                if(!isDotPressed){
                    txtScreen.append(".");
                    isDotPressed = true;
                    isNumberPressed = false;
                    isSubtractPressed = true;
                    isEqualPressed = false;
                }
            }
        });

        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtScreen.setText("");
                resetAllFlags();
               /* if(isDotPressed || isNumberPressed || isOperatorPressed) {
                    String temp = txtScreen.getText().toString();
                    temp = temp.substring(0, Math.max(0, temp.length() - 1));
                    txtScreen.setText(temp);
                }*/
            }
        });

        findViewById(R.id.eqlButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEqualPressed = true;
                resetAllFlags();
                try {
                    getResult();
                }catch(Exception e){txtScreen.setText("Error");}
            }
        });
    }

    //Operator Onclick Listener
    private void setOperatorsOnclickListener(){

        View.OnClickListener operators = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button)view;
                if(isNumberPressed){
                    txtScreen.append(button.getText());
                    isNumberPressed = false;
                    isDotPressed = false;
                    if(button.getText().toString().equalsIgnoreCase("+")){
                         isSubtractPressed = true;
                    }

                }
            }
        };

        for(int id: operatorButtonsID){

            findViewById(id).setOnClickListener(operators);
        }

        //subtract onclick listener
        findViewById(R.id.btnSubtract).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isEqualPressed)
                    txtScreen.setText("");
                if(!isSubtractPressed){
                    Button button = (Button)view;
                    txtScreen.append(button.getText());
                    isSubtractPressed = true;
                    isDotPressed = false;
                    isNumberPressed = false;
                    isEqualPressed = false;
                }
            }
        });


    }//end of setOperatorsOnclickListener method


    private void setNumbersOnclickListener(){

        View.OnClickListener numbers = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button button = (Button) view;
                if(isEqualPressed){
                    txtScreen.setText("");
                }
                txtScreen.append(button.getText());
                isNumberPressed = true;
                isEqualPressed = false;
                isSubtractPressed = false;
            }
        };

        for(int id: numericButtonsID){

            findViewById(id).setOnClickListener(numbers);
        }
    }//end of setButtonsOnclickListeners


    private void getResult() throws Exception{

        String txt = txtScreen.getText().toString();
        Expression expression = new ExpressionBuilder(txt).build();
        double result = expression.evaluate();

        String tempResult = Double.toString(result);
        String[] tokens = tempResult.split("\\.");
        if(tokens[1].equalsIgnoreCase("0")){
            txtScreen.setText(tokens[0]);
            return;
        }
        txtScreen.setText(Double.toString(result));

    }

    private void resetAllFlags(){

         isNumberPressed = false;
         isDotPressed = false;
         isOperatorPressed = false;
         isSubtractPressed = false;
    }
}
