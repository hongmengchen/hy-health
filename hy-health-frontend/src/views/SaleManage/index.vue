<template>
  <el-container>
    <el-header height="76px">
      <h2>销售地点管理</h2>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>销售地点管理</el-breadcrumb-item>
      </el-breadcrumb>
    </el-header>

    <el-main>
      <div class="main-title">
        <h3>销售地点列表</h3>
        <button class="new-add" @click="addFormVisible = true" v-if="hasRole" />
      </div>

      <el-row :gutter="20">
        <el-col :span="23" class="search-col">
          <keep-alive>
            <el-input
                placeholder="查询（输入要查询的药店名称）"
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
        <el-table-column prop="saleId" label="药店编号" sortable />
        <el-table-column prop="saleName" label="药店名称" />
        <el-table-column prop="salePhone" label="药店电话" />
        <el-table-column label="操作" v-if="hasRole">
          <template #default="scope">
            <button class="table-btn-delete" @click="handleDeleteSalePlace(scope.row.saleId, scope.row.saleName)" />
            <button class="table-btn-update" @click="handleModifyFormVisible(scope.row.saleId, scope.row.saleName, scope.row.salePhone)" />
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
            :current-page="currentPage"
            :page-size="pageSize"
            layout="total, prev, pager, next, jumper"
            :total="tableData.total"
            @current-change="handleCurrentChange"
        />
      </div>
    </el-main>

    <!-- 新增弹窗 -->
    <el-dialog title="新增销售地点" v-model="addFormVisible" @closed="handleAddClose">
      <el-form :model="addForm" ref="addForm" label-width="110px" hide-required-asterisk>
        <el-form-item label="药店名称" prop="saleName" :rules="rules.nameRules">
          <el-input v-model.trim="addForm.saleName" autocomplete="off" required />
        </el-form-item>
        <el-form-item label="药店电话" prop="salePhone" :rules="rules.phoneRules">
          <el-input v-model.number="addForm.salePhone" autocomplete="off" required />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="addFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleAddSalePlace('addForm')">确 定</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 修改弹窗 -->
    <el-dialog title="修改销售地点信息" v-model="modifyFormVisible" @closed="handleModifyClose">
      <el-form :model="modifyForm" ref="modifyForm" label-width="110px" hide-required-asterisk>
        <el-form-item label="药店编号">
          <el-input v-model="modifyForm.saleId" autocomplete="off" disabled />
        </el-form-item>
        <el-form-item label="药店名称" prop="saleName" :rules="rules.nameRules">
          <el-input v-model.trim="modifyForm.saleName" autocomplete="off" required />
        </el-form-item>
        <el-form-item label="药店电话" prop="salePhone" :rules="rules.phoneRules">
          <el-input v-model.number="modifyForm.salePhone" autocomplete="off" required />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="modifyFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleModifySalePlace('modifyForm')">确 定</el-button>
        </div>
      </template>
    </el-dialog>
    <!-- 放置AI助手组件 -->
    <AiAssistant />
  </el-container>
</template>

<script>
import { mapGetters } from "vuex";
import rules from "../../utils/validator";
import AiAssistant from "@/components/AiAssistant.vue";

export default {
  name: "SaleManage",
  components: {AiAssistant},
  data() {
    return {
      currentPage: 1,
      pageSize: 5,
      keywordDefault: "",
      addFormVisible: false,
      addForm: {
        saleName: "",
        salePhone: "",
      },
      modifyFormVisible: false,
      modifyForm: {
        saleId: "",
        saleName: "",
        salePhone: "",
      },
      rules,
    };
  },
  methods: {
    getSalePlaceInfo(pn = this.currentPage) {
      this.$store.dispatch("saleInfoManage/getSalePlaceInfo", {
        pn,
        size: this.pageSize,
        keyword: this.keyword,
      });
    },
    handleCurrentChange(newPage) {
      this.currentPage = newPage;
      this.getSalePlaceInfo(newPage);
    },
    handleQuery() {
      this.currentPage = 1;
      this.getSalePlaceInfo(1);
    },
    handleAddSalePlace(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.addFormVisible = false;
          this.$store
              .dispatch("saleInfoManage/addSalePlace", {
                saleName: this.addForm.saleName,
                salePhone: this.addForm.salePhone,
                size: this.pageSize,
              })
              .then(() => {
                this.currentPage = 1;
                this.getSalePlaceInfo(1);
              });
        } else {
          this.$message({ message: "请检查输入的内容是否合规", type: "warning" });
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
            this.$store
                .dispatch("saleInfoManage/deleteSalePlace", {
                  saleId,
                  pn: this.currentPage,
                  size: this.pageSize,
                  keyword: this.keyword,
                })
                .then(() => {
                  this.getSalePlaceInfo();
                });
          })
          .catch(() => {
            this.$message({ type: "info", message: "已取消删除" });
          });
    },
    handleModifyFormVisible(saleId, saleName, salePhone) {
      this.modifyForm = { saleId, saleName, salePhone };
      this.modifyFormVisible = true;
    },
    handleModifySalePlace(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.modifyFormVisible = false;
          this.$store
              .dispatch("saleInfoManage/modifySalePlaceInfo", {
                saleId: this.modifyForm.saleId,
                saleName: this.modifyForm.saleName,
                salePhone: this.modifyForm.salePhone,
                pn: this.currentPage,
                size: this.pageSize,
                keyword: this.keyword,
              })
              .then(() => {
                this.getSalePlaceInfo();
              });
        } else {
          this.$message({ message: "请检查输入的内容是否合规", type: "warning" });
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
    this.getSalePlaceInfo();
  },
  computed: {
    ...mapGetters({
      tableData: "salePlaceInfo",
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
</style>
