package bank.app.simple.util;

import bank.app.simple.dao.ExchangeRateDao;
import bank.app.simple.entity.Currency;
import bank.app.simple.entity.ExchangeRate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExchangeRateUtil {

    public static double convertMoney(ExchangeRateDao rateDao, Currency curFrom, Currency curTo, double sum) throws IOException {
        double convertedSum;
        ExchangeRate rate;

        if (curFrom == Currency.UAH) {
            rate = getRateFromPrivateBank(curTo);
            if (rate == null){
                rate = rateDao.findRateByCurrency(curTo);
            }
            convertedSum = sum / rate.getBuyRate();
        } else if (curTo == Currency.UAH) {
            rate = getRateFromPrivateBank(curFrom);
            if (rate == null){
                rate = rateDao.findRateByCurrency(curFrom);
            }
            convertedSum = sum * rate.getSellRate();
        } else {
            convertedSum = convertMoney(rateDao, curFrom, Currency.UAH, sum);
            convertedSum = convertMoney(rateDao, Currency.UAH, curTo, convertedSum);
        }
        return convertedSum;
    }

    private static ExchangeRate getRateFromPrivateBank(Currency type) throws IOException {
        URL url = new URL("https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=3");
        URLConnection conn = url.openConnection();
        if (conn == null) {
            return null;
        }

        String[] rateData = null;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if(inputLine.contains(type.name())){
                    int indexFrom = inputLine.indexOf(type.name());
                    String extractString = inputLine.substring(indexFrom);
                    int indexTo = extractString.indexOf("/>");
                    extractString = extractString.substring(0, indexTo);
                    rateData = extractString.split(" ");
                    break;
                }
            }
        }
        if (rateData == null){
            return null;
        }
        double[] parsedRateData = parseRateData(rateData);
        if (parsedRateData.length == 2){
            return new ExchangeRate(type, parsedRateData[0], parsedRateData[1]);
        }
        return null;
    }

    private static double[] parseRateData(String[] rateData) throws NumberFormatException{
        double buyRate = 0.0;
        double saleRate = 0.0;

        for (String data : rateData) {
            if (data.contains("buy")){
                String buy = data.substring(data.indexOf("\"") + 1, data.length()-1);
                buyRate = Double.parseDouble(buy);
            }
            if (data.contains("sale")){
                String sale = data.substring(data.indexOf("\"") + 1, data.length()-1);
                saleRate = Double.parseDouble(sale);
            }
        }
        return new double[]{buyRate, saleRate};
    }

}
