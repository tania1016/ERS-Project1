function store() { //stores items in the sessionStorage
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;

    const sessionrecord = {
        username: username,
        password: password,
    }
    
    window.sessionStorage.setItem('sessionrecord',JSON.stringify(sessionrecord));  
    //converting object to string
}

function retrieveRecords() { //retrieves items in the sessionStorage
        console.log("retrive records");
        var records = window.sessionStorage.getItem('sessionrecord');
        var paragraph = document.createElement("p");
        var infor = document.createTextNode(records);
        paragraph.appendChild(infor);
        var element = document.getElementById("retrieve");
        element.appendChild(paragraph);
} 
window.onload =function() { //ensures the page is loaded before functions are executed.
        document.getElementById("loginservlet").onsubmit = store;
        document.getElementById("createreimburse").onclick = retrieveRecords;
}