package ingSw2018StaglianoVagagginiVergari.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.Channel;
import java.util.InputMismatchException;
import java.util.concurrent.Callable;

public class IntegerNonBlockingScanner implements Callable<Integer> {
    /** method to create a non blocking scanner
     * @return the Integer input given by the user
     */
    public Integer call() throws InterruptedException, InputMismatchException{
        try {
            System.in.read(new byte[System.in.available()]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input="";
        do {
            try {
                while (!br.ready()) {
                    Thread.sleep(100);
                }
                input = br.readLine();
            }catch(IOException ex){
                throw new InputMismatchException();
            }
        } while ("".equals(input));

        int result;
        try{
            result = Integer.parseInt(input);
            return result;
        }catch(NumberFormatException ex){
            throw new InputMismatchException();
        }

    }
}
