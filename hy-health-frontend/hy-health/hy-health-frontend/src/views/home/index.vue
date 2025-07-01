<template>
  <div class="HomeBackground">
    <!-- 左侧导航栏 -->
    <el-menu
      class="side-nav"
      background-color="#00b78d"
      text-color="#fff"
      active-text-color="#ffd04b"
      router
      :default-active="$route.path"
    >
      <el-menu-item index="/home">
        <i class="el-icon-s-home"></i>
        <span>首页</span>
      </el-menu-item>
      <el-menu-item index="/pharmacyManagement">
        <i class="el-icon-medal-1"></i>
        <span>医药公司管理</span>
      </el-menu-item>
      <el-menu-item index="/salesSiteManagement">
        <i class="el-icon-location-outline"></i>
        <span>销售地点管理</span>
      </el-menu-item>
      <el-menu-item index="/cityInformationManagement">
        <i class="el-icon-document"></i>
        <span>城市信息管理</span>
      </el-menu-item>
      <el-menu-item index="/drugInformationManagement">
        <i class="el-icon-s-goods"></i>
        <span>药品信息管理</span>
      </el-menu-item>
      <el-menu-item index="/medicalInsurancePolicyManagement">
        <i class="el-icon-s-order"></i>
        <span>医保政策管理</span>
      </el-menu-item>
      <el-menu-item index="/pharmacyCompanyPolicyManagement">
        <i class="el-icon-s-platform"></i>
        <span>医药公司政策管理</span>
      </el-menu-item>
      <el-menu-item index="/doctorInformationManagement">
        <i class="el-icon-user"></i>
        <span>医生信息管理</span>
      </el-menu-item>
      <el-menu-item index="/essentialMaterialManagement">
        <i class="el-icon-notebook-2"></i>
        <span>必备材料管理</span>
      </el-menu-item>
    </el-menu>

    <!-- 主要内容区域 -->
    <div class="main-content">
      <div class="header-section">
        <img src="https://placehold.co/600x450" alt="十年专注，一路引领！">
        <div class="header-text">
          <h1>十年专注，一路引领！</h1>
          <h2>医疗健康产业最具思想领导力的价值创造者！</h2>
        </div>
      </div>
      
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
</template>

<script>
export default {
  name: "HomeView",
  data() {
    return {
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
.HomeBackground {
  display: flex;
  width: 100%;
  height: 100%;
}

.side-nav {
  width: 200px;
  height: 100%;
}

.main-content {
  flex: 1;
  padding: 20px;
}

.header-section {
  position: relative;
  margin-bottom: 20px;
}

.header-section img {
  width: 100%;
  height: auto;
}

.header-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: #ff0000;
  text-align: center;
}

.header-text h1 {
  font-size: 36px;
  margin-bottom: 10px;
}

.header-text h2 {
  font-size: 24px;
}

.card-list {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.card-content {
  padding: 10px;
  text-align: center;
}
</style>