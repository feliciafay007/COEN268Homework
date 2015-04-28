package ex1.android.scu.edu.loancalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.view.View.OnClickListener;

public class MainActivity extends ActionBarActivity {
    private EditText text; // the borrowedAmount, from input.
    private SeekBar seekBar; // the seekBar.
    private Button calculateButton; // button.
    private TextView textView; // result text view.
    private double currentProgress = 50.0; //current interest rate.
    private double maxInterestRate = 20.0; //max interest rate.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calculateButton = (Button) findViewById(R.id.button);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setProgress((int)currentProgress);
        calculateButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                /**
                 * 1. receive input
                 */
                double borrowedAmount = getBorrowedAmount();
                //System.out.println(borrowedAmount);

                /**
                 * 2. get interest rate
                 */
                double interestRate = getInterestRate();

                /**
                 * 3. get loan term
                 */
                int loanTerm = getLoanTerm();

                /**
                 * 4. get tax included or not info
                 */
                boolean isTaxIncluded = isTaxIncluded();

                /**
                 * 5. calculate
                 */
                double result = LoanFormular.calculateLoan(borrowedAmount, interestRate, loanTerm, isTaxIncluded);

                /**
                 * 6. show result
                 */
                shownResult(result);
                return;
            }
        });

        seekBar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentProgress = progress;
                textView = (TextView) findViewById(R.id.textView9);
                textView.setText(String.format("%.1f", currentProgress / seekBar.getMax() * maxInterestRate));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textView = (TextView) findViewById(R.id.textView9);
                textView.setText(String.format("%.1f", currentProgress / seekBar.getMax() * maxInterestRate));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private double getBorrowedAmount () {
        text = (EditText) findViewById(R.id.editText);
        if (text.getText().length() == 0) {
            Toast.makeText(this, "Please enter the amount of dollars", Toast.LENGTH_LONG).show();
            return 0.0;
        } else {
            return Double.parseDouble(text.getText().toString());
        }
    }

    /**
     * This will be a SeekBar ranging from 0.0 to 20.0,
     * indicating the annual percentage rate of the interest.
     * This value should start at 10.0.
     */
    private double getInterestRate() {
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        return currentProgress / seekBar.getMax() * maxInterestRate;
    }

    private int getLoanTerm () {
        RadioButton radioButton1 = (RadioButton) findViewById(R.id.radioButton);
        RadioButton radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton radioButton3  = (RadioButton) findViewById(R.id.radioButton3);
        if (radioButton1.isChecked()) {
            return 15;
        } else if (radioButton2.isChecked()) {
            return 20;
        } else {
            return 30;
        }
    }

    private boolean isTaxIncluded() {
        CheckBox checkbox1 = (CheckBox) findViewById(R.id.checkBox);
        return checkbox1.isChecked();
    }

    private boolean shownResult(double result) {
           textView = (TextView) findViewById(R.id.textView6);
           //textView.setText(String.valueOf(result));
           textView.setText(String.format("%.2f $", result));
           return true;
    }
}
