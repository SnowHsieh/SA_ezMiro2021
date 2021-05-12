import './App.css';
import React, { useState, useEffect, useRef, createRef } from 'react';
import { Rnd } from "react-rnd";
import ContentEditable from 'react-contenteditable';
import { TwitterPicker } from 'react-color';
import { ContextMenu, MenuItem, ContextMenuTrigger } from "react-contextmenu";
import {AiOutlineArrowUp, AiOutlineArrowDown} from 'react-icons/ai'
import {RiDeleteBin6Line} from 'react-icons/ri'

export default function Note({id, key, note, onNoteCoordinateChange, onNoteSizeChange, onNoteDescriptionChange, onNoteColorChange, onNoteDelete, onNoteBringToFront, onNoteSendToBack}) {
    let [isEditable, setIsEditable] = useState(false);
    let [width, setWidth] = useState(note.width);
    let [height, setHeight] = useState(note.height);
    let [position, setPosition] = useState(note.coordinate);
    let [color, setColor] = useState(note.color);
    let [displayColorPicker, setDisplayColorPicker] = useState("none");
    let description = useRef(note.description);
    let contentEditableRef = createRef();
    let [zorder, setZOrder] = useState(note.zorder);
  
    let handleDragStop = (event, d) => {
      onNoteCoordinateChange(id, {
        x: d.x, 
        y: d.y
      });
      setPosition({ x: d.x, y: d.y });
    }
    
    let handleResizeStop = (event, direction, ref, delta, position) => {
      onNoteSizeChange(id, {
        width: parseInt(ref.style.width),
        height: parseInt(ref.style.height)
      });
      onNoteCoordinateChange(id, 
        position
      );
      setWidth(parseInt(ref.style.width));
      setHeight(parseInt(ref.style.height));
      setPosition(position);
    }
  
    let handleEditMode = (event) => {
      event.stopPropagation();
      setIsEditable(true);
      setDisplayColorPicker(true);
    }
  
    let handleDescriptionEditDone = (event) => {
      onNoteDescriptionChange(id, description.current);
      setIsEditable(false);
      setDisplayColorPicker("none");
    }
  
    let handleDescriptionChange = (event) => {
      description.current = event.target.value;
    }
  
    let handleColorChangeComplete = (event) => {
      setColor(event.hex);
      setDisplayColorPicker("none");
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
  
    useEffect(() => { 
      onNoteColorChange(id, color)
    }, [color]);
    
    useEffect(() => {
      setZOrder(note.zorder);
    }, [note.zorder]);
  
    return (
      <div key={key}>
      <ContextMenuTrigger id={note.noteId} holdToDisplay="-1">
      <Rnd
          className="draggable-note" 
          style={{backgroundColor: color,
            zIndex: zorder}}
            size={{ width: width, height: height }}
            position={ position }
            onDragStop={handleDragStop}
            onResizeStop={handleResizeStop}
            onDoubleClick={handleEditMode}
            onBlur={handleDescriptionEditDone}
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