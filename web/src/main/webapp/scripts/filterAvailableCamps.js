const sinceInput = document.getElementById("since")
const untilInput = document.getElementById("until")
const educativeLevelInput = document.getElementById("educative_level")
const capacityInput = document.getElementById("capacity")



sinceInput.addEventListener("change", () => {
	console.log("Fecha desde ha cambiado a: " + sinceInput.value)
})

untilInput.addEventListener("change", () => {
	console.log("Fecha hasta ha cambiado a: " + untilInput.value)
})

educativeLevelInput.addEventListener("change", () => {
	console.log("Nivel educativo ha cambiado a: " + educativeLevelInput.value)
})

capacityInput.addEventListener("change", () => {
	console.log("Capacidad ha cambiado a: " + capacityInput.value)
})

const campDetailsDivs = document.querySelector('#list_of_available_camps').querySelectorAll('.camp_details_div');

const camps = []



campDetailsDivs.forEach(campDetailsDiv => {
	const stringDate = campDetailsDiv.querySelector(".camp_details_start_date").innerText
	const date = new Date(Date.from({
	  year: stringDate.split("/")[2],
	  month: stringDate.split("/")[1],
	  day: stringDate.split("/")[0]
	}));
	
	camps.push({
		div: campDetailsDiv,
		startDate: new Date(campDetailsDiv.querySelector(".camp_details_start_date").innerText),
		educativeLevel: date,
		availableInscriptions: parseInt(campDetailsDiv.querySelector(".camp_details_available_inscriptions").innerText)
	})
})

console.log(camps)