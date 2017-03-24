/**
 * Created by Алексей on 20.02.2017.
 */

public class ApiResponse {
    private String base;
    private String date;
    private RateObject rates;

    public String getBase() {
        return base;
    }

    public String getDate() {
        return date;
    }

    public RateObject getRates() {
        return rates;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRates(RateObject rates) {
        this.rates = rates;
    }
}