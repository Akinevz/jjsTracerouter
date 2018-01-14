const connect = require('connect');
const serve = require('serve-static');
const compression = require('compression');
const browserify = require('browserify-middleware');
const path = require('path');

const app = connect();

app.use(compression());
app.use('/index.js',browserify('index.js'));
app.use(serve(path.join(__dirname,'www')));

app.listen(8080);