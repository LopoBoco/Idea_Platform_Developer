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

            int sum = 0;
            for (int i = 0; i < prices.length(); i++) {
                sum += prices.getInt(i);
            }
            int avgPrice = sum / prices.length();

            int median;
            if (prices.length() % 2 == 0) {
                median = (prices.getInt(prices.length() / 2 - 1) + prices.getInt(prices.length() / 2)) / 2;
            } else {
                median = prices.getInt(prices.length() / 2);
            }
            
            int timeToFly = Integer.MAX_VALUE;
            System.out.println();
            for (Map.Entry<String, Integer> entry : minFlightTimes.entrySet()) {
                if (timeToFly > entry.getValue())
                    timeToFly = entry.getValue();
            }
            System.out.println("Минимальное время полета: " + "\n" + timeToFly + " часов");

            System.out.println("\n" + "Разница между средней ценой и медианой: " + "\n" + (avgPrice - median) + " рублей");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}