<template>
  <el-container style="height: 100vh;">
    <!-- 头部区域 -->
    <el-header height="76px">
      <h2 v-if="hasRole">
        {{ visualization ? "销售地点管理" : "数据面板" }}
      </h2>
      <h2 v-else>
        {{ visualization ? "销售地点查看" : "数据面板查看" }}
      </h2>

      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item v-if="hasRole">
          {{ visualization ? "销售地点管理" : "数据面板" }}
        </el-breadcrumb-item>
        <el-breadcrumb-item v-else>
          {{ visualization ? "销售地点查看" : "数据面板查看" }}
        </el-breadcrumb-item>
      </el-breadcrumb>
    </el-header>

    <!-- 主体内容 -->
    <el-main>
      <!-- 切换开关 -->
      <el-switch
          v-model="visualization"
          active-text="切换到销售地图"
          inactive-text="切换到数据面板"
          active-color="#13ce66"
          inactive-color="#409EFF"
          style="margin-bottom: 20px;"
      />

      <!-- 数据面板 -->
      <div v-show="!visualization" class="dashboard-container">
        <!-- 你的数据面板完整内容开始 -->
        <div class="number_container">
          <div class="square">
            <div icon-class="doc" class="icon"/>
            <div class="title">
              <div class="subtitle">医师人数</div>
              <div class="number">{{ doctors }}</div>
            </div>
          </div>
          <div class="square">
            <div icon-class="bag" class="icon"/>
            <div class="title">
              <div class="subtitle">药物种类</div>
              <div class="number">{{ drugs }}</div>
            </div>
          </div>
          <div class="square">
            <div icon-class="operation" class="icon"/>
            <div class="title">
              <div class="subtitle">合作企业</div>
              <div class="number">{{ companies }}</div>
            </div>
          </div>
          <div class="square">
            <div icon-class="patient" class="icon"/>
            <div class="title">
              <div class="subtitle">入驻药店</div>
              <div class="number">{{ sales }}</div>
            </div>
          </div>
        </div>
        <div class="father_chart"></div>
        <div id="histogram" class="histogram"></div>
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
                  <template v-slot:header>
                    <a href="?#/manage/company/policy" target="_blank">More&lt;&lt;</a>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
        </div>
        <!-- 你的数据面板完整内容结束 -->
      </div>

      <!-- 销售地图 -->
      <div v-show="visualization" style="height: 700px;">
        <el-button
            type="primary"
            style="margin-bottom: 20px"
            @click="handleAdd"
            v-if="hasRole"
        >
          新增地点
        </el-button>
        <div
            id="mapContainer"
            style="width: 100%; height: 650px; border-radius: 10px; box-shadow: 0 0 10px #ccc;"
        ></div>

        <!-- 新增销售地点弹窗 -->
        <el-dialog
            title="新增销售地点"
            v-model:visible="addFormVisible"
            :modal-append-to-body="false"
            @close="handleAddClose"
        >
          <el-form
              :model="addForm"
              hide-required-asterisk
              ref="addForm"
              label-width="110px"
          >
            <el-form-item label="药店名称" prop="saleName" :rules="rules.nameRules">
              <el-input v-model.trim="addForm.saleName" autocomplete="off" required/>
            </el-form-item>
            <el-form-item label="药店电话" prop="salePhone" :rules="rules.phoneRules">
              <el-input v-model.number="addForm.salePhone" autocomplete="off" required/>
            </el-form-item>
          </el-form>
          <template #footer>
            <div class="dialog-footer">
              <el-button @click="addFormVisible = false">取消</el-button>
              <el-button type="primary" @click="handleAddSalePlace('addForm')">确定</el-button>
            </div>
          </template>
        </el-dialog>

        <!-- 修改销售地点弹窗 -->
        <el-dialog
            title="修改销售地点信息"
            :visible="modifyFormVisible"
            :modal-append-to-body="false"
            @close="handleModifyClose"
        >
          <el-form
              :model="modifyForm"
              hide-required-asterisk
              ref="modifyForm"
              label-width="110px"
          >
            <el-form-item label="药店编号">
              <el-input v-model="modifyForm.saleId" autocomplete="off" disabled/>
            </el-form-item>
            <el-form-item label="药店名称" prop="saleName" :rules="rules.nameRules">
              <el-input v-model.trim="modifyForm.saleName" autocomplete="off" required/>
            </el-form-item>
            <el-form-item label="药店电话" prop="salePhone" :rules="rules.phoneRules">
              <el-input v-model.number="modifyForm.salePhone" autocomplete="off" required/>
            </el-form-item>
          </el-form>
          <template #footer>
            <div class="dialog-footer">
              <el-button @click="modifyFormVisible = false">取消</el-button>
              <el-button type="primary" @click="handleModifySalePlace('modifyForm')">确定</el-button>
            </div>
          </template>
        </el-dialog>
      </div>
    </el-main>
    <!-- 放置AI助手组件 -->
    <AiAssistant />
  </el-container>
</template>

<script>
import {mapGetters} from "vuex";
import rules from "../../utils/validator";
import * as echarts from "echarts";
import AMapLoader from "@amap/amap-jsapi-loader";
import axios from "axios";
import {getAllDashboardInfo} from "@/api/admin/dashboardApi";
import AiAssistant from "@/components/AiAssistant.vue";

export default {
  name: "HomePage",
  components: {AiAssistant},
  data() {
    return {
      // 切换控制
      visualization: false, // false显示数据面板，true显示销售地图

      // --------- 数据面板相关 ----------
      doctors: 0,
      drugs: 0,
      companies: 0,
      sales: 0,
      docLevelType: ["主任医师", "普通医师", "实习医师"],
      docLevel: [],
      picData: [],
      materials: [],
      policys: [],

      // --------- 销售地图相关 ----------
      currentPage: 1,
      pageSize: 5,
      keywordDefault: "",
      addFormVisible: false,
      addForm: {
        saleName: "",
        salePhone: "",
        lat: 0,
        lng: 0,
      },
      modifyFormVisible: false,
      modifyForm: {
        saleId: "",
        saleName: "",
        salePhone: "",
      },
      rules,
      map: null,
      addStatus: 0,
      markers: [],
      address: "",

      hasRole: true, // 这里可根据权限动态赋值
    };
  },
  computed: {
    ...mapGetters({
      tableData: "salePlaceInfo",
      mapData: "saleAllPlaceInfo",
    }),
    keyword: {
      get() {
        return this.keywordDefault;
      },
      set(val) {
        this.keywordDefault = val;
      },
    },
  },
  watch: {
    visualization(val) {
      if (val) {
        // 切换到销售地图时，延迟加载地图及数据
        this.loadMap();
        setTimeout(() => {
          this.refreshMap();
        }, 1000);
      }
    },
  },
  mounted() {
    this.initData();
    this.getSalePlaceInfo();
    this.getAllSalePlaceInfo();
  },
  methods: {
    // -------- 数据面板方法 --------
    initData() {
      getAllDashboardInfo().then((res) => {
        let dashboardData = res.data.data.data;
        this.doctors = dashboardData.doctorNumb;
        this.drugs = dashboardData.drugNumb;
        this.companies = dashboardData.companyNumb;
        this.sales = dashboardData.saleNumb;
        this.docLevel = [
          dashboardData.docLevel.l1,
          dashboardData.docLevel.l2,
          dashboardData.docLevel.l3,
        ];
        this.prepareHistogram();
        this.picData = Object.entries(dashboardData.treatMap).map(([name, value]) => ({name, value}));
        this.preparePieChart();

        this.materials = dashboardData.materials.map((ele) => ({
          notice: ele.message.length > 30 ? ele.message.slice(0, 29) + "···" : ele.message,
          date: ele.updateTime,
        }));

        this.policys = dashboardData.policys.map((ele) => ({
          notice: ele.message.length > 30 ? ele.message.slice(0, 29) + "···" : ele.message,
          date: ele.updateTime,
        }));
      });
    },
    prepareHistogram() {
      const chartDom = document.getElementById("histogram");
      const myChart = echarts.init(chartDom);
      const option = {
        title: {text: "医生职级", left: "60px", padding: [10, 10, 10, 0]},
        tooltip: {trigger: "axis", axisPointer: {type: "shadow"}},
        grid: {left: "3%", right: "4%", bottom: "3%", containLabel: true},
        xAxis: {type: "category", data: this.docLevelType},
        yAxis: {type: "value"},
        series: [
          {
            data: [
              {value: this.docLevel[0], itemStyle: {color: "#2ecc71"}},
              {value: this.docLevel[1], itemStyle: {color: "#70a1ff"}},
              {value: this.docLevel[2], itemStyle: {color: "#eccc68"}},
            ],
            type: "bar",
          },
        ],
      };
      myChart.setOption(option);
    },
    preparePieChart() {
      const pieChart = echarts.init(document.getElementById("piechart"));
      const option = {
        tooltip: {trigger: "item", formatter: "{a} <br/>{b}: {c} ({d}%)"},
        legend: {orient: "vertical", left: "left"},
        series: [
          {
            name: "医师人数",
            type: "pie",
            radius: ["50%", "70%"],
            avoidLabelOverlap: false,
            label: {show: false, position: "center"},
            emphasis: {label: {show: true, fontSize: 30, fontWeight: "bold"}},
            labelLine: {show: false},
            data: this.picData,
          },
        ],
      };
      pieChart.clear();
      pieChart.setOption(option, true);
    },

    // -------- 销售地图方法 --------
    handleAdd() {
      this.addStatus = 1;
      this.$message({
        message: "请点击地图上的位置",
        type: "warning",
      });
    },
    creatLocation(lng, lat) {
      let url = `https://restapi.amap.com/v3/geocode/regeo?key=9cd46347d210aa0e244f85fea9aa0015&output=json&location=${lng},${lat}`;
      axios
          .get(url)
          .then((res) => {
            this.addForm.lat = lat;
            this.addForm.lng = lng;
            this.address = res.data.regeocode.formatted_address;
            this.addFormVisible = true;
            this.$message({
              showClose: true,
              message: "位置选择成功，请输入详细信息",
              type: "success",
            });
          })
          .catch(console.log);
    },
    handleModifyFormVisible(saleId, saleName, salePhone) {
      this.modifyForm = {saleId, saleName, salePhone};
      this.modifyFormVisible = true;
    },
    refreshMap() {
      if (!this.map) return;
      if (this.markers.length) {
        this.markers.forEach((m) => this.map.remove(m));
      }
      this.markers = [];
      this.mapData.list.forEach((element) => {
        const marker = new window.AMap.Marker({
          title: element.saleName,
          position: [element.lng, element.lat],
        });
        marker.on("click", () => {
          this.handleModifyFormVisible(element.saleId, element.saleName, element.salePhone);
        });
        this.map.add(marker);
        this.markers.push(marker);
      });
    },
    getSalePlaceInfo() {
      this.$store.dispatch("saleInfoManage/getSalePlaceInfo", {
        pn: this.currentPage,
        size: this.pageSize,
      });
    },
    getAllSalePlaceInfo() {
      this.$store.dispatch("saleInfoManage/getAllSalePlaceInfo", {
        pn: this.currentPage,
        size: this.pageSize,
      });
    },
    handleCurrentChange(event) {
      this.currentPage = event.page;
      if (this.keyword.length) this.handelQuery(this.keyword);
      else this.getSalePlaceInfo();
    },
    handelQuery(keyword) {
      this.$store.dispatch("saleInfoManage/getSalePlaceInfo", {
        pn: this.currentPage,
        size: this.pageSize,
        keyword,
      });
    },
    handleAddSalePlace(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.addFormVisible = false;
          this.$store.dispatch("saleInfoManage/addSalePlace", {
            saleName: this.addForm.saleName,
            salePhone: this.addForm.salePhone,
            lng: this.addForm.lng,
            lat: this.addForm.lat,
            address: this.address,
            size: this.pageSize,
          });
          setTimeout(() => {
            this.refreshMap();
          }, 2000);
        } else {
          this.$message({message: "请检查输入的内容是否合规", type: "warning"});
          return false;
        }
      });
    },
    handleDeleteSalePlace(saleId, saleName) {
      this.$confirm(`确定要删除“${saleName}”的相关信息吗？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
          .then(() => {
            this.$store.dispatch("saleInfoManage/deleteSalePlace", {
              saleId,
              pn: this.currentPage,
              size: this.pageSize,
              keyword: this.keyword,
            });
          })
          .catch(() => {
            this.$message({type: "info", message: "已取消删除"});
          });
    },
    handleModifySalePlace(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.modifyFormVisible = false;
          this.$store.dispatch("saleInfoManage/modifySalePlaceInfo", {
            saleId: this.modifyForm.saleId,
            saleName: this.modifyForm.saleName,
            salePhone: this.modifyForm.salePhone,
            pn: this.currentPage,
            size: this.pageSize,
            keyword: this.keyword,
            isAll: this.visualization,
          });
          setTimeout(() => {
            this.refreshMap();
          }, 2000);
        } else {
          this.$message({message: "请检查输入的内容是否合规", type: "warning"});
          return false;
        }
      });
    },
    handleAddClose() {
      this.addForm = {};
      this.$refs.addForm.resetFields();
    },
    handleModifyClose() {
      this.$refs.modifyForm.resetFields();
    },
    loadMap() {
      if (this.map) return; // 避免重复加载
      AMapLoader.load({
        key: "5d8f45224495953b8b0a5b1bca67a38d",
        version: "2.0",
        plugins: [],
      })
          .then((AMap) => {
            this.map = new AMap.Map("mapContainer", {
              mapStyle: "amap://styles/whitesmoke",
              zoom: 11,
              center: [104.06707, 30.660842],
              plugins: ["AMap.Geocoder", "AMap.AutoComplete"],
            });
            this.map.on("click", (e) => {
              if (this.addStatus === 1) {
                this.creatLocation(e.lnglat.getLng(), e.lnglat.getLat());
                this.addStatus = 0; // 选点完成，重置状态
              }
            });
          })
          .catch(console.log);
    },
  },
};
</script>

<style scoped>
.el-header h2 {
  font-size: 20px;
  margin-bottom: 15px;
  font-weight: 400; /* 根据原样式调节 */
  color: #333; /* 如果原来颜色是默认黑色 */
}

.dashboard-container {
  margin: 10px 20px;
  height: 800px;
}

/* 你原有的样式继续放这里 */
.number_container {
  display: flex;
  justify-content: center;
  align-items: center;
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

.histogram {
  width: 1200px;
  height: 400px;
  margin: 0 auto;
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
  padding: 20px;
  width: 590px;
  height: 500px;
  border-radius: 15px;
  background-color: white;
  box-shadow: 0px 12px 26px rgba(16, 30, 115, 0.06);
  margin: 10px;
}

.piechart {
  margin-top: 40px;
  width: 600px;
  height: 400px;
}

/* 销售地图切换开关样式 */
.el-switch {
  margin-bottom: 20px;
}

/* 你可以根据需要把销售地图的样式再细化 */
</style>
