package com.beehopuse.webservice;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface HoneyQuantityService {
    
    @WebMethod
    float getBeeHouseHoneyActualQuantity(@WebParam(name = "beeHouseID") int beeHouseID);
} 