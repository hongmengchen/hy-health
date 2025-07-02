<!-- 医药公司政策管理 -->
<template>
  <el-container>
    <!-- 头部区域 -->
    <el-header height="76px">
      <h2 v-if="hasRole">医药公司政策管理</h2>
      <h2 v-else>医药公司政策查询</h2>
      <!-- 面包屑导航区域 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item v-if="hasRole">医药公司政策管理</el-breadcrumb-item>
        <el-breadcrumb-item v-else>医药公司政策查询</el-breadcrumb-item>
      </el-breadcrumb>
    </el-header>
    <!-- 主体内容区域 -->
    <el-main>
      <!--header -->
      <div class="main-title">
        <h3>医药公司政策列表</h3>
        <button

            class="new-add"
            @click="addFormVisible = true"
            v-if="hasRole"
        ></button
        >
      </div>
      <!-- 搜索 -->
      <el-row :gutter="20">
        <el-col :span="23" class="search-col">
          <el-input
              placeholder="查询（输入要查询的关键字）"
              clearable
              size="small"
              v-model="keyword"
              @input="handelQuery"
          >
          </el-input>
        </el-col>
      </el-row>

      <!-- 表格 -->
      <el-table
          :data="tableData.list"
          stripe
          :default-sort="{ prop: 'date', order: 'descending' }"
          max-height="375"
          highlight-current-row
      >
        <el-table-column type="expand">
          <template v-slot="scope">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="医药公司政策内容">
                <span>{{ scope.row.message }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column prop="drugCompanyModel.companyName" label="公司名称"/>
        <el-table-column prop="id" label="政策编号" sortable/>
        <el-table-column prop="title" label="政策名称"/>
        <el-table-column prop="updateTime" label="发布时间" sortable/>
        <el-table-column label="操作" v-if="hasRole">
          <template v-slot="scope">
            <button

                class="table-btn-delete"
                @click="handleDeleteCompanyPolicy(scope.row.id, scope.row.title)"
            ></button>
            <button
                class="table-btn-update"
                @click="
                handleModifyFormVisible(
                  scope.row.id,
                  scope.row.title,
                  scope.row.message,
                  scope.row.drugCompanyModel.companyId
                )
              "
            ></button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <div class="pagination">
        <pagination
            v-model:current-page="currentPage"
            :layout="'total,prev,pager,next,jumper'"
            :total="tableData.total"
            v-model:page-size="pageSize"
            @currentChange="handleCurrentChange($event)"
            @update:page="currentPage = $event"
        ></pagination>
      </div>
    </el-main>
    <!-- 点击新增后的弹窗 -->
    <el-dialog
        title="新增医药公司政策"
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
        <el-form-item label="政策名称" prop="title" :rules="rules.nameRules">
          <el-input
              v-model.trim="addForm.title"
              autocomplete="off"
              required
          ></el-input>
        </el-form-item>
        <el-form-item label="政策内容" prop="message" :rules="rules.infoRules">
          <el-input
              v-model.trim="addForm.message"
              autocomplete="off"
              required
              autosize
              type="textarea"
          ></el-input>
        </el-form-item>
        <el-form-item
            label="生效公司"
            prop="companyId"
            :rules="rules.requiredRules"
        >
          <el-select v-model="addForm.companyId" placeholder="请选择生效公司">
            <el-option
                v-for="company in companyInfo.list"
                :key="company.companyId"
                :label="company.companyName"
                :value="company.companyId"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="addFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleAddCompanyPolicy('addForm')"
          >确 定
          </el-button
          >
        </div>
      </template>
    </el-dialog>
    <!-- 点击修改后的弹窗 -->
    <el-dialog
        title="修改医药公司政策信息"
        v-model:visible="modifyFormVisible"
        :modal-append-to-body="false"
        @close="handleModifyClose"
    >
      <el-form
          :model="modifyForm"
          hide-required-asterisk
          ref="modifyForm"
          label-width="110px"
      >
        <el-form-item label="政策名称" prop="title" :rules="rules.nameRules">
          <el-input
              v-model.trim="modifyForm.title"
              autocomplete="off"
              required
          />
        </el-form-item>
        <el-form-item label="政策内容" prop="message" :rules="rules.infoRules">
          <el-input
              v-model.trim="modifyForm.message"
              autocomplete="off"
              required
              autosize
              type="textarea"
          />
        </el-form-item>
        <el-form-item
            label="生效公司"
            prop="companyId"
            :rules="rules.requiredRules"
        >
          <el-select
              v-model="modifyForm.companyId"
              placeholder="请选择生效公司"
          >
            <el-option
                v-for="company in companyInfo.list"
                :key="company.companyId"
                :label="company.companyName"
                :value="company.companyId"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="modifyFormVisible = false">取 消</el-button>
          <el-button
              type="primary"
              @click="handleModifyMedicalPolicy('modifyForm')"
          >确 定
          </el-button
          >
        </div>
      </template>
    </el-dialog>
  </el-container>
</template>

<script>
import Pagination from "../../components/Pagination";
import {mapGetters} from "vuex";
import rules from "../../utils/validator";
import dayjs from "dayjs";

export default {
  name: "CompanyPolicy",
  components: {
    Pagination,
  },
  data() {
    return {
      currentPage: 1,
      pageSize: 5, // 每页的数据条数
      keywordDefault: "",
      addFormVisible: false, // 控制新增公司页面的显示
      addForm: {
        companyId: "",
        message: "",
        title: "",
      },
      modifyFormVisible: false,
      modifyForm: {
        companyId: "",
        id: "",
        message: "",
        title: "",
      },
      rules, // 封装好的表单验证
    };
  },
  methods: {
    // 切换分页及首次进入获取数据
    getCompanyPolicyInfo() {
      this.$store.dispatch("companyPolicyInfoManage/getCompanyPolicyInfo", {
        pn: this.currentPage,
        size: this.pageSize,
      });
    },
    //当前页改变时触发,跳转其他页
    handleCurrentChange(event) {
      this.currentPage = event.page;
      if (this.keyword.length) {
        this.handelQuery(this.keyword);
      } else {
        this.getCompanyPolicyInfo();
      }
    },
    // 通过关键字查询数据
    handelQuery(keyword) {
      this.$store.dispatch("companyPolicyInfoManage/getCompanyPolicyInfo", {
        pn: this.currentPage,
        size: this.pageSize,
        keyword,
      });
    },
    //新增公司
    handleAddCompanyPolicy(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.addFormVisible = false;
          this.$store.dispatch("companyPolicyInfoManage/addCompanyPolicy", {
            companyId: this.addForm.companyId,
            message: this.addForm.message,
            title: this.addForm.title,
            size: this.pageSize,
          });
        } else {
          this.$message({
            message: "请检查输入的内容是否合规",
            type: "warning",
          });
          return false;
        }
      });
    },
    // 删除
    handleDeleteCompanyPolicy(id, title) {
      this.$confirm(`确定要删除“${title}”的相关信息吗？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
          .then(() => {
            this.$store.dispatch("companyPolicyInfoManage/deleteCompanyPolicy", {
              id,
              pn: this.currentPage,
              size: this.pageSize,
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除",
            });
          });
    },
    // 控制修改表单弹出
    handleModifyFormVisible(id, title, message, companyId) {
      this.modifyForm = {
        id,
        title,
        message,
        companyId,
      };
      this.modifyFormVisible = true;
    },
    // 修改
    handleModifyMedicalPolicy(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.modifyFormVisible = false;
          this.$store.dispatch(
              "companyPolicyInfoManage/modifyCompanyPolicyInfo",
              {
                id: this.modifyForm.id,
                companyId: this.modifyForm.companyId,
                title: this.modifyForm.title,
                updateTime: dayjs().format("YYYY-MM-DD"),
                message: this.modifyForm.message,
                pn: this.currentPage,
                size: this.pageSize,
              }
          );
        } else {
          this.$message({
            message: "请检查输入的内容是否合规",
            type: "warning",
          });
          return false;
        }
      });
    },
    // 每次关闭表单的时候重置表单
    handleAddClose() {
      this.addForm = {};
      this.$refs.addForm.resetFields();
    },
    handleModifyClose() {
      this.$refs.modifyForm.clearValidate();
    },
  },
  mounted() {
    this.getCompanyPolicyInfo(); // 首次渲染
    this.$store.dispatch("companyInfoManage/getAllCompanyInfo");
  },
  computed: {
    ...mapGetters({
      tableData: "companyPolicyInfo",
      companyInfo: "companyInfo",
    }), //后端返回的数据
    keyword: {
      get() {
        return this.keywordDefault;
      },
      set(val) {
        this.keywordDefault = val;
      },
    },
  },
};
</script>
<style lang="less" scoped>
@import "../../style/infoManage.less";
</style>