
 
 window.onload = function(){
	document.getElementById('getTickets').addEventListener('click', getTickets);
	document.getElementById('decide').addEventListener('click', updateTicket);
	console.log('clicks loaded');
	let xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
		console.log("the ready state changed");
		if(xhttp.readyState == 4 && xhttp.status == 200) {
			console.log("before parse");
			let check = JSON.parse(xhttp.responseText);
			
			if(check == "Invalid Session") {
				window.location.replace("http://localhost:8080/project1/html/index.html");
			}
			
		}
		
	}
	
	xhttp.open("GET", `http://localhost:8080/project1/sessioncheck.json`);
	
	xhttp.send();
};

const table = document.querySelector("#tableBody");

// set up a new button and field to update a ticket, automatically refresh the table

function updateTicket() {
	console.log("update button clicked");
	let xhttp = new XMLHttpRequest();
	let ticketId = document.getElementById('ticketId').value;
	let decision = document.getElementById('judgement').value;
	console.log(ticketId);
	console.log(decision);
	
	xhttp.onreadystatechange = function() {
		console.log("the ready state changed");
		if(xhttp.readyState == 4 && xhttp.status == 200) {
			console.log("before parse");
			let message = JSON.parse(xhttp.responseText);
			console.log(message);
			document.getElementById('ticketId').value = '';
			getTickets();
		}
		
	}
	
	xhttp.open("GET", `http://localhost:8080/project1/fmdecide.json`);
	
	xhttp.setRequestHeader('ticketId', ticketId);
	xhttp.setRequestHeader('decision', decision);
	
	xhttp.send();
	
}

function getTickets() {
	console.log("button clicked");
	let xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function(){
		console.log("the ready state changed");
		if(xhttp.readyState == 4 && xhttp.status == 200) {
			console.log("before parse");
			let tableObj = JSON.parse(xhttp.responseText);
			console.log("success");
			DOMDisplay(tableObj);
		}
	}
	
	xhttp.open("GET", `http://localhost:8080/project1/${document.getElementById('filter').value}fmview.json`);
	
	xhttp.send();

}

function DOMDisplay(tableJSON) {
	console.log(tableJSON);
	document.getElementById('message').innerHTML = "";
	// delete table
	while(table.firstChild) {
		table.removeChild(table.firstChild);
	}
	
	// fill table
	tableJSON.forEach((row) => {
		let tr = document.createElement("tr");
		
		let td = document.createElement("td");
			td.textContent = row.id;
			tr.appendChild(td);
			
			td = document.createElement("td");
			td.textContent = row.amount;
			tr.appendChild(td);
			
			td = document.createElement("td");
			td.innerHTML = row.submitted.year + "-" + row.submitted.monthValue + "-" + row.submitted.dayOfMonth;
			tr.appendChild(td);
			
			td = document.createElement("td");
			td.textContent = row.description;
			tr.appendChild(td);
			
			td = document.createElement("td");
			td.textContent = row.authorId;
			tr.appendChild(td);
			
			td = document.createElement("td");
			td.textContent = row.resolverId;
			tr.appendChild(td);
			
			td = document.createElement("td");
			td.textContent = row.type;
			tr.appendChild(td);
			
			td = document.createElement("td");
			td.textContent = row.status;
			tr.appendChild(td);
			
		table.appendChild(tr);
		
	});
	
	
	
	
	
}