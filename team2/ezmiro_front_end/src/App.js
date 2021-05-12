import './App.css';
import Axios from 'axios';
import { Component, createRef } from 'react';
import Note from './Note'
import SockJsClient from 'react-stomp';

const SOCKET_URL = 'http://localhost:8080/ezmiro/ws-message';

class App extends Component {
  constructor(props){
    super(props);
    this.state = { 
      notes: [],
      reconnectTime: 0,
      isConnect: false,
      message:'You server message here.',
      onlineUsers: []
    };
    this.getBoardContent();
    this.getBoardContent = this.getBoardContent.bind(this);
    this.createNote = this.createNote.bind(this);
    this.deleteNote = this.deleteNote.bind(this);
    this.updateMousePosition = this.updateMousePosition.bind(this);
    this.componentWillUnmount = this.componentWillUnmount.bind(this);
    window.addEventListener("mousemove", this.updateMousePosition);
    this.clientRef = createRef();
    this.cursorBroadcast = null;
    this.userId = "userId"
    this.cursor = {
      x: 0,
      y: 0
    }
  }

  componentDidMount() {
    this.cursorBroadcast = setInterval(()=>{
      try {
        const myCursor = {
          userId: this.userId,
          ...this.cursor
        }
        this.clientRef.current.sendMessage("/app/sendMessage", JSON.stringify(myCursor))
      } catch(error) {
        console.error(error);  
      }
    }, 100);
    this.userId = prompt("Your user Id")
  }

  componentWillUnmount() {
    window.removeEventListener("mousemove", this.updateMousePosition);
    this.clientRef.current.disconnect()
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
      this.setState({notes: response.data});
      console.log("getBoardContent",response.data);
    }).catch(error => console.error(error));
  }

  moveNote = (id, coordinate) => {
    Axios.post(`http://localhost:8080/ezmiro/miro/notes/${id}/move`,{
      coordinate: {
        x: coordinate.x,
        y: coordinate.y
      }
    }).catch(error => console.error(error));
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
    }).catch(error => console.error(error));
  }

  bringNoteToFront = (id) => {
    Axios.post(`http://localhost:8080/ezmiro/miro/notes/${id}/edit/zorder/front`,{
    }).then((response) => {
      console.log("bringNoteToFront", response.data);
      this.getBoardContent();
    }).catch(error => console.error(error));
  }

  sendNoteToBack = (id) => {
    Axios.post(`http://localhost:8080/ezmiro/miro/notes/${id}/edit/zorder/back`,{
    }).then((response) => {
      console.log("sendNoteToBack", response.data);
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
        console.log("creatNote", response.data)
        this.getBoardContent();
      }).catch(error => console.error(error));
  }

  onPositionReceived = (allUserCursors) => {
    const onlineUsers = allUserCursors.filter(cursor => cursor.userId !== this.userId)
      console.log("received onlineUsers:", onlineUsers)
      this.setState({
        onlineUsers: onlineUsers
      })
  }

  render() {
    return (
      <div id="canvas" style={{width: window.innerWidth, height: window.innerHeight}} onDoubleClick={this.createNote}> 
        <SockJsClient
          url={SOCKET_URL}
          topics={['/topic/position']}
          onConnect={()=> console.log("Connected!")}
          onDisconnect={()=> console.log("Disconnected!")}
          onMessage={this.onPositionReceived}
          ref={ (client) => { this.clientRef.current = client }}
        />
        {this.state.onlineUsers.map(user => (
          <div style={{position: "fixed", top: user.y, left: user.x}}>{user.userId}</div>
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
          />
        )}
      </div>
    );
  }
}

export default App;