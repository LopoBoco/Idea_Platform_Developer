import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class FlightAnalyzer {

    public static void main(String[] args) {
        try {
            JSONTokener tokener = new JSONTokener(new FileReader("tickets.json"));
            JSONArray flights = new JSONArray(tokener);

            Map<String, Integer> minFlightTimes = new HashMap<>();

            JSONArray prices = new JSONArray();

            for (int i = 0; i < flights.length(); i++) {
                JSONObject flight = flights.getJSONObject(i);
                String origin = flight.getString("origin");
                String destination = flight.getString("destination");
                String carrier = flight.getString("carrier");
                int flightTime = flight.getInt("flight_time");

                if (origin.equals("Владивосток") && destination.equals("Тель-Авив")) {
                    if (!minFlightTimes.containsKey(carrier) || flightTime < minFlightTimes.get(carrier)) {
                        minFlightTimes.put(carrier, flightTime);
                    }

                    prices.put(flight.getDouble("price"));
                }
            }

            double sum = 0;
            for (int i = 0; i < prices.length(); i++) {
                sum += prices.getDouble(i);
            }
            double avgPrice = sum / prices.length();

            double median;
            if (prices.length() % 2 == 0) {
                median = (prices.getDouble(prices.length() / 2 - 1) + prices.getDouble(prices.length() / 2)) / 2;
            } else {
                median = prices.getDouble(prices.length() / 2);
            }

            System.out.println("Минимальное время полета для каждого авиаперевозчика:");
            for (Map.Entry<String, Integer> entry : minFlightTimes.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            System.out.println("Разница между средней ценой и медианой: " + (avgPrice - median));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}