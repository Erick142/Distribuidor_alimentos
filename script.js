var usuarios =[
	{ID:id,nombre:"Jorge",aPaterno:"Cisterna",aMaterno:"Calderón",email:"jorge@institucion.com",password:"pass1",telefono:"987654321",institucion:"Ufro",rol:"usuario"},
	{ID:id,nombre:"Erick",aPaterno:"Martinez",aMaterno:"Fuentes",email:"erick@distribuidor.com",password:"pass2",telefono:"998877665",institucion:"Ufro",rol:"admin"}
];

var id = usuarios.length;

var noticias=[
	{titulo:"Titulo de Ejemplo",subtitulo:"subtitulo de ejemplo",autor:"Erick Martínez",etiquetas:["etiqueta1","etiqueta2","etiqueta3"],imagen:""
		,cuerpoNoticia:"Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veritatis error ab perspiciatis illum natus distinctio nulla laborum vero suscipit qui a, eaque reiciendis saepe facere tempore, aliquam in voluptatibus sed. Cumque sunt eum ipsum nostrum! Quae consequuntur expedita pariatur delectus blanditiis at omnis ipsum debitis unde. Corporis magni magnam, pariatur molestias distinctio accusamus suscipit illo culpa. Veniam dolorum exercitationem cumque? Nihil vel quia, facilis maxime temporibus quo accusantium veniam dolor iusto aut fuga debitis harum laborum sint nam odit libero eum esse id, possimus quidem rerum totam saepe. Ut, dolore? Veniam in maiores, tempore ipsa ipsum nihil nesciunt error soluta eveniet adipisci voluptates vero totam debitis dignissimos cumque sed illum possimus excepturi sequi ad voluptatum? Nemo quibusdam commodi doloribus non.\n" +
			"Lorem ipsum dolor sit, amet consectetur adipisicing elit. Similique tenetur laudantium aliquam provident natus suscipit dolor expedita nihil! Consequuntur aliquam facere vel itaque ad consequatur, numquam culpa eaque veniam est. Lorem ipsum, dolor sit amet consectetur adipisicing elit. Eligendi earum accusamus quasi distinctio, nam exercitationem ratione quibusdam dolorum libero alias ut veritatis consequatur atque necessitatibus, doloribus totam nihil eaque! Magni. Sit laborum ea exercitationem quod nisi voluptatum reiciendis unde recusandae minus alias, porro cum eaque velit laudantium, obcaecati sapiente asperiores doloremque ad iste. Error voluptatibus, ipsum molestias impedit molestiae laudantium! Et repellat molestias saepe sed earum atque similique aliquam dolorum soluta ullam nam, eveniet enim. Aspernatur, laborum error, sint ducimus voluptas nobis porro ipsa assumenda sed eum at, veritatis similique? Sunt dolorum beatae, id, asperiores voluptates nihil consequuntur sequi impedit ut obcaecati laboriosam odio. Atque ducimus, adipisci provident amet, reiciendis eligendi praesentium tempora perspiciatis, harum dignissimos unde! Nemo, explicabo asperiores. Sit numquam rerum explicabo unde sequi hic, tempore totam maiores culpa, deleniti nihil sapiente ab distinctio commodi iure quo incidunt repudiandae enim corrupti iste reiciendis quod soluta deserunt. Sint, sed! Iure itaque atque ea. Nobis dolores quaerat sed. Ea omnis excepturi velit dignissimos ratione, aliquid quos molestias ipsum dolores provident sed porro magni, quia illo maiores, debitis expedita aut! Eveniet?"},
]

//elementos HTML
const formRegistro = document.getElementById("formRegistro");
const nombre = document.getElementById("nombreReg");
const aPaterno = document.getElementById("aPaternoReg");
const aMaterno = document.getElementById("aMaternoReg");
const email = document.getElementById("correoReg");
const password = document.getElementById("passReg");
const telefono = document.getElementById("telefonoReg");
const institucion = document.getElementById("nombreInstReg");
const correoLogin = document.getElementById("correoLogin");
const passLogin = document.getElementById("passLogin");
const formLogin = document.getElementById("formLogin");
const btnLogout = document.querySelector("#btnLogout");

try{
	btnLogout.addEventListener("click",()=>{
		alert("Se ha desconectado correctamente");
		window.location="index.html";
	})
}catch (e) {
	console.log(e)
}



console.log(usuarios)

//registro
try{
	formRegistro.addEventListener("submit",(e)=>{
		e.preventDefault();
		console.log(nombre.value)
		nuevoUsuario(nombre.value,aPaterno.value,aMaterno.value,email.value,password.value,telefono.value,institucion.value);
	})
}catch (e) {
}



function nuevoUsuario(nombre,aPaterno,aMaterno,email,password,telefono,institucion) {
	id++;
	usuarios.push({ID:id,nombre:nombre,aPaterno:aPaterno,aMaterno:aMaterno,email:email,password:password,telefono:telefono,institucion:institucion});
	alert("registro exitoso");
	console.log(usuarios);
	window.location="ConfirmacionRegistro.html";
}

//login
try{
	formLogin.addEventListener("submit",(e)=>{
		e.preventDefault();
		let correo = correoLogin.value;
		if(correo == 'jorge@institucion.com'){
			window.location = "userhome.html";
		}else if (correo == 'erick@distribuidor.com'){
			window.location = "homedistribuidor.html";
		}else{
			alert("Email o contraseña incorrectas")
		}

		/*
		usuarios.find(function(elem, index) {
			alert(elem.email)
			if(elem.email == 'jorge@institucion.com'){
				window.location = "userhome.html";
			}else if (elem.email == 'erick@distribuidor.com'){
				window.location = "homedistribuidor.html";
			}
		});

		 */
	});
}catch (e) {
}





//Listar en tabla
const tbody=document.getElementById("bodyTabla")

function agregarAtabla(index,id,nombre,cantidad,descripcion,categoria){
	const iconoEditar = '<i class="fa-solid fa-pen-to-square" style="font-size: 1rem" ></i>';
	const iconoBorrar = '<i class="fa-solid fa-trash" style="font-size: 1rem"></i>';
	let trow = document.createElement("tr");
	trow.setAttribute("id",`${index}`)
	let td1 = document.createElement("td");
	td1.setAttribute('style',"display:none;")
	let td2 = document.createElement("td");
	let td3 = document.createElement("td");
	let td4 = document.createElement("td");
	let td5 = document.createElement("td")
	let td6 = document.createElement("td");
	let td7 = document.createElement("td");
	let td8 = document.createElement("td");

	td1.innerHTML=id;
	td2.innerHTML=nombre;
	td3.innerHTML=cantidad;
	td4.innerHTML=descripcion;
	td5.innerHTML=categoria;
	td6.innerHTML=`
                <button class="btnEditar btn btn-secondary" data-bs-toggle="modal" data-bs-target="#modalEdit">${iconoEditar}</button>
                <button class="btnBorrar btn btn-danger" data-bs-toggle="modal" data-bs-target="#modalBorrar" id="iconoBorrar">${iconoBorrar}</button>
                    `
	trow.appendChild(td1);
	trow.appendChild(td2);
	trow.appendChild(td3);
	trow.appendChild(td4);
	trow.appendChild(td5);
	trow.appendChild(td6);
	tbody.appendChild(trow);
}
