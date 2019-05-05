package com.nish.android.playground.common;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.browser.customtabs.CustomTabsService;

public class CustomTabHelper {

    private static final String STABLE_PACKAGE = "com.android.chrome";
    private static String packageNameToUse;

    private PackageManager packageManager;

    public CustomTabHelper(PackageManager packageManager) {
        this.packageManager = packageManager;
    }


    public String getPackageNameToUse() {
        if (packageNameToUse != null) {
            return packageNameToUse;
        }
        Intent activityIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.co.in"));
        ResolveInfo defaultViewHandlerInfo = packageManager.resolveActivity(activityIntent, 0);
        String defaultViewHandlerPackageName = null;
        if (defaultViewHandlerInfo != null) {
            defaultViewHandlerPackageName = defaultViewHandlerInfo.activityInfo.packageName;
        }
        List<ResolveInfo> resolvedActivityList = packageManager.queryIntentActivities(activityIntent, 0);
        List<String> packagesSupportingCustomTabs = new ArrayList<>();
        for (ResolveInfo info : resolvedActivityList) {
            Intent serviceIntent = new Intent();
            serviceIntent.setAction(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION);
            serviceIntent.setPackage(info.activityInfo.packageName);
            if (packageManager.resolveService(serviceIntent, 0) != null) {
                packagesSupportingCustomTabs.add(info.activityInfo.packageName);
            }
        }
        if (packagesSupportingCustomTabs.isEmpty()) {
            packageNameToUse = null;
        } else if (packagesSupportingCustomTabs.size() == 1) {
            packageNameToUse = packagesSupportingCustomTabs.get(0);
        } else if (!TextUtils.isEmpty(defaultViewHandlerPackageName)
                && packagesSupportingCustomTabs.contains(defaultViewHandlerPackageName)) {
            packageNameToUse = defaultViewHandlerPackageName;
        } else if (packagesSupportingCustomTabs.contains(STABLE_PACKAGE)) {
            packageNameToUse = STABLE_PACKAGE;
        }

        return packageNameToUse;
    }
}
