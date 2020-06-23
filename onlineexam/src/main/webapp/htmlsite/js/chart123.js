function bbb(){
	var barChartCanvas = $("#baChart").get(0).getContext("2d");
	var options = {
		scales: {
			yAxes: [{
				ticks: {
					beginAtZero: true
				}
			}]
		},
		legend: {
			display: false
		},
		elements: {
			point: {
				radius: 0
			}
		}
	};
	var data = {
		labels: ["60以下", "[60,70)", "[70,80)", "[80,90)", "[90,100)", "100"],
		datasets: [{
			label: '# of Votes',
			data: [10, 80, 23, 50, 12, 60],
			backgroundColor: [
				'rgba(255, 99, 132, 0.2)',
				'rgba(54, 162, 235, 0.2)',
				'rgba(255, 206, 86, 0.2)',
				'rgba(75, 192, 192, 0.2)',
				'rgba(153, 102, 255, 0.2)',
				'rgba(255, 159, 64, 0.2)'
			],
			borderColor: [
				'rgba(255,99,132,1)',
				'rgba(54, 162, 235, 1)',
				'rgba(255, 206, 86, 1)',
				'rgba(75, 192, 192, 1)',
				'rgba(153, 102, 255, 1)',
				'rgba(255, 159, 64, 1)'
			],
			borderWidth: 1,
			fill: false
		}]
	};
	var barChart = new Chart(barChartCanvas, {
		type: 'bar',
		data: data,
		options: options
	});
}