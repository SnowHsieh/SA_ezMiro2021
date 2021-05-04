<template>
    <div>
        <canvas id="canvas"></canvas>
    </div>
</template>

<script>
import { markRaw } from '@vue/reactivity';
import { fabric } from 'fabric';

export default {
    name: 'DemoBoard',
    data () {
        return {
            canvas: null
        }
    },
    async mounted () {
        this.canvas = markRaw(
            new fabric.Canvas('canvas', {
                width: 1600, 
                height: 900
            })
        )
        this.canvas.on('object:moving', (event) => {
            var target = event.target
            console.log('moving')
            console.log(target)
            this.$api.note.moveNote(target.figureId, target.top, target.left)

        })
        this.canvas.on('object:scaling', (event) => {
            var target = event.target
            console.log('scaling')
            console.log(target)
            var height = target.height * target.scaleY
            height = parseInt(Math.round(height), 10)
            var width = target.width * target.scaleX
            width = parseInt(Math.round(width), 10)
            this.$api.note.resizeNote(target.figureId, height, width)
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

        const boardId = await this.$api.board.createBoard('projectId', 'BoardName')
        console.log(boardId)
        await this.$api.board.getBoardContent(boardId)
        await this.$api.note.postNote(boardId, 200, 200, 100, 100, '#CADF58')
        await this.$api.note.postNote(boardId, 325, 200, 100, 100, '#6CD5F5')
        await this.$api.note.postNote(boardId, 450, 200, 100, 100, '#FD9E4B')
        await this.$api.note.postNote(boardId, 575, 200, 100, 100, '#C08AC9')
        await this.$api.note.postNote(boardId, 388, 120, 100, 100, '#FFF9B2')
        const figures = await this.$api.board.getBoardContent(boardId)
        console.log(figures)
        this.createNotes(figures)
    },
    methods: {
        parseNote (note) {
            return {
                figureId: note.figureId,
                left: note.leftTopPositionX,
                top: note.leftTopPositionY,
                height: note.height,
                width: note.width,
                stroke: note.color,
                fill: note.color
            }
        },
        createNotes (notes) {
            notes.forEach(note => {
                const rect = new fabric.Rect(this.parseNote(note))
                rect.figureId = note.figureId
                this.canvas.add(rect)
            })
            this.canvas.renderAll()
        }
    }
}
</script>