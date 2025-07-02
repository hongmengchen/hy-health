<template>
  <el-container>
    <!-- 头部区域 -->
    <el-header height="76px">
      <h2>数据面板</h2>
      <!-- 面包屑导航区域 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>数据面板</el-breadcrumb-item>
      </el-breadcrumb>
    </el-header>
    <!-- 主体内容区域 -->
    <el-main>
      <div class="dashboard-container">
        <!-- 四个图标显示 -->
        <div class="number_container">
          <div class="square">
            <div icon-class="doc" class="icon" />
            <div class="title">
              <div class="subtitle">医师人数</div>
              <div class="number">{{ doctors }}</div>
            </div>
          </div>

          <div class="square">
            <div icon-class="bag" class="icon" />
            <div class="title">
              <div class="subtitle">药物种类</div>
              <div class="number">{{ drugs }}</div>
            </div>
          </div>

          <div class="square">
            <div icon-class="operation" class="icon" />
            <div class="title">
              <div class="subtitle">合作企业</div>
              <div class="number">{{ companies }}</div>
            </div>
          </div>

          <div class="square">
            <div icon-class="patient" class="icon" />
            <div class="title">
              <div class="subtitle">入驻药店</div>
              <div class="number">{{ sales }}</div>
            </div>
          </div>
        </div>

        <div class="father_chart"></div>

        <!-- 病人数量+图标 -->

        <!-- 医生数量 -->

        <!-- 药物种类 -->
        <div id="histogram" class="histogram"></div>
        <!-- 销售城市量柱状图表 -->
        <div class="chartcontainer">
          <div class="rectangle">
            <h1 style="color: gray">医院科室</h1>
            <div id="piechart" class="piechart"></div>
          </div>

          <div class="rectangle">
            <h1 style="color: gray">最新政策</h1>
            <div style="float: left; width: 100%">
              <el-table :data="materials" stripe style="width: 100%">
                <el-table-column prop="notice" label="最新医保政策" width="450">
                  <template v-slot="scope">
                    <div>{{ scope.row.notice }}</div>
                  </template>
                </el-table-column>

                <el-table-column prop="date" width="100">
                  <template v-slot:header>
                    <a href="?#/manage/medical/policy" target="_blank">More&lt;&lt;</a>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div style="float: left; width: 100%">
              <el-table :data="policys" stripe style="width: 100%">
                <el-table-column prop="notice" label="最新医药公司政策" width="450">
                  <template v-slot="scope">
                    <div>{{ scope.row.notice }}</div>
                  </template>
                </el-table-column>

                <el-table-column prop="date" width="100">
                  <template v-slot:header >
                    <a href="?#/manage/company/policy" target="_blank">More&lt;&lt;</a>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
        </div>

        <!-- 医生人数表格 -->
      </div>
    </el-main>
  </el-container>
</template>

<script>
import { mapGetters } from "vuex";
import * as echarts from "echarts";
import "echarts/extension/bmap/bmap";
import { getAllDashboardInfo } from "@/api/admin/dashboardApi"
// import { getAllDoctors, getDoctorKind } from '@/api/doctor'
// import { getAllDrugs } from '@/api/drug'
// import { getAllSales } from '@/api/sale'
// import { getAllCompany } from '@/api/company_policy'

export default {
  name: "DataDashboard",
  computed: {
    ...mapGetters(["name"]),
  },
  data() {
    return {
      doctors: 10,
      drugs: 10,
      companies: 10,
      sales: 10,
      docLevelType:["主任医师","普通医师","实习医师"],
      docLevel:[],
      picData:[],

      materials:[
        // {
        //   notice:'医保卡报销额度变高了',
        //   date:'2022-10-5'
        // }
      ],
      policys:[

      ]
    };
  },
  mounted() {
    this.initData();


    // this.initMap()
    // this.barchart()
  },
  created() {},
  methods: {

    initData(){
      getAllDashboardInfo().then((res)=>{
      let dashboardData = res.data.data.data;
      console.log(dashboardData)
      this.doctors = dashboardData.doctorNumb
      this.drugs = dashboardData.drugNumb
      this.companies  = dashboardData.companyNumb
      this.sales = dashboardData.saleNumb
      console.log(dashboardData.docLevel)
      this.docLevel.push(dashboardData.docLevel.l1)
      this.docLevel.push(dashboardData.docLevel.l2)
      this.docLevel.push(dashboardData.docLevel.l3)
      this.histogram();
      let treatMap = dashboardData.treatMap
      for(var item in treatMap){
        this.picData.push({
          name:item,
          value:treatMap[item]
        })
      }
      this.piechart();
      let materialsList = dashboardData.materials
      materialsList.forEach(element => {
        if(element.message.length>30){
          element.message = element.message.substr(0,29)+'···'
          console.log(element.message)
        }
        this.materials.push({
          notice:element.message,
          date:element.updateTime
        })
      });
      let policyList = dashboardData.policys
      policyList.forEach(element => {
        if(element.message.length>30){
          element.message = element.message.substr(0,29)+'···'
          console.log(element.message)
        }
        this.policys.push({
          notice:element.message,
          date:element.updateTime
        })
      });
      // policys
      console.log(this.picData)

    })
    },
    histogram() {
      var chartDom = document.getElementById("histogram");
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        title: {
          text: "医生职级",
          left: "60px",
          padding: [10, 10, 10, 0],
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
          },
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true,
        },
        xAxis: {
          type: "category",
          data: this.docLevelType,
        },
        yAxis: {
          type: "value",
        },
        series: [
          {
            data: [
              {
                value: this.docLevel[0],
                itemStyle: {
                  color: "#2ecc71",
                },
              },
              {
                value: this.docLevel[1],
                itemStyle: {
                  color: "#70a1ff",
                },
              },
              {
                value: this.docLevel[2],
                itemStyle: {
                  color: "#eccc68",
                },
              },
            ],
            type: "bar",
          },
        ],
      };

      option && myChart.setOption(option);
    },


    piechart() {
      var pieChart = echarts.init(document.getElementById("piechart"));

      var option = {
        title: {
          text: "",
        },
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b}: {c} ({d}%)",
        },
        legend: {
          orient: "vertical",
          left: "left",
        },
        series: [
          {
            name: "医师人数",
            type: "pie",
            radius: ["50%", "70%"],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: "center",
            },
            emphasis: {
              label: {
                show: true,
                fontSize: "30",
                fontWeight: "bold",
              },
            },
            labelLine: {
              show: false,
            },
            data: this.picData
          },
        ],
      };
      pieChart.clear();
      pieChart.setOption(option, true); //true是设置是否可动态更新数据
    },
  },
};
</script>

<style  scoped>
.el-container {
  height: 100%;
}
.el-container .el-header {
  background-color: #fff;
  border-bottom: 1px solid #e6e9eb;
  padding: 15px 20px 0 20px;
}
.el-container .el-header h2 {
  font-size: 20px;
  margin-bottom: 15px;
}
.el-container .el-main {
  box-sizing: border-box;
  margin: 20px 15px 10px 20px;
  background-color: #fff;
  border-top: 3px solid #e8ebed;
  padding: 0;
  /* overflow: hidden; */
}
.el-container .el-main .main-title {
  height: 35px;
  padding: 10px;
  border-bottom: 1px solid #e6e9eb;
  margin-bottom: 10px;
}
.el-container .el-main .main-title h3 {
  margin-top: 10px;
  float: left;
}
/* .el-container .el-main .main-title .new-add {
  float: right;
  background: url("../assets/add.png") no-repeat;
  background-size: 100%;
  height: 40px;
  width: 40px;
  margin-right: 20px;
} */
.el-container .el-main .search-col {
  margin-left: 20px;
  margin-bottom: 5px;
}
.el-container .el-table {
  padding: 0px 20px;
  border: 0;
}
.el-container .el-table .demo-table-expand {
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.el-container .el-table .demo-table-expand .el-form-item {
  margin: 0;
  flex: 1;
  display: flex;
  flex-direction: column;
}

.el-container .pagination {
  display: flex;
  justify-content: center;
  align-items: center;
}

.dashboard-container {
  margin: 10px;
  margin-left: 20px;
  height: 800px;
}
.dashboard-text {
  font-size: 30px;
  line-height: 46px;
}
.number_container {
  display: flex;
  justify-content: center;
  align-items: center;
  /* margin-top: 20px; */
  margin-bottom: 20px;
  height: 100px;
}
.square {
  padding: 20px;
  width: 285px;
  height: 50px;
  background-color: white;
  margin: 10px;
  border-radius: 15px;
  box-shadow: 0px 12px 26px rgba(16, 30, 115, 0.06);
}
.father_chart {
  display: flex;
  /* 将父级 div 设置为 flex 容器 */
  justify-content: center;
  /* 将子级 div 在主轴方向上居中对齐 */
}
.histogram {
  width: 1200px;
  height: 400px;
  margin: 0 auto;
  /* 将子级 div 的左右外边距设置为 auto */
  padding: 20px;
  border-radius: 15px;
  background-color: white;
  box-shadow: 0px 12px 26px rgba(16, 30, 115, 0.06);
}
.chartcontainer {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
}
.rectangle {
  padding: 20px 20px 20px 20px;
  width: 590px;
  height: 500px;
  border-radius: 15px;
  background-color: white;
  box-shadow: 0px 12px 26px rgba(16, 30, 115, 0.06);
  margin: 10px;
}
.piechart {
  /* margin-left: -10px; */
  margin-top: 40px;
  width: 600px;
  height: 400px;
}
.map {
  margin-left: -10px;
  width: 600px;
  height: 400px;
}
.icon {
  width: 50px;
  height: 50px;
  display: inline-block;
}
.title {
  display: inline-block;
  margin-left: 30px;
  vertical-align: top;
}
.subtitle {
  font-size: 18px;
  font-weight: 700;
  color: #25282b;
}
.number {
  margin-top: 12px;
  color: #336cfb;
  font-size: 22px;
  display: inline-block;
}
.variation {
  display: inline-block;
  margin-left: 15px;
  vertical-align: auto;
  color: #0deb48;
}
</style>