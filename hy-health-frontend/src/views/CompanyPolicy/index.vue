<template>
  <el-container>
    <el-header height="76px">
      <h2 v-if="hasRole">医药公司政策管理</h2>
      <h2 v-else>医药公司政策查询</h2>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item to="/">首页</el-breadcrumb-item>
        <el-breadcrumb-item v-if="hasRole">医药公司政策管理</el-breadcrumb-item>
        <el-breadcrumb-item v-else>医药公司政策查询</el-breadcrumb-item>
      </el-breadcrumb>
    </el-header>

    <el-main>
      <!-- 标题与新增按钮 -->
      <div class="main-title">
        <h3>医药公司政策列表</h3>
        <el-tooltip content="新增政策" placement="top">
          <button class="new-add" @click="addFormVisible = true" v-if="hasRole"/>
        </el-tooltip>
      </div>

      <!-- 搜索框 -->
      <div class="search-policy">
        <el-input
            v-model="keyword"
            @input="handleQuery"
            clearable
            size="small"
            placeholder="查询（输入要查询的关键字）"
        />
      </div>

      <!-- 表格 -->
      <el-table
          :data="tableData.list"
          stripe
          max-height="375"
          highlight-current-row
      >
        <el-table-column type="expand">
          <template #default="scope">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="企业政策内容">
                <span>{{ scope.row.message }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column prop="drugCompanyModel.companyName" label="公司名称"/>
        <el-table-column prop="id" label="政策编号" sortable/>
        <el-table-column prop="title" label="政策名称"/>
        <el-table-column prop="updateTime" label="发布时间" sortable/>
        <el-table-column v-if="hasRole" label="操作">
          <template #default="scope">
            <el-tooltip content="删除政策" placement="top">
              <button
                  class="table-btn-delete"
                  @click="handleDeleteCompanyPolicy(scope.row.id, scope.row.title)"
              />
            </el-tooltip>
            <el-tooltip content="编辑政策" placement="top">
              <button
                  class="table-btn-update"
                  @click="handleModifyFormVisible(
            scope.row.id,
            scope.row.title,
            scope.row.message,
            scope.row.drugCompanyModel.companyId
          )"
              />
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
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

    <!-- 新增弹窗 -->
    <el-dialog title="新增医药公司政策" v-model="addFormVisible" @closed="handleAddClose">
      <el-form :model="addForm" ref="addForm" label-width="110px" hide-required-asterisk>
        <el-form-item label="政策名称" prop="title" :rules="rules.nameRules">
          <el-input v-model.trim="addForm.title" required/>
        </el-form-item>
        <el-form-item label="政策内容" prop="message" :rules="rules.infoRules">
          <el-input v-model.trim="addForm.message" type="textarea" autosize required/>
        </el-form-item>
        <el-form-item label="生效公司" prop="companyId" :rules="rules.requiredRules">
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
          <el-button type="primary" @click="handleAddCompanyPolicy">确 定</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 修改弹窗 -->
    <el-dialog title="修改医药公司政策信息" v-model="modifyFormVisible" @closed="handleModifyClose">
      <el-form :model="modifyForm" ref="modifyForm" label-width="110px" hide-required-asterisk>
        <el-form-item label="政策名称" prop="title" :rules="rules.nameRules">
          <el-input v-model.trim="modifyForm.title" required/>
        </el-form-item>
        <el-form-item label="政策内容" prop="message" :rules="rules.infoRules">
          <el-input v-model.trim="modifyForm.message" type="textarea" autosize required/>
        </el-form-item>
        <el-form-item label="生效公司" prop="companyId" :rules="rules.requiredRules">
          <el-select v-model="modifyForm.companyId" placeholder="请选择生效公司">
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
          <el-button type="primary" @click="handleModifyMedicalPolicy">确 定</el-button>
        </div>
      </template>
    </el-dialog>
    <!-- 放置AI助手组件 -->
    <AiAssistant/>
  </el-container>
</template>

<script>
import {mapGetters} from "vuex";
import rules from "../../utils/validator";
import dayjs from "dayjs";
import AiAssistant from "@/components/AiAssistant.vue";

export default {
  name: "CompanyPolicyManage",
  components: {AiAssistant},
  data() {
    return {
      currentPage: 1,
      pageSize: 5,
      keywordDefault: "",
      addFormVisible: false,
      addForm: {companyId: "", title: "", message: ""},
      modifyFormVisible: false,
      modifyForm: {id: "", companyId: "", title: "", message: ""},
      rules,
    };
  },
  computed: {
    ...mapGetters({
      tableData: "companyPolicyInfo",
      companyInfo: "companyInfo",
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
  methods: {
    getCompanyPolicyInfo(pn = this.currentPage) {
      this.$store.dispatch("companyPolicyInfoManage/getCompanyPolicyInfo", {
        pn,
        size: this.pageSize,
        keyword: this.keyword,
      });
    },
    handleCurrentChange(newPage) {
      this.currentPage = newPage;
      this.getCompanyPolicyInfo(newPage);
    },
    handleQuery() {
      this.currentPage = 1;
      this.getCompanyPolicyInfo(1);
    },
    handleAddCompanyPolicy() {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          this.addFormVisible = false;
          this.$store
              .dispatch("companyPolicyInfoManage/addCompanyPolicy", {
                ...this.addForm,
                size: this.pageSize,
              })
              .then(() => {
                this.currentPage = 1;
                this.getCompanyPolicyInfo(1);
              });
        }
      });
    },
    handleDeleteCompanyPolicy(id, title) {
      this.$confirm(`确定要删除“${title}”吗？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
          .then(() => {
            return this.$store.dispatch("companyPolicyInfoManage/deleteCompanyPolicy", {
              id,
              pn: this.currentPage,
              size: this.pageSize,
            });
          })
          .then(() => {
            this.getCompanyPolicyInfo();
          })
          .catch(() => {
            // 用户取消或其他异常，静默处理，避免控制台报错
          });
    },
    handleModifyFormVisible(id, title, message, companyId) {
      this.modifyForm = {id, companyId, title, message};
      this.modifyFormVisible = true;
    },
    handleModifyMedicalPolicy() {
      this.$refs.modifyForm.validate((valid) => {
        if (valid) {
          this.modifyFormVisible = false;
          this.$store
              .dispatch("companyPolicyInfoManage/modifyCompanyPolicyInfo", {
                ...this.modifyForm,
                updateTime: dayjs().format("YYYY-MM-DD"),
                pn: this.currentPage,
                size: this.pageSize,
              })
              .then(() => {
                this.getCompanyPolicyInfo();
              });
        }
      });
    },
    handleAddClose() {
      this.$refs.addForm.resetFields();
    },
    handleModifyClose() {
      this.$refs.modifyForm.resetFields();
    },
  },
  mounted() {
    this.getCompanyPolicyInfo();
    this.$store.dispatch("companyInfoManage/getAllCompanyInfo");
  },
};
</script>

<style lang="less" scoped>
@import "../../style/infoManage.less";

.main-title {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 10px;
}

.search-policy {
  background: #fff;
  padding: 10px 20px;
  margin-bottom: 20px;
}

.pagination {
  margin-top: 20px;
}

.new-add,
.table-btn-delete,
.table-btn-update {
  width: 36px;
  height: 36px;
  border: none;
  border-radius: 6px;
  background-repeat: no-repeat;
  background-position: center;
  background-size: 70%;
  cursor: pointer;
  transition: all 0.3s ease;
  margin-right: 6px;

  &:hover {
    transform: translateY(-3px) scale(1.05);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  }

  &:active {
    transform: scale(0.95);
  }
}
</style>
