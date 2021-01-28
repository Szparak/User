package com.pernal.calculator;

public class UserCalculator {

    public static double calculate(long followersCount, long publicReposCount) throws IllegalArgumentException{
        if(followersCount == 0){
            throw new IllegalArgumentException("Followers count equals 0 - unable to perform calculations");
        }

        return (double)6/followersCount*(2+publicReposCount);
    }
}
