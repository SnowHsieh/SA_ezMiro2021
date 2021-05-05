import './App.css';
import Axios from 'axios';
import { Component, useState, useEffect, useRef, createRef } from 'react';
import { Rnd } from "react-rnd";
import ContentEditable from 'react-contenteditable';
import { TwitterPicker } from 'react-color';

function Note({index, note, onNoteCoordinateChange, onNoteSizeChange, onNoteDescriptionChange, onNoteColorChange, onNoteDisplayOrderChange, onNoteDelete}) {
  let [isEditable, setIsEditable] = useState(false);
  let [width, setWidth] = useState(note.width);
  let [height, setHeight] = useState(note.height);
  let [position, setPosition] = useState(note.coordinate);
  let [color, setColor] = useState(note.color);
  let [displayColorPicker, setDisplayColorPicker] = useState("none");
  let description = useRef(note.description);
  let contentEditableRef = createRef();
  let [displayOrder, setDisplayOrder] = useState(note.displayOrder);
  let displayOrderTmp = note.displayOrder;
  let [isDelete, setIsDelete] = useState("visible");

  let handleDragStop = (event, d) => {
    onNoteCoordinateChange(index, {
      x: d.x, 
      y: d.y
    });
    setPosition({ x: d.x, y: d.y });
  }
  
  let handleResizeStop = (event, direction, ref, delta, position) => {
    onNoteSizeChange(index, {
      width: parseInt(ref.style.width),
      height: parseInt(ref.style.height)
    });
    onNoteCoordinateChange(index, 
      position
    );
    setWidth(parseInt(ref.style.width));
    setHeight(parseInt(ref.style.height));
    setPosition(position);
  }

  let handleEditMode = () => {
    setIsEditable(true);
    setDisplayColorPicker(true);
  }

  let handleDescriptionEditDone = (event) => {
    onNoteDescriptionChange(index, description.current);
    setIsEditable(false);
    setDisplayColorPicker("none");
  }

  let handleDescriptionChange = (event) => {
    description.current = event.target.value;
  }

  let handleColorChangeComplete = (event) => {
    setColor(event.hex);
    console.log(event.hex);
    setDisplayColorPicker("none");
  }

  let handleKeyDown = (event) => {
    if (event.keyCode == 33) {
      onNoteDisplayOrderChange(index, displayOrderTmp+1);
      setDisplayOrder(displayOrderTmp+1);
    } else if (event.keyCode == 34) {
      onNoteDisplayOrderChange(index, displayOrderTmp-1);
      setDisplayOrder(displayOrderTmp-1); 
    } else if (event.keyCode == 46) {
      onNoteDelete(index);
      setIsDelete("hidden");
    }
  }

  useEffect(() => {onNoteColorChange(index, color)}, [color]);

  return (
    <Rnd
      className="draggable-note" 
      style={{backgroundColor: note.color, 
        zIndex: displayOrder, 
        visibility: isDelete}}
      size={{ width: width, height: height }}
      position={ position }
      onDragStop={handleDragStop}
      onResizeStop={handleResizeStop}
      onDoubleClick={handleEditMode}
      onBlur={handleDescriptionEditDone}
      onKeyDown={handleKeyDown}
    >
      <ContentEditable
        innerRef = {contentEditableRef}
        style={{width: "100%", height: "100%", display: "flex",
        justifyContent: "center",
        alignItems: "center"}}
        html={description.current} 
        disabled={!isEditable}  
        onChange={handleDescriptionChange} 
      />

      <div style={ {position: 'absolute', display: displayColorPicker, zIndex: '2'} }>
        <TwitterPicker
          color={color}
          onChangeComplete={handleColorChangeComplete}
        /> 
      </div>
      </Rnd>  
  );
}

class App extends Component {
  constructor(props){
    super(props);
    this.state = {
      notes: []
    };
    this.getNotes();
  }

  getNotes() {
    Axios.get('http://localhost:8080/ezmiro/miro/notes?boardId=boardId').then(response => {
      this.setState({notes: response.data.notes});
    }).catch(error => console.error(error));
  }

  moveNote = (index, coordinate) => {
    var {notes} = this.state;
    notes[index].coordinate = coordinate;
    this.setState({notes: notes});
    Axios.post(`http://localhost:8080/ezmiro/miro/notes/${notes[index].noteId}/move`,{
      coordinate: {
        x: coordinate.x,
        y: coordinate.y
      }
    }).catch(error => console.error(error));
  }

  changeNoteSize = (index, size) => {
    var {notes} = this.state;
    notes[index].size = size;
    this.setState({notes: notes});
    Axios.post(`http://localhost:8080/ezmiro/miro/notes/${notes[index].noteId}/edit/size`,{
      size: size
    }).catch(error => console.error(error));
  }

  changeNoteDescription = (index, description) => {
    var {notes} = this.state;
    notes[index].description = description;
    this.setState({notes: notes});
    Axios.post(`http://localhost:8080/ezmiro/miro/notes/${notes[index].noteId}/edit/description`,{
      description: description
    }).catch(error => console.error(error));
  }

  changeNoteColor = (index, color) => {
    var {notes} = this.state;
    notes[index].color = color;
    this.setState({notes: notes});
    Axios.post(`http://localhost:8080/ezmiro/miro/notes/${notes[index].noteId}/edit/color`,{
      color: color
    }).catch(error => console.error(error));
  }

  changeNoteDisplayOrder = (index, displayOrder) => {
    var {notes} = this.state;
    notes[index].displayOrder = displayOrder;
    this.setState({notes: notes});
    Axios.post(`http://localhost:8080/ezmiro/miro/notes/${notes[index].noteId}/edit/displayorder`,{
      displayOrder: displayOrder
    }).catch(error => console.error(error));
  }

  deleteNote = (index) => {
    var {notes} = this.state;
    this.setState({notes: notes});
    Axios.post(`http://localhost:8080/ezmiro/miro/notes/${notes[index].noteId}/delete?boardId=boardId`,{
    }).catch(error => console.error(error));
  }

  render() {
    return (
      <div id="canvas"> 
        {this.state.notes.map((note, index) => 
          <Note
            index={index}
            note={note}
            onNoteCoordinateChange={this.moveNote}
            onNoteSizeChange={this.changeNoteSize}
            onNoteDescriptionChange={this.changeNoteDescription}
            onNoteColorChange={this.changeNoteColor}
            onNoteDisplayOrderChange={this.changeNoteDisplayOrder}
            onNoteDelete={this.deleteNote}
          />
        )}
      </div>
    );
  }
}

export default App;