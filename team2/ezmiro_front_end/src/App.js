import './App.css';
import Axios from 'axios';
import { Component, createRef } from 'react';
import Note from './Note';
import SockJsClient from 'react-stomp';
import {RiCursorFill} from 'react-icons/ri'
import LineDrawer from './LineDrawer';
import { AiTwotoneProject } from 'react-icons/ai';

const SOCKET_URL = 'http://localhost:8080/ezmiro/ws-message';

class App extends Component {
  constructor(props){
    super(props);
    this.state = { 
      notes: [],
      lines: [],
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
    this.boardId = "15fccdbf-f344-4b39-a15a-3e912501d810"
    this.noteEnter = createRef();
  }

  componentDidMount() {
    this.noteEnter.current = null
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
      let lines = response.data.lines.map((line) => {
        let temp = {...line}
        if(line.startConnectableFigureId != "") {
          let {x:noteX, y:noteY} = response.data.notes.find(x => x.noteId == line.startConnectableFigureId).coordinate
          temp={...temp, startOffset:{x: noteX + line.startOffset.x, y: noteY + line.startOffset.y}}
        }
        if (line.endConnectableFigureId != "") {
          let {x:noteX, y:noteY} = response.data.notes.find(x => x.noteId == line.endConnectableFigureId).coordinate
          temp={...temp, endOffset:{x: noteX + line.endOffset.x, y: noteY + line.endOffset.y}}
        }
        return temp
      })
      this.setState({notes: response.data.notes, lines: lines, boardChannel: response.data.boardChannel})
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
    // this.setState({
    //   notes: this.state.notes.map(x => x.noteId === id ? {...x, coordinate} : x)
    // })
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

  createLine = () => {
    Axios.post(`http://localhost:8080/ezmiro/miro/figures/lines?boardId=${this.boardId}`,
      {
        "startOffset": {
          "x": 0,
          "y": 0
        }, 
        "endOffset": {
          "x": 100,
          "y": 100
        }, 
        "startConnectableFigureId": "",
        "endConnectableFigureId": ""
      }).then(response => {
        console.log("createLine", response)
      }).catch(error => console.error(error));
  }

  connectLineToFigure = (lineId, linePoint, position) => {
    Axios.post(`http://localhost:8080/ezmiro/miro/lines/${lineId}/connect-to-figure`,
      {
        "figureId": this.noteEnter.current,
        "linePoint": linePoint, 
        "offset": {
          "x": 50,
          "y": 50
        }
      }).then(response => {
        console.log("connectLineToFigure", response)
      }).catch(error => console.error(error));
  }

  moveLinePoint = (lineId, linePoint, pointDelta) => {
    Axios.post(`http://localhost:8080/ezmiro/miro/lines/${lineId}/move-point`,
      {
        "linePoint": linePoint,
        "pointDelta": pointDelta
      }).then(response => {
        console.log("moveLinePoint", response)
      }).catch(error => console.error(error));
  }

  deleteLine = (lineId) => {
    Axios.post(`http://localhost:8080/ezmiro/miro/lines/${lineId}/delete?boardId=${this.boardId}`).then(response => {
        console.log("deleteLine", response)
      }).catch(error => console.error(error));
  }

  onEventReceived = (domainEvent, channel) => {
    if (channel === `/topic/${this.state.boardChannel}/cursor`) {
      this.handleCursorEvent(domainEvent)
    } else if (channel === `/topic/${this.state.boardChannel}/note`) {
      this.handleNoteEvent(domainEvent)
    } else if (channel === `/topic/${this.state.boardChannel}/draggingNote`) {
      this.handleDraggingNote(domainEvent)
    } else if (channel === `/topic/${this.state.boardChannel}/line`) {
      this.handleLineEvent(domainEvent)
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

  handleLineEvent = (lineEvent) => {
    console.log("lineEvent", lineEvent)
    let line = JSON.parse(lineEvent.jsonEvent);
    switch (lineEvent.eventType) {
      case "LineCreated":
        this.handleLineCreated(line);
        break;
      case "LinePointMoved":
        this.handleLinePointMoved(line);
        break;
      case "LineDeleted":
        this.handleLineDeleted(line);
        break;
      case "LineConnectedToFigure":
        this.handleLineConnectedToFigure(line);
        break;
      default:
        break;
    }
  }

  handleLineCreated = (line) => {
    console.log("handleLineCreated", line);
    this.setState({
      lines: [...this.state.lines,
        {
          boardId: line.boardId,
          lineId: line.lineId,
          startConnectableFigureId: line.startConnectableFigureId, 
          endConnectableFigureId: line.endConnectableFigureId, 
          startOffset: line.startOffset,
          endOffset: line.endOffset,
          startArrowStyle: line.startArrowStyle,
          endArrowStyle: line.endArrowStyle,
          zorder: this.state.lines.length
        }
      ]
    })
  }

  handleLinePointMoved = (line) => {
    console.log("handleLinePointMoved", line);
    let originLine = this.state.lines.find(x => x.lineId === line.lineId);
    this.setState({
      lines: [...this.state.lines.filter(x => x.lineId !== line.lineId),
        {
          ...originLine, 
          startOffset: line.linePoint == "START" ? line.newOffset : originLine.startOffset,
          endOffset: line.linePoint == "END" ? line.newOffset : originLine.endOffset
        }
      ]
    })
  }

  handleLineDeleted = (line) => {
    console.log("handleLineDeleted", line);
    this.setState({
      lines: this.state.lines.filter(x => x.lineId !== line.lineId)
    })
  }

  handleLineConnectedToFigure = (line) => {
    console.log("handleLineConnectedToFigure", line)
    let originLine = this.state.lines.find(x => x.lineId === line.lineId);
    let targetNote = this.state.notes.find(x => x.noteId === line.figureId)
    let newPoint = {
      x: targetNote.coordinate.x + line.offset.x,
      y: targetNote.coordinate.y + line.offset.y,
    }
    this.setState({
      lines: [...this.state.lines.filter(x => x.lineId !== line.lineId),
        {
          ...originLine, 
          startConnectableFigureId: line.linePoint == "START" ? line.figureId : originLine.startConnectableFigureId,
          endConnectableFigureId: line.linePoint == "END" ? line.figureId : originLine.endConnectableFigureId,
          startOffset: line.linePoint == "START" ? newPoint : originLine.startOffset,
          endOffset: line.linePoint == "END" ? newPoint : originLine.endOffset
        }
      ]
    }, console.log("handleLineConnectedToFigure",this.state.lines))
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
    let newLines = this.state.lines.map((line) => {
      let temp = {...line}
      let {x:noteX, y:noteY} = originNote.coordinate
      let deltaX = note.coordinate.x - noteX
      let deltaY = note.coordinate.y - noteY
      console.log("originNote.coordinate", originNote.coordinate, "note.coordinate", note.coordinate)
      console.log("deltaX", deltaX , "deltaY", deltaY)
      if(line.startConnectableFigureId == note.noteId) {
        temp={...temp, startOffset:{x: deltaX + line.startOffset.x, y: deltaY + line.startOffset.y}}
      }
      if (line.endConnectableFigureId == note.noteId) {
        temp={...temp, endOffset:{x: deltaX + line.endOffset.x, y: deltaY + line.endOffset.y}}
      }
      return temp
    })

    this.setState({
      notes: [...this.state.notes.filter(x => x.noteId !== note.noteId),
      {
        ...originNote,
        coordinate: note.coordinate
      }],
      lines: newLines
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
    
    let newLines = this.state.lines.map((line) => {
      let temp = {...line}
      let {x:noteX, y:noteY} = originNote.coordinate
      let deltaX = draggingNote.coordinate.x - noteX
      let deltaY = draggingNote.coordinate.y - noteY
      console.log("originNote.coordinate", originNote.coordinate, "draggingNote.coordinate", draggingNote.coordinate)
      console.log("deltaX", deltaX , "deltaY", deltaY)
      if(line.startConnectableFigureId == draggingNote.noteId) {
        temp={...temp, startOffset:{x: deltaX + line.startOffset.x, y: deltaY + line.startOffset.y}}
      }
      if (line.endConnectableFigureId == draggingNote.noteId) {
        temp={...temp, endOffset:{x: deltaX + line.endOffset.x, y: deltaY + line.endOffset.y}}
      }
      return temp
    })

    if( draggingNote.noteId !== this.draggingNote) {
      this.setState({
        notes: [...this.state.notes.filter(x => x.noteId !== draggingNote.noteId),
        {
          ...originNote,
          coordinate: draggingNote.coordinate
        }],
        lines: newLines
      })
    }
  }

  enterBoard = () => {
    this.clientRef.current.sendMessage(`/app/enterBoard/${this.boardId}`, this.userId)
  }

  dragStopLine = (lineId, linePoint, position) => {
    // console.log("dragStopLine", linePoint)
    if(this.noteEnter.current !== null){
      this.connectLineToFigure(lineId, linePoint, position)
    }
  }
  
  enterNote = (id) => {
    this.noteEnter.current = id
  }

  leaveNote = () => {
    this.noteEnter.current = null
  }



  render() {
    return (
      <div id="canvas" style={{width: window.innerWidth, height: window.innerHeight}} onDoubleClick={this.createNote}> 
        <button type="button" onClick={this.createLine}>Create Line</button>
        <SockJsClient
          url={SOCKET_URL}
          topics={[`/topic/${this.state.boardChannel}/cursor`, `/topic/${this.state.boardChannel}/note`, `/topic/${this.state.boardChannel}/draggingNote`, `/topic/${this.state.boardChannel}/line`]}
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
            onNoteEnter={this.enterNote}
            onNoteLeave={this.leaveNote}
          />
        )}
        <LineDrawer
          lineData={this.state.lines}
          onLinePointMoved={this.moveLinePoint}
          onLineDeleted={this.deleteLine}
          onLineDragStop={this.dragStopLine}
        />
      </div>
    );
  }
}

export default App;