<template>
  <NavBar/>
  <router-view></router-view>
</template>

<script>
import NavBar from './components/NavBar.vue';
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"


export default {
  components: {
    NavBar
  },
  setup() {
    // $.ajax({
    //   url: "http://127.0.0.1:8080/user/account/register/",
    //   type: "post",
    //   data: {
    //     username: 'syr1',
    //     password: '123',
    //     confirmedPassword: '123',
    //   },
    //   success(resp) {
    //     console.log(resp);
    //   },
    //   error(resp) {
    //     console.log(resp);
    //   }
    // });

  }
}
</script>

<style>
body {
  background-image: url("@/assets/image/背景图片.png");
  background-size: cover;
}

</style>
