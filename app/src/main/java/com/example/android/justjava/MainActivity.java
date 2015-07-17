package com.example.android.justjava;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */

public class MainActivity extends AppCompatActivity {

    private int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        createOrderSummary();
    }

    /**
     * This method creates the order summary.
     */

    private void createOrderSummary() {
        final Context context = this;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View orderSummary = layoutInflater.inflate(R.layout.order_summary, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(orderSummary);
        TextView orderSummaryText = (TextView) orderSummary.findViewById(R.id.order_summary_textview);
        alertDialogBuilder.setCancelable(true).setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
        String message = "Your order has been registered with us!\n\nToppings\n";
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_check_box);
        boolean topping = false;
        if(whippedCream.isChecked()) {
            message += "\t- Whipped Cream\n";
            topping = true;
        }
        if(chocolate.isChecked()) {
            message += "\t- Chocolate\n";
            topping = true;
        }
        if(!topping)
            message += "\t- None\n";
        message += "\nOrder Cost: " + calculatePrice() + "\n\nThank You!";
        orderSummaryText.setText(message);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity() {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

    /**
     * This method displays the given price on the screen.
     */

    private int calculatePrice(){
        return quantity*5;
    }

    private void displayPrice() {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(calculatePrice()));
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        quantity++;
        displayQuantity();
        displayPrice();
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if(quantity>0)
            quantity--;
        else return;
        displayQuantity();
        displayPrice();
    }

}
