package ru.startandroid.develop.laba3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import static com.arithmeticparser.MathExpressionAnalyzer.*;
import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView resultField,operationField;
    EditText numberField;
    Double operand = null;
    String lastOperation = "=";
    Button[] button = new Button[9];
    public static final String ACTION ="Second";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultField = (TextView) findViewById(R.id.resultField);
        numberField = (EditText) findViewById(R.id.numberField);
        operationField =(TextView) findViewById(R.id.operationField);
        for (int i = 1; i <button.length ; i++) {
           String strId = "button"+String.valueOf(i);
           int resId = getResources().getIdentifier(strId, "id",getPackageName());
           button[i] = (Button) findViewById(resId);
           button[i].setTextColor(getResources().getColor(R.color.gray));
           button[i].setEnabled(false);
        }
        numberField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().length()==0 && resultField.length()==0){
                    for (int i = 1; i < button.length ; i++) {
                        button[i].setTextColor(getResources().getColor(R.color.gray));
                        button[i].setEnabled(false);
                    }
                } else {
                    for (int i = 1; i < button.length ; i++) {
                        button[i].setTextColor(getResources().getColor(R.color.orange));
                        button[i].setEnabled(true);
                    }
                }
            }
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putString("OPERATION",lastOperation);
        if(operand!=null){
            outState.putDouble("OPERAND",operand);
        }
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand = savedInstanceState.getDouble("OPERAND");
        resultField.setText(operand.toString());
        operationField.setText(lastOperation);
    }
    public void onNumberClick(View view){
        Button button = (Button)view;
        numberField.append(button.getText());
        if(lastOperation.equals("=") && operand!=null){
            operand = null;
        }
    }
    public void onOperationClick(View view){
        Button button = (Button)view;
        String op = button.getText().toString();
        String number = numberField.getText().toString();
        if(number.length()>0){
            number = number.replace(',','.');
            try{
                performOperation(Double.valueOf(number),op);
            }catch(NumberFormatException ex){
                numberField.setText("");
            }
        }
        lastOperation = op;
        operationField.setText(lastOperation);
    }
    public void OnAC_click(View view){
        operationField.setText(null);
        resultField.setText(null);
        numberField.setText(null);
    }
    public void onRemoveLastNumber(View view){
        int lengthOfNumberField = numberField.getText().length();
        if(lengthOfNumberField>0){
            numberField.getText().delete(lengthOfNumberField-1,lengthOfNumberField);
        }
    }
    private void performOperation(Double number, String operation){
        if(operand==null){
            operand = number;
        }
        else{
            if(lastOperation.equals("=")){
                lastOperation = operation;
            }
            switch (lastOperation){
                case "=":
                    operand = number;
                    break;
                case "/":
                    if(number==0){
                        operand = null;
                    }
                    operand /= number;
                    break;
                case "*":
                    operand *=number;
                    break;
                case "+":
                    operand += number;
                    break;
                case "-":
                    operand -= number;
                    break;
                case "^":
                   operand = Math.pow(operand,number);
                    break;
                case "_/~":
                    operand = Math.pow(operand,1/number);
                    break;
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        resultField.setText(decimalFormat.format(operand).replace('.',','));
        //resultField.setText(operand.toString().replace('.',','));
        numberField.setText("");
    }
    public void startNewActivity(View v) {
        Intent intent = new Intent(this,SecondActivity.class);
        startActivity(intent);
    }
}