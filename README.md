# ScoutAgro Project

## ScoutAgro is an Android Java-based project that utilizes Retrofit for making HTTP requests to a web service. The project is built using Gradle and includes functionality for user authentication and accessing protected resources using JWT tokens.  

# Screenhots

![alt text](https://github.com/betolix/ScoutAgro/blob/main/Screenshots/001.png?raw=true)
![alt text](https://github.com/betolix/ScoutAgro/blob/main/Screenshots/002.png?raw=true)
![alt text](https://github.com/betolix/ScoutAgro/blob/main/Screenshots/003.png?raw=true)
![alt text](https://github.com/betolix/ScoutAgro/blob/main/Screenshots/004.png?raw=true)
![alt text](https://github.com/betolix/ScoutAgro/blob/main/Screenshots/005.png?raw=true)
![alt text](https://github.com/betolix/ScoutAgro/blob/main/Screenshots/006.png?raw=true)
![alt text](https://github.com/betolix/ScoutAgro/blob/main/Screenshots/007.png?raw=true)
![alt text](https://github.com/betolix/ScoutAgro/blob/main/Screenshots/008.png?raw=true)
![alt text](https://github.com/betolix/ScoutAgro/blob/main/Screenshots/009.png?raw=true)
![alt text](https://github.com/betolix/ScoutAgro/blob/main/Screenshots/010.png?raw=true)
![alt text](https://github.com/betolix/ScoutAgro/blob/main/Screenshots/011.png?raw=true)
![alt text](https://github.com/betolix/ScoutAgro/blob/main/Screenshots/012.png?raw=true)
![alt text](https://github.com/betolix/ScoutAgro/blob/main/Screenshots/013.png?raw=true)
![alt text](https://github.com/betolix/ScoutAgro/blob/main/Screenshots/014.png?raw=true)
![alt text](https://github.com/betolix/ScoutAgro/blob/main/Screenshots/015.png?raw=true)
![alt text](https://github.com/betolix/ScoutAgro/blob/main/Screenshots/016.png?raw=true)
![alt text](https://github.com/betolix/ScoutAgro/blob/main/Screenshots/017.png?raw=true)

<!--- ### INTRO PARAGRAPH ( SEO Value keywords ) Deeper dive -->
# A deeper dive

### Features:

Login Screen - User Authentication: Secure user authentication using a local backend, Encrypted Token stored in Shared Preferences.

Encrypted Shared Preferences: Securely store user data using encrypted shared preferences.

MapView showing


Project Structure
app/src/main/java/io/h3llo/scoutagro/api/  
WebServiceApi.java: Defines the API endpoints for user login and accessing protected resources.
WebServiceJWT.java: Configures the Retrofit instance with logging and base URL settings.
gradle/wrapper/gradle-wrapper.properties: Configuration for the Gradle wrapper.  
Dependencies
Java
Gradle
Retrofit
OkHttp
Gson
Setup
Clone the repository:  
git clone <repository-url>
cd scoutagro
Configure Gradle: Ensure that the gradle-wrapper.properties file points to the correct Gradle distribution:  
distributionUrl=https\://services.gradle.org/distributions/gradle-8.9-bin.zip
Build the project:  
./gradlew build
Usage
WebServiceApi
This interface defines the API endpoints for the application.  
obtenerToken(@Body Login login): POST request to obtain a token.
obtenerMovimientosBancarios(@Header("Authorization") String authHeader): GET request to access protected resources.
WebServiceJWT
This class configures the Retrofit instance.
getInstance(): Returns a singleton instance of WebServiceJWT.
createService(Class<S> serviceClass): Creates a service for the specified API interface.
Example

WebServiceJWT webServiceJWT = WebServiceJWT.getInstance();
WebServiceApi apiService = webServiceJWT.createService(WebServiceApi.class);

// Example usage for obtaining token
Login login = new Login("username", "password");
Call<Resp> call = apiService.obtenerToken(login);


Configuration
Base URL: The base URL for the web service can be configured in WebServiceJWT.java by setting the BASE_URL_JWT variable.
License
This project is licensed under the MIT License. See the LICENSE file for details.  
Contact
For any inquiries or issues, please contact the project maintainer at betolix@gmail.com.
