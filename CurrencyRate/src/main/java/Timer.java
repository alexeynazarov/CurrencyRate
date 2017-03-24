/**
 * Created by Алексей on 21.02.2017.
 */

public class Timer extends Thread{
    public void run(){
        while(true){
            System.out.print(".");
            try{
                Thread.sleep(100);
            }
            catch (InterruptedException e){
                break;
            }
        }
    }
}