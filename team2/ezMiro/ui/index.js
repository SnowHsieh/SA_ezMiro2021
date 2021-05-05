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

function makeNoteBeDraggable(i){
    $( "#draggable" + i ).draggable();
}

async function main() {
    let notes = await getNotes('http://localhost:8080/ezmiro/miro/notes?boardId=boardId');
    var canvas = document.getElementById("canvas"); 
    for(let i = 0; i < notes.notes.length; i ++ ){
      var tmp = createNote(notes.notes[i], i);
      canvas.appendChild(tmp);
      makeNoteBeDraggable(i);
    }
}

function createNote(note, i){
  var tmp = document.createElement("div");
  var description = document.createTextNode(note.description);
  tmp.appendChild(description);
  tmp.setAttribute("id", "draggable"+i);
  tmp.setAttribute("class", "draggable-note");
  tmp.style.zIndex = note.displayOrder;
  tmp.style.backgroundColor = note.color;
  tmp.style.width = note.width + "px";
  tmp.style.height = note.height + "px";
  tmp.style.top = note.coordinate.y + "px";
  tmp.style.left = note.coordinate.x + "px";
  return tmp;
}