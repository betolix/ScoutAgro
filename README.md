# ScoutAgro Project

## ScoutAgro is an Android application, used to measure planted land in the country of Perú for agricultural insurance purposes. It is a Java-based project that utilizes Retrofit for making HTTP requests to a local backend for authentication and authorization. The architecture of the project does not meet clean architecture standards because it was needed in a very short time period. It uses xml layouts and fragments for presenting google maps. On top of this maps the user can see it's own location and land markers are positioned by the user with the purpose of marking a planted parcel and to measure the area and then report the coordinates in geojson format.

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

Login Screen - User Authentication: Secure user authentication using a local backend.

Encrypted Shared Preferences: Securely store user data using encrypted shared preferences.

MapView overlay showing google maps layer as a base layer, and on top the geojson shape of the country of Perú, the regions that are under evalluation.

On short press of the customInfoWindow the shape of all the statistical sectors in the selected region will appear.

On long press of the customInfoWindow a list of all the insured products on the statistical sectors withion the region will appear.

On selection of a product and a statistical sector you will navigate to a new Mapview, where the statistical appear as an overlay. Once in there you can observe your current location in a blue marker, and can drop pins marking a perimeter to calculate the area and share the data as text and as a geojson.

Technologies Used

Java: Primary programming language. xml ( imperative paradigm ) : Backend services including authentication and authorization. Google maps for base overlay.
GPS positioning. Java, Gradle, Retrofit, OkHttp, Gson.



# Project Structure
app/src/main/java/io/h3llo/scoutagro/api/  

WebServiceApi.java: Defines the API endpoints for user login and accessing protected resources.
WebServiceJWT.java: Configures the Retrofit instance with logging and base URL settings.

gradle/wrapper/gradle-wrapper.properties: Configuration for the Gradle wrapper.  
Dependencies

# Setup

Install postgres
Create the database user node_user with password node_password ant the corresponding privileges(this can be changed accordingly but they have to be modifyed in the file backend-server/secrets/db_configuration.js)
Create the databases id_db and role_db with the RestoreScript_id_db and the RestoreScript_role_db (execute sql commands in pgAdmin or as psql)

Install node
In the command line, navigate to the backend-server folder
Execute the following commands
npm install
npm audit fix --force
npm run start

On a differente terminar run this command and local the local ip address of the computer where you are executing the backend

ifconfig | grep "inet.*broadcast"  

Clone the repository:  
git clone https://github.com/betolix/ScoutAgro
Open the android project with AndroidStudio
On the file api/WebServiceJWT.java and replace local ip you found on the ifcofig command

Now you can run the app with the default credentials.


### License
This project is licensed under the MIT License. See the LICENSE file for details.  
### Contact 
For any inquiries or issues, please contact the project maintainer at betolix@gmail.com.
