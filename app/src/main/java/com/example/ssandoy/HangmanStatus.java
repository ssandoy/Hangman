package com.example.ssandoy.s236305;

/**
 * Created by ssandoy on 15.09.2016.
 */
public class HangmanStatus {

    private int status;

    public HangmanStatus() {
        status = 6;
    }

    public  int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void wrongGuess() {
        status--;
    }

    public boolean isGameLost(){
        return status == 0;
    }
}

