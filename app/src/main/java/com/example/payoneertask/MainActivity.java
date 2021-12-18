package com.example.payoneertask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.payoneertask.Adapter.PaymentAdapter;
import com.example.payoneertask.ViewModel.PaymentViewModel;
import com.payoneer.checkout.model.ApplicableNetwork;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PaymentAdapter paymentAdapter;
    private ProgressDialog pd;
    private TextView noPaymentSourcesTextView;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUI(this);

        PaymentViewModel paymentViewModel = new PaymentViewModel();

        paymentViewModel.getHolidays().observe(this, networksList -> {
            paymentAdapter.addAll(networksList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(paymentAdapter);

            if (networksList.size()>0){
                recyclerView.setVisibility(View.VISIBLE);
                noPaymentSourcesTextView.setVisibility(View.GONE);
            }
            else{
                recyclerView.setVisibility(View.GONE);
                noPaymentSourcesTextView.setVisibility(View.VISIBLE);
            }
        });

        paymentViewModel.getIsLoading().observe(this, isLoading ->{
            if (isLoading)
                pd.show();
            else {
                pd.dismiss();
                paymentViewModel.getStringMessage().observe(this, stringMessage ->{
                    Toast.makeText(getApplicationContext(),stringMessage, Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void initializeUI(Context context) {
        recyclerView = findViewById(R.id.paymentRecyclerView);
        paymentAdapter = new PaymentAdapter(context, new ArrayList<>());
        pd = new ProgressDialog(context);
        pd.setMessage(getString(R.string.loading));
        noPaymentSourcesTextView = findViewById(R.id.no_payments_textview);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}