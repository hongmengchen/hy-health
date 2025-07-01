<template>
  <div class="home-container">
    <!-- 轮播图 -->
    <el-carousel class="home-carousel" :interval="4000" type="card" height="400px">
      <el-carousel-item v-for="(item, index) in carouselItems" :key="index">
        <img :src="item.image" class="carousel-image" />
      </el-carousel-item>
    </el-carousel>

    <!-- 功能模块 -->
    <div class="module-grid">
      <div
        v-for="(module, index) in modules"
        :key="index"
        class="module-card"
        @click="navigateTo(module.path)"
      >
        <div class="module-icon">
          <i :class="module.icon"></i>
        </div>
        <div class="module-title">{{ module.title }}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "HomePage",
  data() {
    return {
      // 轮播图数据
      carouselItems: [
        { image: require("../../assets/hUlLAP.png") },
        { image: require("../../assets/hUlH0I.png") },
        { image: require("../../assets/hUlOtf.jpg") }
      ],

      // 功能模块数据
      modules: [
        {
          title: "基础信息管理",
          icon: "el-icon-setting",
          path: "/info/basic"
        },
        {
          title: "药品信息管理",
          icon: "el-icon-medicine-box",
          path: "/info/drug"
        },
        {
          title: "医保政策管理",
          icon: "el-icon-document",
          path: "/policy/insurance"
        },
        {
          title: "医生信息管理",
          icon: "el-icon-user",
          path: "/doctors"
        },
        {
          title: "医药公司政策",
          icon: "el-icon-office-building",
          path: "/policy/company"
        },
        {
          title: "必备材料管理",
          icon: "el-icon-tickets",
          path: "/materials"
        }
      ]
    };
  },
  methods: {
    navigateTo(path) {
      this.$router.push(path);
    }
  }
};
</script>

<style scoped>.home-container {
  padding: 20px;
}

.home-carousel {
  margin-bottom: 30px;
}

.carousel-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 8px;
}

.module-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 20px;
  margin-top: 20px;
}

.module-card {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
  cursor: pointer;
  transition: transform 0.2s;
  text-align: center;
}

.module-card:hover {
  transform: translateY(-5px);
}

.module-icon {
  font-size: 48px;
  color: #2abeb2;
  margin-bottom: 15px;
}

.module-title {
  font-size: 16px;
  color: #333;
}
</style>