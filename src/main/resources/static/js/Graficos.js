
var ctx1 = document.getElementById("grafActual").getContext("2d");
var ctx2 = document.getElementById("grafAnterior").getContext("2d");

const config = {
	type:'bar',
	data:{
		datasets: [{
			label: ['raciones solicitadas'],
			backgroundColor: ['#6bf1ab','#63d69f', '#438c6c', '#509c7f', '#1f794e', '#34444c', '#90CAF9', '#64B5F6', '#42A5F5', '#2196F3', '#0D47A1'],
			borderColor: ['black'],
			borderWidth:1
		}]
	},
	options:{
		scales:{
			y:{
				beginAtZero:true
			}
		}
	}
}

var grafActual = new Chart(ctx1,config)
var grafAnterior = new Chart(ctx2,config)

mostrarGrafico(pedidoActual,grafActual)
mostrarGrafico(pedidoAnterior,grafAnterior)


const mostrarGrafico = (datosPedido,grafico) =>{
	datosPedido.forEach(element => {
		grafico.data['labels'].push(element.tipo)
		console.log(element.tipo)
		grafico.data['datasets'][0].data.push(element.cantidad)
		console.log(element.cantidad)
		grafico.update()
	});
}
