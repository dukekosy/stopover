package ca.northland.stopover.controllers;

import ca.northland.stopover.models.AirportHotels;
import ca.northland.stopover.service.HotelsService;
import com.amadeus.exceptions.ResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.Set;

@Controller
@RequestMapping(value = {"/airport"})
class HotelsController {

  @Autowired private HotelsService hotelsService;

  @GetMapping("/hotels")
  @ResponseBody
  public Set<AirportHotels> getAirportHotelsNearby(
      @RequestParam(name = "cityCode") String cityCode,
      @RequestParam(name = "checkInDate", required = false) @DateTimeFormat(pattern = "d.MM.yyyy")
          LocalDate checkInDate,
      @RequestParam(name = "checkOutDate") @DateTimeFormat(pattern = "d.MM.yyyy")
          LocalDate checkOutDate)
      throws ResponseException {
    return hotelsService.getAirportHotelsNearby(cityCode, checkInDate, checkOutDate);
  }
}
