package com.example.prudentialfinance.ViewModel.Goal;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.prudentialfinance.API.HTTPRequest;
import com.example.prudentialfinance.API.HTTPService;
import com.example.prudentialfinance.Container.CategoryAdd;
import com.example.prudentialfinance.Container.GoalAdd;
import com.example.prudentialfinance.Model.Category;
import com.example.prudentialfinance.Model.Goal;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GoalAddViewModel extends ViewModel {
    private MutableLiveData<GoalAdd> object;
    private Retrofit service;
    private MutableLiveData<Boolean> isLoading;

    public LiveData<Boolean> getIsLoading() {
        if (isLoading == null)
            isLoading = new MutableLiveData<>();
        return isLoading;
    }

    public LiveData<GoalAdd> getObject() {
        if (object == null) {
            object = new MutableLiveData<>();
        }
        return object;
    }


    public void saveData(Map<String, String> headers, Goal data) {
        isLoading.setValue(true);
        this.service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        Call<GoalAdd> container;
        container = api.addNewGoal(headers, data.getName(), data.getBalance(), data.getAmount(), data.getDeadline());
        container.enqueue(new Callback<GoalAdd>() {
            @Override
            public void onResponse(@NonNull Call<GoalAdd> call, @NonNull Response<GoalAdd> response) {
                isLoading.setValue(false);
                if (response.isSuccessful()) {
                    GoalAdd resource = response.body();
                    assert resource != null;
                    System.out.println("add " + resource.toString());
                    object.setValue(resource);
                    return;
                }

                object.setValue(null);
            }

            @Override
            public void onFailure(@NonNull Call<GoalAdd> call, @NonNull Throwable t) {
                isLoading.setValue(false);
                object.setValue(null);
            }
        });
    }

    public void updateData(Map<String, String> headers, Goal data) {
        isLoading.setValue(true);
        this.service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        Call<GoalAdd> container;
        container = api.editGoal(headers, data.getId(), data.getName(), data.getBalance(), data.getAmount(), data.getDeadline());
        container.enqueue(new Callback<GoalAdd>() {
            @Override
            public void onResponse(@NonNull Call<GoalAdd> call, @NonNull Response<GoalAdd> response) {
                isLoading.setValue(false);
                if (response.isSuccessful()) {
                    GoalAdd resource = response.body();
                    assert resource != null;
                    object.setValue(resource);
                    return;
                }
                object.setValue(null);
            }

            @Override
            public void onFailure(@NonNull Call<GoalAdd> call, @NonNull Throwable t) {
                isLoading.setValue(false);
                object.setValue(null);
            }
        });
    }
}
