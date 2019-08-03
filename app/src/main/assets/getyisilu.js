var tbody =  document.getElementsByTagName('tbody')[1];

var trs = tbody.getElementsByTagName("tr");
var array = [];
for(var i = 0; i < trs.length; i++) {
   var sub_array = [];
   var tds  =  trs[i].getElementsByTagName("td");
   for(var j =0 ;j <tds.length;j++){
      var td =  tds[j];
      var name =  td.getAttribute("data-name");
      var value = td.innerText;
      sub_array.push({"name":name,"value":value});
   }
    array.push(sub_array);
}
    var  str   = JSON.stringify(array);
console.info(str);
window.Android.onCallback(str);