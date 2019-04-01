package com.stockone.pod;

import io.reactivex.Observable;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    String result = "";

    @Test
    public void addition_isCorrect() {
        result = "";
        Observable<String> observer = Observable.just("Hello");
        observer.subscribe(s -> result =s);
//        assertEquals(4, 2 + 2);
        assertEquals("Hello", result);
    }
}