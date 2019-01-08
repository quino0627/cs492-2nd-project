var express = require('express');
module.exports = function (app,url) {

    // app.get('/storage/:image_url', function (req, res) {

    //     res.send('.\storage\woLoginTest++190105163011.png');
    //     console.log("testtest");
    // })
    app.get('/storage/:image_url', function(req,res) {
        // express.static('storage/woLoginTest++190105163011.png');
        return express.static('storage/woLoginTest++190105163011.png');
    });
      

};