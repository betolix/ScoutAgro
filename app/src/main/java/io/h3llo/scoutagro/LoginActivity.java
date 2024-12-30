package io.h3llo.scoutagro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.h3llo.scoutagro.api.WebServiceApi;
import io.h3llo.scoutagro.api.WebServiceJWT;
import io.h3llo.scoutagro.model.Login;
import io.h3llo.scoutagro.model.Resp;
import io.h3llo.scoutagro.model.User;
import io.h3llo.scoutagro.shared_pref.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btToken;

    private User user;


    private String ok;

    private String usuario;
    private String primer_nombre;
    private String apellido_paterno;
    private String tipo_documento;
    private String numero_documento;
    private String email_1;
    private String numero_celular;
    private String role_id;
    private String role_name;
    private String img;

    private String token;

    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupView();
    }

    private void setupView() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btToken = findViewById(R.id.btToken);
        btToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerToken();
            }
        });

//
//        btObtenerRecurso = findViewById(R.id.btObtenerRecurso);
//        btObtenerRecurso.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                obtenerRecursoConToken();
//            }
//        });
//        btSolicitudTokenErroneo = findViewById(R.id.btSolicitudTokenErroneo);
//        btSolicitudTokenErroneo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                obtenerRecursoConTokenErroneo();
//            }
//        });
    }

//    private void obtenerRecursoConTokenErroneo(){
//        token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGJlcnRvIiwidXNlcklkIjoiMiIsInJvbGUiOiJBZG1pbiJ9.A6_dKk_GcyzsYIiHzzo8Q7nqeFePjera56KUoFVbNK4";
//        String authHeader = "Bearer " + token;
//
//        Call<List<String>> call = WebServiceJWT
//                .getInstance()
//                .createService(WebServiceApi.class)
//                .obtenerMovimientosBancarios(authHeader);
//
//        call.enqueue(new Callback<List<String>>() {
//            @Override
//            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
//                if(response.code() == 200){
//                    for(int i=0; i<response.body().size(); i++){
//                        Log.d("TAG1", "Movimiento Bancario " + i + " " + response.body().get(i));
//                    }
//                }else {
//                    Log.d("TAG1", "Token es incorrecto y no podemos obtener la respuesta");
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<String>> call, Throwable t) {
//
//            }
//        });
//
//
//
//    }
//
//    private void obtenerRecursoConToken(){
//        String authHeader = "Bearer " + token;
//        Call<List<String>> call = WebServiceJWT
//                .getInstance()
//                .createService(WebServiceApi.class)
//                .obtenerMovimientosBancarios(authHeader);
//
//        call.enqueue(new Callback<List<String>>() {
//            @Override
//            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
//                if(response.code() == 200){
//                    for(int i=0; i<response.body().size(); i++){
//                        Log.d("TAG1", "Movimiento Bancario " + i + " " + response.body().get(i));
//                    }
//                }else {
//                    Log.d("TAG1", "Token es incorrecto y no podemos obtener la respuesta");
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<String>> call, Throwable t) {
//
//            }
//        });
//
//    }

    private void obtenerToken(){


        Login login = new Login();
        login.setEmail_1(etEmail.getText().toString());
        login.setPassword(etPassword.getText().toString());

        Call<Resp> call = WebServiceJWT
                .getInstance()
                .createService(WebServiceApi.class)
                .obtenerToken(login);


        call.enqueue(new Callback<Resp>() {
            @Override
            public void onResponse(Call<Resp> call, Response<Resp> response) {
                if(response.code() == 200){


                    ok = response.body().getOk();

                    id = response.body().getId().toString();
                    usuario = response.body().getUsuario().getUsuario().toString();
                    primer_nombre = response.body().getUsuario().getPrimer_nombre().toString();;
                    apellido_paterno = response.body().getUsuario().getApellido_paterno().toString();;
                    tipo_documento= response.body().getUsuario().getTipo_documento().toString();;
                    numero_documento= response.body().getUsuario().getNumero_documento().toString();;
                    email_1= response.body().getUsuario().getEmail_1().toString();;
                    numero_celular= response.body().getUsuario().getNumero_celular().toString();;
                    role_id= response.body().getUsuario().getRole_id().toString();;
                    role_name= response.body().getUsuario().getRole_name().toString();;
                    img= response.body().getUsuario().getImg().toString();

                    token = response.body().getToken().toString();



                    Log.d("TAG1", "El ok es : " + ok);
                    Log.d("TAG1", "El id es : " + id);
                    Log.d("TAG1", "El Usuario es : " + usuario);
                    Log.d("TAG1", "El primer_nombre es : " + primer_nombre);
                    Log.d("TAG1", "El apellido_paterno es : " + apellido_paterno);
                    Log.d("TAG1", "El tipo_documento es : " + tipo_documento);
                    Log.d("TAG1", "El numero_documento es : " + numero_documento);
                    Log.d("TAG1", "El email_1 es : " + email_1);
                    Log.d("TAG1", "El numero_celular es : " + numero_celular);
                    Log.d("TAG1", "El role_id es : " + role_id);
                    Log.d("TAG1", "El role_name es : " + role_name);
                    Log.d("TAG1", "El img es : " + img);
                    Log.d("TAG1", "El Token es : " + token);

                    Log.d("TAG1", "SPMNGR " + response.body().getId());

                    SharedPrefManager.getInstance(getApplicationContext())
                            .saveUser(response.body().getUsuario());





                    startActivity(new Intent(getApplicationContext(), MainActivity.class));



                }else if (response.code() == 401) {
                    Log.d("TAG1", "No Autorizado ");
                }else {
                    Log.d("TAG1", "Token no obtenido" );
                }

            }

            @Override
            public void onFailure(Call<Resp> call, Throwable t) {

            }


        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Log.d("TAG1", "Ya loggeado!!!");
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
//            Log.d("TAG1", "Ya loggeado!!!");
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//        }
//
//    }
}