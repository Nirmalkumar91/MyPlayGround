package com.nish.android.playground.common.events;

public class OpenCustomTabEvent extends BaseViewEvent {

    private String url;
    private boolean finishActivity;

    private OpenCustomTabEvent(Builder builder) {
        this.url = builder.url;
        this.emitter = builder.emitter;
        this.finishActivity = builder.finishActivity;
    }

    public String getUrl() {
        return url;
    }

    public boolean finishActivity() {
        return finishActivity;
    }

    public static Builder getEventBuilder(Object emitter) {
        return new Builder(emitter);
    }

    public static class Builder {

        private Object emitter;
        private String url;
        private boolean finishActivity;

        private Builder(Object emitter) {
            this.emitter = emitter;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setFinishActivity(boolean finishActivity) {
            this.finishActivity = finishActivity;
            return this;
        }

        public OpenCustomTabEvent build() {
            return new OpenCustomTabEvent(this);
        }
    }
}
