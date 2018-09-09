package com.example.hamzarazzaque.justjava;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URI;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int NoOfOrder = 0, base = 5, whip = 3, choc = 4, caram = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method adds the given quantity value on the screen.
     */
    public void Addition(View view) {
        display(++NoOfOrder);
    }

    /**
     * This method subtracts the given quantity value on the screen.
     */
    public void Subtraction(View view) {

        if (NoOfOrder == 0)
            NoOfOrder = 0;
        else
            display(--NoOfOrder);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method calculates the given price on the screen.
     */
    private int calculatePrice(int order, boolean whipBool, boolean chocBool, boolean caramBool) {
        int Total;

        if ((whipBool == true) && (chocBool == true) && (caramBool == true))
            Total = order * (base + whip + choc + caram);
        else if ((whipBool == true) && (chocBool == true))
            Total = order * (base + whip + choc);
        else if ((whipBool == true) && (caramBool == true))
            Total = order * (base + whip + caram);
        else if ((chocBool == true) && (caramBool == true))
            Total = order * (base + choc + caram);
        else if (whipBool == true)
            Total = order * (base + whip);
        else if (chocBool == true)
            Total = order * (base + choc);
        else if (caramBool == true)
            Total = order * (base + caram);
        else
            Total = order * 5;

        return Total;
    }

    /**
     * This method is called to create order summary.
     */
    public String CreateSummary(String name, boolean boole1, boolean boole2, boolean boole3, int quant, int pricee) {
        String PriceMessage;
        PriceMessage  = "\n" + getString(R.string.disp_name     , name);
        PriceMessage += "\n" + getString(R.string.disp_whip     , boole1);
        PriceMessage += "\n" + getString(R.string.disp_chcoo    , boole2);
        PriceMessage += "\n" + getString(R.string.disp_caram    , boole3);
        PriceMessage += "\n" + getString(R.string.disp_quantity , quant);
        PriceMessage += "\n" + getString(R.string.disp_price    , pricee);
        PriceMessage += "\n" + getString(R.string.disp_thankyou) + "\n\n";
        return PriceMessage;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void happen(View view) {
        EditText NameField = (EditText) findViewById(R.id.name_field);
        String Name = NameField.getText().toString();

        CheckBox Check1 = (CheckBox) findViewById(R.id.checkBox1);
        CheckBox Check2 = (CheckBox) findViewById(R.id.checkBox2);
        CheckBox Check3 = (CheckBox) findViewById(R.id.checkBox3);

        boolean See1 = Check1.isChecked();
        boolean See2 = Check2.isChecked();
        boolean See3 = Check3.isChecked();
        int Price = calculatePrice(NoOfOrder, See1, See2, See3);

        String Message = CreateSummary(Name, See1, See2, See3, NoOfOrder, Price);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse(getString(R.string.mail)));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject, Name));
        intent.putExtra(Intent.EXTRA_TEXT, Message);
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }
}
