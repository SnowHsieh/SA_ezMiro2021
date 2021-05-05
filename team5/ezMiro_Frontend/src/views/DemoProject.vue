<template>
    <div>
        <br>
        <button @click="createBoard">Add Board</button>
        <br>
        <br>
        <button v-for="board in boards" :key="board.id" @click="gotoBoard(board.id)">
            {{board.name}}
        </button>
    </div>
</template>

<script>
export default {
    data() {
        return {
            boardCount: 1,
            boards: []
        }
    },
    methods: {
        async createBoard () {
            var boardName = `board${this.boardCount}`
            var boardId = await this.$api.board.createBoard('1', boardName)
            this.boards.push({
                id: boardId,
                name: boardName
            })
            this.boardCount++
        },
        gotoBoard (id) {
            this.$router.push(`/demoboard/${id}`)
        }
    }
}
</script>