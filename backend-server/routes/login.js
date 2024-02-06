var express = require("express");
var bcrypt = require('bcryptjs');
var jwt = require('jsonwebtoken');

var SEED = require('../config/config').SEED;

var app = express();
var Usuario = require("../models/usuario");

// POSTGRES
//const pool = require('../db').pool;
const pool = require('../db/index');

//const pool_x = require('../db').pool_x;
const pool_x = require('../db/index1');
//console.log('pool ', pool_x);


// Google
var CLIENT_ID = require('../config/config').CLIENT_ID;
const { OAuth2Client } = require('google-auth-library');
const client = new OAuth2Client(CLIENT_ID);

var mdAutenticacion = require('../middlewares/autenticacion');

//==================================================
// Autenticación de Google
//==================================================
app.get('/renuevatoken', mdAutenticacion.verificaToken, (req, res) => {

    var token = jwt.sign({ usuario: req.usuario }, SEED, { expiresIn: 14400 }); // 4 HORAS

    res.status(200).json({
        ok: true,
        token: token
    });

});


//==================================================
// Autenticación de Google
//==================================================
async function verify(token) {
    const ticket = await client.verifyIdToken({
        idToken: token,
        audience: CLIENT_ID,  // Specify the CLIENT_ID of the app that accesses the backend
        // Or, if multiple clients access the backend:
        //[CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3]
    });

    const payload = ticket.getPayload();
    //  const userid = payload['sub'];
    // If request specified a G Suite domain:
    //const domain = payload['hd'];
    return {
        nombre: payload.name,
        email: payload.email,
        img: payload.picture, //Image URL
        google: true
        // payload: payload
    }
}


app.post('/google', async (req, res) => {

    var token = req.body.token;

    var googleUser = await verify(token)
        .catch(e => {
            return res.status(403).json({
                ok: false,
                mensaje: 'Token no válido'
            });
        })

    console.log('googleUser ', googleUser);


    // Usuario.findOne( { email: googleUser.email}, ( err, usuarioDB ) => {
    pool.query('select _id, email_1, password, img, role_id, google from t_usuario WHERE email_1 = $1 ORDER BY _id ASC', [googleUser.email], (err, usuarioDB) => {

        console.log('err ', err);
        if (err) {
            return res.status(500).json({
                ok: false,
                mensaje: 'Error al buscar usuario',
                errors: err
            });
        }

        if (usuarioDB.rowCount > 0) {
            if (usuarioDB.rows.google === false) {

                return res.status(400).json({
                    ok: false,
                    mensaje: 'Debe usar su autenticación normal'
                });
            } else {

                console.log('usuarioDB.rows[0] ', usuarioDB.rows[0]);
                var token = jwt.sign({ usuario: usuarioDB.rows[0] }, SEED, { expiresIn: 14400 }); // 4 HORAS

                res.status(200).json({
                    ok: true,
                    usuario: usuarioDB.rows[0],
                    token: token,
                    id: usuarioDB.rows[0]._id,
                    menu: obtenerMenu(usuarioDB.rows[0].role)
                });

            }
        } else {
            // EL USUARIO NO EXISTE, HAY QUE CREARLO
            var usuario = new Usuario();

            usuario.nombre = googleUser.nombre;
            usuario.email = googleUser.email;
            usuario.img = googleUser.img;
            usuario.google = true;
            usuario.password = ':)';

            console.log('googleUser.nombre ', googleUser.nombre);


            // usuario.save( (err, usuarioDB) => {
            pool.query('INSERT INTO public.t_usuario( usuario, email_1, password, role_id, img, google) VALUES ($1,$2,$3,$4,$5,$6) returning *', [usuario.nombre, usuario.email, usuario.password, usuario.role, usuario.img, false], (error, usuarioDB) => {

                console.log('error ', error);
                console.log('usuarioDB.rows  ', usuarioDB);

                var token = jwt.sign({ usuario: usuarioDB.rows[0] }, SEED, { expiresIn: 14400 }); // 4 HORAS

                res.status(200).json({
                    ok: true,
                    usuario: usuarioDB.rows[0],
                    token: token,
                    id: usuarioDB.rows._id,
                    menu: obtenerMenu(usuarioDB.role)
                });




            });
        }

    })

    // return res.status(200).json({
    //   ok: true,
    //   mensaje: 'OK!!!',
    //   googleUser: googleUser
    // });


});




//==================================================
// Autenticación Normal
//==================================================
app.post('/', (req, res) => {

    var id = req.params.id;
    var body = req.body;
    //req.body.password=':)';
    //console.log('body ', req.body);
    
    console.log('Alohaaa');

    // Usuario.findOne({ email: body.email }, (err, usuarioDB ) => {
    pool.query('select _id, usuario, primer_nombre, segundo_nombre, apellido_paterno, apellido_materno, tipo_documento, numero_documento, email_1, email_2, operador_celular, numero_celular, fecha_nacimiento, role_id, role_name, password, img, google from t_usuario WHERE email_1= $1 ', [body.email_1], (err, usuarioDB) => {
    //pool.query('select * from t_usuario WHERE email_1= $1 ', [body.email_1], (err, usuarioDB) => {

        console.log('Mahalooo');
        console.log('err');
        // usuarioDB.password=':)';
        // console.log('usuarioDB.rows[0] ', usuarioDB.rows[0]);


        if (err) {
            console.log('err ', err);
            return res.status(500).json({
                ok: false,
                mensaje: 'Error al buscar usuario',
                errors: err
            });
        }

        if (usuarioDB.rowCount == 0) {
            return res.status(400).json({
                ok: false,
                mensaje: 'Credenciales incorrectas - email',
                errors: err
            });

        }
        // AQUI ESTA EL ERROR DEL BCRYPT
        // console.log('body.password: ',body.password );
        // console.log('usuarioDB.rows[0].password: ',usuarioDB.rows[0].password );

        //OJO A COMENTAR ESTAS LINEAS ↑↑
        if (!bcrypt.compareSync(body.password, usuarioDB.rows[0].password)) {
            return res.status(400).json({
                ok: false,
                mensaje: 'Credenciales incorrectas - password',
                errors: err
            });

        }
        usuarioDB.rows[0].password = ':)'; // PARA NO MANDAR EL PASSWORD EN EL TOKEN

        // CREAR UN TOKEN!!!
        console.log('// Crear un token!!!');
        // console.log('usuarioDB.rows[0] ', usuarioDB.rows[0]);
        var token = jwt.sign({ usuario: usuarioDB.rows[0] }, SEED, { expiresIn: 14400 }); // 4 HORAS

        //menu = ()=>{obtenerMenuX( usuarioDB.rows[0].role );}
        //console.log('menu: ', menu);
        
        ROLE = 1;
        var menu = [];
        var submenu =[];
        
        console.log('[usuarioDB.rows[0] ',usuarioDB.rows[0] );
        // pool_x.query('select * from menu order by id', (err3, resp3) => {
          pool_x.query('select M.menu_id, M.displayname, M.disabled, M.iconname, M.route, M.nivel, M.parentid, M.ura from users_roles_app as URA inner join role_menu as RM on URA.role_id = RM.id_role inner join menu as M on RM.id_menu = M.menu_id where URA.user_id = $1 and URA.app_id =1 and URA.role_id = $2 ORDER BY M.menu_id;', [usuarioDB.rows[0]._id, usuarioDB.rows[0].role_id] ,(err3, resp3) => {
        //pool_x.query('select M.menu_id, M.displayname, M.disabled, M.iconname, M.route, M.nivel, M.parentid, M.ura from users_roles_app as URA inner join role_menu as RM on URA.role_id = RM.id_role inner join menu as M on RM.id_menu = M.menu_id where URA.user_id = $1 and URA.app_id =1 and URA.role_id = $2;',[usuarioDB.rows[0]._id, usuarioDB.rows[0].role_id], (err3, resp3) => {
        
            if (err3) {
                console.log('err ', err3);
                return res.status(500).json({
                    ok: false,
                    mensaje: 'Error al buscar rol',
                    errors: err3
                });
            }
            if (resp3.rowCount == 0) {
                return res.status(400).json({
                    ok: false,
                    mensaje: 'Rol incorrectas - email',
                    errors: err3
                });
            };


            //submenu = [];
            //             { titulo: 'Dashboard', url: '/dashboard'},
            //             { titulo: 'ProgressBar', url: '/progress'},
            //             { titulo: 'Gráficas', url: '/graficas1'},
            //             { titulo: 'Promesas', url: '/promesas'},
            //             { titulo: 'RxJs', url: '/rxjs'}
            //         ]
                    
            let contMenu=0;
            let contSubMenu=0;
            //const submenux = [];
            let i = 0;
            let j = 0;
            //let k = 0;

            console.log('HHH ',resp3.rows);

            // console.log('resp3.rows.length ', resp3.rows.length);
            for (i; i < resp3.rows.length; i++) {
                
                //console.log('i ',i,' resp3.rows[i].parentid ',resp3.rows[i].parentid);
                if(resp3.rows[i].parentid == 0){
                   // console.log('YES');
                   // console.log('i:',i,' j: ',j,' contMenu: ', contMenu, 'contSubMenu: ', contSubMenu);
                    menu[contMenu] = {
                        titulo: resp3.rows[i].displayname,
                        icono: resp3.rows[i].iconname,
                        submenu: []
                        
                    };
                    contMenu++;
                }
            }
            console.log('menu 1: \n', menu);
            console.log('menu.length', menu.length);

            for(j=0 ; j<menu.length;j++){

                for(k=0 ; k<resp3.rows.length;k++){

                    
                    if(resp3.rows[k].parentid == resp3.rows[j].menu_id){
                        // console.log('Entro j es: ',j,' k es ',k);
                        // console.log(resp3.rows[k].displayname, 'es submenu de: ', resp3.rows[j].displayname);
                        menu[j].submenu[contSubMenu]={
                            titulo: resp3.rows[k].displayname,
                            url: resp3.rows[k].route
                        };
                        //console.log('submenux: ',submenux[contSubMenu]);
                        
                        contSubMenu++; 
                    }
                    
                }
                //console.log('submenux.length: ',submenux.length);
                //if(submenux.length>0){
                //    menu[j].submenu=submenux;
                //}
                
                contSubMenu=0;
                
            }
            
            

            console.log('menu2: ', menu);
            console.log('Keys: ', menu[0].keys);
        
            
            res.status(200).json({
                ok: true,
                usuario: usuarioDB.rows[0],
                token: token,
                id: usuarioDB.rows[0]._id,
                menu: menu//obtenerMenuX( usuarioDB.rows[0].role )
                // menu: ()=>{ obtenerMenuX( usuarioDB.rows[0].role );}
    
            });



        });




    })

});


// LOCAL STATIC HARD CODED FUNCION
function obtenerMenu(ROLE) {

    var menu = [
        {
            titulo: 'Principal',
            icono: 'mdi mdi-gauge',
            submenu: [
                { titulo: 'Dashboard', url: '/dashboard' },
                { titulo: 'ProgressBar', url: '/progress' },
                { titulo: 'Gráficas', url: '/graficas1' },
                { titulo: 'Promesas', url: '/promesas' },
                { titulo: 'RxJs', url: '/rxjs' }
            ]
        },
        {
            titulo: 'Mantenimientos',
            icono: 'mdi mdi-folder-lock-open',
            submenu: [
                // { titulo: 'Usuarios', url: '/usuarios' },
                { titulo: 'Hospitales', url: '/hospitales' },
                { titulo: 'Médicos', url: '/medicos' }
            ]
        }
    ];

    console.log('ROLE: ', ROLE);

    if (ROLE === 'ADMIN_ROLE') {
        menu[1].submenu.unshift({ titulo: 'Usuarios', url: '/usuarios' });
    }

    console.log('menu ', menu);
    return menu;
}



//==================================================
// FUNCION TO GET MENU BASED ON ROLES FROM DB
//==================================================

function obtenerMenuX(ROLE) {

    // var id = req.params.id;
    // var body = req.body;
    //req.body.password=':)';
    ROLE = 1;
    var menu = [];
    console.log('obtenerMenuX ');

    // Usuario.findOne({ email: body.email }, (err, usuarioDB ) => {
    //pool_role.query('select id, user_id, role_id, app_id from users_roles_app WHERE role_id= $1 ', [ROLE], (err, roleDB) => {


    console.log('WTf!!!! ');

    pool_x.query('select * from menu', (err1, resp) => {


        //console.log('resp ', resp.rows);
        if (err1) {
            console.log('err ', err1);
            return resp.status(500).json({
                ok: false,
                mensaje: 'Error al buscar rol',
                errors: err1
            });
        }
        if (resp.rowCount == 0) {
            return res.status(400).json({
                ok: false,
                mensaje: 'Rol incorrectas - email',
                errors: err1
            });
        };

        for (i = 0; i < resp.rows.length; i++) {
            console.log('length ', resp.rows.length);
            menu[i] = {
                // titulo: resp.rows[i].displayname,
                titulo: 'mdi mdi-gauge',
                icono: 'mdi mdi-gauge',
                // submenu: [
                //   { titulo: 'Dashboard', url: '/dashboard'},
                //   { titulo: 'ProgressBar', url: '/progress'},
                //   { titulo: 'Gráficas', url: '/graficas1'},
                //   { titulo: 'Promesas', url: '/promesas'},
                //   { titulo: 'RxJs', url: '/rxjs'}]
            }

        }
        //console.log('menu ', menu);
        console.log('menuXXX', menu);
        return menu;

    });


    /// AQUIIIIIII

    // var menu = [
    //   {
    //     titulo: 'REPORTES',
    //     icono: 'mdi mdi-gauge',
    //     submenu: [
    //       { titulo: 'Dashboard', url: '/dashboard'},
    //       { titulo: 'ProgressBar', url: '/progress'},
    //       { titulo: 'Gráficas', url: '/graficas1'},
    //       { titulo: 'Promesas', url: '/promesas'},
    //       { titulo: 'RxJs', url: '/rxjs'}
    //     ]
    //   },
    //   {
    //     titulo: 'SINIESTROS',
    //     icono: 'mdi mdi-folder-lock-open',
    //     submenu: [
    //       // { titulo: 'Usuarios', url: '/usuarios' },
    //       { titulo: 'Siniestros Prestatarios',  url: '/hospitales' },
    //       { titulo: 'Siniestros Hogar', url: '/medicos' }
    //     ]
    //   }
    // ];



}


module.exports = app;