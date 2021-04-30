'use strict';

function getNotes(path) {
  return new Promise(function (resolve, reject) {
    axios.get(path)
    .then(response => {
      var result = response.data;
      resolve(result);
    })
    .catch(error => console.error(error));
  });
}

function makeNoteBeDraggable(){
  $( function() {
    $( "#draggable0" ).draggable();
    $( "#draggable1" ).draggable();
    $( "#draggable2" ).draggable();
    $( "#draggable3" ).draggable();
    $( "#draggable4" ).draggable();
    } );
}

async function main() {
    let notes = await getNotes('http://localhost:8080/ezmiro/miro/notes?boardId=boardId');
    var canvas = document.getElementById("canvas"); 
    for(let i = 0; i < notes.notes.length; i ++ ){
      var tmp = createNote(notes.notes[i], i);
      canvas.appendChild(tmp);
    }
    makeNoteBeDraggable();
}

function createNote(note, i){
  var tmp = document.createElement("div");
  var description = document.createTextNode(note.description);
  tmp.appendChild(description);
  tmp.setAttribute("id", "draggable"+i);
  tmp.setAttribute("class", "draggable-note");
  tmp.style.zIndex = mapColorToZIndex(note.color);
  tmp.style.backgroundColor = note.color;
  tmp.style.width = note.width + "px";
  tmp.style.height = note.height + "px";
  tmp.style.top = note.coordinate.position.y + "px";
  tmp.style.left = note.coordinate.position.x + "px";
  return tmp;
}

function mapColorToZIndex(color) {
    var order;
    switch (color) {
    case "#C9DF56":
        order = 1;
    break;
    case "#6CD8FA":
        order = 2;
    break;
    case "#FF9D48":
        order = 3;
    break;
    case "#FFF9B1":
        order = 4;
    break;
    case "#FEF445":
        order = 5;
    break;
    default:
        order = 999;
    }

    return order;
}