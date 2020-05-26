package com.hhub.bookhub.Activities.SignupActivity;

import com.hhub.bookhub.Services.APIService;
import com.hhub.bookhub.Services.Service;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SignupPresenter implements SignupInterface {

    private SignupView signupView;
    private Service service = APIService.getService();

    public SignupPresenter(SignupView signupView) {
        this.signupView = signupView;
    }

    @Override
    public void signup(final String username, final String password) {
        Observable<String> observable = service.signup(username,
                password);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        if(s.equals("success")) {
                            signupView.signup(username, password);
                        }
                    }
                });
    }
}
