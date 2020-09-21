package ca.northland.stopover.apis.travel.amadeus;

import ca.northland.stopover.apis.travel.Login;
import ca.northland.stopover.apis.travel.TravelApi;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.HotelOffer;
import com.amadeus.resources.Location;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

// I want all such services to follow the same structure
@Service
public class AmadeusImpl implements TravelApi {

  @Override
  public Location getLocationInformation(String locationId) throws ResponseException {

    // get city coordinates

    return Login.INSTANCE.getAmadeus().referenceData.location("C" + locationId).get();
  }

  @Override
  public Location[] getClosestAirport(double longitude, double latitude) throws ResponseException {

    // get closest airports try reduce this. //use radius
    return Login.INSTANCE
        .getAmadeus()
        .referenceData
        .locations
        .airports
        .get(Params.with("longitude", longitude).and("latitude", latitude).and("sort", "distance"));
  }

  @Override
  public HotelOffer[] searchHotels(
      double longitude, double latitude, LocalDate checkInDate, LocalDate checkOutDate)
      throws ResponseException {

    // order by price
    // get 10 results this doesn't seem to work well getting only 3
    // consider checking best rate later
    int radius = 20;
    String radiusUnit = "KM";
    String sortBy = "PRICE";
    return Login.INSTANCE
        .getAmadeus()
        .shopping
        .hotelOffers
        .get(
            Params.with("checkInDate", checkInDate.toString())
                .and("checkOutDate", checkOutDate.toString())
                .and("longitude", longitude)
                .and("latitude", latitude)
                .and("radius", radius)
                .and("radiusUnit", radiusUnit)
                .and("bestRateOnly", true)
                .and("sort", sortBy)
                .and("page%5Blimit%5D", 10));
  }
}
