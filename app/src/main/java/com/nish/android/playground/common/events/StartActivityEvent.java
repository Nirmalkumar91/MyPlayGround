package com.nish.android.playground.common.events;

public class StartActivityEvent extends BaseViewEvent {

    private Class activityClass;

    private StartActivityEvent(Builder builder) {
        this.emitter = builder.emitter;
        this.activityClass = builder.activityClass;
    }

    public Class getActivityClass() {
        return activityClass;
    }

    public static Builder getEventBuilder(Object emitter) {
        return new Builder(emitter);
    }

    public static class Builder {

        private Object emitter;
        private Class activityClass;

        private Builder(Object emitter) {
            this.emitter = emitter;
        }

        public Builder setActivity(Class activityClass) {
            this.activityClass = activityClass;
            return this;
        }

        public StartActivityEvent build() {
            return new StartActivityEvent(this);
        }
    }
}
