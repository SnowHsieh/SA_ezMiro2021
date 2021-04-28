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
        const boardId = await this.createBoard('projectId', 'BoardName')
        await this.getBoardContent(boardId)
        await this.postNote(boardId, 200, 200, 100, 100, '#CADF58')
        await this.postNote(boardId, 325, 200, 100, 100, '#6CD5F5')
        await this.postNote(boardId, 450, 200, 100, 100, '#FD9E4B')
        await this.postNote(boardId, 575, 200, 100, 100, '#C08AC9')
        await this.postNote(boardId, 388, 120, 100, 100, '#FFF9B2')
        const figures = await this.getBoardContent(boardId)
        console.log(figures)
        this.createNotes(figures)
    },
    methods: {
        async createBoard (projectId, name) {
            var boardId = null
            await this.$api.board.createBoard({
                projectId: projectId,
                name: name
            }).then((response) => {
                boardId = response.data.id
            })
            return boardId
        },
        async getBoardContent (boardId) {
            var figures = []
            await this.$api.board.getBoardContent({
                boardId: boardId
            }).then((response) => {
                figures = response.data.figureDtos
            })
            return figures
        },
        async postNote (boardId, leftTopPositionX, leftTopPositionY, height, width, color) {
            var id = null
            await this.$api.note.postNote({
                boardId: boardId,
                leftTopPositionX: leftTopPositionX,
                leftTopPositionY: leftTopPositionY,
                height: height,
                width: width,
                color: color
            }).then((response) => {
                id = response.data.id
            })
            return id
        },
        parseNote (note) {
            return {
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
                this.canvas.add(rect)
            })
            this.canvas.renderAll()
        }
    }
}
</script>