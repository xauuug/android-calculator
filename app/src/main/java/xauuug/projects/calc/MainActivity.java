package xauuug.projects.calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String result;
    TextView tvResult;
    Button btnDel;
    List<String> exp;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.result = "0";
        tvResult = (TextView)findViewById(R.id.tv_result);
        tvResult.setText(this.result);
        btnDel = (Button)findViewById(R.id.btnDel);
        this.number = "";
        this.exp = new ArrayList<String>();
    }

    public void onClickNumbers(View v) {
        switch (v.getId()) {
            case R.id.btn0: this.number += "0"; break;
            case R.id.btn1: this.number += "1"; break;
            case R.id.btn2: this.number += "2"; break;
            case R.id.btn3: this.number += "3"; break;
            case R.id.btn4: this.number += "4"; break;
            case R.id.btn5: this.number += "5"; break;
            case R.id.btn6: this.number += "6"; break;
            case R.id.btn7: this.number += "7"; break;
            case R.id.btn8: this.number += "8"; break;
            case R.id.btn9: this.number += "9"; break;
        }

        tvResult.setText(this.expToString()+this.number);
    }

    public void onActionsClick(View v) {
        switch (v.getId()) {
            case R.id.btnDel:
                if (number.length() > 0 ) {
                    try {
                        number = number.substring(0, number.length() - 1);
                        Float.parseFloat(number);
                    } catch (Exception e){
                        number = "";
                    }
                } else {
                    if (this.exp.size() > 0) {
                        try {
                            Float.parseFloat(this.exp.get(this.exp.size()-1));
                            number = this.exp.get(this.exp.size()-1);
                            number = number.substring(0,number.length()-1);
                            this.exp.remove(this.exp.size()-1);
                        } catch (Exception e) {
                            this.exp.remove(this.exp.size()-1);
                            this.number = this.exp.get(this.exp.size()-1);
                            this.exp.remove(this.exp.size()-1);
                        }
                    }
                }
        }

        tvResult.setText(this.expToString()+this.number);
    }

    public void onOpClick(View v) {
        String op = "";
        switch (v.getId()) {
            case R.id.btnAdd: op = "+"; break;
            case R.id.btnSub: op = "-"; break;
            case R.id.btnMul: op = "*"; break;
            case R.id.btnDiv: op = "/"; break;
        }
        if (this.exp.size() > 0) {
            try {
                Float.parseFloat(this.exp.get(this.exp.size() - 1));
                this.exp.add(op);
            } catch (Exception e) {
                if (number.length() > 0) {
                    this.exp.add(number);
                    this.exp.add(op);
                    this.number = "";
                } else {
                    this.exp.set(this.exp.size() - 1, op);
                }

            }
        }else {
            if (this.number.length() > 0) {
                this.exp.add(this.number);
                this.exp.add(op);
                this.number = "";
            }
        }

        tvResult.setText(expToString());
    }

    public String expToString() {
        String str = "";
        for (String i : this.exp) {
            str += i;
        }
        Log.i("debug","N: "+number);
        Log.i("debug","L: "+this.exp.toString());
        return str;
    }

    public void onDot(View v) {
        if (number.length() > 0 && number.indexOf(".") == -1) {
            number += ".";
        }

        tvResult.setText(this.expToString()+this.number);
    }

    public void onEnter(View v) {
        if (this.exp.size() > 0) {
            try {
                Float.parseFloat(this.exp.get(this.exp.size()-1));
            } catch (Exception e) {
                if (number == "") {
                    this.exp.remove(this.exp.get(this.exp.size() - 1));
                } else {
                    this.exp.add(number);
                    number = "";
                }
            }
            solveExp();
        }
        tvResult.setText(this.expToString()+this.number);
    }

    public void solveExp() {
        Log.i("solve","SOLVE START"+this.exp.size());
        while(this.exp.size() > 1) {

            Log.i("solve","S: "+this.exp.toString());

            Integer mul = this.exp.indexOf("*");
            Integer div = this.exp.indexOf("/");
            Integer add = this.exp.indexOf("+");
            Integer sub = this.exp.indexOf("-");

            Float res = new Float(0.0);

            if (mul != -1) {
                res = Float.parseFloat(this.exp.get(mul-1)) * Float.parseFloat(this.exp.get(mul+1));
                this.exp.remove(mul+1);
                this.exp.set(mul, Float.toString(res));
                this.exp.remove(mul-1);
                continue;
            }

            if (div != -1) {
                res = Float.parseFloat(this.exp.get(div-1)) / Float.parseFloat(this.exp.get(div+1));
                this.exp.remove(div+1);
                this.exp.set(div, Float.toString(res));
                this.exp.remove(div-1);
                continue;
            }

            if (add != -1) {
                res = Float.parseFloat(this.exp.get(add-1)) + Float.parseFloat(this.exp.get(add+1));
                this.exp.remove(add+1);
                this.exp.set(add, Float.toString(res));
                this.exp.remove(add-1);
                continue;
            }

            if (sub != -1) {
                res = Float.parseFloat(this.exp.get(sub-1)) - Float.parseFloat(this.exp.get(sub+1));
                this.exp.remove(sub+1);
                this.exp.set(sub, Float.toString(res));
                this.exp.remove(sub-1);
                continue;
            }
        }
        this.number = this.exp.get(0);
        this.exp.remove(0);
    }
}
