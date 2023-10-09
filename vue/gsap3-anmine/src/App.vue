
<script setup lang="ts">
import { ref } from 'vue'
import gsap from 'gsap'
const x = ref(1024);
//到顶部位置
const y = ref(-860);

function start(): void {
  console.log("执行开始~");
  //     操作元素（支持css选择器）    动作
  gsap.to('.box1', { x: x.value, fill: 'yellow', duration: 1, scale: 2 })
  // repeat 重复几次执行？yoyo：true 溜溜球一样  duration 延迟
  gsap.to('.box2', { x: x.value, fill: 'black', duration: 2, rotation: 360, backgroundColor: 'black', repeat: -1, yoyo: true, ease: 'none' })
  // repeatDelay 重复延迟
  gsap.to('.box3', { rotation: 360, repeatDelay: 1, repeat: 4, duration: 3, ease: 'bounce.out' })
  gsap.to('.box4', { rotation: 360, repeatDelay: 1, repeat: 4, duration: 3, ease: 'linear' })
  gsap.to('.box5', { rotation: 360, repeatDelay: 1, repeat: 4, duration: 3, ease: 'bounce.out' })
  gsap.to('.box6', { rotation: 360, repeatDelay: 1, repeat: 4, duration: 3, ease: 'linear' })

  //延迟加载
  const timeline = gsap.timeline();

  timeline.to('.box7', {x: x.value, duration: 2})
  timeline.to('.box8', {x: x.value, duration: 1})


}

function backTop(){
  
  //返回顶部
  gsap.to('.back', {y: y.value, ease: 'power1.inOut'})
}

function init() {
  x.value = 0;
  y.value = 0;
}


//
// Draggable.create('.canvos-move .box1', {bounds: '.canvos-move'});

</script>

<template>
  <section>
    <div class="box">
      <div class="canvos">
        <div class="box1"></div>
        <div class="box2"></div>
        <div class="box3"></div>
        <div class="box4"></div>
        <div class="box5"></div>
        <div class="box6"></div>
        <div class="box7"></div>
        <div class="box8"></div>
      </div>
    </div>
    <!-- 移动画布 -->
    <div class="canvos-move">
      <div class="box1">移动</div>
    </div>
    <div class="back" @click="backTop">
      <img src="./assets/images/huojian.png" alt="返回顶部">
    </div>
    <div class="btn-box">
      <button id="init" @click="init">复位</button>
      <button id="start" @click="start">开始滚动吧</button>
    </div>
  </section>
</template>

<style lang="scss">
section {
  .box {
    display: flex;
    align-items: center;

    .canvos {
      margin: 10px 0;

      div {
        width: 50px;
        height: 50px;
        margin-bottom: 50px;

        &.box1 {
          background-color: red;
          border-radius: 50%;
        }

        &.box2 {
          border-radius: 10px;
          background-color: green;
        }

        &.box3 {
          background-color: #0b0994;
          border-radius: 5px;
        }

        &.box4 {
          background-color: #62c90e;
          border-radius: 5px;
        }
        &.box5 {
          background-color: #216b9c;
          border-radius: 5px;
        }
        &.box6 {
          background-color: #c90e37;
          border-radius: 5px;
        }
        &.box7 {
          background-color: #321b66;
          border-radius: 5px;
        }
        &.box8 {
          background-color: #c90ea0;
          border-radius: 5px;
        }
      }
    }
  }
  
  .canvos-move{
    width: 1080px;
    height: 600px;
    background-color: #000;
    user-select: none;
      .box1{
        width: 100px;
        height: 80px;
        background-color: #62c90e;
        border-radius: 5px;
        color: #fff;
       display: flex;
       justify-content: center;
       align-items: center;
        font-weight: bold;
      }
    }
}
.back{
  position: fixed;
  right: 50px;
  bottom: 100px;
  width: 100px;
  height: 100px;
  cursor: pointer;
}
.back img{
  width: 100%;
  height: 100%;
}
.btn-box {
  position: fixed;
  right: 30px;
  bottom: 50px;

  #start {}
}
</style>
