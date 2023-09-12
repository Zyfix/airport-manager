package com.zyfix.airportmanager.facade;

import com.zyfix.airportmanager.api.AirplaneDto;
import com.zyfix.airportmanager.api.StewardDto;

public interface AirportFacade {

    AirplaneDto[] getAirplanes();

    AirplaneDto createAirplane(AirplaneDto airplaneDto);

    StewardDto[] getStewards();

    StewardDto createSteward(StewardDto stewardDto);

}
