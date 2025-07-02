<!-- 医生信息管理 -->
<template>
  <el-container>
    <!-- 头部区域 -->
    <el-header height="76px">
      <h2>医生信息管理</h2>
      <!-- 面包屑导航区域 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>医生信息管理</el-breadcrumb-item>
      </el-breadcrumb>
    </el-header>
    <!-- 主体内容区域 -->
    <el-main>
      <!--header -->
      <div class="main-title">
        <h3>医生信息列表</h3>
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
          highlight-current-row
      >
        <el-table-column prop="name" label="医生姓名"/>
        <el-table-column prop="age" label="年龄" sortable/>
        <el-table-column prop="sex" label="性别" sortable>
          <template v-slot="scope">
            <span>{{ scope.row.sex == 1 ? "男" : "女" }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="doctorLevel" label="级别" sortable/>
        <el-table-column prop="phoneNumber" label="手机号"/>
        <el-table-column prop="treatType" label="诊治类别" sortable/>
        <el-table-column label="操作" width="210px" v-if="hasRole">
          <template v-slot="scope">
            <button
                class="table-btn-delete"
                @click="handleDeleteDoctor(scope.row.id, scope.row.name)"
            />
            <button
                class="table-btn-update"
                size="small"
                @click="
                handleModifyFormVisible(
                  scope.row.name,
                  scope.row.age,
                  scope.row.sex,
                  scope.row.levelId,
                  scope.row.phoneNumber,
                  scope.row.typeId,
                  scope.row.accountId,
                  scope.row.id
                )
              "
            />
            <el-button
                plain
                size="mini"
                style="width: 75px;"
                class="table-btn"
                @click="resetPassword(scope.row.accountId, scope.row.name)"
            >重置密码
            </el-button
            >
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
        title="新增医生信息"
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
        <el-form-item
            label="医生姓名"
            prop="name"
            :rules="doctorRules.nameRules"
        >
          <el-input
              v-model.trim="addForm.name"
              autocomplete="off"
              required
          ></el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="age" :rules="doctorRules.ageRules">
          <el-input
              v-model.number="addForm.age"
              autocomplete="off"
              required
          ></el-input>
        </el-form-item>
        <el-form-item
            label="性别"
            prop="sex"
            :rules="doctorRules.requiredRules"
        >
          <el-select v-model="addForm.sex" placeholder="请选择性别">
            <el-option label="男" :value="1"/>
            <el-option label="女" :value="2"/>
          </el-select>
        </el-form-item>
        <el-form-item
            label="级别"
            prop="levelId"
            :rules="doctorRules.requiredRules"
        >
          <el-select v-model="addForm.levelId" placeholder="请选择级别">
            <el-option
                v-for="item in levelAndTypeData.allLevel"
                :key="item.id"
                :label="item.name"
                :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
            label="手机号"
            prop="phoneNumber"
            :rules="doctorRules.phoneRules"
        >
          <el-input
              v-model="addForm.phoneNumber"
              autocomplete="off"
              required
          ></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="pwd" :rules="pwdRules">
          <el-input
              type="password"
              v-model.trim="addForm.pwd"
              autocomplete="off"
              required
          ></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="pwdCheck" :rules="checkPwdRules">
          <el-input
              type="password"
              v-model.trim="addForm.pwdCheck"
              autocomplete="off"
              required
          ></el-input>
        </el-form-item>
        <el-form-item
            label="诊治类别"
            prop="typeId"
            :rules="doctorRules.requiredRules"
        >
          <el-select v-model="addForm.typeId" placeholder="请选择诊治类别">
            <el-option
                v-for="item in levelAndTypeData.allTreatType"
                :key="item.id"
                :label="item.name"
                :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="addFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleAddDoctor('addForm')">确 定</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 点击修改后的弹窗 -->
    <el-dialog
        title="修改医生信息"
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
        <el-form-item
            label="医生姓名"
            prop="name"
            :rules="doctorRules.nameRules"
        >
          <el-input v-model.trim="modifyForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="age" :rules="doctorRules.ageRules">
          <el-input
              v-model.number="modifyForm.age"
              autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="性别" :rules="doctorRules.requiredRules">
          <el-select v-model="modifyForm.sex" placeholder="请选择性别">
            <el-option label="男" :value="1"/>
            <el-option label="女" :value="2"/>
          </el-select>
        </el-form-item>
        <el-form-item label="级别" :rules="doctorRules.requiredRules">
          <el-select v-model="modifyForm.levelId" placeholder="请选择级别">
            <el-option
                v-for="item in levelAndTypeData.allLevel"
                :key="item.id"
                :label="item.name"
                :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
            label="手机号"
            prop="phoneNumber"
            :rules="doctorRules.phoneRules"
        >
          <el-input
              v-model="modifyForm.phoneNumber"
              autocomplete="off"
              required
          ></el-input>
        </el-form-item>
        <el-form-item label="诊治类别" :rules="doctorRules.requiredRules">
          <el-select v-model="modifyForm.typeId" placeholder="请选择诊治类别">
            <el-option
                v-for="item in levelAndTypeData.allTreatType"
                :key="item.id"
                :label="item.name"
                :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="modifyFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleModifyDoctor('modifyForm')">确 定</el-button>
        </div>
      </template>
    </el-dialog>
  </el-container>
</template>

<script>
import Pagination from "../../components/Pagination";
import {mapGetters} from "vuex";
import {doctorRules, validatePass} from "@/utils/validator";
import {resetPassword} from "@/api/admin/doctorInfoManage";

export default {
  name: "DoctorManage",
  components: {
    Pagination,
  },
  data() {
    let validatorPass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else if (!validatePass(value)) {
        callback(
            new Error(
                "密码应以字母开头 长度在5~18之间 只能包含字母、数字和下划线"
            )
        );
      } else {
        if (this.addForm.pwdCheck !== "") {
          this.$refs.addForm.validateField("pwdCheck");
        }
        callback();
      }
    };
    let validatorPass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.addForm.pwd) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      currentPage: 1,
      pageSize: 5, // 每页的数据条数
      keywordDefault: "",
      addFormVisible: false, // 控制新增页面的显示
      resetPwdDialogVisible: false, //控制重置密码对话框显示
      addForm: {
        age: "",
        levelId: "",
        name: "",
        phoneNumber: "",
        pwd: "",
        pwdCheck: "",
        sex: "",
        typeId: "",
      },
      modifyFormVisible: false, // 控制修改信息页面的显示
      modifyForm: {
        accountId: "",
        age: "",
        levelId: "",
        name: "",
        phoneNumber: "",
        sex: "",
        typeId: "",
        id: "",
      },
      doctorRules, // 封装好的表单验证
      pwdRules: [{required: true, validator: validatorPass, trigger: "blur"}],
      checkPwdRules: [
        {required: true, validator: validatorPass2, trigger: "blur"},
      ],
    };
  },
  methods: {
    // 切换分页及首次进入获取数据
    getDoctorInfo() {
      this.$store.dispatch("doctorInfoManage/getDoctorInfo", {
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
        this.getDoctorInfo();
      }
    },
    // 通过关键字查询数据
    handelQuery(keyword) {
      this.$store.dispatch("doctorInfoManage/getDoctorInfo", {
        pn: this.currentPage,
        size: this.pageSize,
        keyword,
      });
    },
    //新增
    handleAddDoctor(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$store
              .dispatch("doctorInfoManage/addDoctor", {
                age: this.addForm.age,
                levelId: this.addForm.levelId,
                name: this.addForm.name,
                phoneNumber: this.addForm.phoneNumber,
                pwd: this.addForm.pwd,
                sex: this.addForm.sex,
                typeId: this.addForm.typeId,
                size: this.pageSize,
              })
              .then((res) => {
                if (res) {
                  this.addFormVisible = false;
                }
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
    handleDeleteDoctor(id, name) {
      this.$confirm(`确定要删除“${name}”的相关信息吗？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
          .then(() => {
            this.$store.dispatch("doctorInfoManage/deleteDoctor", {
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
    // 控制修改表单的弹出
    handleModifyFormVisible(name, age, sex, levelId, phoneNumber, typeId, accountId, id) {
      this.modifyForm = {
        name,
        age,
        sex,
        levelId,
        phoneNumber,
        typeId,
        accountId,
        id
      };
      this.modifyFormVisible = true;
    },
    // 修改
    handleModifyDoctor(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$store
              .dispatch("doctorInfoManage/modifyDoctor", {
                accountId: this.modifyForm.accountId,
                age: this.modifyForm.age,
                levelId: this.modifyForm.levelId,
                name: this.modifyForm.name,
                phoneNumber: this.modifyForm.phoneNumber,
                pwd: this.modifyForm.pwd,
                sex: this.modifyForm.sex,
                typeId: this.modifyForm.typeId,
                id: this.modifyForm.id,
                pn: this.currentPage,
                size: this.pageSize,
                keyword: this.keyword,
              })
              .then((res) => {
                if (res) {
                  this.modifyFormVisible = false;
                }
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
    // 重置医生密码
    resetPassword(id, name) {
      this.$confirm(`确定要重置“${name}”的密码吗？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
          .then(() => {
            resetPassword(id);
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消重置",
            });
          });
    },
    // 每次关闭表单的时候重置表单
    handleAddClose() {
      this.$refs.addForm.resetFields();
    },
    // 每次关闭表单的时候清除表单的验证
    handleModifyClose() {
      this.$refs.modifyForm.clearValidate();
    },
  },
  mounted() {
    this.getDoctorInfo(); // 首次渲染
    this.$store.dispatch("doctorInfoManage/getDoctorLevelAndType");
  },
  computed: {
    ...mapGetters({
      tableData: "doctorInfo",
      levelAndTypeData: "doctorLevelAndType",
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