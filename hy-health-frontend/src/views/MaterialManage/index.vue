<!-- 必备材料管理 -->
<template>
  <el-container>
    <!-- 头部区域 -->
    <el-header height="76px">
      <h2 v-if="hasRole">必备材料管理</h2>
      <h2 v-else>必备材料查询</h2>

      <!-- 面包屑导航区域 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item v-if="hasRole">必备材料管理</el-breadcrumb-item>
        <el-breadcrumb-item v-else>必备材料查询</el-breadcrumb-item>
      </el-breadcrumb>
    </el-header>

    <!-- 主体内容区域 -->
    <el-main>
      <!--header -->
      <div class="main-title">
        <h3>必备材料列表</h3>
        <button
            class="new-add"
            @click="addFormVisible = true"
            v-if="hasRole"
        />
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
        <el-table-column prop="id" label="材料编号" sortable/>
        <el-table-column prop="title" label="材料标题"/>
        <el-table-column prop="message" label="材料内容" min-width="400"/>
        <el-table-column label="操作" v-if="hasRole">
          <template v-slot="scope">
            <button
                class="table-btn-delete"
                @click="handleDeleteMaterial(scope.row.id, scope.row.title)"
            />
            <button
                class="table-btn-update"
                @click="
                handleModifyFormVisible(
                  scope.row.id,
                  scope.row.message,
                  scope.row.title
                )
              "
            />
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
        title="新增必备材料"
        v-model="addFormVisible"
        :append-to-body="false"
        @closed="handleAddClose"
    >
      <el-form :model="addForm" hide-required-asterisk ref="addForm">
        <el-form-item
            label="材料标题"
            label-width="110px"
            prop="title"
            :rules="rules.nameRules"
        >
          <el-input
              v-model.trim="addForm.title"
              autocomplete="off"
              required
          ></el-input>
        </el-form-item>
        <el-form-item
            label="材料内容"
            label-width="110px"
            prop="message"
            :rules="rules.infoRules"
        >
          <el-input
              v-model.trim="addForm.message"
              autocomplete="off"
              required
          ></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="addFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleAddMaterial('addForm')">确 定</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 点击修改后的弹窗 -->
    <el-dialog
        title="修改必备材料信息"
        v-model="modifyFormVisible"
        :append-to-body="false"
        @closed="handleModifyClose"
    >
      <el-form
          :model="modifyForm"
          hide-required-asterisk
          ref="modifyForm"
          label-width="110px"
      >
        <el-form :model="modifyForm" hide-required-asterisk ref="modifyForm">
          <el-form-item
              label="材料标题"
              label-width="110px"
              prop="title"
              :rules="rules.nameRules"
          >
            <el-input
                v-model.trim="modifyForm.title"
                autocomplete="off"
                required
            ></el-input>
          </el-form-item>
          <el-form-item
              label="材料内容"
              label-width="110px"
              prop="message"
              :rules="rules.infoRules"
          >
            <el-input
                v-model.trim="modifyForm.message"
                autocomplete="off"
                required
            ></el-input>
          </el-form-item>
        </el-form>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="modifyFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleModifyMeterial('modifyForm')">确 定</el-button>
        </div>
      </template>
    </el-dialog>
  </el-container>
</template>

<script>
import Pagination from "../../components/Pagination";
import {mapGetters} from "vuex";
import rules from "../../utils/validator";

export default {
  name: "MaterialManageIndex",
  components: {
    Pagination,
  },
  data() {
    return {
      currentPage: 1,
      pageSize: 5,
      keywordDefault: "",
      addFormVisible: false,
      addForm: {
        title: "",
        message: "",
      },
      modifyFormVisible: false,
      modifyForm: {
        id: "",
        message: "",
        title: "",
      },
      rules,
    };
  },
  methods: {
    // 切换分页及首次进入获取数据
    getMaterialInfo() {
      this.$store.dispatch("materialInfoManage/getMaterialInfo", {
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
        this.getMaterialInfo();
      }
    },
    // 通过关键字查询数据
    handelQuery(keyword) {
      this.$store.dispatch("materialInfoManage/getMaterialInfo", {
        pn: this.currentPage,
        size: this.pageSize,
        keyword,
      });
    },
    //新增
    handleAddMaterial(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.addFormVisible = false;
          this.$store.dispatch("materialInfoManage/addMaterial", {
            title: this.addForm.title,
            message: this.addForm.message,
            size: this.pageSize,
          });
          this.currentPage += 1;
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
    handleDeleteMaterial(id, title) {
      this.$confirm(`确定要删除“${title}”的相关信息吗？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
          .then(() => {
            this.$store.dispatch("materialInfoManage/deleteMaterial", {
              id,
              pn: this.currentPage,
              size: this.pageSize,
              keyword: this.keyword,
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
    handleModifyFormVisible(id, message, title) {
      this.modifyForm = {
        id,
        message,
        title,
      };
      this.modifyFormVisible = true;
    },
    // 修改
    handleModifyMeterial(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.modifyFormVisible = false;
          this.$store.dispatch("materialInfoManage/modifyMaterial", {
            id: this.modifyForm.id,
            message: this.modifyForm.message,
            title: this.modifyForm.title,
            pn: this.currentPage,
            size: this.pageSize,
            keyword: this.keyword,
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
    // 每次关闭表单的时候重置表单
    handleAddClose() {
      this.$refs.addForm.resetFields();
    },
    handleModifyClose() {
      this.$refs.modifyForm.clearValidate();
    },
  },
  mounted() {
    this.getMaterialInfo(); // 首次渲染
  },
  computed: {
    ...mapGetters({
      tableData: "materialInfo",
    }), //后端返回的数据
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