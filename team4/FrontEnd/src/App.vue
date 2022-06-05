<template>
  <div id="app">
    <router-view/>
  </div>
</template>
<script>

export default {
  name: 'App'
}
</script>
<style>
@import './assets/styles/contextMenuStyle.css';
@import './assets/styles/loginstyle.css';
@import './assets/styles/signupstyle.css';
#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
