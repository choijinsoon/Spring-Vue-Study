<script setup>
  import { useAuthStore } from '../../stores/UserDetails';
  import { useRoute, useRouter } from 'vue-router'
  import bcrypt from 'bcryptjs';
  import { onUpdated, reactive, watch } from '@vue/runtime-core';

  const route = useRoute();
  const router = useRouter();
  const store = useAuthStore();

  const user = reactive({ uid: "", pwd: "" });
  const reactMember = reactive({member:{}});
  const error = false;

  async function getUser(uid) {
    const response = await fetch(`http://localhost:8080/members/${uid}`);

    const json = await response.json();
    reactMember.member = json;
  };

  function loginHandler() {
    getUser(user.uid);
  }

  watch(reactMember, ({member:newOne}, prevOne)=>{
    console.log("watch")
    bcrypt.compare(user.pwd, newOne.pwd, (err, res)=>{
      if(res){
        // 인증 상태 저장
        console.log(store.username);
        // 로그인된 페이지로 이동
        store.userDetails.username = user.uid;
        console.log(store.username);
        
        if(route.query.returnURL){
          router.push(route.query.returnURL);
          return;
        }


        router.push('/admin/index');
        return;
      }
    error = true;
    });
  })

  onUpdated(()=>{
    console.log("updated");
  })
</script>


<template>
  <main>
    <section>
      <header class="header-default">
        <h1 class="text-title1-h1">로그인</h1>
      </header>
      <section class="login">
        <h1 class="d-none">일반 로그인</h1>
        <div v-if="error" style="color:red;text-align:center;padding:20px">
          아이디 또는 비밀번호가 일치하지 않습니다.
        </div>
        <form>
          <div class="d-flex align-items-center">
            <label class="d-none">아이디</label
            ><input
              v-model="user.uid"
              class="btn btn-cancel"
              type="text"
              placeholder="로그인 아이디를 입력하세요"
            />
          </div>
          <div class="d-flex align-items-center">
            <label class="d-none">비밀번호</label
            ><input
              v-model="user.pwd"
              class="btn btn-cancel"
              type="password"
              placeholder="비밀번호"
            />
          </div>

          <div class="d-flex align-items-center justify-content-center">
            <input type="checkbox" />
            <label>로그인 저장</label>
          </div>

          <div class="d-flex align-items-center justify-content-center">
            <input
              class="btn btn-default"
              type="submit"
              value="로그인"
              @click.prevent="loginHandler"
            />
            <a class="btn btn-cancel" href="">취소하기</a>
          </div>
        </form>
      </section>

      <section class="register">
        <h1 class="d-none">회원가입</h1>
        <a href="signup.html">회원가입</a>
        <a href="">아이디 찾기</a>
        <a href="">비밀번호 찾기</a>
      </section>

      <section class="social-login">
        <h1 class="d-none">소셜 로그인</h1>
        <span>또는 다음으로 로그인</span>
        <div>
          <a class="icon icon-naver mx-2" href="">네이버 로그인</a>
          <a class="icon icon-kakao mx-2" href="">카카오 로그인</a>
          <a class="icon icon-youtube mx-2" href="">구글 로그인</a>
        </div>
      </section>
    </section>
  </main>
</template>

<style scoped>
input {
  color: black;
}
</style>