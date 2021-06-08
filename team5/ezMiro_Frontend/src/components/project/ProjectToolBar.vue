<template>
  <v-navigation-drawer
    absolute
    color="grey lighten-3"
    mini-variant
  >
    <v-avatar
      class="d-block text-center mx-auto mt-4"
      color="grey darken-1"
      size="36"
    ></v-avatar>

    <v-divider class="mx-3 my-5"></v-divider>

    <v-avatar
      v-for="n in 6"
      :key="n"
      class="d-block text-center mx-auto mb-9"
      color="grey lighten-1"
      size="28"
    ></v-avatar>
  </v-navigation-drawer>
</template>
<script>
export default {
  name: 'ProjectToolBar'
}
</script>
