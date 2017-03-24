import java.net.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.google.gson.*;

/**
 * Created by Алексей on 20.02.2017.
 */

public class ExchangeRate {
    private CurrencyCode basic;
    private CurrencyCode target;
    private ApiResponse response;

    public ExchangeRate(CurrencyCode basic, CurrencyCode target) {
        this.basic = basic;
        this.target = target;
        response = null;
    }

    public ApiResponse getResponse() {
        return response;
    }

    public void getRate(){
        readFile();
        if (response==null) {
            getOnline();
            if (response!=null) {
                WriteFile writeFile = new WriteFile();
                writeFile.start();
            }
        }
    }

    private void getOnline() {
        try {
            HttpURLConnection connection = null;
            URL url = new URL("http://api.fixer.io/latest?base=" + basic.toString() + "&symbols=" + target.toString());
            connection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(RateObject.class, new RatesDeserializer())
                    .create();
            response = gson.fromJson(in, ApiResponse.class);
            setDate();
            in.close();
        }

        catch (Exception e) {
            System.out.println("Connection error");
        }
    }

    private void readFile(){
        try{
            BufferedReader br = new BufferedReader (new FileReader ("rates.txt"));
            String line;
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("yyyy-MM-dd");

            while ((line = br.readLine()) != null){
                String[] array = line.split(" ");
                Date date = format.parse(array[3]);
                Date today = new Date();
                if (array[0].equals(basic.toString()) && array[1].equals(target.toString()) && (date.getTime()+(1000*60*60*24))>today.getTime()){
                    response = new ApiResponse();
                    response.setBase(array[0]);
                    response.setRates(new RateObject(array[1], Double.parseDouble(array[2])));
                    break;
                }
            }
            br.close();
        }

        catch(Exception e){}
    }

    private void setDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
       response.setDate(dateFormat.format(date));
    }

    class WriteFile extends Thread{
        public void run(){
            try{
                PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("rates.txt", true)));
                writer.write(response.getBase() + " " + response.getRates().getName() +
                        " " + response.getRates().getRate()  + " " + response.getDate()
                        + System.getProperty("line.separator"));
                writer.close();
            }

            catch(Exception e){
                System.out.println("Writing error");
            }
        }
    }
}
