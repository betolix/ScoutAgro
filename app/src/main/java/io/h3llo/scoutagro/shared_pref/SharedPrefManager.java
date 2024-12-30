package io.h3llo.scoutagro.shared_pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import io.h3llo.scoutagro.model.User;


public class SharedPrefManager {

    private static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";

    private static final String SHARED_PREFERENCES_ID = "SHARED_PREFERENCES_ID";
    private static final String SHARED_PREFERENCES_USUARIO = "SHARED_PREFERENCES_USUARIO";
    private static final String SHARED_PREFERENCES_PRIMER_NOMBRE = "SHARED_PREFERENCES_PRIMER_NOMBRE";
    private static final String SHARED_PREFERENCES_APELLIDO_PATERNO = "SHARED_PREFERENCES_APELLIDO_PATERNO";
    private static final String SHARED_PREFERENCES_TIPO_DOCUMENTO = "SHARED_PREFERENCES_TIPO_DOCUMENTO";
    private static final String SHARED_PREFERENCES_NUMERO_DOCUMENTO = "SHARED_PREFERENCES_NUMERO_DOCUMENTO";
    private static final String SHARED_PREFERENCES_EMAIL = "SHARED_PREFERENCES_EMAIL";
    private static final String SHARED_PREFERENCES_NUMERO_CELULAR = "SHARED_PREFERENCES_NUMERO_CELULAR";
    private static final String SHARED_PREFERENCES_ROLE_ID = "SHARED_PREFERENCES_ROLE_ID";
    private static final String SHARED_PREFERENCES_ROLE_NAME = "SHARED_PREFERENCES_ROLE_NAME";
    private static final String SHARED_PREFERENCES_FOTO = "SHARED_PREFERENCES_FOTO";


    private static SharedPrefManager instance;
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    private SharedPrefManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized io.h3llo.scoutagro.shared_pref.SharedPrefManager getInstance(Context context){
        if(instance == null){
            instance = new io.h3llo.scoutagro.shared_pref.SharedPrefManager(context);
        }
        return instance;
    }

    public void saveUser(User user){


        Log.d("TAG1", "WEEEE " + user.get_id());
        Log.d("TAG1", "WEEEE " + user.getUsuario());
        editor.putString(SHARED_PREFERENCES_ID , user.get_id());
        editor.putString(SHARED_PREFERENCES_USUARIO , user.getUsuario());
        editor.putString(SHARED_PREFERENCES_PRIMER_NOMBRE , user.getPrimer_nombre());
        editor.putString(SHARED_PREFERENCES_APELLIDO_PATERNO , user.getApellido_paterno());
        editor.putString(SHARED_PREFERENCES_TIPO_DOCUMENTO , user.getTipo_documento());
        editor.putString(SHARED_PREFERENCES_NUMERO_DOCUMENTO , user.getNumero_documento());
        editor.putString(SHARED_PREFERENCES_EMAIL , user.getEmail_1());
        editor.putString(SHARED_PREFERENCES_NUMERO_CELULAR , user.getNumero_celular());
        editor.putString(SHARED_PREFERENCES_ROLE_ID , user.getRole_id());
        editor.putString(SHARED_PREFERENCES_ROLE_NAME , user.getRole_name());
        editor.putString(SHARED_PREFERENCES_FOTO , user.getImg());

        editor.apply();
    }

    public boolean isLoggedIn(){
        if(sharedPreferences.getString(SHARED_PREFERENCES_ID, null) != null){
            return true;
        }else
            return false;
    }

    public User getUser(){
        User user = new User(
                sharedPreferences.getString(SHARED_PREFERENCES_ID, null),
                sharedPreferences.getString(SHARED_PREFERENCES_USUARIO, null),
                sharedPreferences.getString(SHARED_PREFERENCES_PRIMER_NOMBRE, null),
                sharedPreferences.getString(SHARED_PREFERENCES_APELLIDO_PATERNO, null),
                sharedPreferences.getString(SHARED_PREFERENCES_TIPO_DOCUMENTO, null),
                sharedPreferences.getString(SHARED_PREFERENCES_NUMERO_DOCUMENTO, null),
                sharedPreferences.getString(SHARED_PREFERENCES_EMAIL, null),
                sharedPreferences.getString(SHARED_PREFERENCES_NUMERO_CELULAR, null),
                sharedPreferences.getString(SHARED_PREFERENCES_ROLE_ID, null),
                sharedPreferences.getString(SHARED_PREFERENCES_ROLE_NAME, null),
                sharedPreferences.getString(SHARED_PREFERENCES_FOTO, null)
        );
        return user;
    }

    public void logOut(){
        editor.clear();
        editor.apply();
    }



}
