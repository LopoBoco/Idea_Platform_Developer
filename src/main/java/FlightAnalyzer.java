import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlightAnalyzer {

    public static void main(String[] args) {
        try {
            MinFlightTime.minFlightTime();

            String json = new String(Files.readAllBytes(Paths.get("tickets.json")));
            JSONObject jsonObject = new JSONObject(json);
            JSONArray tickets = jsonObject.getJSONArray("tickets");

            List<Integer> prices = new ArrayList<>();


            for (int i = 0; i < tickets.length(); i++) {
                JSONObject ticket = tickets.getJSONObject(i);
                if (ticket.getString("origin").equals("VVO") && ticket.getString("destination").equals("TLV")) {

                    prices.add(ticket.getInt("price"));
                }
            }

            int averagePrice = (int) prices.stream().mapToInt(Integer::intValue).average().orElse(0);
            Collections.sort(prices);
            int medianPrice;
            if (prices.size() % 2 == 0) {
                medianPrice = (prices.get(prices.size() / 2 - 1) + prices.get(prices.size() / 2)) / 2;
            } else {
                medianPrice = prices.get(prices.size() / 2);
            }

            int priceDifference = averagePrice - medianPrice;
            System.out.println("\n" + "Разница между средней ценой и медианой: " + "\n" + priceDifference + " рублей");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}