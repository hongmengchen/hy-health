<template>
  <div class="LoginBackground">
    <div class="LoginForm">
      <div class="big-contain">
        <!-- 原有轮播图 -->
        <el-carousel :interval="3000" height="450px" trigger="click" arrow="hover">
          <el-carousel-item v-for="(url, index) in picList" :key="index">
            <el-image :src="url.image" fit="contain"></el-image>
          </el-carousel-item>
        </el-carousel>

        <!-- 卡片列表区域 -->
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
    </div>
  </div>
</template>

<script>
export default {
  name: "HomeView",
  data() {
    return {
      picList: [
        { image: require('../../assets/hUlLAP.png') },
        { image: require('../../assets/hUlH0I.png') },
        { image: require('../../assets/hUlOtf.jpg') },
        { image: require('../../assets/hUlb7t.jpg') },
        { image: require('../../assets/hUl7nA.jpg') }
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

<style scoped>
.LoginBackground {
  background: url("../../assets/LoginBackground.jpg") no-repeat;
  background-size: cover;
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.LoginForm {
  background: url("../../assets/Login.png") no-repeat;
  background-size: 800px 599px;
  width: 1000px;
  height: 800px;
  margin-left: 555px;
  margin-top: 100px;
  overflow: hidden;
}

.big-contain {
  margin-left: -60px;
  padding: 20px;
}

/* 保留原有卡片样式 */
.card-list {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  padding: 20px;
}

.card-content {
  padding: 10px;
  text-align: center;
}

/* 输入框样式（未使用，但保留结构一致性） */
.UserName,
.PassWord {
  overflow: hidden;
  border: 2px solid #2abeb2;
  border-radius: 20px;
  margin-left: 200px;
  height: 70px;
  width: 530px;
}

.Input {
  overflow: hidden;
  margin-left: 20px;
  height: 80px;
  font-size: 35px;
  color: #2abeb2;
}

.Logo {
  overflow: hidden;
  margin-left: 15px;
  margin-bottom: 5px;
}

.LoginBtn {
  overflow: hidden;
  background: url("../../assets/LoginButton.png");
  background-size: 100%;
  width: 220px;
  height: 65px;
  margin-left: 290px;
  margin-top: 10px;
}

.NameNotNull,
.PasswordNotNull {
  margin-left: 200px;
}

input::-webkit-input-placeholder,
input::-ms-input-placeholder {
  color: #2abeb2;
}
</style>
