import java.util.Scanner;

/**
 * Created by Алексей on 20.02.2017.
 */

public class Solution {
    public static void main(String[] args) {
        ExchangeRate rate = newRate();

        Timer timer = new Timer();
        timer.start();

        rate.getRate();
        ApiResponse r = rate.getResponse();

        timer.interrupt();
        System.out.println();

        try{
            System.out.println(r.getBase() + " => " + r.getRates().getName() + " : " + r.getRates().getRate());
        }
        catch (Exception e){
            System.out.println("Getting error");
        }
    }

    private static ExchangeRate newRate(){
        CurrencyCode c1 = null;
        CurrencyCode c2 = null;

        Scanner in = new Scanner(System.in);

        //First currency
        while (true) {
            try {
                System.out.println("Enter from currency:");
                c1 = CurrencyCode.valueOf(in.nextLine().toUpperCase());
                break;
            } catch (Exception e) {
                System.out.println("Wrong currency code");
            }
        }

        //Second currency
        while (true) {
            try {
                System.out.println("Enter to currency:");
                c2 = CurrencyCode.valueOf(in.nextLine().toUpperCase());
                break;
            } catch (Exception e) {
                System.out.println("Wrong currency code");
            }
        }

        in.close();

        return new ExchangeRate(c1, c2);
    }
}