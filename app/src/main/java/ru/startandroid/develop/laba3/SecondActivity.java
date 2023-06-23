package ru.startandroid.develop.laba3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static com.arithmeticparser.MathExpressionAnalyzer.*;

import java.text.DecimalFormat;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    public static EditText numberField;
    TextView resultField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        numberField = (EditText) findViewById(R.id.numberField);
        resultField =(TextView) findViewById(R.id.resultField);
    }

    public void calcResult(View view){
        String strResult = numberField.getText().toString();
        List<Lexeme> lexemes = lexAnalyze(strResult);
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        String formatedStr = decimalFormat.format(expr(lexemeBuffer));
        resultField.setText(formatedStr);
    }
    public void OnAC_click(View view){
        numberField.setText(null);
        resultField.setText(null);
    }
}