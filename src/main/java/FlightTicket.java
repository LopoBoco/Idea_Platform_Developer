class FlightTicket {
    private String origin;
    private String destination;
    private String carrier;
    private String departureTime;
    private String arrivalTime;


    public FlightTicket(String origin, String destination, String carrier, String departureTime, String arrivalTime) {
        this.origin = origin;
        this.destination = destination;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;

    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getCarrier() {
        return carrier;
    }

    public long getFlightDuration() {
        long departureMinutes = Long.parseLong(departureTime.split(":")[0]) * 60 + Integer.parseInt(departureTime.split(":")[1]);
        long arrivalMinutes = Long.parseLong(arrivalTime.split(":")[0]) * 60 + Integer.parseInt(arrivalTime.split(":")[1]);
        return arrivalMinutes - departureMinutes;
    }

    public String getFlightDurationAsString() {
        long duration = getFlightDuration();
        long hours = duration / 60;
        long minutes = duration % 60;
        return hours + " ч " + minutes + " мин";
    }

}