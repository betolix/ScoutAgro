const { Pool } = require ('pg');

// LINEA IMPORTANTE
const {user, host, database, password, port} = require ('../secrets/db_configuration')

//const {user, host, database, password, port} = require ('../secrets/db_configuration1')

const pool = new Pool({user, host, database, password, port});

//const pool_x = new Pool({user_role, host_role, database_role, password_role, port_role});

console.log('POSTGRES: id_db \x1b[32m%s\x1b[0m', 'ON-LINE  ...Yeahh baby!!!');


//module.exports = {pool, pool_x};
module.exports = pool;
//module.exports = pool_role;
