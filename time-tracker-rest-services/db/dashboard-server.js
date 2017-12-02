"use strict";
var express = require("express");
var path = require("path");
var model_1 = require("./model");
var app = express();
/* with authentication */
//app.use('/', auth,            express.static(path.join(__dirname, '..', 'client')));
/* without authentication */
app.use('/', express.static(path.join(__dirname, '..', 'client')));
app.use('/timetracker', express.static(path.join(__dirname, '..', 'timetracker')));
app.use('/timetracker/timeEntry', express.static(path.join(__dirname, '..', 'timetracker')));

app.use('/node_modules', express.static(path.join(__dirname, '..', 'node_modules')));
app.get('/urls/:category/:id', function (req, res) {
    res.json(model_1.getURLById(parseInt(req.params.id), req.params.category));
});
app.get('/allReportCategory', function (req, res) {
    var reportCategoryList = model_1.getAllReportCategoory();
    console.log("data");
    /*reportCategoryList.forEach((reportCategory, index,reportCategoryList) =>{
        reportCategory.reportList=null;
        reportCategoryList[index]=	reportCategory;
    });*/
    res.json(reportCategoryList);
});
var httpServer = app.listen(80, function () {
    var _a = httpServer.address(), address = _a.address, port = _a.port;
    console.log('Listening on %s %s', address, port);
});
//# sourceMappingURL=dashboard-server.js.map