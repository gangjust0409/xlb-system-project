<template>
  <!-- 布局 -->
  <div class="common-chat-position">
    <slot></slot>
  </div>
</template>

<script setup lang="ts">

</script>

<style scoped lang="scss">
.common-chat-position {
  height: 4.25rem;
  margin-bottom: .25rem;
  background-color: rgba($color: #0c91df, $alpha: .7);
  padding: .25rem;

  &:last-of-type {
    margin-bottom: 0;
  }

}
</style>