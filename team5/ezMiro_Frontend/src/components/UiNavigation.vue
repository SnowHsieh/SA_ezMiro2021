<template>
  <div id="nav">
    <router-link to="/">Home</router-link>
    <router-link to="/board">Board</router-link>
  </div>
</template>

<style scoped>
#nav {
  display: flex;
  justify-content: center;
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
  padding: 0 10px;
}

#nav a.router-link-exact-active {
  color: red;
}
</style>
