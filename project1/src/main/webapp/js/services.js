/**
 * 
 */

window.onload = function(){
	document.getElementById('submit').addEventListener('click', loginUser);
};

function loginUser(){
	let uname = document.getElementById('name').value;
	let pass = document.getElementById('pass').value;
	console.log(uname + " " + pass);
	
	}
	
	