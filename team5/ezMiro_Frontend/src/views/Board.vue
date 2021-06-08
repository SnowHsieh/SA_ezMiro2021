<template>
  <div>
    <ez-miro-canvas v-bind:boardId="this.$route.params.id"/>
  </div>
</template>
<script>
import EzMiroCanvas from '@/components/board/EzMiroCanvas.vue'

export default {
  name: 'Board',
  components: {
    EzMiroCanvas
  }
}
</script>
