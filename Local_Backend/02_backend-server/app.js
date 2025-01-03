// Requires
var express = require ('express');
//var mongoose = require('mongoose');

// BODY PARSE ESTA DEPRECATED - EN UN FUTURO DEBE SER REEMPLAZADO
var bodyParser = require('body-parser');

// POSTGRES
const pool = require('./db');

// Inicializar variables
var app = express ();

// CORS
app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*"); // update to match the domain you will make the request from
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    res.header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS ");
    next();
});


// Body Parser
// parse application/x-www-form-urlencoded
// parse application/json
app.use(bodyParser.urlencoded({ extended: false }))
app.use(bodyParser.json())




// Importar Rutas
var appRoutes = require ('./routes/app');
var usuarioRoutes = require ('./routes/usuario');
var loginRoutes = require ('./routes/login');
var hospitalRoutes = require ('./routes/hospital');
var medicoRoutes = require ('./routes/medico');
var busquedaRoutes = require ('./routes/busqueda');
var uploadRoutes = require ('./routes/upload');
var imagenesRoutes = require ('./routes/imagenes');


// Conexión a la base de datos MONGO

//mongoose.connection.openUri('mongodb://localhost:27017/hospitalDB', (err, res) => {
//    if( err ) throw err;
//
//    console.log('Base de datos \x1b[32m%s\x1b[0m', 'online ');
// });

// Conexión a la base de datos POSTGRES
// seria aqui pero se ejecuta en db/index

// Rutas
app.use('/usuario', usuarioRoutes);
app.use('/hospital', hospitalRoutes);
app.use('/medico', medicoRoutes);
app.use('/login', loginRoutes);
app.use('/busqueda', busquedaRoutes);
app.use('/upload', uploadRoutes);
app.use('/img', imagenesRoutes);

app.use('/', appRoutes);



// Escuchar peticiones
app.listen(3000, () => {
    console.log('Express server puerto 3000: \x1b[32m%s\x1b[0m', 'online ');
});
