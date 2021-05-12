<template>
    <div>

        <div v-if="isActive" >
            <button @click="changeColor('#CADF58')" style="width:100px;background-color:#CADF58">Read Model</button>
            <button @click="changeColor('#6CD5F5')" style="width:100px;background-color:#6CD5F5">Command</button>
            <button @click="changeColor('#FD9E4B')" style="width:100px;background-color:#FD9E4B">Domain Event</button>
            <button @click="changeColor('#C08AC9')" style="width:100px;background-color:#C08AC9">Policy</button>
            <button @click="changeColor('#FFF9B2')" style="width:100px;background-color:#FFF9B2">Aggregate</button>
        </div>
        <div v-else>
            <button @click="createNotes('#CADF58')" style="width:100px;background-color:#CADF58">Read Model</button>
            <button @click="createNotes('#6CD5F5')" style="width:100px;background-color:#6CD5F5">Command</button>
            <button @click="createNotes('#FD9E4B')" style="width:100px;background-color:#FD9E4B">Domain Event</button>
            <button @click="createNotes('#C08AC9')" style="width:100px;background-color:#C08AC9">Policy</button>
            <button @click="createNotes('#FFF9B2')" style="width:100px;background-color:#FFF9B2">Aggregate</button>
        </div>
        <br>
        <div>
            <button @click="bringFigureForward()" style="width:100px;">Bring Figure Forward</button>
            <button @click="bringFigureToFront()" style="width:100px;">Bring Figure To Front</button>
            <button @click="sendFigureBackwards()" style="width:100px;">Send Figure Backwards</button>
            <button @click="sendFigureToBack()" style="width:100px;">Send Figure To Back</button>
        </div>
        <canvas id="canvas"></canvas>
        <div v-for="user in users" :key="user.name">
            <div
                v-if="user.name !== this.user.name"
                style="position: absolute;"
                :style="{ top: user.y + 'px', left: user.x + 'px' }"
            >
                x<br />{{ user.name }}
            </div>
        </div>
    </div>
</template>

<script>
import { markRaw } from '@vue/reactivity';
import { fabric } from 'fabric';

export default {
    name: 'DemoBoard',
    data () {
        return {
            boardId: '',
            canvas: null,
            activeObject: null,
            activeTextBox: null,
            isOverText: false,
            isEditingText: false,
            isActive: false,
            webSocket: null,
            user: null,
            users: []
        }
    },
    async mounted () {
        this.canvas = markRaw(
            new fabric.Canvas('canvas', {
                width: 1600, 
                height: 900
            })
        )
        
        this.user = {
            name: `匿名使用者${Math.floor(Math.random() * 1000) + 1}`,
            x: 0,
            y: 0
        }
        
        this.webSocket = new WebSocket(`ws://localhost:8080/WebSocketServer/${this.user.name}`)
        this.webSocket.onopen = (e) => {
            console.log(e)
            console.log('WebSocket connected.')
        }
        this.webSocket.onmessage = async (e) => {
            const users = await JSON.parse(e.data)
            console.log(users)
            this.users = users
        }
        this.canvas.on('mouse:move', (e) => {
            this.webSocket.send(JSON.stringify({x: e.absolutePointer.x, y: e.absolutePointer.y}))
        })


        this.canvas.on('object:moving', (event) => {
            var target = event.target
            this.$api.note.moveNote(target.figureId, target.top, target.left)
        })
        this.canvas.on('object:scaling', (event) => {
            var target = event.target
            var height = target.height * target.scaleY
            height = parseInt(Math.round(height), 10)
            var width = target.width * target.scaleX
            width = parseInt(Math.round(width), 10)
            this.setTextBoxFontSize(target.item(1), target)
            this.$api.note.resizeNote(target.figureId, height, width)
            this.$api.note.moveNote(target.figureId, target.top, target.left)
        })
        window.addEventListener('keydown', (event) => {
            if (event.key === 'Delete') {
                this.canvas.getActiveObjects().forEach((target) => {
                    if (target.selectable) {
                        this.canvas.remove(target)
                        this.$api.note.deleteNote(target.figureId)
                    }
                })
            }
        })
        this.canvas.on("mouse:down", (event) => {
            if (this.isEditingText === true) {
                this.leaveEditingMode()
            }
            this.isActive = event.target !== null
            this.activeObject = event.target
        })

        const boardId = this.$route.params.id
        this.boardId = boardId
        var figures = await this.$api.board.getBoardContent(boardId)
        this.drawNotes(figures)
    },
    methods: {
        parseNote (note) {
            return {
                figureId: note.figureId,
                left: note.leftTopPositionY,
                top: note.leftTopPositionX,
                height: note.height,
                width: note.width,
                stroke: note.color,
                fill: note.color,
                text: note.text
            }
        },
        drawNotes (notes) {
            this.canvas.clear()
            notes.forEach(note => {
                const parseNote = this.parseNote(note)
                const rect = new fabric.Rect({
                    height: parseNote.height,
                    width: parseNote.width,
                    stroke: parseNote.stroke,
                    fill: parseNote.fill,
                    originX: 'center',
                    originY: 'center'
                })

                const text = new fabric.Textbox(parseNote.text, {
                    originX: 'center',
                    originY: 'center'
                })

                const group = new fabric.Group([ rect, text ], {
                    left: parseNote.left,
                    top: parseNote.top,
                    height: parseNote.height,
                    width: parseNote.width,
                    selectable: true,
                    evented: true,
                })
                group.figureId = note.figureId

                this.addNoteSettings(group)
                this.addTextBoxSettings(text, group)

                this.canvas.add(group)
            })
            this.canvas.renderAll()
        },
        async createNotes (color) {
            await this.$api.note.postNote(this.boardId, 200, 200, 100, 100, color)
            var figures = await this.$api.board.getBoardContent(this.boardId)
            console.log(figures)
            this.drawNotes(figures)
        },
        changeColor (color) {
            this.activeObject.item(0).set({
                'fill': color,
                'stroke': color
            })
            this.$api.note.changeNoteColor(this.activeObject.figureId, color)
            this.canvas.renderAll()
        },
        addTextBoxSettings (textBox, group) {
            textBox.on("mouseover", () => {
                this.isOverText = true
            })

            textBox.on("mouseout", () => {
                this.isOverText = false
            })

            textBox.on("changed", () => {
                this.editNoteText(textBox, group)
            })

            textBox.set({
                hasControls: false,
                hasBorders: false,
                lockMovementX: true,
                lockMovementY: true,
                textAlign: 'center' 
            })
            this.setTextBoxFontSize(textBox, group)
        },
        addNoteSettings (group) {
            const invisibleControls = ["mt", "mr", "ml", "mb", "mtr"]
            invisibleControls.forEach((side) => {
                group.setControlVisible(side, false)
            })

            group.on("mousedblclick", () => {
                group.set({
                selectable: false,
                evented: false,
                })
                this.isEditingText = true
                this.activeObject = group

                this.activeTextBox = group.item(1)
                this.activeTextBox.scaleX *= group.scaleX
                this.activeTextBox.scaleY *= group.scaleY
                this.activeTextBox.left = group.left + (group.height / 2) * this.activeTextBox.scaleX
                this.activeTextBox.top = group.top + (group.width / 2) * this.activeTextBox.scaleY

                group.remove(group.item(1))
                this.canvas.add(this.activeTextBox).setActiveObject(this.activeTextBox)
                this.activeTextBox.enterEditing()

                this.canvas.renderAll()
            })
        },
        leaveEditingMode () {
            if (this.isEditingText === true && this.isOverText === false) {
                this.activeTextBox.exitEditing()

                this.canvas.remove(this.activeTextBox)

                this.activeObject.addWithUpdate(this.activeTextBox)

                this.canvas.setActiveObject(this.activeObject)

                this.isEditingText = false
                this.activeObject.set({
                    selectable: true,
                    evented: true,
                })

                this.resetData()
                this.canvas.renderAll()
            }
        },
        setTextBoxFontSize (textBox, group) {
            let lineNumber = 0
            let maxLineTextWidth = 0

            textBox._textLines.forEach(() => {
                const lineTextWidth = textBox.getLineWidth(lineNumber)
                if (lineTextWidth > maxLineTextWidth) {
                maxLineTextWidth = lineTextWidth
                }
                lineNumber += 1
            })
            textBox.width = maxLineTextWidth

            const maxFixedWidth = group.item(0).width * 0.9
            const maxFiexdHeight = group.item(0).height * 0.9
            const maxFontSize = group.item(0).height * 0.9

            let newFontSize = textBox.fontSize

            newFontSize *= maxFixedWidth / (textBox.width + 1)
            if (newFontSize > maxFontSize) {
                newFontSize = maxFontSize
                // textBox.set({ fontSize: maxFontSize })
            } else {
                // textBox.set({ fontSize: newFontSize })
            }
            textBox.width = maxFixedWidth

            while (textBox.height > maxFiexdHeight) {
                const scale = textBox.height / maxFiexdHeight
                if (newFontSize > maxFontSize) {
                    newFontSize = maxFontSize
                }
                newFontSize -= scale
            }

            textBox.set({ fontSize: newFontSize })
        },
        editNoteText (textBox, group) {
            this.setTextBoxFontSize(textBox, group)
            this.$api.note.editNoteText(group.figureId, textBox.text)
            this.canvas.renderAll()
        },
        resetData () {
            this.groupObject = null
            this.textBox = null
        },
        async bringFigureForward () {
            this.$api.board.bringFigureForward(this.boardId, this.activeObject.figureId)
            await this.canvas.bringForward(this.activeObject)
            this.canvas.renderAll()
        },
        async bringFigureToFront () {
            this.$api.board.bringFigureToFront(this.boardId, this.activeObject.figureId)
            await this.canvas.bringToFront(this.activeObject)
            this.canvas.renderAll()
        },
        async sendFigureBackwards () {
            this.$api.board.sendFigureBackwards(this.boardId, this.activeObject.figureId)
            await this.canvas.sendBackwards(this.activeObject)
            this.canvas.renderAll()
        },
        async sendFigureToBack () {
            this.$api.board.sendFigureToBack(this.boardId, this.activeObject.figureId)
            await this.canvas.sendToBack(this.activeObject)
            this.canvas.renderAll()
        }
    }
}
</script>