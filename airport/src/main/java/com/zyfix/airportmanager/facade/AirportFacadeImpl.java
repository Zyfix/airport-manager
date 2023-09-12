package com.zyfix.airportmanager.facade;

import com.zyfix.airportmanager.api.AirplaneDto;
import com.zyfix.airportmanager.api.StewardDto;
import com.zyfix.airportmanager.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class AirportFacadeImpl implements AirportFacade {

//    private final Environment env;

    @Value("${airplane_url}")
    private String airplane_url;

    @Value("${steward_url}")
    private String steward_url;

//    @Autowired
//    public AirportFacadeImpl(Environment env) {
//        this.airplane_url = env.getProperty("airplane_url");
//        this.steward_url = env.getProperty("steward_url");
//
//        log.info("Airport url: " + airplane_url);
//        log.info("Steward url: " + steward_url);
//        this.env = env;
//    }

    @Override
    public AirplaneDto[] getAirplanes() {
        String url = this.airplane_url + "api/v1/airplanes/";
        log.info("Requesting airplanes from url: " + url);

        AirplaneDto[] airplanes = getRequest(url, AirplaneDto[].class, HttpStatus.OK);

        return airplanes;
    }

    @Override
    public StewardDto[] getStewards() {
        String url = this.steward_url + "/api/v1/stewards/";
        log.info("Requesting stewards from url: " + url);

        StewardDto[] stewards = getRequest(url, StewardDto[].class, HttpStatus.OK);

        return stewards;
    }

    @Override
    public AirplaneDto createAirplane(AirplaneDto airplaneDto) {
        String url = this.airplane_url + "api/v1/airplanes/";

        String urlTemplate = UriComponentsBuilder.fromUriString(url)
                .queryParam("id", airplaneDto.id())
                .queryParam("manufacturer", airplaneDto.manufacturer())
                .queryParam("model", airplaneDto.model())
                .queryParam("capacity", airplaneDto.capacity())
                .toUriString();

        AirplaneDto airplane = postRequestUrl(urlTemplate, AirplaneDto.class, HttpStatus.CREATED);
        return airplane;
    }

    @Override
    public StewardDto createSteward(StewardDto stewardDto) {
        String url = this.steward_url + "/api/v1/stewards/";
        String urlTemplate = UriComponentsBuilder.fromUriString(url)
                .queryParam("id", stewardDto.id())
                .queryParam("firstName", stewardDto.firstName())
                .queryParam("lastName", stewardDto.lastName())
                .queryParam("dateOfBirth", stewardDto.dateOfBirth())
                .queryParam("hireDate", stewardDto.hireDate())
                .toUriString();

        StewardDto steward = postRequestUrl(urlTemplate, StewardDto.class, HttpStatus.CREATED);


        return steward;
    }

    private static <T> T getRequest(String url, Class classType, HttpStatus code) {
        var restTemplate = new RestTemplate();
        ResponseEntity<T> responseEntity;
        try{
            responseEntity = restTemplate.getForEntity(url, classType);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw new BadRequestException();
        }

        if (responseEntity.getStatusCode() != code) {
            log.error("Error bad status code. Got: " + responseEntity.getStatusCode() + " Wanted: " + code);
            throw new BadRequestException();
        }
        return responseEntity.getBody();
    }

    private static <T> T postRequestUrl(String url, Class classType, HttpStatus code) {
        var restTemplate = new RestTemplate();
        ResponseEntity<T> responseEntity;
        try{
            responseEntity = restTemplate.postForEntity(url, null, classType);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            throw new BadRequestException();
        }

        if (responseEntity.getStatusCode() != code) {
            log.error("Error bad status code. Got: " + responseEntity.getStatusCode() + " Wanted: " + code);
            throw new BadRequestException();
        }
        return responseEntity.getBody();
    }


}
