var express = require('express');
var bcrypt = require('bcryptjs');
var jwt = require('jsonwebtoken');

// POSTGRES
const pool = require('../db');

// var SEED = require('../config/config').SEED;

var mdAutenticacion = require('../middlewares/autenticacion');

var app = express();

var Usuario = require('../models/usuario');

// Rutas

//==================================================
// Obtener todos los usuarios
//==================================================
/*
app.get("/", (req, res, next) => {

  var desde = req.query.desde || 0;
  desde = Number(desde);

  Usuario.find({}, 'nombre email img role google ')
  .skip(desde)
  .limit(5)
  .exec(  
    
    (err, usuarios) => {
    if (err) {
      return res.status(500).json({
        ok: false,
        mensaje: 'Error cargando usuario',
        errors: err
      });
    }

    Usuario.count({}, (err, conteo) => {

      res.status(200).json({
        ok: true,
        usuarios: usuarios,
        total: conteo
      });

    } )

    

  });
});
*/

//==================================================
// Obtener todos los usuarios POSTGRES OK
//==================================================
app.get("/", (req, res, next) => {

  // console.log('Weeeee');
  pool.query('select _id, usuario, primer_nombre, segundo_nombre, apellido_paterno, apellido_materno, tipo_documento, numero_documento, email_1, email_2, operador_celular, numero_celular, fecha_nacimiento, role_id, role_name, password, img, google from t_usuario ORDER BY _id ASC', (error, response) => {
    if (error ) {
      return res.status(500).json({
        ok: false,
        mensaje: 'Error cargando usuario',
        errors: error
      });
    }
    //console.log(response.rows);
    res.status(200).json({
      ok: true,
      usuarios: response.rows,
      total: response.rowCount
    });

  });

});




// //==================================================
// // Actualizar usuario
// //==================================================
// app.put('/:id', [mdAutenticacion.verificaToken, mdAutenticacion.verificaADMIN_o_MismoUsuario ] ,(req, res) => {

//   var id = req.params.id;
//   var body = req.body;

//   Usuario.findById( id, (err, usuario) => {
//     if (err) {
//       return res.status(500).json({
//         ok: false,
//         mensaje: "Error al buscar usuario",
//         errors: err
//       });
//     }

//     if ( !usuario ) {
//       return res.status(400).json({
//         ok: false,
//         mensaje: 'El usuari con el id ' + id + 'no existe',
//         errors: { message: 'No existe un usuario con ese ID' }
//       });
//     }

//     usuario.nombre = body.nombre;
//     usuario.email = body.email;
//     usuario.role = body.role;

//     usuario.save( (err, usuarioGuardado) => {
//       if (err) {
//         return res.status(400).json({
//           ok: false,
//           mensaje: "Error al actualizar usuario",
//           errors: err
//         });
//       }

//       usuarioGuardado.password = ':)';

//       res.status(200).json({
//         ok: true,
//         usuario: usuarioGuardado
//       });


//     } );


//   } );

// } );

//==================================================
// Actualizar usuario POSTGRES OK
//==================================================
app.put('/:id', /*[mdAutenticacion.verificaToken, mdAutenticacion.verificaADMIN_o_MismoUsuario ],*/ (req, res) => {
 
  var id = req.params.id;
  var body = req.body;

  //Usuario.findById( id, (err, usuario) => {
  pool.query('select _id, usuario, email_1, img, role_id, google  from t_usuario WHERE _id= $1 ORDER BY _id ASC', [id], (err, usuario) => {
    if (err) {
      // console.log('Weeerr: ', err);
      return res.status(500).json({
        ok: false,
        mensaje: "Error al buscar usuario",
        errors: err
      });
    }

    if ( usuario.rowCount == 0 ) {
      return res.status(400).json({
        ok: false,
        mensaje: 'El usuario con el id ' + id + ' no existe',
        errors: { message: 'No existe un usuario con ese ID' }
      });
    }

    console.log('body.usuario', body.usuario);
    usuario.user = body.usuario;
    usuario.primer_nombre = body.primer_nombre;
    usuario.segundo_nombre = body.segundo_nombre;
    usuario.apellido_paterno = body.apellido_paterno;
    usuario.apellido_materno = body.apellido_materno;
    usuario.tipo_documento = body.tipo_documento;
    usuario.numero_documento = body.numero_documento;
    usuario.email_1 = body.email_1;
    usuario.email_2 = body.email_2;
    usuario.operador_celular = body.operador_celular;
    usuario.numero_celular = body.numero_celular;
    usuario.fecha_nacimiento = body.fecha_nacimiento;
    usuario.role_id = body.role_id;
    usuario.role_name = body.role_name;
    //usuario.password = body.password;
    // usuario.img = body.img;
    usuario.google = body.google;


    //usuario.email = body.email;
    //usuario.role = body.role;

    // usuario.save( (err, usuarioGuardado) => {
      pool.query('UPDATE t_usuario SET usuario=$1, primer_nombre=$2, segundo_nombre=$3, apellido_paterno=$4, apellido_materno=$5, tipo_documento=$6, numero_documento=$7, email_1=$8, email_2=$9, operador_celular=$10, numero_celular=$11, fecha_nacimiento=$12, role_id=$13, role_name=$14, img=$15, google=$16 WHERE _id=$16 RETURNING *', [body.usuario, body.primer_nombre, body.segundo_nombre, body.apellido_paterno, body.apellido_materno, body.tipo_documento, body.numero_documento, body.email_1, body.email_2, body.operador_celular, body.numero_celular, body.fecha_nacimiento, body.role_id, body.role_nme, body.img, body.google, id], (error, usuarioGuardado) => {
        if (error) {
        console.log(error);
        return res.status(400).json({
          ok: false,
          mensaje: "Error al actualizar usuario",
          errors: err
        });
      }

      usuarioGuardado.rows[0].password = ':)';

      //console.log(usuarioGuardado.rows[0].password);
      res.status(200).json({
        ok: true,
        usuario: usuarioGuardado.rows
      });


    } );


  } );

} );

// //==================================================
// // Crear un nuevo usuario
// //==================================================
// app.post('/', /* mdAutenticacion.verificaToken ,*/ (req, res) => {

//     var body = req.body;

//     var usuario = new Usuario({
//         nombre: body.nombre,
//         email: body.email,
//         password: bcrypt.hashSync( body.password, 10),
//         img: body.img,
//         role: body.role
//     });

//     usuario.save( (err, usuarioGuardado ) => {

//         if (err) {
//             return res.status(400).json({
//               ok: false,
//               mensaje: "Error al crear usuario",
//               errors: err
//             });
//           }

//           res.status(201).json({
//             ok: true,
//             usuario: usuarioGuardado,
//             usuariotoken: req.usuario
//           });

//     }  );

// } )


//==================================================
// Crear un nuevo usuario POSTGRES OK
//==================================================
app.post('/', /*mdAutenticacion.verificaToken,*/  (req, res) => {

  var body = req.body;
  console.log('bodyx' ,body);

  var usuario = new Usuario({
      user: body.usuario,
      primer_nombre: body.primer_nombre,
      segundo_nombre: body.segundo_nombre,
      apellido_paterno: body.apellido_paterno,
      apellido_materno: body.apellido_materno,
      tipo_documento: body.tipo_documento,
      numero_documento: body.numero_documento,
      email_1: body.email_1,
      email_2: body.email_2,
      operador_celular: body.operador_celular,
      numero_celular: body.numero_celular,
      fecha_nacimiento: body.fecha_nacimiento,
      role_id: body.role_id,
      role_id: body.role_name,
      //OJO AQUI ESTA EL ERROR
      // password: body.password,
      password: bcrypt.hashSync( body.password, 10),
      img: body.img,
      google: body.google
  });

  // usuario.save( (err, usuarioGuardado ) => {
  //pool.query('INSERT INTO public.t_usuario( primer_nombre, email_1, password, role_id, img, google) VALUES ($1,$2,$3,$4,$5, $6) returning *', [usuario.nombre, usuario.email, usuario.password, usuario.role, usuario.img, false ], (error, usuarioGuardado) => {
  pool.query('INSERT INTO public.t_usuario(usuario, primer_nombre, segundo_nombre, apellido_paterno, apellido_materno, tipo_documento, numero_documento,  email_1, email_2, operador_celular, numero_celular, fecha_nacimiento, role_id, role_name, password, img, google) VALUES ($1,$2,$3,$4,$5,$6,$7,$8,$9,$10,$11,$12,$13,$14,$15,$16,$17) returning *', [usuario.user, usuario.primer_nombre, usuario.segundo_nombre, usuario.apellido_paterno, usuario.apellido_materno, usuario.tipo_documento, usuario.numero_documento, usuario.email_1, usuario.email_2, usuario.operador_celular, usuario.numero_celular, usuario.fecha_nacimiento, usuario.role_id, usuario.role_name, usuario.password, usuario.img, usuario.google], (error, usuarioGuardado) => {

      if (error) {
        console.log(error);
          return res.status(400).json({
            ok: false,
            mensaje: "Error al crear usuario",
            errors: error
          });
        }
        usuarioGuardado.rows[0].password = ':)';

        res.status(201).json({
          ok: true,
          usuario: usuarioGuardado.rows[0],
          usuariotoken: req.usuario
        });

  }  );

} )




// //==================================================
// // Borrar un usuario por el id
// //==================================================

// app.delete('/:id', [mdAutenticacion.verificaToken, mdAutenticacion.verificaADMIN_ROLE] , (req, res) => {

//   var id = req.params.id;
//   Usuario.findByIdAndRemove(id, (err, usuarioBorrado) => {

//     if (err) {
//       return res.status(500).json({
//         ok: false,
//         mensaje: "Error al borrar usuario",
//         errors: err
//       });
//     }

//     if ( !usuarioBorrado ) {
//       return res.status(400).json({
//         ok: false,
//         mensaje: 'No existe un usuario con ese id',
//         errors: { message: 'No existe un usuario con ese id' }
//       });
//     }




//     res.status(200).json({
//       ok: true,
//       usuario: usuarioBorrado
//     });

//   });


// })

//==================================================
// Borrar un usuario por el id POSTGRES
//==================================================

app.delete('/:id', /*[mdAutenticacion.verificaToken, mdAutenticacion.verificaADMIN_ROLE],*/ (req, res) => {

  var id = req.params.id;
  // Usuario.findByIdAndRemove(id, (err, usuarioBorrado) => {
  pool.query( 'DELETE FROM usuario WHERE _id=($1) RETURNING *', [id], (err, response) => { // res
    
    
    if (err) {
      console.log('err', err);
      return res.status(500).json({
        ok: false,
        mensaje: "Error al borrar usuario",
        errors: err
      });
    }

    if ( response.rowCount == 0  ) {
      // console.log('usuarioBorrado.usuario === null');
      return res.status(400).json({
        ok: false,
        mensaje: 'No existe un usuario con ese id',
        errors: { message: 'No existe un usuario con ese id' }
      });
    }

    response.rows[0].password = ':)';

    return res.status(200).json({
      ok: true,
      usuario: response.rows[0]
    });

  });


})



//==================================================
// Obtener usuario por id POSTGRES OK
//==================================================
app.get('/:id', /*[mdAutenticacion.verificaToken, mdAutenticacion.verificaADMIN_o_MismoUsuario ],*/ (req, res) => {
 
  var id = req.params.id;
  var body = req.body;

  //pool.query('select _id, usuario, email_1, img, role_id, google  from t_usuario WHERE _id= $1 ORDER BY _id ASC', [id], (err, usuario) => {
  pool.query('SELECT _id, user_status, usuario, primer_nombre, segundo_nombre, apellido_paterno, apellido_materno, tipo_documento, numero_documento, email_1, email_2, operador_celular, numero_celular, fecha_nacimiento, role_id, role_name, password, img, google FROM t_usuario WHERE _id= $1 ORDER BY _id ASC', [id], (err, usuario) => {
  // SELECT _id, user_status, usuario, primer_nombre, segundo_nombre, apellido_paterno, apellido_materno, tipo_documento, numero_documento, email_1, email_2, operador_celular, numero_celular, fecha_nacimiento, role_id, role_name, password, img, google FROM public.t_usuario;
    if (err) {
      // console.log('Weeerr: ', err);
      return res.status(500).json({
        ok: false,
        mensaje: "Error al buscar usuario",
        errors: err
      });
    }

    if ( usuario.rowCount == 0 ) {
      return res.status(400).json({
        ok: false,
        mensaje: 'El usuario con el id ' + id + ' no existe',
        errors: { message: 'No existe un usuario con ese ID' }
      });
    }

    console.log('body.usuario', body.usuario);
    usuario.user = body.usuario;
    usuario.primer_nombre = body.primer_nombre;
    usuario.segundo_nombre = body.segundo_nombre;
    usuario.apellido_paterno = body.apellido_paterno;
    usuario.apellido_materno = body.apellido_materno;
    usuario.tipo_documento = body.tipo_documento;
    usuario.numero_documento = body.numero_documento;
    usuario.email_1 = body.email_1;
    usuario.email_2 = body.email_2;
    usuario.operador_celular = body.operador_celular;
    usuario.numero_celular = body.numero_celular;
    usuario.fecha_nacimiento = body.fecha_nacimiento;
    usuario.role_id = body.role_id;
    usuario.role_name = body.role_name;
    //usuario.password = body.password;
    // usuario.img = body.img;
    usuario.google = body.google;


    //usuario.email = body.email;
    //usuario.role = body.role;

    // usuario.save( (err, usuarioGuardado) => {
      // pool.query('UPDATE t_usuario SET usuario=$1, primer_nombre=$2, segundo_nombre=$3, apellido_paterno=$4, apellido_materno=$5, tipo_documento=$6, numero_documento=$7, email_1=$8, email_2=$9, operador_celular=$10, numero_celular=$11, fecha_nacimiento=$12, role_id=$13, role_name=$14, img=$15, google=$16 WHERE _id=$16 RETURNING *', [body.usuario, body.primer_nombre, body.segundo_nombre, body.apellido_paterno, body.apellido_materno, body.tipo_documento, body.numero_documento, body.email_1, body.email_2, body.operador_celular, body.numero_celular, body.fecha_nacimiento, body.role_id, body.role_nme, body.img, body.google, id], (error, usuarioGuardado) => {
      //   if (error) {
      //   console.log(error);
      //   return res.status(400).json({
      //     ok: false,
      //     mensaje: "Error al actualizar usuario",
      //     errors: err
      //   });
      // }

      usuario.rows[0].password = ':)';
      console.log('TEST', usuario.rows[0])

      //console.log(usuarioGuardado.rows[0].password);
      res.status(200).json({
        ok: true,
        usuario: usuario.rows
      });


    // } );


  } );

} );



module.exports = app;
