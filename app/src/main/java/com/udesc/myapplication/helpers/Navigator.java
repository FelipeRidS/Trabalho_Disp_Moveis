package com.udesc.myapplication.helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Navigator {
    public static void callActivity(Context context, Class<?> activity, Bundle bundle) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent, bundle);
    }
    public static void callActivity(Context context, Class<?> activity) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }
}
