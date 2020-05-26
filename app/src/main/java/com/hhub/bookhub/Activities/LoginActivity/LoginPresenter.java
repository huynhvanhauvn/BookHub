package com.hhub.bookhub.Activities.LoginActivity;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import com.hhub.bookhub.Models.Background;
import com.hhub.bookhub.Models.User;
import com.hhub.bookhub.R;
import com.hhub.bookhub.Services.APIService;
import com.hhub.bookhub.Services.Service;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginPresenter implements LoginInterface {
    private LoginView loginView;
    private Service service = APIService.getService();

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void infoUser(String user, String pass, final Context context, final Resources resources) {
        Observable<User> observable = service.login(user,pass);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(User user) {
                        if(user.getId()!=null) {
                            loginView.infoUser(user);
                        } else {
                            Toast.makeText(context,
                                    resources.getString(R.string.login_failed),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void updateBackground(String language) {
        Observable<Background> obBackground = service.background("login",language);
        obBackground.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Background>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Background background) {
                        if(background.getId() != null) {
                            loginView.updateBackground(background);
                        }
                    }
                });
    }
}
