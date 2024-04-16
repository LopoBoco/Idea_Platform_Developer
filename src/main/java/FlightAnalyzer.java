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

            System.out.println("Минимальное время полета для каждого авиаперевозчика:");
            for (String carrier : List.of("TK", "S7", "SU", "BA")) {
                int minFlightTime = Integer.MAX_VALUE;
                for (int i = 0; i < tickets.length(); i++) {
                    JSONObject ticket = tickets.getJSONObject(i);
                    if (ticket.getString("carrier").equals(carrier) && !ticket.getString("destination_name").equals("Уфа") && !ticket.getString("origin_name").equals("Ларнака")) {
                        int departureHour = Integer.parseInt(ticket.getString("departure_time").split(":")[0]);
                        int arrivalHour = Integer.parseInt(ticket.getString("arrival_time").split(":")[0]);
                        int flightTime = arrivalHour - departureHour;
                        minFlightTime = Math.min(minFlightTime, flightTime);
                    }
                }
                System.out.println(carrier + ": " + minFlightTime + " hours");
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