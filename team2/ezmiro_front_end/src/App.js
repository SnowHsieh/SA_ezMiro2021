import './App.css';
import Axios from 'axios';
import { Component, createRef } from 'react';
import Note from './Note';
import SockJsClient from 'react-stomp';
import {RiCursorFill} from 'react-icons/ri'

const SOCKET_URL = 'http://localhost:8080/ezmiro/ws-message';

class App extends Component {
  constructor(props){
    super(props);
    this.state = { 
      notes: [],
      reconnectTime: 0,
      isConnect: false,
      onlineUsers: [],
      boardChannel: ""
    };
    this.getBoardContent();
    this.getBoardContent = this.getBoardContent.bind(this);
    this.createNote = this.createNote.bind(this);
    this.deleteNote = this.deleteNote.bind(this);
    this.updateMousePosition = this.updateMousePosition.bind(this);
    this.moveNote = this.moveNote.bind(this);
    window.addEventListener("mousemove", this.updateMousePosition);
    this.componentWillUnmount = this.componentWillUnmount.bind(this);
    this.clientRef = createRef();
    this.noteRef = createRef();
    this.cursorBroadcast = null;
    this.userId = "小貓熊"+Math.floor(Math.random()*100+1);
    this.cursor = {
      x: 0,
      y: 0
    }
    this.oldCursor = {
      x: 0,
      y: 0
    }
    this.draggingNote = "";
    this.boardId = "262a68ed-cc97-4a16-9d5a-1bb5b68c4364"
  }

  componentDidMount() {
    this.getBoardContent()
    this.cursorBroadcaster = setInterval(()=>{
      try {
        if (this.oldCursor !== this.cursor) {
          const myCursor = {
            userId: this.userId,
            ...this.cursor
          }
          this.clientRef.current.sendMessage(`/app/sendMessage/${this.boardChannel}`, JSON.stringify(myCursor));
          this.oldCursor = this.cursor;
        }
      } catch(error) {
        console.error(error);
      }
    }, 100);
  }

  componentWillUnmount() {
    window.removeEventListener("mousemove", this.updateMousePosition);
    this.clientRef.current.disconnect()
    this.noteRef.current.disconnect()
    clearInterval(this.cursorBroadcast);
  }

  updateMousePosition = (event) => {
    this.cursor = {
      x: event.clientX,
      y: event.clientY
    }
  };

  getBoardContent() {
    Axios.get(`http://localhost:8080/ezmiro/miro/boards/${this.boardId}/getcontent`).then(response => {
      this.setState({notes: response.data.notes, boardChannel: response.data.boardChannel})
    }).catch(error => console.error(error));
  }

  moveNote = (id, coordinate) => {
    this.draggingNote = ""
    Axios.post(`http://localhost:8080/ezmiro/miro/notes/${id}/move`,{
      coordinate: {
        x: coordinate.x,
        y: coordinate.y
      }
    }).then((response) => {
      console.log("moveNote", response)
    }
    ).catch(error => console.error(error));
  }

  dragNote = (id, coordinate) => {
    this.draggingNote = id
    this.setState({
      notes: this.state.notes.map(x => x.noteId === id ? {...x, coordinate} : x)
    })
    try {
      this.clientRef.current.sendMessage(`/app/sendNote/${this.state.boardChannel}`, JSON.stringify({noteId: id, coordinate: coordinate}))
    } catch(error) {
      console.error(error);
    }
  }

  changeNoteSize = (id, size) => {
    Axios.post(`http://localhost:8080/ezmiro/miro/notes/${id}/edit/size`,{
      size: size
    }).then((response) => {
      console.log("changeNoteSize", response)
    }).catch(error => console.error(error));
  }

  changeNoteDescription = (id, description) => {
    Axios.post(`http://localhost:8080/ezmiro/miro/notes/${id}/edit/description`,{
      description: description
    }).then((response) => {
      console.log("changeNoteDescription", response)
    }).catch(error => console.error(error));
  }

  changeNoteColor = (id, color) => {
    Axios.post(`http://localhost:8080/ezmiro/miro/notes/${id}/edit/color`,{
      color: color
    }).then((response) => {
      console.log("changeNoteColor", response)
    }).catch(error => console.error(error));
  }

  bringNoteToFront = (id) => {
    Axios.post(`http://localhost:8080/ezmiro/miro/notes/${id}/edit/zorder/front`,{
    }).then((response) => {
      console.log("bringNoteToFront", response)
    }).catch(error => console.error(error));
  }

  sendNoteToBack = (id) => {
    Axios.post(`http://localhost:8080/ezmiro/miro/notes/${id}/edit/zorder/back`,{
    }).then((response) => {
      console.log("sendNoteToBack", response)
    }).catch(error => console.error(error));
  }

  deleteNote = (id) => {
    var {notes} = this.state;
    this.setState({
      notes:notes.filter( x => x.noteId !== id)
    });
    Axios.post(`http://localhost:8080/ezmiro/miro/notes/${id}/delete?boardId=${this.boardId}`,{
    }).then((response) => {
      console.log("deleteNote", response)
    }).catch(error => console.error(error));
  }

  createNote = () => {
    console.log("double click")
    Axios.post(`http://localhost:8080/ezmiro/miro/figures/notes?boardId=${this.boardId}`,
      {
        "coordinate": {
          "x": this.cursor.x - 50,
          "y": this.cursor.y - 50
        }
      }).then(response => {
        console.log("createNote", response)
      }).catch(error => console.error(error));
  }

  onEventReceived = (domainEvent, channel) => {
    console.log("channel", channel)
    if (channel === `/topic/${this.state.boardChannel}/cursor`) {
      this.handleCursorEvent(domainEvent)
    } else if (channel === `/topic/${this.state.boardChannel}/note`) {
      this.handleNoteEvent(domainEvent)
    } else if (channel === `/topic/${this.state.boardChannel}/draggingNote`) {
      this.handleDraggingNote(domainEvent)
    }
  }

  handleCursorEvent = (cursorEvent) =>{
    let cursor = JSON.parse(cursorEvent.jsonEvent);
    if (cursorEvent.eventType === "CursorRemoved") {
      this.setState({
        onlineUsers: [...this.state.onlineUsers.filter(x => x.cursorId !== cursor.cursorId)]
      })
    } else {
      if (cursor.userId !== this.userId) {
        this.setState({
          onlineUsers: [...this.state.onlineUsers.filter(x => x.userId !== cursor.userId),
          {
            x: cursor.coordinate.x, 
            y: cursor.coordinate.y, 
            userId: cursor.userId,
            cursorId: cursor.cursorId
          }]
        })
      }
    }
  }

  handleNoteEvent = (noteEvent) =>{
    console.log("noteEvent", noteEvent)
    let note = JSON.parse(noteEvent.jsonEvent);
    switch (noteEvent.eventType) {
      case "NoteCreated":
        this.handleNoteCreated(note);
        break;
      case "NoteColorChanged":
        this.handleNoteColorChanged(note);
        break;
      case "NoteDescriptionChanged":
        this.handleNoteDescriptionChanged(note);
        break;
      case "NoteSizeChanged":
        this.handleNoteSizeChanged(note);
        break;
      case "NoteMoved":
        this.handleNoteMoved(note);
        break;
      case "NoteDeleted":
        this.handleNoteDeleted(note);
        break;
      case "NoteBroughtToFront":
        this.handleNoteBroughtToFront(note);
        break;
      case "NoteSentToBack":
        this.handleNoteSentToBack(note);
        break;
      default:
        break;
    }
  }

  handleNoteCreated = (note) => {
    console.log("handleNoteCreated", note);
    this.setState({
      notes: [...this.state.notes,
      {
        boardId: note.boardId,
        noteId: note.noteId,
        width: note.width, 
        height: note.height, 
        coordinate: note.coordinate,
        color: note.color,
        description: note.description,
        zorder: this.state.notes.length
      }]
    })
  }

  handleNoteColorChanged = (note) => {
    console.log("handleNoteColorChanged", note);
    let originNote = this.state.notes.find(x => x.noteId === note.noteId);
    this.setState({
      notes: [...this.state.notes.filter(x => x.noteId !== note.noteId),
      {
        ...originNote,
        color: note.color
      }]
    })
  }

  handleNoteDescriptionChanged = (note) => {
    console.log("handleNoteDescriptionChanged", note);
    let originNote = this.state.notes.find(x => x.noteId === note.noteId);
    this.setState({
      notes: [...this.state.notes.filter(x => x.noteId !== note.noteId),
      {
        ...originNote,
        description: note.description
      }]
    })
  }

  handleNoteSizeChanged = (note) => {
    console.log("handleNoteSizeChanged", note);
    let originNote = this.state.notes.find(x => x.noteId === note.noteId);
    this.setState({
      notes: [...this.state.notes.filter(x => x.noteId !== note.noteId),
      {
        ...originNote,
        width: note.width,
        height: note.height
      }]
    })
  }

  handleNoteMoved = (note) => {
    console.log("handleNoteMoved", note);
    let originNote = this.state.notes.find(x => x.noteId === note.noteId);
    this.setState({
      notes: [...this.state.notes.filter(x => x.noteId !== note.noteId),
      {
        ...originNote,
        coordinate: note.coordinate
      }]
    })
  }

  handleNoteBroughtToFront = (note) => {
    console.log("handleNoteBroughtToFront", note);
    let originNote = this.state.notes.find(x => x.noteId === note.noteId);
    let newNotes = this.state.notes.filter(x => x.noteId !== originNote.noteId).map(x => {
      if (x.zorder > originNote.zorder) {
        return {...x, zorder: x.zorder - 1};
      } else {
        return x;
      }} )
    this.setState({
      notes: [...newNotes,
      {
        ...originNote,
        zorder: newNotes.length
      }]
    })
  }

  handleNoteSentToBack = (note) => {
    console.log("handleNoteSentToBack", note);
    let originNote = this.state.notes.find(x => x.noteId === note.noteId);
    let newNotes = this.state.notes.filter(x => x.noteId !== originNote.noteId).map(x => {
      if (x.zorder < originNote.zorder) {
        return {...x, zorder: x.zorder + 1};
      } else {
        return x;
      }} )
    this.setState({
      notes: [...newNotes,
      {
        ...originNote,
        zorder: 0
      }]
    })
  }

  handleNoteDeleted = (note) => {
    console.log("handleNoteDeleted", note);
    this.setState({
      notes: this.state.notes.filter(x => x.noteId !== note.noteId)
    })
  }

  handleDraggingNote = (draggingNote) => {
    console.log("handleDraggingNote", draggingNote);
    let originNote = this.state.notes.find(x => x.noteId === draggingNote.noteId);
    if( draggingNote.noteId !== this.draggingNote) {
      this.setState({
        notes: [...this.state.notes.filter(x => x.noteId !== draggingNote.noteId),
        {
          ...originNote,
          coordinate: draggingNote.coordinate
        }]
      })
    }
  }

  enterBoard = () => {
    this.clientRef.current.sendMessage(`/app/enterBoard/${this.boardId}`, this.userId)
  }

  render() {
    return (
      <div id="canvas" style={{width: window.innerWidth, height: window.innerHeight}} onDoubleClick={this.createNote}> 
        <SockJsClient
          url={SOCKET_URL}
          topics={[`/topic/${this.state.boardChannel}/cursor`, `/topic/${this.state.boardChannel}/note`, `/topic/${this.state.boardChannel}/draggingNote`]}
          onConnect={this.enterBoard}
          onDisconnect={()=> console.log("DisconnectedNote!")}
          onMessage={this.onEventReceived}
          ref={ (client) => { this.clientRef.current = client }}
        />

        {this.state.onlineUsers.map(user => (
          <div style={{position: "fixed", top: user.y, left: user.x, zIndex:999}}><RiCursorFill/>{user.userId}</div>
        ))}
        {
          this.state.notes.map((note) => 
          <Note
            id={note.noteId}
            key={note.noteId}
            note={note}
            onNoteCoordinateChange={this.moveNote}
            onNoteSizeChange={this.changeNoteSize}
            onNoteDescriptionChange={this.changeNoteDescription}
            onNoteColorChange={this.changeNoteColor}
            onNoteDisplayOrderChange={this.changeNoteDisplayOrder}
            onNoteDelete={this.deleteNote}
            onNoteBringToFront={this.bringNoteToFront}
            onNoteSendToBack={this.sendNoteToBack}
            onNoteDragging={this.dragNote}
          />
        )}
      </div>
    );
  }
}

export default App;