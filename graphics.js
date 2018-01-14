const THREE = require('three');
const OrbitControls = require('three-orbit-controls')(THREE);


const textures = {
    earth: new THREE.TextureLoader().load('texture.png'),
    stars: new THREE.TextureLoader().load('starmap.png'),
};

const materials = {
    normal: new THREE.MeshNormalMaterial({
        flatShading:true,
        side:THREE.DoubleSide
    }),
    earth: new THREE.MeshBasicMaterial({
        map: textures.earth
    }),
    stars: new THREE.MeshBasicMaterial({
        map: textures.stars,
        side:THREE.BackSide
        
    }),
}


const camera = new THREE.PerspectiveCamera(70, window.innerWidth / window.innerHeight, 1, 4000);

const sizes = {
    earth: 500,
    stars: 2000
};

const stars = new THREE.Mesh(new THREE.SphereBufferGeometry(
    sizes.stars,
    100,
    100
), materials.stars);

const earth = new THREE.Mesh(new THREE.SphereBufferGeometry(
    sizes.earth,
    sizes.earth,
    sizes.earth
), materials.earth);

const meshes = [
    stars, earth
];

const onWindowResize = function () {

    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();

    renderer.setSize(window.innerWidth, window.innerHeight);

}



module.exports = function (targ) {
    console.log("will initialise p5 on", canvas);

    /* set up scene */
    const scene = new THREE.Scene();
    const controls = new OrbitControls(camera);
    const renderer = new THREE.WebGLRenderer({
        canvas: targ
    });

    const animate = function () {
        renderer.render(scene, camera);
        requestAnimationFrame(animate);
    }

    window.addEventListener('resize', onWindowResize, false);
    renderer.setPixelRatio(window.devicePixelRatio);
    renderer.setSize(window.innerWidth, window.innerHeight);

    earth.geometry.computeBoundingSphere();

    controls.enablePan = false;
    controls.maxDistance = sizes.stars*0.90;
    controls.minDistance = earth.geometry.boundingSphere.radius * 1.2;
    controls.target = new THREE.Vector3();

    camera.position.z = (controls.maxDistance - controls.minDistance) / 2;
    camera.lookAt(controls.center);

    stars.position = camera.position;
    
    for (mesh of meshes) {
        scene.add(mesh);
    }

    animate();

    return renderer.domElement;
}
