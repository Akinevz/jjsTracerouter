const ready = require('document-ready');
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

canvas
    .then(() => document.addEventListener("fullscreenchange", maximizeCanvas, false))
    .then(() => window.addEventListener("resize", maximizeCanvas, false))
    .then(maximizeCanvas)
    .then(()=>canvas)
    .then(graphics);

form.then(f => f.submit)
    .then(f => f.onclick = function () {
        console.log("Submit!");
    })
    .then(() => console.log("assigned submit"));

container
    .then(sleep(500))
    .then(c => c.classList.add("loaded"));
