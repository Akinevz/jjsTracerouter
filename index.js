const ready = require('document-ready');
const ip = require('ip');

const graphics = require('./graphics');




const sleep = (ms) => function (obj) {
    return new Promise((wake, die) => {
        console.log("sleeping for", ms);
        window.setTimeout(() => wake(obj), ms)
    })
};

const loaded = new Promise(ready);
const form = loaded.then(() => {
    return document.forms.bottom;
});
const canvas = loaded.then(() => {
    return document.getElementById('canvas');
});
const container = loaded.then(() => {
    return document.getElementById('container');
});

const formInputBegin = function () {

    console.log("hovering on", this);
    this.value = "";
    this.onfocus = undefined;

};

const maximizeCanvas = function () {
    canvas.then(c => c.width = document.body.clientWidth);
    canvas.then(c => c.height = document.body.clientHeight);
}

form.then(f => f.payload)
    .then(f => f.onfocus = formInputBegin)
    .then(() => console.log("assigned onfocus"));

const gfx = canvas.then(() => document.addEventListener("fullscreenchange", maximizeCanvas, false))
    .then(() => window.addEventListener("resize", maximizeCanvas, false))
    .then(maximizeCanvas)
    .then(() => canvas)
    .then(graphics);

const getLatLon = function(ip){
    console.log("getting lat long for ",ip);
    return {
        lat:222,
        lon:111
    };
};

const doProcessInput = function (istr) {
    console.log("click!");
    const pIp = Promise
        .resolve(istr)
        .then(function (s) {
            if(!ip.isV4Format(s)) throw 'Not an IP address'
            return ip.toBuffer(s);
            
        })
        .then(ip.toString)
        .then(getLatLon)
        .then(p=>gfx.then(g=>g.focus(p)))
        .catch(alert);

}

form.then(f => f.doneClick.onclick = f.onsubmit = function () {
        doProcessInput(f.payload.value);
        return false;
    })
    .then(() => console.log("assigned submit"));

container
    .then(sleep(500))
    .then(c => c.classList.add("loaded"));
