package com.JavaLab.AdvJava.service;

import com.JavaLab.AdvJava.models.ExchangeRate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//A service utilizing RestClient geared specifically to call Privat API
@Service
public class PrivatCallerService {

    private static final Logger log = LogManager.getLogger(PrivatCallerService.class);
    private final RestClient restClient;

    //build a restClient with given url
    public PrivatCallerService(RestClient.Builder restClientBuilder)
    {
        this.restClient = restClientBuilder.baseUrl("https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11").build();
    }

    public List<Float> makeRestCall()
    {
        List<Float> result = new ArrayList<>();
        //Using a class matching Json structure of api get a list of them using ParameterizedTypeReference
        List<ExchangeRate> responces = this.restClient.get().retrieve().body(new ParameterizedTypeReference<List<ExchangeRate>>() {
        });

        if (responces == null)
        {
            return Collections.emptyList();
        }

        //Get the sales values from responces
        for(ExchangeRate resp : responces)
        {
            try
            {
                if(resp.getSale() == null)
                {
                    result.add(0.0f);
                    log.error("Sale is null");
                }
                result.add(Float.parseFloat(resp.getSale()));
            }
            catch (NumberFormatException e)
            {
                log.error("not parsed float correctly");
                result.add(0.0f);
            }

        }

        return result;
    }

}
