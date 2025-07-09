<template>
  <el-container>
    <el-header height="76px">
      <h2>城市信息管理</h2>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>城市信息管理</el-breadcrumb-item>
      </el-breadcrumb>
    </el-header>

    <el-main>
      <div class="main-title">
        <h3>城市列表</h3>
        <button class="new-add" @click="addFormVisible = true" v-if="hasRole"/>
      </div>

      <el-row :gutter="20">
        <el-col :span="23" class="search-col">
          <keep-alive>
            <el-input
                placeholder="查询（输入要查询的城市或省份）"
                size="small"
                v-model="keyword"
                @input="handleQuery"
            />
          </keep-alive>
        </el-col>
      </el-row>

      <el-table
          stripe
          :data="tableData.list"
          highlight-current-row
      >
        <el-table-column prop="cityNumber" label="城市编号" sortable/>
        <el-table-column prop="province" label="所属省"/>
        <el-table-column prop="city" label="城市名称"/>
        <el-table-column prop="cityOperation" label="操作" v-if="hasRole">
          <template #default="scope">
            <button
                class="table-btn-delete"
                @click="handleDeletecity(scope.row.cityId, scope.row.city)"
            />
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
            :current-page="currentPage"
            :page-size="pageSize"
            :total="tableData.total"
            layout="total, prev, pager, next, jumper"
            @current-change="handleCurrentChange"
        />
      </div>

    </el-main>

    <el-dialog
        title="新增城市"
        v-model="addFormVisible"
        @closed="handleAddClose"
    >
      <el-form :model="addForm" ref="addForm" hide-required-asterisk>
        <el-form-item
            label="城市名称"
            :label-width="formLabelWidth"
            prop="cityNumber"
            :rules="rules.requiredRules"
        >
          <el-cascader
              size="large"
              :options="options"
              v-model="addForm.cityNumber"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="addFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleAddcity('addForm')">确 定</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 放置AI助手组件 -->
    <AiAssistant />
  </el-container>
</template>

<script>
import AiAssistant from '@/components/AiAssistant.vue';
import {mapGetters} from "vuex";
import rules from "../../utils/validator";
import {provinceAndCityData} from "element-china-area-data";

export default {
  name: "CityManage",
  components: {
    AiAssistant,
  },
  data() {
    return {
      currentPage: 1,
      pageSize: 5,
      keywordDefault: "",
      addFormVisible: false,
      addForm: {
        cityNumber: [],
      },
      formLabelWidth: "110px",
      rules,
      options: provinceAndCityData,
    };
  },
  methods: {
    getCityInfo(pn = this.currentPage) {
      this.$store.dispatch("cityInfoManage/getCityInfo", {
        pn,
        size: this.pageSize,
        keyword: this.keyword,
      });
    },
    handleCurrentChange(newPage) {
      this.currentPage = newPage;
      this.getCityInfo(newPage);
    },
    handleQuery() {
      this.currentPage = 1;
      this.getCityInfo(1);
    },
    handleAddcity(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.addFormVisible = false;
          this.$store.dispatch("cityInfoManage/addCity", {
            cityNumber: this.addForm.cityNumber[1],
            size: this.pageSize,
          }).then(() => {
            this.currentPage = 1;
            this.getCityInfo(1);
          });
        } else {
          this.$message({
            message: "请检查输入的内容是否合规",
            type: "warning",
          });
        }
      });
    },
    handleDeletecity(cityId, cityName) {
      this.$confirm(
          `确定要删除“${cityName}”的相关信息吗？该操作会同时删除该城市相关的医保政策`,
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }
      ).then(() => {
        this.$store.dispatch("cityInfoManage/deleteCity", {
          cityId,
          pn: this.currentPage,
          size: this.pageSize,
          keyword: this.keyword,
        }).then(() => {
          this.getCityInfo();
        });
      }).catch(() => {
        this.$message({
          type: "info",
          message: "已取消删除",
        });
      });
    },
    handleAddClose() {
      this.$refs.addForm.resetFields();
    },
  },
  mounted() {
    this.getCityInfo();
  },
  computed: {
    ...mapGetters({
      tableData: "cityInfo",
    }),
    keyword: {
      get() {
        return this.keywordDefault;
      },
      set(val) {
        this.keywordDefault = val;
      },
    },
    hasRole() {
      const userInfo = JSON.parse(localStorage.getItem("userInfo"));
      return userInfo && userInfo.utype === "1"; // "1" 是管理员
    },
  },
};
</script>

<style lang="less" scoped>
@import "../../style/infoManage.less";
</style>
