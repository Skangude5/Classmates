package com.skangude5.classmates.staticData;

import com.skangude5.classmates.R;

public class Icons {
    public static Integer getIcon(String iconName){
        switch (iconName){
            case "python":
                return R.drawable.python;
            case "javascript":
                return R.drawable.js;
            case "google":
                return R.drawable.google;
            default:
                return R.drawable.android;
        }
    }
}
