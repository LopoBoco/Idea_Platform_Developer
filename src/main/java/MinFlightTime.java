import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MinFlightTime {

    public static void minFlightTime() {
        String filePath = "tickets.json";

        List<FlightTicket> tickets = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filePath));
            JSONArray ticketsArray = (JSONArray) jsonObject.get("tickets");

            for (Object ticketObj : ticketsArray) {
                JSONObject ticketJson = (JSONObject) ticketObj;
                FlightTicket ticket = new FlightTicket(
                        (String) ticketJson.get("origin"),
                        (String) ticketJson.get("destination"),
                        (String) ticketJson.get("carrier"),
                        (String) ticketJson.get("departure_time"),
                        (String) ticketJson.get("arrival_time")
                );
                tickets.add(ticket);
            }

            calculateMinFlightTime(tickets);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static void calculateMinFlightTime(List<FlightTicket> tickets) {
        System.out.println("Минимальное время полета для каждого авиаперевозчика:");
        tickets.stream().filter(o -> o.getOrigin().equals("VVO")).filter(o -> o.getDestination().equals("TLV"))
                .collect(Collectors.groupingBy(FlightTicket::getCarrier,
                        Collectors.minBy(Comparator.comparingLong(FlightTicket::getFlightDuration))))
                .forEach((carrier, minFlightTime) -> {
                    System.out.println(carrier + ": " + minFlightTime.get().getFlightDurationAsString());
                });
    }
}


