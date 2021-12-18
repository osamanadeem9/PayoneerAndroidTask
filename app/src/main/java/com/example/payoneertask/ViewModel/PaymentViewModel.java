package com.example.payoneertask.ViewModel;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.payoneertask.R;
import com.example.payoneertask.Repository.PaymentRepo;
import com.payoneer.checkout.model.ApplicableNetwork;

import java.util.List;

public class PaymentViewModel extends ViewModel {

    private PaymentRepo paymentRepo;
    LiveData<Boolean> isLoading;
    private MutableLiveData<List<ApplicableNetwork>> networksLiveData;

    public PaymentViewModel(){
        paymentRepo = new PaymentRepo();
    }

    public LiveData<List<ApplicableNetwork>> getHolidays() {
        if(networksLiveData==null){
            networksLiveData = paymentRepo.requestNetworks();
        }
        return networksLiveData;
    }

    public LiveData<Boolean> getIsLoading(){
        isLoading= paymentRepo.getIsLoading();
        return isLoading;
    }

}