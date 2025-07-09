<template>
  <el-container>
    <el-header height="76px">
      <h2>医药公司信息管理</h2>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item to="/">首页</el-breadcrumb-item>
        <el-breadcrumb-item>医药公司信息管理</el-breadcrumb-item>
      </el-breadcrumb>
    </el-header>

    <el-main>
      <div class="main-title">
        <h3>医药公司信息列表</h3>
        <!-- 新增按钮 -->
        <el-tooltip content="新增公司" placement="top">
          <button class="new-add" @click="addFormVisible = true" v-if="hasRole"/>
        </el-tooltip>
      </div>

      <el-row :gutter="20">
        <el-col :span="23" class="search-col">
          <keep-alive>
            <el-input
                placeholder="查询（输入要查询的公司名称）"
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
        <el-table-column prop="companyId" label="医药公司编号" sortable/>
        <el-table-column prop="companyName" label="公司名称"/>
        <el-table-column prop="companyPhone" label="公司电话"/>
        <el-table-column prop="companyOperation" label="操作" v-if="hasRole">
          <template #default="scope">
            <el-tooltip content="删除公司" placement="top">
              <button class="table-btn-delete"
                      @click="handleDeleteCompany(scope.row.companyId, scope.row.companyName)"/>
            </el-tooltip>
            <el-tooltip content="编辑公司信息" placement="top">
              <button class="table-btn-update"
                      @click="handleModifyFormVisible(scope.row.companyId, scope.row.companyName, scope.row.companyPhone)"/>
            </el-tooltip>
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

    <!-- 新增弹窗 -->
    <el-dialog title="新增医药公司" v-model="addFormVisible" @closed="handleAddClose">
      <el-form :model="addForm" ref="addForm" label-width="110px" hide-required-asterisk>
        <el-form-item label="公司名称" prop="companyName" :rules="rules.nameRules">
          <el-input v-model.trim="addForm.companyName" autocomplete="off" required/>
        </el-form-item>
        <el-form-item label="公司电话" prop="companyPhone" :rules="rules.phoneRules">
          <el-input v-model.number="addForm.companyPhone" autocomplete="off" required/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="addFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleAddCompany('addForm')">确 定</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 修改弹窗 -->
    <el-dialog title="修改医药公司信息" v-model="modifyFormVisible" @closed="handleModifyClose">
      <el-form :model="modifyForm" ref="modifyForm" label-width="110px" hide-required-asterisk>
        <el-form-item label="医药公司编号">
          <el-input v-model="modifyForm.companyId" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item label="公司名称" prop="companyName" :rules="rules.nameRules">
          <el-input v-model.trim="modifyForm.companyName" autocomplete="off" required/>
        </el-form-item>
        <el-form-item label="公司电话" prop="companyPhone" :rules="rules.phoneRules">
          <el-input v-model.number="modifyForm.companyPhone" autocomplete="off" required/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="modifyFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleModifyCompany('modifyForm')">确 定</el-button>
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
import AiAssistant from "@/components/AiAssistant.vue";

export default {
  name: "CompanyManage",
  components: {AiAssistant},
  data() {
    return {
      currentPage: 1,
      pageSize: 5,
      keywordDefault: "",
      addFormVisible: false,
      addForm: {
        companyName: "",
        companyPhone: "",
      },
      modifyFormVisible: false,
      modifyForm: {
        companyId: "",
        companyName: "",
        companyPhone: "",
      },
      rules,
    };
  },
  methods: {
    getCompanyInfo(pn = this.currentPage) {
      this.$store.dispatch("companyInfoManage/getCompanyInfo", {
        pn,
        size: this.pageSize,
        keyword: this.keyword,
      });
    },
    handleCurrentChange(newPage) {
      this.currentPage = newPage;
      this.getCompanyInfo(newPage);
    },
    handleQuery() {
      this.currentPage = 1;
      this.getCompanyInfo(1);
    },
    handleAddCompany(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.addFormVisible = false;
          this.$store.dispatch("companyInfoManage/addCompany", {
            companyName: this.addForm.companyName,
            companyPhone: this.addForm.companyPhone,
            size: this.pageSize,
          }).then(() => {
            this.currentPage = 1;
            this.getCompanyInfo(1);
          });
        } else {
          this.$message({message: "请检查输入的内容是否合规", type: "warning"});
        }
      });
    },
    handleDeleteCompany(companyId, companyName) {
      this.$confirm(`确定要删除“${companyName}”的相关信息吗？该操作会同时删除对应的公司政策`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$store.dispatch("companyInfoManage/deleteCompany", {
          companyId,
          pn: this.currentPage,
          size: this.pageSize,
          keyword: this.keyword,
        }).then(() => {
          this.getCompanyInfo();
        });
      }).catch(() => {
        this.$message({type: "info", message: "已取消删除"});
      });
    },
    handleModifyFormVisible(companyId, companyName, companyPhone) {
      this.modifyForm = {companyId, companyName, companyPhone};
      this.modifyFormVisible = true;
    },
    handleModifyCompany(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.modifyFormVisible = false;
          this.$store.dispatch("companyInfoManage/modifyCompanyInfo", {
            companyId: this.modifyForm.companyId,
            companyName: this.modifyForm.companyName,
            companyPhone: this.modifyForm.companyPhone,
            pn: this.currentPage,
            size: this.pageSize,
            keyword: this.keyword,
          }).then(() => {
            this.getCompanyInfo();
          });
        } else {
          this.$message({message: "请检查输入的内容是否合规", type: "warning"});
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
  },
  mounted() {
    this.getCompanyInfo();
  },
  computed: {
    ...mapGetters({
      tableData: "companyInfo",
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

<style scoped lang="less">
@import "../../style/infoManage.less";

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
