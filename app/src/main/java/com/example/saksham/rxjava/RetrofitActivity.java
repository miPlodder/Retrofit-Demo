package com.example.saksham.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class RetrofitActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvResult;
    Button btnFetch;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        tvResult = findViewById(R.id.tv_text);
        tvResult.setMovementMethod(new ScrollingMovementMethod());
        btnFetch = findViewById(R.id.btn_fetch);
        btnFetch.setOnClickListener(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onClick(View v) {

        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        try {
            //retrofit returns observable
            Observable<MyPojo> observable = apiService.getUser(1);
            observable.subscribe(new DisposableObserver<Object>() {
                @Override
                public void onNext(Object o) {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });


            compositeDisposable.add(observable
                    //.io() or .newThread() any can be used
                    .subscribeOn(Schedulers.newThread())
                    //observing on the main thread
                    .observeOn(AndroidSchedulers.mainThread())
                    //newer method added
                    .subscribeWith(new DisposableObserver<MyPojo>() {
                        @Override
                        public void onNext(MyPojo o) {
                            tvResult.append("first name = "+o.getDataPojo().getFirstName());
                            tvResult.append("\nlast name = "+o.getDataPojo().getLastName());
                            tvResult.append("\navatar = "+o.getDataPojo().getAvatar());
                            tvResult.append("\nid = "+o.getDataPojo().getId());
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(RetrofitActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    }));
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
