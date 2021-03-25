/**
 * 
 */
  window.onload = function(){
	document.getElementById('getTickets').addEventListener('click', getTickets);
	document.getElementById('newTicket').addEventListener('click', newTicket);
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
	
	}
	const table = document.querySelector("#tableBody");
	
	function newTicket() {
	console.log("new ticket button clicked");
	let xhttp = new XMLHttpRequest();
	let amount = document.getElementById('amount').value;
	let description = document.getElementById('description').value;
	let type = document.getElementById('type').value;
	console.log(amount);
	console.log(description);
	console.log(type);
	
	xhttp.onreadystatechange = function() {
		console.log("the ready state changed");
		if(xhttp.readyState == 4 && xhttp.status == 200) {
			console.log("before parse");
			let message = JSON.parse(xhttp.responseText);
			console.log(message);
			document.getElementById('amount').value = '';
			document.getElementById('description').value = '';
			getTickets();
		}
		
	}
	
	xhttp.open("GET", `http://localhost:8080/project1/emrequest.json`);
	
	xhttp.setRequestHeader('amount', amount);
	xhttp.setRequestHeader('description', description);
	xhttp.setRequestHeader('type', type);
	
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
	
	xhttp.open("GET", `http://localhost:8080/project1/emview.json`);
	
	
	xhttp.send();
	document.getElementById('message').innerHTML = "";
}

function DOMDisplay(tableJSON) {
	console.log(tableJSON);
	//document.getElementById('message').innerHTML = "";
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
		
	})
}
	