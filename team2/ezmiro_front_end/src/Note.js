import './App.css';
import React, { useState, useEffect, createRef } from 'react';
import { Rnd } from "react-rnd";
import ContentEditable from 'react-contenteditable';
import { TwitterPicker } from 'react-color';
import { ContextMenu, MenuItem, ContextMenuTrigger } from "react-contextmenu";
import {AiOutlineArrowUp, AiOutlineArrowDown} from 'react-icons/ai'
import {RiDeleteBin6Line} from 'react-icons/ri'

export default function Note({id, key, note, onNoteCoordinateChange, onNoteSizeChange, onNoteDescriptionChange, onNoteColorChange, onNoteDelete, onNoteBringToFront, onNoteSendToBack, onNoteDragging, onNoteEnter, onNoteLeave}) {
    let [isEditable, setIsEditable] = useState(false);
    let [width, setWidth] = useState(note.width);
    let [height, setHeight] = useState(note.height);
    let [coordinate, setCoordinate] = useState(note.coordinate);
    let [color, setColor] = useState(note.color);
    let [displayColorPicker, setDisplayColorPicker] = useState("none");
    let [description, setDescription] = useState(note.description);
    let contentEditableRef = createRef();
    let [zorder, setZOrder] = useState(note.zorder);
  
    let handleDrag = (event, d) => {
      onNoteDragging(id, {
        x: d.x, 
        y: d.y
      })
      setCoordinate({ x: d.x, y: d.y });
    }

    let handleDragStop = (event, d) => {
      onNoteCoordinateChange(id, {
        x: d.x, 
        y: d.y
      });
      setCoordinate({ x: d.x, y: d.y });
    }
    
    let handleResizeStop = (event, direction, ref, delta, coordinate) => {
      onNoteSizeChange(id, {
        width: parseInt(ref.style.width),
        height: parseInt(ref.style.height)
      });
      onNoteCoordinateChange(id, 
        coordinate
      );
      setWidth(parseInt(ref.style.width));
      setHeight(parseInt(ref.style.height));
      setCoordinate(coordinate);
    }
  
    let handleEditMode = (event) => {
      event.stopPropagation();
      setIsEditable(true);
      setDisplayColorPicker(true);
    }
  
    let handleDescriptionEditDone = (event) => {
      onNoteDescriptionChange(id, description);
      setIsEditable(false);
      setDisplayColorPicker("none");
    }
  
    let handleDescriptionChange = (event) => {
      setDescription(event.target.value);
    }
  
    let handleColorChangeComplete = (event) => {
      setColor(event.hex);
      setDisplayColorPicker("none");
      onNoteColorChange(id, event.hex);
    }
  
    let handleDelete = (event) => {
      onNoteDelete(id);
    }
  
    let handleBringToFront = (event) => {
      onNoteBringToFront(id);
    }
  
    let handleSendToBack = (event) => {
      onNoteSendToBack(id);
    }

    let handleMouseEnter = (event) => {
      onNoteEnter(id)
    }

    let handleMouseLeave = (event) => {
      onNoteLeave(id)
    }
  
    useEffect(() => { 
      setColor(note.color);
    }, [note.color]);

    useEffect(() => { 
      setDescription(note.description);
    }, [note.description]);
    
    useEffect(() => {
      setZOrder(note.zorder);
    }, [note.zorder]);

    useEffect(()=> {
      setCoordinate(note.coordinate);
    },[note, note.coordinate])

    useEffect(()=> {
      setWidth(note.width);
      setHeight(note.height)
    },[note.width, note.height])

    return (
      <div key={key}>
      <ContextMenuTrigger id={note.noteId} holdToDisplay="-1">
      <Rnd
          className="draggable-note" 
          style={{backgroundColor: color, zIndex: zorder}}
          size={{ width: width, height: height }}
          position={ coordinate }
          onDrag={handleDrag}
          onDragStop={handleDragStop}
          onResizeStop={handleResizeStop}
          onDoubleClick={handleEditMode}
          onBlur={handleDescriptionEditDone}
          onMouseEnter={handleMouseEnter}
          onMouseLeave={handleMouseLeave}
          >
          <ContentEditable
            innerRef = {contentEditableRef}
            style={{width: "100%", height: "100%", display: "flex",
            justifyContent: "center",
            alignItems: "center"}}
            html={description} 
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
      </ContextMenuTrigger> 
      <ContextMenu id={note.noteId}>
        <MenuItem onClick={handleBringToFront}>
          <AiOutlineArrowUp className="front"/>
          <span>Bring to Front</span>
        </MenuItem>
        <MenuItem onClick={handleSendToBack}>
          <AiOutlineArrowDown className="back"/>
          <span>Send to Back</span>
        </MenuItem>
        <MenuItem onClick={handleDelete}>
          <RiDeleteBin6Line className="delete"/>
          <span>Delete</span>
        </MenuItem>
      </ContextMenu>
      </div>
    );
}