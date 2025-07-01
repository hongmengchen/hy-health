<!--欢迎页面  -->
<template>
  <div>
    <el-carousel :interval="3000" height="450px" trigger="click" arrow="hover">
      <el-carousel-item v-for="(url, index) in picList" :key="index">
        <el-image :src="url.image" fit="contain"></el-image>
      </el-carousel-item>
    </el-carousel>

    <div class="card-list">
      <el-card
          :body-style="{ padding: '0px' }"
          v-for="(card, index) in cardList"
          :key="index"
          shadow="hover"
      >
        <el-image
            style="width: 200px; height: 150px;"
            :src="card.pic"
            fit="cover"
        />
        <div class="card-content">
          <span>{{ card.content }}</span>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "welcomeView",
  data() {
    return {
      administrators: "admin",
      picList: [
        {image: require('../../assets/hUlLAP.png')},
        {image: require('../../assets/hUlH0I.png')},
        {image: require('../../assets/hUlOtf.jpg')},
        {image: require('../../assets/hUlb7t.jpg')},
        {image: require('../../assets/hUl7nA.jpg')}
      ],
      cardList: [
        {
          pic: require('../../assets/hUDHDP.jpg'),
          content: "基础信息管理",
        },
        {
          pic: require('../../assets/hU0KDf.jpg'),
          content: "药品信息管理",
        },
        {
          pic: require('../../assets/hUBref.jpg'),
          content: "医保政策管理",
        },
        {
          pic: require('../../assets/hU0yG9.jpg'),
          content: "医药公司政策管理",
        },
        {
          pic: require('../../assets/hUB2Wj.jpg'),
          content: "医生信息管理",
        },
        {
          pic: require('../../assets/hUDYn0.jpg'),
          content: "必备材料管理",
        },
      ],
    };
  },
};
</script>
<style lang="less" scoped>
.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

.card-list {
  display: flex;
  justify-content: space-around;
  align-items: center;
  margin-top: 10px;
}

.card-content {
  display: flex;
  justify-content: left;
  align-items: center;
  border-left: 7px solid #455a64;
  margin-top: -2px;
  padding: 14px;
}

.card-content span {
  font-size: 16px;
  font-weight: 900;
}
</style>