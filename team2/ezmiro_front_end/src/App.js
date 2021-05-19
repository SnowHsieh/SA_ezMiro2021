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
      onlineUsers: []
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
    this.boardChannel = "";
    this.cursor = {
      x: 0,
      y: 0
    }
    this.oldCursor = {
      x: 0,
      y: 0
    }
    this.draggingNote = ""
  }

  componentDidMount() {
    this.cursorBroadcaster = setInterval(()=>{
      try {
        if (this.oldCursor != this.cursor) {
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
    Axios.get('http://localhost:8080/ezmiro/miro/boards/boardId/getcontent').then(response => {
      this.setState({notes: response.data.notes})
      this.boardChannel = response.data.boardChannel
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
      this.getBoardContent();
      try {
        this.noteRef.current.sendMessage("/app/sendNote", JSON.stringify(this.state.notes))
      } catch(error) {
        console.error(error);
      }
    }
    ).catch(error => console.error(error));
  }

  dragNote = (id, coordinate) => {
    this.draggingNote = id
    this.setState({
      notes: this.state.notes.map(x => x.noteId === id ? {...x, coordinate} : x)
    })
    try {
      this.noteRef.current.sendMessage("/app/sendNote", JSON.stringify(this.state.notes))
    } catch(error) {
      console.error(error);
    }
  }

  changeNoteSize = (id, size) => {
    Axios.post(`http://localhost:8080/ezmiro/miro/notes/${id}/edit/size`,{
      size: size
    }).catch(error => console.error(error));
  }

  changeNoteDescription = (id, description) => {
    Axios.post(`http://localhost:8080/ezmiro/miro/notes/${id}/edit/description`,{
      description: description
    }).catch(error => console.error(error));
  }

  changeNoteColor = (id, color) => {
    Axios.post(`http://localhost:8080/ezmiro/miro/notes/${id}/edit/color`,{
      color: color
    }).then((response) => {
      try {
        this.noteRef.current.sendMessage("/app/sendNote", JSON.stringify(this.state.notes))
      } catch(error) {
        console.error(error); 
      }
    }

    ).catch(error => console.error(error));
  }

  bringNoteToFront = (id) => {
    Axios.post(`http://localhost:8080/ezmiro/miro/notes/${id}/edit/zorder/front`,{
    }).then((response) => {
      this.getBoardContent();
      try {
        this.noteRef.current.sendMessage("/app/sendNote", JSON.stringify(this.state.notes))
      } catch(error) {
        console.error(error);
      }
    }).catch(error => console.error(error));
  }

  sendNoteToBack = (id) => {
    Axios.post(`http://localhost:8080/ezmiro/miro/notes/${id}/edit/zorder/back`,{
    }).then((response) => {
      this.getBoardContent();
    }).catch(error => console.error(error));
  }

  deleteNote = (id) => {
    var {notes} = this.state;
    this.setState({
      notes:notes.filter( x => x.noteId !== id)
    });
    Axios.post(`http://localhost:8080/ezmiro/miro/notes/${id}/delete?boardId=boardId`,{
    }).then(() => {
      this.getBoardContent();
      try {
        this.noteRef.current.sendMessage("/app/sendNote", JSON.stringify(this.state.notes))
      } catch(error) {
        console.error(error);
      }
    }).catch(error => console.error(error));
  }

  createNote = () => {
    console.log("double click")
    Axios.post('http://localhost:8080/ezmiro/miro/figures/notes?boardId=boardId',
      {
        "coordinate": {
          "x": this.cursor.x - 50,
          "y": this.cursor.y - 50
        }
      }).then(response => {
        this.getBoardContent();
        try {
          this.noteRef.current.sendMessage("/app/sendNote", JSON.stringify(this.state.notes))
        } catch(error) {
          console.error(error);
        }
      }).catch(error => console.error(error));
  }

  onPositionReceived = (allUserCursors) => {
    const onlineUsers = allUserCursors.filter(cursor => cursor.userId !== this.userId)
      this.setState({
        onlineUsers: onlineUsers
      })
  }
  
  onNoteReceived = (allOnlineNotes) => {
    console.log("allOnlineNotes", allOnlineNotes)
    let notes = allOnlineNotes.map(x => {
      if(x.noteId === this.draggingNote) {
        return this.state.notes.find(y => y.noteId === this.draggingNote)
      }
      return x
    })
    this.setState({
      notes
    })
  }

  enterBoard = () => {
    this.clientRef.current.sendMessage("/app/enterBoard/boardId", this.userId)
  }

  render() {
    return (
      <div id="canvas" style={{width: window.innerWidth, height: window.innerHeight}} onDoubleClick={this.createNote}> 
        <SockJsClient
          url={SOCKET_URL}
          topics={[`/topic/${this.boardChannel}/position`]}
          onConnect={this.enterBoard}
          onDisconnect={()=> console.log("DisconnectedNote!")}
          onMessage={this.onPositionReceived}
          ref={ (client) => { this.clientRef.current = client }}
        />

        <SockJsClient
          url={SOCKET_URL}
          topics={['/topic/note']}
          onConnect={()=> console.log("ConnectedNote!")}
          onDisconnect={()=> console.log("DisconnectedNote!")}
          onMessage={this.onNoteReceived}
          ref={ (client) => { this.noteRef.current = client }}
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