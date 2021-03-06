package com.example.android.justjava;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        if(quantity == 0) {
            Toast toast = Toast.makeText(getApplication(), "Quantity cannot be 0", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 100);
            toast.show();
        }
        else
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
        String message = "Your order has been registered with us!\n\nName: ";
        message += getName();
        message += "\n\nQuantity: " + quantity;
        message+= "\n\nToppings\n";
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
        message += "\nOrder Cost: " + NumberFormat.getCurrencyInstance().format(calculatePrice()) + "\n\nThank You!";
        orderSummaryText.setText(message);
        //orderSummaryText.setText(message + "\n\nA confirmation email has been sent to you!");
        //sendEmailConfirmation(message);
    }

    /**
     * This method sends a confirmation email to the user.
     */
/*
    private void sendEmailConfirmation(String message) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto"));
        EditText orderName = (EditText) findViewById(R.id.name_edittext);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Order Confirmation for " + orderName.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
    }
*/
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
        int price = quantity *5;
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_check_box);
        if(whippedCream.isChecked()) {
            price += quantity;
        }
        if(chocolate.isChecked()) {
           price += quantity *2;
        }
        return price;
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

    /**
     * This method gets the name of the user
     */
    private String getName(){
        EditText name = (EditText) findViewById(R.id.name_edittext);
        return name.getText().toString();
    }

    /**
     * This method works for the toppings checkboxes
     */

    public void checkToppings(View view) {
        displayPrice();
    }

}
