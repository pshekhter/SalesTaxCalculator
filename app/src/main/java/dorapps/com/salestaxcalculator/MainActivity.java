/**
 * This app is developed by Pavel Shekhter.
 * Copyright 2017.
 */

package dorapps.com.salestaxcalculator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    BigDecimal price = new BigDecimal("0.0");
    BigDecimal rate = new BigDecimal("0.0");
    BigDecimal result = new BigDecimal("0.0");
    BigDecimal total = new BigDecimal("0.0");

    String strResult;

    @BindView(R.id.etPrice)
    EditText etPrice;
    @BindView(R.id.etRate)
    EditText etRate;
    @BindView(R.id.btnCalculate)
    Button btnCalculate;
    @BindView(R.id.tvResult)
    TextView tvResult;

    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mContext = getApplicationContext();

        strResult = mContext.getString(R.string.strResult);
        setGUI();
    }

    @OnTextChanged(R.id.etPrice)
    public void onPriceTextChanged() {
        setPrice();
    }

    private void setPrice() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        String pattern = "####.##";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        try {
            price = (BigDecimal) decimalFormat.parse(etPrice.getText().toString());

        } catch (ParseException e) {
            Log.d("Error", "Invalid price.");
            Log.d("Error", "Current data: " + e.toString());
        }
    }

    @OnTextChanged(R.id.etRate)
    public void onRateTextChanged() {
        setRate();
    }

    private void setRate() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        String pattern = "####.##";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        try {
            rate = (BigDecimal) decimalFormat.parse(etRate.getText().toString());

        } catch (ParseException e) {
            Log.d("Error", "Invalid rate.");
            Log.d("Error", "Current data: " + e.toString());
        }
    }

    @OnClick(R.id.btnCalculate)
    public void onClick() {

        if (!(etPrice.getText().toString().equals("")) && !(etRate.getText().toString().equals(""))) {

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            String pattern = "####.00";
            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
            decimalFormat.setParseBigDecimal(true);

            try {
                BigDecimal d1 = (BigDecimal) decimalFormat.parse(etPrice.getText().toString());
                BigDecimal d2 = (BigDecimal) decimalFormat.parse(etRate.getText().toString());

                if (d1.compareTo(new BigDecimal("0.0")) != 0 && d2.compareTo(new BigDecimal("0.0")) != 0) {
                    BigDecimal percent = rate.divide(new BigDecimal("100"));
                    result = price.multiply(percent);
                    total = price.add(result);

                    setGUI();
                } else if (d1.compareTo(new BigDecimal("0.0")) == 0) {
                    result = new BigDecimal("0.0");
                    total = new BigDecimal("0.0");
                } else if (d2.compareTo(new BigDecimal("0.0")) == 0) {
                    result = new BigDecimal("0.0");
                    total = new BigDecimal("0.0");
                }

            } catch (ParseException e) {
                Log.d("Error", "Invalid total.");
                Log.d("Error", "Current data: " + e.toString());
            }

        } else if (etPrice.getText().toString().equals("")) {
            String error = mContext.getString(R.string.error_enterPrice);
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        } else if (etRate.getText().toString().equals("")) {
            String error = mContext.getString(R.string.error_enterRate);
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        }
    }

    private void setGUI() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        String pattern = "####.00";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        try {
            total = (BigDecimal) decimalFormat.parse(total.toString());

        } catch (ParseException e) {
            Log.d("Error", "Invalid total.");
            Log.d("Error", "Current data: " + e.toString());
        }

        total = total.setScale(2, RoundingMode.HALF_EVEN);

        tvResult.setText(strResult + " $" + total.toString());
        result = new BigDecimal("0.00");
        total = new BigDecimal("0.00");
    }
}
