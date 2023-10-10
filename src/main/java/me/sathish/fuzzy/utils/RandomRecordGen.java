package me.sathish.fuzzy.utils;

import com.github.javafaker.Faker;

public class RandomRecordGen {

    public static final void buildJRandRecords() {
        System.out.println("The JRAN Name is " + Faker.instance().name().firstName());
    }
}
