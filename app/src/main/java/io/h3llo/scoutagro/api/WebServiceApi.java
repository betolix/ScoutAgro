package io.h3llo.scoutagro.api;

import java.util.List;


import io.h3llo.scoutagro.model.Login;
import io.h3llo.scoutagro.model.Resp;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface WebServiceApi {

    //@POST("/token")
    @POST("/login")
    Call<Resp> obtenerToken(@Body Login login);

    @GET("/api/acceso_solo_con_jwt")
    Call<List<String>> obtenerMovimientosBancarios(@Header("Authorization") String authHeader);
}