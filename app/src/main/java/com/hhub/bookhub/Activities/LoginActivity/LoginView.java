package com.hhub.bookhub.Activities.LoginActivity;

import android.content.Context;
import android.content.res.Resources;

import com.hhub.bookhub.Models.Background;
import com.hhub.bookhub.Models.User;

public interface LoginView {
    void infoUser(User user);
    void updateBackground(Background background);
}
