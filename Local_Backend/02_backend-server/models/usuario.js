var mongoose = require ('mongoose');

var uniqueValidator = require ('mongoose-unique-validator');

var Schema = mongoose.Schema;

// ESTA VALIDACION SOLO SIRVA PARA MONGOOSE CREAR UNA VALIDACION EN POSTGRES
var rolesValidos = {
    values: ['ADMIN_ROLE', 'USER_ROLE', 'EXT_ROLE'],
    message: '{VALUE} no es un rol permitido'
};

var usuarioSchema = new Schema({

    user: { type: String, required: [true, 'El usuario es necesario'] },
    primer_nombre: { type: String, required: [true, 'El primer nombre es necesario'] },
    segundo_nombre: { type: String, required: false },
    apellido_paterno: { type: String, required: [true, 'El apellido paterno es necesario'] },
    apellido_materno: { type: String, required: false },
    tipo_documento: { type: String, required: [true, 'El tipo de dopcumento es necesario'] },
    numero_documento: { type: String, required: [true, 'El numero de documento es necesario'] },
    email_1: { type: String, required: false },
    email_2: { type: String, required: false },
    operador_celular: { type: String, required: [true, 'El operador celular es necesario'] },
    numero_celular: { type: String, required: [true, 'El numero de celular es necesario'] },
    fecha_nacimiento: { type: Date, required: [true, 'El fecha de nacimiento es necesaria'] },

    // nombre: { type: String, required: [true, 'El nombre es necesario'] },
    //email: { type: String, unique: true, required: [true, 'El correo es necesario'] },
    role_id: { type: Number, required: true, default: 1 },
    role_name: { type: String, required: true, default: '1', enum: rolesValidos },
    
    password: { type: String, required: [true, 'La contraseña es necesaria'] },
    img: { type: String, required: false },
    google: { type: Boolean, default: false }
});



// ESA LINEA SE DEBE IR XQ YA NO SE DEBE USAR MONGOOSSE
usuarioSchema.plugin( uniqueValidator, { message: ' {PATH} debe ser unico' } );

module.exports = mongoose.model('Usuario', usuarioSchema);