package com.example.payoneertask.Service;

import com.payoneer.checkout.model.ListResult;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PaymentService {
    @GET("optile/checkout-android/develop/shared-test/lists/listresult.json")
    Call<ListResult> getListResult();
}
