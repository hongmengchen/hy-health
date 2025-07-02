<!-- 医保政策信息 -->
<template>
  <el-container>
    <!-- 头部区域 -->
    <el-header height="76px">
      <h2 v-if="hasRole">医保政策管理</h2>
      <h2 v-else>医保政策查询</h2>

      <!-- 面包屑导航区域 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item v-if="hasRole">医保政策管理</el-breadcrumb-item>
        <el-breadcrumb-item v-else>医保政策查询</el-breadcrumb-item>
      </el-breadcrumb>
    </el-header>

    <!-- 主体内容区域 -->
    <el-main>
      <!--header -->
      <div class="search-policy">
        <div class="demo-input-suffix demo-input-size">
          <el-form :model="searchLimit" ref="searchLimit">
            <div class="input-item">
              <h5>医保编号</h5>
              <el-form-item prop="id" :rules="searchRules.intRules">
                <el-input
                    placeholder="请输入"
                    size="small"
                    v-model.number="searchLimit.id"
                    maxlength="6"
                />
              </el-form-item>
            </div>
            <div class="input-item">
              <h5>政策标题</h5>
              <el-form-item prop="title">
                <el-input
                    placeholder="请输入"
                    size="small"
                    v-model="searchLimit.title"
                    maxlength="14"
                />
              </el-form-item>
            </div>
            <div class="input-item">
              <h5>发布时间</h5>
              <el-form-item prop="publishTime">
                <el-date-picker
                    type="date"
                    placeholder="选择日期"
                    size="small"
                    v-model="searchLimit.publishTime"
                    value-format="yyyy-MM-dd"
                />
              </el-form-item>
            </div>
            <div class="input-item">
              <h5>城市</h5>
              <el-form-item prop="city">
                <el-select
                    placeholder="请选择需要查询的城市"
                    v-model="searchLimit.city"
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
          </el-form>
          <el-button
              type="primary"
              size="small"
              @click="handleLimitedSearch('searchLimit')"
          >查找
          </el-button
          >
          <el-button
              type="primary"
              size="small"
              @click="addFormVisible = true"
              v-if="hasRole"
          >新增
          </el-button
          >
        </div>
      </div>

      <!-- 表格 -->
      <div class="table-policy">
        <el-table
            :data="tableData.list"
            stripe
            :default-sort="{ prop: 'date', order: 'descending' }"
            max-height="260"
            highlight-current-row
        >
          <el-table-column type="expand">
            <template v-slot="scope">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="医保内容">
                  <span>{{ scope.row.message }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column prop="id" label="医保编号" sortable/>
          <el-table-column prop="title" label="政策标题"/>
          <el-table-column prop="cityModel.city" label="城市"/>
          <el-table-column prop="updateTime" label="发布时间" sortable/>
          <el-table-column
              prop="medical_policy_operation"
              label="操作"
              v-if="hasRole"
          >
            <template v-slot="scope">
              <button
                  class="table-btn-delete"
                  @click="handleDeletMedicalPolicy(scope.row.id, scope.row.title)"
              />
              <button
                  class="table-btn-update"
                  @click="
                  handleModifyFormVisible(
                    scope.row.id,
                    scope.row.title,
                    scope.row.message,
                    scope.row.cityModel.cityId
                  )
                "
              />
            </template>
          </el-table-column>
        </el-table>
      </div>

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
        title="新增医保政策"
        v-model="addFormVisible"
        :append-to-body="false"
        @closed="handleAddClose"
    >
      <el-form
          :model="addForm"
          hide-required-asterisk
          ref="addForm"
          label-width="110px"
      >
        <el-form-item label="政策标题" prop="title" :rules="rules.nameRules">
          <el-input v-model.trim="addForm.title" autocomplete="off" required/>
        </el-form-item>
        <el-form-item label="政策内容" prop="message" :rules="rules.infoRules">
          <el-input
              v-model.trim="addForm.message"
              autocomplete="off"
              required
              autosize
              type="textarea"
          />
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
          <el-button type="primary" @click="handleAddMedicalPolicy('addForm')">确 定</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 点击修改后的弹窗 -->
    <el-dialog
        title="修改医保政策信息"
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
        <el-form-item label="政策标题" prop="title" :rules="rules.nameRules">
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
          <el-button type="primary" @click="handleModifyMedicalPolicy('modifyForm')">确 定</el-button>
        </div>
      </template>
    </el-dialog>
  </el-container>
</template>

<script>
import Pagination from "../../components/Pagination";
import {mapGetters} from "vuex";
import rules, {searchRules} from "../../utils/validator";
import dayjs from "dayjs";

export default {
  name: "MedicalPolicyManage",
  components: {
    Pagination,
  },
  data() {
    return {
      rules,
      currentPage: 1,
      pageSize: 5, // 每页的数据条数
      searchLimit: {
        // 查询条件
        id: "",
        title: "",
        publishTime: "",
        city: "",
      },
      addForm: {
        title: "",
        message: "",
        city: "",
        updateTime: dayjs().format("YYYY-MM-DD"),
      },
      addFormVisible: false,
      modifyForm: {
        id: "",
        title: "",
        message: "",
        city: "",
      },
      modifyFormVisible: false,
      searchRules,
    };
  },
  methods: {
    //当前页改变时触发,跳转其他页
    handleCurrentChange(event) {
      this.currentPage = event.page;
      this.getMedicalPolicyInfo();
    },
    // 查询医药政策信息
    getMedicalPolicyInfo() {
      this.$store.dispatch(
          "medicalPolicyInfoManage/getMedicalPolicyInfo",
          this.params
      );
    },
    // 条件查询
    handleLimitedSearch(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.getMedicalPolicyInfo();
        } else {
          this.$message.warning("请检查输入的内容是否合规");
          return false;
        }
      });
    },
    //新增医保政策
    handleAddMedicalPolicy(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.addFormVisible = false;
          this.$store.dispatch("medicalPolicyInfoManage/addMedicalPolicy", {
            cityId: this.addForm.city,
            title: this.addForm.title,
            updateTime: this.addForm.updateTime,
            message: this.addForm.message,
            size: this.pageSize,
          });
          this.$refs.searchLimit.resetFields();
        } else {
          this.$message.warning("请检查输入的内容是否合规");
          return false;
        }
      });
    },
    // 删除医保政策
    handleDeletMedicalPolicy(id, title) {
      this.$confirm(`确定要删除“${title}”的相关信息吗？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
          .then(() => {
            this.$store.dispatch("medicalPolicyInfoManage/deleteMedicalPolicy", {
              id,
              params: this.params,
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除",
            });
          });
    },
    // 控制修改医保政策信息的表单弹出
    handleModifyFormVisible(id, title, message, city) {
      this.modifyForm = {
        id,
        title,
        message,
        city,
      };
      this.modifyFormVisible = true;
    },
    // 修改医保政策信息
    handleModifyMedicalPolicy(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.modifyFormVisible = false;
          this.$store.dispatch(
              "medicalPolicyInfoManage/modifyMedicalPolicyInfo",
              {
                id: this.modifyForm.id,
                cityId: this.modifyForm.city,
                title: this.modifyForm.title,
                updateTime: dayjs().format("YYYY-MM-DD"),
                message: this.modifyForm.message,
                params: this.params,
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
    // 每次关闭表单的时候清除验证器和输入框内容
    handleAddClose() {
      this.$refs.addForm.resetFields();
    },
    handleModifyClose() {
      this.$refs.modifyForm.clearValidate();
    },
  },
  mounted() {
    this.getMedicalPolicyInfo();
    this.$store.dispatch("cityInfoManage/getAllCityInfo");
  },
  computed: {
    ...mapGetters({
      tableData: "medicalPolicyInfo",
      cityInfo: "cityInfo",
    }), //后端返回的数据
    params() {
      //查询操作用到的参数
      return {
        pn: this.currentPage,
        size: this.pageSize,
        cityId: this.searchLimit.city,
        title: this.searchLimit.title,
        updateTime: this.searchLimit.publishTime,
        id: this.searchLimit.id,
      };
    },
  },
};
</script>

<style lang="less" scoped>
@import "../../style/infoManage.less";

.search-policy {
  background-color: #fff;
  margin-bottom: 10px;

  .demo-input-suffix {
    width: 700px;
    padding: 15px 0 10px 20px;
    height: 200px;

    .input-item {
      float: left;
      margin-right: 20px;
      margin: 0px 30px 0 0;

      h5 {
        margin-bottom: 10px;
      }

      .el-input,
      .el-date-picker,
      .el-select {
        width: 300px;
      }
    }
  }
}

.table-policy {
  border-top: 3px solid #e8ebed;
}
</style>