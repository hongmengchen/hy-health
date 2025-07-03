<template>
  <el-container>
    <!-- 头部 -->
    <el-header height="76px">
      <h2 v-if="hasRole">医保政策管理</h2>
      <h2 v-else>医保政策查询</h2>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item v-if="hasRole">医保政策管理</el-breadcrumb-item>
        <el-breadcrumb-item v-else>医保政策查询</el-breadcrumb-item>
      </el-breadcrumb>
    </el-header>

    <el-main>
      <!-- 搜索 + 新增 -->
      <div class="search-policy">
        <el-form :model="searchLimit" ref="searchLimit" inline>
          <div class="input-item">
            <h5>医保编号</h5>
            <el-form-item prop="id" :rules="searchRules.intRules">
              <el-input
                  v-model.number="searchLimit.id"
                  size="small"
                  placeholder="请输入"
                  maxlength="6"
              />
            </el-form-item>
          </div>
          <div class="input-item">
            <h5>政策标题</h5>
            <el-form-item prop="title">
              <el-input
                  v-model="searchLimit.title"
                  size="small"
                  placeholder="请输入"
                  maxlength="14"
              />
            </el-form-item>
          </div>
          <div class="input-item">
            <h5>发布时间</h5>
            <el-form-item prop="publishTime">
              <el-date-picker
                  v-model="searchLimit.publishTime"
                  type="date"
                  size="small"
                  placeholder="选择日期"
                  value-format="YYYY-MM-DD"
              />
            </el-form-item>
          </div>
          <div class="input-item">
            <h5>城市</h5>
            <el-form-item prop="city">
              <el-select
                  v-model="searchLimit.city"
                  size="small"
                  placeholder="请选择需要查询的城市"
                  clearable
              >
                <el-option
                    v-for="city in cityInfo.list"
                    :key="city.cityId"
                    :label="city.city"
                    :value="city.cityId"
                />
              </el-select>
            </el-form-item>
          </div>
          <el-button size="small" type="primary" @click="handleLimitedSearch">
            查找
          </el-button>
          <el-button
              size="small"
              type="primary"
              v-if="hasRole"
              @click="addFormVisible = true; resetPage()"
          >
            新增
          </el-button>
        </el-form>
      </div>

      <!-- 表格 -->
      <div class="table-policy">
        <el-table
            :data="tableData.list"
            stripe
            max-height="260"
            highlight-current-row
        >
          <el-table-column type="expand">
            <template #default="scope">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="医保内容">
                  <span>{{ scope.row.message }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column prop="id" label="医保编号" sortable />
          <el-table-column prop="title" label="政策标题" />
          <el-table-column prop="cityModel.city" label="城市" />
          <el-table-column prop="updateTime" label="发布时间" sortable />
          <el-table-column v-if="hasRole" label="操作">
            <template #default="scope">
              <button
                  class="table-btn-delete"
                  @click="handleDeleteMedicalPolicy(scope.row.id, scope.row.title)"
              />
              <button
                  class="table-btn-update"
                  @click="handleModifyFormVisible(
                  scope.row.id,
                  scope.row.title,
                  scope.row.message,
                  scope.row.cityModel.cityId
                )"
              />
            </template>
          </el-table-column>
        </el-table>
      </div>

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
    <el-dialog title="新增医保政策" v-model="addFormVisible" @closed="handleAddClose">
      <el-form :model="addForm" ref="addForm" label-width="110px" hide-required-asterisk>
        <el-form-item label="政策标题" prop="title" :rules="rules.nameRules">
          <el-input v-model.trim="addForm.title" required />
        </el-form-item>
        <el-form-item label="政策内容" prop="message" :rules="rules.infoRules">
          <el-input v-model.trim="addForm.message" type="textarea" autosize required />
        </el-form-item>
        <el-form-item label="生效城市" prop="city" :rules="rules.requiredRules">
          <el-select v-model="addForm.city" placeholder="请选择生效城市">
            <el-option
                v-for="city in cityInfo.list"
                :key="city.cityId"
                :label="city.city"
                :value="city.cityId"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="addFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleAddMedicalPolicy">确 定</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 修改弹窗 -->
    <el-dialog title="修改医保政策信息" v-model="modifyFormVisible" @closed="handleModifyClose">
      <el-form :model="modifyForm" ref="modifyForm" label-width="110px" hide-required-asterisk>
        <el-form-item label="政策标题" prop="title" :rules="rules.nameRules">
          <el-input v-model.trim="modifyForm.title" required />
        </el-form-item>
        <el-form-item label="政策内容" prop="message" :rules="rules.infoRules">
          <el-input v-model.trim="modifyForm.message" type="textarea" autosize required />
        </el-form-item>
        <el-form-item label="生效城市" prop="city" :rules="rules.requiredRules">
          <el-select v-model="modifyForm.city" placeholder="请选择生效城市">
            <el-option
                v-for="city in cityInfo.list"
                :key="city.cityId"
                :label="city.city"
                :value="city.cityId"
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
  </el-container>
</template>

<script>
import { mapGetters } from "vuex";
import rules, { searchRules } from "../../utils/validator";
import dayjs from "dayjs";

export default {
  name: "MedicalPolicyManage",
  data() {
    return {
      currentPage: 1,
      pageSize: 5,
      rules,
      searchRules,
      searchLimit: { id: "", title: "", publishTime: "", city: "" },
      addForm: {
        title: "",
        message: "",
        city: "",
        updateTime: dayjs().format("YYYY-MM-DD"),
      },
      modifyForm: { id: "", title: "", message: "", city: "" },
      addFormVisible: false,
      modifyFormVisible: false,
    };
  },
  computed: {
    ...mapGetters({
      tableData: "medicalPolicyInfo",
      cityInfo: "cityInfo",
    }),
    params() {
      return {
        pn: this.currentPage,
        size: this.pageSize,
        id: this.searchLimit.id,
        title: this.searchLimit.title,
        updateTime: this.searchLimit.publishTime,
        cityId: this.searchLimit.city,
      };
    },
    hasRole() {
      return true;
    },
  },
  methods: {
    getMedicalPolicyInfo(pn = this.currentPage) {
      this.$store.dispatch("medicalPolicyInfoManage/getMedicalPolicyInfo", {
        ...this.params,
        pn,
      });
    },
    handleCurrentChange(newPage) {
      this.currentPage = newPage;
      this.getMedicalPolicyInfo(newPage);
    },
    handleLimitedSearch() {
      this.$refs.searchLimit.validate(valid => {
        if (valid) {
          this.resetPage();
          this.getMedicalPolicyInfo(1);
        }
      });
    },
    handleAddMedicalPolicy() {
      this.$refs.addForm.validate(valid => {
        if (valid) {
          this.addFormVisible = false;
          this.$store
              .dispatch("medicalPolicyInfoManage/addMedicalPolicy", {
                cityId: this.addForm.city,
                title: this.addForm.title,
                updateTime: this.addForm.updateTime,
                message: this.addForm.message,
                size: this.pageSize,
              })
              .then(() => {
                this.resetPage();
                this.getMedicalPolicyInfo(1);
                this.$refs.searchLimit.resetFields();
              });
        }
      });
    },
    handleDeleteMedicalPolicy(id, title) {
      this.$confirm(`确定要删除“${title}”吗？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
          .then(() => {
            return this.$store.dispatch("medicalPolicyInfoManage/deleteMedicalPolicy", {
              id,
              params: this.params,
            });
          })
          .then(() => {
            this.getMedicalPolicyInfo();
          })
          .catch(() => {
            // 用户取消或发生其他错误，静默处理
          });
    },
    handleModifyFormVisible(id, title, message, city) {
      this.modifyForm = { id, title, message, city };
      this.modifyFormVisible = true;
    },
    handleModifyMedicalPolicy() {
      this.$refs.modifyForm.validate(valid => {
        if (valid) {
          this.modifyFormVisible = false;
          this.$store
              .dispatch("medicalPolicyInfoManage/modifyMedicalPolicyInfo", {
                id: this.modifyForm.id,
                cityId: this.modifyForm.city,
                title: this.modifyForm.title,
                updateTime: dayjs().format("YYYY-MM-DD"),
                message: this.modifyForm.message,
                params: this.params,
              })
              .then(() => {
                this.getMedicalPolicyInfo();
              });
        }
      });
    },
    resetPage() {
      this.currentPage = 1;
    },
    handleAddClose() {
      this.$refs.addForm.resetFields();
    },
    handleModifyClose() {
      this.$refs.modifyForm.resetFields();
    },
  },
  mounted() {
    this.getMedicalPolicyInfo();
    this.$store.dispatch("cityInfoManage/getAllCityInfo");
  },
};
</script>

<style lang="less" scoped>
@import "../../style/infoManage.less";

.search-policy {
  background: #fff;
  margin-bottom: 20px;
  padding: 15px;
  overflow: auto;
  clear: both;

  .input-item {
    float: left;
    margin-right: 30px;
    h5 {
      margin-bottom: 6px;
    }
    .el-input,
    .el-date-picker,
    .el-select {
      width: 240px;
    }
  }
}

.table-policy {
  border-top: 3px solid #e8ebed;
  margin-top: 20px;
}
</style>
