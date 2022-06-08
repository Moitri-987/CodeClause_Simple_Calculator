package android.example.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Reader;
import java.util.ArrayList;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity {

    TextView results;
    TextView workings;
    String workingsletter = "";
    String formula = "";
    String tempFormula = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTextViews();
    }

    private void initTextViews(){
        workings=findViewById(R.id.workingsTextView);
        results=findViewById(R.id.resultsTextView);
    }

    private void setWorkingsletter(String givenValue)
    {
        workingsletter+=givenValue;
        workings.setText(workingsletter);
    }

    public void equalOnClick(View view){
        Double result = null;
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
        checkForPowerOf();

        try {
            result = (double) engine.eval(formula);
        }
        catch (ScriptException e){
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
        }

        if (result!=null)
            results.setText(String.valueOf(result.doubleValue()));
    }

    private void checkForPowerOf() {
        ArrayList<Integer> indexOfPowers = new ArrayList<>();
        for (int i=0; i<workingsletter.length(); i++){
            if (workingsletter.charAt(i)=='^')
                indexOfPowers.add(i);
        }

        formula = workingsletter;
        tempFormula = workingsletter;
        for(Integer index: indexOfPowers){
            changeFormula(index);
        }
        formula=tempFormula;
    }

    private void changeFormula(Integer index) {
        String numberLeft = "";
        String numberRight = "";

        for (int i=index + 1; i<workingsletter.length(); i++){
            if (isNumeric(workingsletter.charAt(i)))
                numberRight+=workingsletter.charAt(i);
            else
                break;
        }

        for (int i=index - 1; i>=0; i--){
            if (isNumeric(workingsletter.charAt(i)))
                numberLeft+=workingsletter.charAt(i);
            else
                break;
        }

        String original = numberLeft+ "^" +numberRight;
        String changed = "Math.pow("+numberLeft+","+numberRight+")";
        tempFormula=tempFormula.replace(original,changed);
    }

    private boolean isNumeric(char charAt) {
        if ((charAt<='9' && charAt>='0') || charAt=='.')
            return true;

        return false;
    }

    public void clearOnClick(View view){
        workings.setText("");
        workingsletter="";
        results.setText("");
        leftBracket=true;
    }

    boolean leftBracket = true;

    public void bracketOnClick(View view){
        if (leftBracket){
            setWorkingsletter("(");
            leftBracket=false;
        }
        else{
            setWorkingsletter(")");
            leftBracket=true;
        }
    }

    public void powerOnClick(View view){
        setWorkingsletter("^");
    }

    public void divisionOnClick(View view){
        setWorkingsletter("/");
    }

    public void sevenOnClick(View view){
        setWorkingsletter("7");
    }

    public void eightOnClick(View view){
        setWorkingsletter("8");
    }

    public void nineOnClick(View view){
        setWorkingsletter("9");
    }

    public void multiplyOnClick(View view){
        setWorkingsletter("*");
    }

    public void fourOnClick(View view){
        setWorkingsletter("4");
    }

    public void fiveOnClick(View view){
        setWorkingsletter("5");
    }

    public void sixOnClick(View view){
        setWorkingsletter("6");
    }

    public void subtractOnClick(View view){
        setWorkingsletter("-");
    }

    public void threeOnClick(View view){
        setWorkingsletter("3");
    }

    public void twoOnClick(View view){
        setWorkingsletter("2");
    }

    public void oneOnClick(View view){
        setWorkingsletter("1");
    }

    public void addOnClick(View view){
        setWorkingsletter("+");
    }

    public void decimalOnClick(View view){
        setWorkingsletter(".");
    }

    public void zeroOnClick(View view){
        setWorkingsletter("0");
    }
}