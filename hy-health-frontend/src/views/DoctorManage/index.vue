<template>
  <el-container>
    <el-header height="76px">
      <h2>医生信息管理</h2>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item to="/">首页</el-breadcrumb-item>
        <el-breadcrumb-item>医生信息管理</el-breadcrumb-item>
      </el-breadcrumb>
    </el-header>

    <el-main>
      <div class="main-title">
        <h3>医生信息列表</h3>
        <el-tooltip content="新增医生" placement="top">
          <button class="new-add" @click="addFormVisible = true" v-if="hasRole"/>
        </el-tooltip>
      </div>

      <div class="search-policy">
        <el-input
            v-model="keyword"
            @input="handleQuery"
            clearable
            size="small"
            placeholder="查询（输入要查询的关键字）"
        />
      </div>

      <el-table :data="tableData.list" stripe highlight-current-row>
        <el-table-column prop="name" label="医生姓名"/>
        <el-table-column prop="age" label="年龄" sortable/>
        <el-table-column prop="sex" label="性别" sortable>
          <template #default="scope">
            <span>{{ scope.row.sex === 1 ? '男' : '女' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="doctorLevel" label="级别" sortable/>
        <el-table-column prop="phoneNumber" label="手机号"/>
        <el-table-column prop="treatType" label="诊治类别" sortable/>
        <el-table-column v-if="hasRole" label="操作" width="240">
          <template #default="scope">
            <el-tooltip content="删除医生" placement="top">
              <button
                  class="table-btn-delete"
                  @click="handleDeleteDoctor(scope.row.id, scope.row.name)"
              />
            </el-tooltip>
            <el-tooltip content="编辑医生" placement="top">
              <button
                  class="table-btn-update"
                  @click="openModify(scope.row)"
              />
            </el-tooltip>
            <el-tooltip content="重置密码" placement="top">
              <el-button
                  plain
                  size="mini"
                  class="table-btn"
                  @click="resetPassword(scope.row.accountId, scope.row.name)"
              >
                重置密码
              </el-button>
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
    <el-dialog title="新增医生信息" v-model="addFormVisible" @closed="handleAddClose">
      <el-form :model="addForm" ref="addForm" label-width="110px" hide-required-asterisk>
        <el-form-item label="医生姓名" prop="name" :rules="doctorRules.nameRules">
          <el-input v-model.trim="addForm.name" required/>
        </el-form-item>
        <el-form-item label="年龄" prop="age" :rules="doctorRules.ageRules">
          <el-input v-model.number="addForm.age" required/>
        </el-form-item>
        <el-form-item label="性别" prop="sex" :rules="doctorRules.requiredRules">
          <el-select v-model="addForm.sex" placeholder="请选择性别">
            <el-option label="男" :value="1"/>
            <el-option label="女" :value="2"/>
          </el-select>
        </el-form-item>
        <el-form-item label="级别" prop="levelId" :rules="doctorRules.requiredRules">
          <el-select v-model="addForm.levelId" placeholder="请选择级别">
            <el-option
                v-for="item in levelAndTypeData.allLevel"
                :key="item.id"
                :label="item.name"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="手机号" prop="phoneNumber" :rules="doctorRules.phoneRules">
          <el-input v-model="addForm.phoneNumber" required/>
        </el-form-item>
        <el-form-item label="密码" prop="pwd" :rules="pwdRules">
          <el-input type="password" v-model.trim="addForm.pwd" required/>
        </el-form-item>
        <el-form-item label="确认密码" prop="pwdCheck" :rules="checkPwdRules">
          <el-input type="password" v-model.trim="addForm.pwdCheck" required/>
        </el-form-item>
        <el-form-item label="诊治类别" prop="typeId" :rules="doctorRules.requiredRules">
          <el-select v-model="addForm.typeId" placeholder="请选择诊治类别">
            <el-option
                v-for="item in levelAndTypeData.allTreatType"
                :key="item.id"
                :label="item.name"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="addFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleAddDoctor">确 定</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 修改弹窗 -->
    <el-dialog title="修改医生信息" v-model="modifyFormVisible" @closed="handleModifyClose">
      <el-form :model="modifyForm" ref="modifyForm" label-width="110px" hide-required-asterisk>
        <el-form-item label="医生姓名" prop="name" :rules="doctorRules.nameRules">
          <el-input v-model.trim="modifyForm.name"/>
        </el-form-item>
        <el-form-item label="年龄" prop="age" :rules="doctorRules.ageRules">
          <el-input v-model.number="modifyForm.age"/>
        </el-form-item>
        <el-form-item label="性别" prop="sex" :rules="doctorRules.requiredRules">
          <el-select v-model="modifyForm.sex">
            <el-option label="男" :value="1"/>
            <el-option label="女" :value="2"/>
          </el-select>
        </el-form-item>
        <el-form-item label="级别" prop="levelId" :rules="doctorRules.requiredRules">
          <el-select v-model="modifyForm.levelId">
            <el-option
                v-for="item in levelAndTypeData.allLevel"
                :key="item.id"
                :label="item.name"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="手机号" prop="phoneNumber" :rules="doctorRules.phoneRules">
          <el-input v-model="modifyForm.phoneNumber" required/>
        </el-form-item>
        <el-form-item label="诊治类别" prop="typeId" :rules="doctorRules.requiredRules">
          <el-select v-model="modifyForm.typeId">
            <el-option
                v-for="item in levelAndTypeData.allTreatType"
                :key="item.id"
                :label="item.name"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="modifyFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleModifyDoctor">确 定</el-button>
        </div>
      </template>
    </el-dialog>
    <!-- 放置AI助手组件 -->
    <AiAssistant/>
  </el-container>
</template>

<script>
import {mapGetters} from "vuex";
import {doctorRules, validatePass} from "@/utils/validator";
import {resetPassword} from "@/api/admin/doctorInfoManage";
import AiAssistant from "@/components/AiAssistant.vue";

export default {
  name: "DoctorManage",
  components: {AiAssistant},
  data() {
    const validatePwd = (rule, value, callback) => {
      if (!validatePass(value)) callback(new Error("密码格式不正确"));
      else callback();
    };
    return {
      currentPage: 1,
      pageSize: 5,
      keywordDefault: "",
      addFormVisible: false,
      modifyFormVisible: false,
      addForm: {name: "", age: "", sex: "", levelId: "", phoneNumber: "", pwd: "", pwdCheck: "", typeId: ""},
      modifyForm: {id: "", accountId: "", name: "", age: "", sex: "", levelId: "", phoneNumber: "", typeId: ""},
      doctorRules,
      pwdRules: [{required: true, validator: validatePwd, trigger: "blur"}],
      checkPwdRules: [{
        validator: (rule, value, callback) =>
            value === this.addForm.pwd ? callback() : callback(new Error("密码不一致")),
        trigger: "blur",
      }],
    };
  },
  computed: {
    ...mapGetters({tableData: "doctorInfo", levelAndTypeData: "doctorLevelAndType"}),
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
    getDoctorInfo(pn = this.currentPage) {
      this.$store.dispatch("doctorInfoManage/getDoctorInfo", {
        pn,
        size: this.pageSize,
        keyword: this.keyword,
      });
    },
    handleCurrentChange(page) {
      this.currentPage = page;
      this.getDoctorInfo(page);
    },
    handleQuery() {
      this.currentPage = 1;
      this.getDoctorInfo(1);
    },
    handleAddDoctor() {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          this.$store.dispatch("doctorInfoManage/addDoctor", {
            ...this.addForm,
            size: this.pageSize,
          }).then(() => {
            this.addFormVisible = false;
            this.getDoctorInfo(1);
          });
        }
      });
    },
    handleDeleteDoctor(id, name) {
      this.$confirm(`确定删除“${name}”？`, "提示", {type: "warning"})
          .then(() =>
              this.$store.dispatch("doctorInfoManage/deleteDoctor", {
                id,
                pn: this.currentPage,
                size: this.pageSize,
                keyword: this.keyword,
              })
          )
          .then(() => this.getDoctorInfo())
          .catch(() => {
            // 取消或错误时静默处理
          });
    },
    openModify(row) {
      this.modifyForm = {...row};
      this.modifyFormVisible = true;
    },
    handleModifyDoctor() {
      this.$refs.modifyForm.validate((valid) => {
        if (valid) {
          this.$store.dispatch("doctorInfoManage/modifyDoctor", {
            ...this.modifyForm,
            pn: this.currentPage,
            size: this.pageSize,
            keyword: this.keyword,
          }).then(() => {
            this.modifyFormVisible = false;
            this.getDoctorInfo();
          });
        }
      });
    },
    resetPassword(id, name) {
      this.$confirm(`确定重置“${name}”密码？`, "提示", {type: "warning"})
          .then(() => resetPassword(id))
          .catch(() => {
            // 取消或错误时静默处理
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
    this.getDoctorInfo();
    this.$store.dispatch("doctorInfoManage/getDoctorLevelAndType");
  },
};
</script>

<style lang="less" scoped>
@import "../../style/infoManage.less";

.main-title {
  display: flex;
  justify-content: space-between;
  align-items: center;
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
