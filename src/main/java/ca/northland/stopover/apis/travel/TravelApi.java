package ca.northland.stopover.apis.travel;

import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.HotelOffer;
import com.amadeus.resources.Location;

import java.time.LocalDate;

public interface TravelApi {

  Location getLocationInformation(String locationId) throws ResponseException;

  Location[] getClosestAirport(double longitude, double latitude) throws ResponseException;

  HotelOffer[] searchHotels(
      double longitude, double latitude, LocalDate checkInDate, LocalDate checkOutDate)
      throws ResponseException;
}
