package com.nish.android.playground.usecase;

import com.nish.android.playground.common.UseCase;

public class MessageUseCase extends UseCase {

    private String message;

    public MessageUseCase(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
