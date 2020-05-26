package com.hhub.bookhub.Activities.LoginActivity;

import android.content.Context;
import android.content.res.Resources;

public interface LoginInterface {
    public void infoUser(String user, String pass, Context context, Resources resources);
    public void updateBackground(String language);
}
