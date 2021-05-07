<template>
  <div class="home"></div>
</template>
<script>

export default {
  setup() {
    
  },
  async created () {
    console.log(this)
    var boardId
    await this.$api.board.createBoard({
      projectId: 'project01',
      name: 'boardName'
    }).then((response) => {
      const boardData = response.data
      boardId = boardData.id
      console.log(boardData)
    })
    await this.$api.note.postNote({
        boardId: boardId,
        leftTopPositionX: 0,
        leftTopPositionY: 0,
        rightBottomPositionX: 10,
        rightBottomPositionY: 10,
        color: '#000000'
      }).then((response) => {
        console.log(response.data)
      })
      await this.$api.board.getBoardContent({
        boardId: boardId
      }).then((response) => {
        console.log(response.data)
      })
  }
}
</script>
