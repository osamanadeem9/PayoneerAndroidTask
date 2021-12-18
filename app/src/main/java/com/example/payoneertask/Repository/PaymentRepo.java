package com.example.payoneertask.Repository;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.payoneertask.Client;
import com.example.payoneertask.Service.PaymentService;
import com.payoneer.checkout.model.ApplicableNetwork;
import com.payoneer.checkout.model.ListResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class PaymentRepo {

    private final String TAG = getClass().getSimpleName();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> messageString = new MutableLiveData<>();
    public MutableLiveData<List<ApplicableNetwork>> requestNetworks() {
        final MutableLiveData<List<ApplicableNetwork>> mutableLiveData = new MutableLiveData<>();
        isLoading.setValue(true);

        PaymentService apiService = Client.retrofit.create(PaymentService.class);
        Call<ListResult> repos = apiService.getListResult();
        repos.enqueue(new Callback<ListResult>() {
            @Override
            public void onResponse(Call<ListResult> call, Response<ListResult> response) {
                ListResult result = response.body();
                List<ApplicableNetwork> applicableNetworkList = result.getNetworks().getApplicable();

                mutableLiveData.setValue(applicableNetworkList);
                isLoading.setValue(false);
                if (response.code()>=200 && response.code() <400)
                    messageString.setValue("Response successful: "+response.code());
                else if (response.code()>=400 && response.code()< 600)
                    messageString.setValue("Response error: "+response.code());
                else
                    messageString.setValue("Response received: "+response.code());

                Log.d(TAG, applicableNetworkList.toString());
            }


            @Override
            public void onFailure(Call<ListResult> call, Throwable throwable) {
                mutableLiveData.setValue(new ArrayList<>());
                isLoading.setValue(false);

                if (throwable instanceof HttpException)
                    messageString.setValue("Response failed: "+"HttpException");
                else if (throwable instanceof IOException)
                    messageString.setValue("Response failed: "+"IOException");
                else
                    messageString.setValue("Response failed: "+"Exception");


                //Toast.makeText(context, "An error has occured", Toast.LENGTH_LONG).show();
//                Log.d(TAG, t.toString());
            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getMessageString() {
        return messageString;
    }
}
