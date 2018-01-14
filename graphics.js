const THREE = require('three');
const OrbitControls = require('three-orbit-controls')(THREE);
module.exports = function (targ) {
    console.log("will initialise p5 on", canvas);

    const scene = new THREE.Scene();
    const camera = new THREE.PerspectiveCamera(70, window.innerWidth / window.innerHeight, 1, 1000);
    const texture = new THREE.TextureLoader().load('texture.png');
    const geometry = new THREE.SphereBufferGeometry(200, 200, 200);
    const uk_geo = new THREE.PlaneGeometry(500,500);
    const colourful = new THREE.MeshNormalMaterial({
        side:THREE.DoubleSide
    });
    const material = new THREE.MeshBasicMaterial({
        map: texture
    });
    const controls = new OrbitControls(camera);
    controls.enablePan = false;
    console.log(camera.far);
    controls.maxDistance = camera.far;
    geometry.computeBoundingSphere();
    controls.minDistance = geometry.boundingSphere.radius * 1.2;
    console.log(controls.minDistance)
    console.log(controls)
    const mesh = new THREE.Mesh(geometry, material);
    const uk_mesh = new THREE.Mesh(uk_geo,colourful);
    const renderer = new THREE.WebGLRenderer({
        canvas: targ
    });

    init();
    animate();

    function init() {

        camera.position.z = (camera.far - camera.near)/2;
        uk_mesh.position.x = 300;
        controls.target = new THREE.Vector3();
        camera.lookAt(controls.center);
        scene.add(mesh);
        
        scene.add(uk_mesh);

        renderer.setPixelRatio(window.devicePixelRatio);
        renderer.setSize(window.innerWidth, window.innerHeight);
        //        document.body.appendChild(renderer.domElement);

        //

        window.addEventListener('resize', onWindowResize, false);

    }

    function onWindowResize() {

        camera.aspect = window.innerWidth / window.innerHeight;
        camera.updateProjectionMatrix();

        renderer.setSize(window.innerWidth, window.innerHeight);

    }

    function animate() {

        requestAnimationFrame(animate);

        //        camera.position.x += 0.005;
        //        camera.position.y += 0.01;

        renderer.render(scene, camera);

    }
    return renderer.domElement;
}
