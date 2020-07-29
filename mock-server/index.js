var express = require('express');
var path = require('path');
var app = express();


app.post('/citiconnect/sb/worldlinkservices/v1/payment/initiation', function(req, res){
    res.contentType('application/xml');
    res.setHeader('apim-guid','na-apimgwgtdu03~60505978-6ccd-4a40-8ddb-1580c310603b');
    res.sendFile(path.join(__dirname , 'paymentInitiationResponse.xml'));
});

app.post('/citiconnect/sb/worldlinkservices/v1/payment/inquiry', function(req, res){
    res.contentType('application/xml');
    console.log("random:"+Math.round(Math.random()));
    if (Math.round(Math.random()) == 0){
        res.sendFile(path.join(__dirname , 'paymentStatusResponseACCP.xml'));
    }else{
        res.sendFile(path.join(__dirname , 'paymentStatusResponseACSP.xml'));
    }
});

var server = app.listen(8080, () => {
    console.log('Started listening on 8080');
});