import React, { useState, useEffect } from 'react';
import { ContextMenu, MenuItem, ContextMenuTrigger } from "react-contextmenu";
import {RiDeleteBin6Line} from 'react-icons/ri'
import { Rnd } from 'react-rnd'

function LineDrawer({lineData, onLinePointMoved, onLineDeleted, onLineDragStop}) {
    const [ lines, setLines ] = useState(lineData)
    useEffect(() => {
        console.log(lineData)
        setLines(lineData)
    }, [lineData])

    const handleDragStartPoint = (index, position) => {
        console.log(index, lines[0], "position ", position)
        setLines(lines.map((line, i) => {
            if(index == i) {
                console.log("moving start", {...lines[i], startOffset:{x: position.x, y:position.y}})
                return {...lines[i], startOffset:{x: position.x, y:position.y}}
            }
            return line
        }))
    }

    const handleDragStopStartPoint = (index, position) => {
        var pointDelta = {
            x: position.x - lineData[index].startOffset.x,
            y: position.y - lineData[index].startOffset.y
        }
        setLines(lines.map((line, i) => {
            if(index == i) {
                return {...lines[i], startOffset:{x: position.x, y:position.y}}
            }
            return line
        }))
        onLinePointMoved(lineData[index].lineId, "START", pointDelta)
        onLineDragStop(lineData[index].lineId, "START", position)
    }

    const handleDragEndPoint = (index, position) => {
        setLines(lines.map((line, i) => {
            if(index == i) {
                return {...lines[i], endOffset:{x: position.x, y: position.y}}
            }
            return line
        }))
    }

    const handleDragStopEndPoint = (index, position) => {
        var pointDelta = {
            x: position.x - lineData[index].endOffset.x,
            y: position.y - lineData[index].endOffset.y
        }
        setLines(lines.map((line, i) => {
            if(index == i) {
                return {...lines[i], endOffset:{x: position.x, y:position.y}}
            }
            return line
        }))
        onLinePointMoved(lineData[index].lineId, "END", pointDelta)
        onLineDragStop(lineData[index].lineId, "END", position)
    }

    const handleDelete = (index) => {
        setLines(lines.filter(x => x.lineId !== lines[index].lineId))
        onLineDeleted(lineData[index].lineId)
    }

    return (
        <div>
            <svg style={{width:"100%", height:"100%", position: "absolute", zIndex: -999}}>
            {   lines.map(({startOffset, endOffset}, index) => <path key={index} d={`M${startOffset.x} ${startOffset.y} L${endOffset.x} ${endOffset.y}`} stroke="black" strokeWidth="2"/> )}
            </svg>
            {lines.map(({startOffset, endOffset}, index) => 
            <div>
                <ContextMenuTrigger id={index} holdToDisplay="-1">
                    <Rnd key={index} position={{x:startOffset.x-5, y:startOffset.y-5}} enableResizing={false} onDrag={(position) => handleDragStartPoint(index, position)} onDragStop={(position) => handleDragStopStartPoint(index, position)}>
                        <div style={{width:10, height:10, backgroundColor:"red"}}/>
                    </Rnd>
                    <Rnd key={index} position={{x:endOffset.x-5, y:endOffset.y-5}} enableResizing={false} onDrag={(position) => handleDragEndPoint(index, position)} onDragStop={(position) => handleDragStopEndPoint(index, position)}>
                        <div style={{width:10, height:10, backgroundColor:"green"}}/>
                    </Rnd>
                </ContextMenuTrigger> 
                <ContextMenu id={index}>
                    <MenuItem onClick={() => handleDelete(index)}>
                    <RiDeleteBin6Line className="delete"/>
                    <span>Delete</span>
                    </MenuItem>
                </ContextMenu>
            </div>
            )}
        </div>
    )
}
export default LineDrawer