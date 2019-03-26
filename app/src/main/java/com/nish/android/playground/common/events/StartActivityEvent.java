package com.nish.android.playground.common.events;

import android.content.Intent;

public class StartActivityEvent extends BaseViewEvent {

    private Class activityClass;
    private boolean finishActivity;
    private boolean isExternalApp;
    private Intent intent;

    private StartActivityEvent(Builder builder) {
        this.emitter = builder.emitter;
        this.activityClass = builder.activityClass;
        this.finishActivity = builder.finishActivity;
        this.isExternalApp = builder.isExternalApp;
        this.intent = builder.intent;
    }

    public Class getActivityClass() {
        return activityClass;
    }

    public boolean finishActivity() {
        return finishActivity;
    }

    public boolean isExternalApp() {
        return isExternalApp;
    }

    public Intent getIntent() {
        return intent;
    }

    public static Builder getEventBuilder(Object emitter) {
        return new Builder(emitter);
    }

    public static class Builder {

        private Object emitter;
        private Class activityClass;
        private boolean finishActivity;
        private boolean isExternalApp;
        private Intent intent;

        private Builder(Object emitter) {
            this.emitter = emitter;
        }

        public Builder setActivity(Class activityClass) {
            this.activityClass = activityClass;
            return this;
        }

        public Builder setFinishActivity(boolean finishActivity) {
            this.finishActivity = finishActivity;
            return this;
        }

        public Builder isExternalApp(boolean isExternalApp) {
            this.isExternalApp = isExternalApp;
            return this;
        }

        public Builder setIntent(Intent intent) {
            this.intent = intent;
            return this;
        }

        public StartActivityEvent build() {
            return new StartActivityEvent(this);
        }
    }
}
