const proxy = require('http-proxy-middleware');
module.exports = function(app) {
    app.use(proxy('/start', 
        { target: 'http://localhost:8081/' }
    ));
}