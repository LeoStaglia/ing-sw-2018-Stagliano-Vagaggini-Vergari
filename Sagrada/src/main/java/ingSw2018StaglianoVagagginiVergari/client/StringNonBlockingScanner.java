package ingSw2018StaglianoVagagginiVergari.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class StringNonBlockingScanner implements Callable<String>{
    public String call() throws InterruptedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        do {
            try {
                // wait until we have data to complete a readLine()
                while (!br.ready()  /*  ADD SHUTDOWN CHECK HERE */) {
                    Thread.sleep(200);
                }
                input = br.readLine();
            } catch(IOException ex){
                ex.printStackTrace();
            }
        } while ("".equals(input));
        return input;
    }
}
