package com.example.dominatorsmad_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Payment extends AppCompatActivity {

    private EditText HolderName;
    private Button done;
    private DatabaseReference databaseReference;
    private EditText CardNo,MM,YY,CVV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        done = findViewById(R.id.btnaddtresspage);

        CardForm cardForm = findViewById(R.id.card_form);
        cardForm.cardRequired(true);
        cardForm.expirationRequired(true);
        cardForm.cvvRequired(true);
        cardForm.setup(Payment.this);

        CVV = cardForm.getCvvEditText();
        CardNo = cardForm.getCardEditText();
        MM = cardForm.getExpirationDateEditText();
        HolderName = findViewById(R.id.txtEditname);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NameOfHolder = HolderName.getText().toString();
                String CardNumber = CardNo.getText().toString();
                String Month = MM.getText().toString();
                String SecurityCode = CVV.getText().toString();

                if (!NameOfHolder.isEmpty()){
                    if(!CardNumber.isEmpty()){
                        if(!Month.isEmpty()){
                            if (!SecurityCode.isEmpty()){
                                PaymentInsert();
                                Toast.makeText(Payment.this, "Data inserted", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), payment_confirm.class);
                                i.putExtra("CardNo",CardNumber);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(Payment.this, "Required Security Code", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(Payment.this, "Required Date", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(Payment.this, "Required Card Number", Toast.LENGTH_SHORT).show();
                    }
                }

                else{
                    Toast.makeText(Payment.this, "Required Holder Name", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
    private void PaymentInsert(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Payment");
        String NameOfHolder = HolderName.getText().toString();
        String CardNumber = CardNo.getText().toString();
        String Month = MM.getText().toString();
        String SecurityCode = CVV.getText().toString();

        PaymentHandle payments = new PaymentHandle(CardNumber, NameOfHolder, SecurityCode, Month);
        databaseReference.child("User1").setValue(payments);


    }
}

