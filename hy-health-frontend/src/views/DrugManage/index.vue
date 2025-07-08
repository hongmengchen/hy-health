<template>
  <el-container>
    <el-header height="76px">
      <h2 v-if="hasRole">药品信息管理</h2>
      <h2 v-else>药品信息查询</h2>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item v-if="hasRole">药品信息管理</el-breadcrumb-item>
        <el-breadcrumb-item v-else>药品信息查询</el-breadcrumb-item>
      </el-breadcrumb>
    </el-header>

    <el-main>
      <div class="main-title">
        <h3>药品信息列表</h3>
        <button class="new-add" @click="addFormVisible = true" v-if="hasRole" />
      </div>

      <el-row :gutter="20">
        <el-col :span="23" class="search-col">
          <keep-alive>
            <el-input
                placeholder="查询（输入要查询的药品名称）"
                size="small"
                v-model="keyword"
                @input="handleQuery"
            />
          </keep-alive>
        </el-col>
      </el-row>

      <el-table
          :data="tableData.list"
          stripe
          max-height="375"
          highlight-current-row
      >
        <el-table-column type="expand">
          <template #default="scope">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="药品信息">
                <span>{{ scope.row.drugInfo }}</span>
              </el-form-item>
              <el-form-item label="药品功效">
                <span>{{ scope.row.drugEffect }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column prop="drugId" label="药品编号" sortable/>
        <el-table-column prop="drugImg" label="药品图片">
          <template #default="scope">
            <el-image
                style="width: 150px; height: 100px;"
                :src="scope.row.drugImg"
                fit="contain"
            />
          </template>
        </el-table-column>
        <el-table-column prop="drugName" label="药品名称"/>
        <el-table-column prop="drugSales" label="销售地点">
          <template #default="scope">
            <span
                v-for="i in scope.row.drugSales"
                :key="i.saleId"
            >{{ i.saleName }}&nbsp;&nbsp;</span>
          </template>
        </el-table-column>
        <el-table-column prop="drugPublisher" label="发布者" sortable/>
        <el-table-column label="操作" v-if="hasRole">
          <template #default="scope">
            <button
                class="table-btn-delete"
                @click="handleDeleteDrug(scope.row.drugId, scope.row.drugName)"
            />
            <button
                class="table-btn-update"
                @click="handleModifyFormVisible(
                scope.row.drugId,
                scope.row.drugName,
                scope.row.drugInfo,
                scope.row.drugEffect,
                scope.row.drugImg,
                scope.row.drugSales
              )"
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

    <!-- 新增弹窗 -->
    <el-dialog
        title="新增药品"
        v-model="addFormVisible"
        @closed="handleAddClose"
    >
      <el-form
          :model="addForm"
          ref="addForm"
          label-width="110px"
          hide-required-asterisk
      >
        <el-form-item label="药品名称" prop="drugName" :rules="rules.nameRules">
          <el-input v-model.trim="addForm.drugName" required/>
        </el-form-item>
        <el-form-item label="药品信息" prop="drugInfo" :rules="rules.infoRules">
          <el-input
              v-model.trim="addForm.drugInfo"
              type="textarea"
              autosize
              required
          />
        </el-form-item>
        <el-form-item label="药品功效" prop="drugEffect" :rules="rules.infoRules">
          <el-input
              v-model.trim="addForm.drugEffect"
              type="textarea"
              autosize
              required
          />
        </el-form-item>
        <el-form-item label="药品图片" prop="drugImg" :rules="rules.requiredRules">
          <el-upload
              class="drugimg-uploader"
              :action="actionUrl"
              :headers="{ Authorization: token }"
              :show-file-list="false"
              :on-success="handleUploadSuccess"
              :on-error="handleUploadError"
              :before-upload="beforeImgUpload"
          >
            <el-image
                v-if="addForm.drugImg"
                class="drugimg"
                :src="addForm.drugImg"
                fit="contain"
            />
            <el-progress
                v-if="uploading"
                type="circle"
                :percentage="percentage"
                width="179"
                :status="status"
            />
            <i v-else class="el-icon-plus drugimg-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="销售药店" prop="saleIds" :rules="rules.requiredRules">
          <el-select v-model="addForm.saleIds" multiple placeholder="请选择销售地点">
            <el-option
                v-for="sale in salePlaceInfo.list"
                :key="sale.saleId"
                :label="sale.saleName"
                :value="sale.saleId"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="addFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleAddDrug('addForm')">确 定</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 修改弹窗 -->
    <el-dialog
        title="修改药品信息"
        v-model="modifyFormVisible"
        @closed="handleModifyClose"
    >
      <el-form
          :model="modifyForm"
          ref="modifyForm"
          label-width="110px"
          hide-required-asterisk
      >
        <el-form-item label="药品名称" prop="drugName" :rules="rules.nameRules">
          <el-input v-model.trim="modifyForm.drugName" required/>
        </el-form-item>
        <el-form-item label="药品信息" prop="drugInfo" :rules="rules.infoRules">
          <el-input
              v-model.trim="modifyForm.drugInfo"
              type="textarea"
              autosize
              required
          />
        </el-form-item>
        <el-form-item label="药品功效" prop="drugEffect" :rules="rules.infoRules">
          <el-input
              v-model.trim="modifyForm.drugEffect"
              type="textarea"
              autosize
              required
          />
        </el-form-item>
        <el-form-item label="药品图片" prop="drugImg" :rules="rules.requiredRules">
          <el-upload
              class="drugimg-uploader"
              :action="actionUrl"
              :headers="{ Authorization: token }"
              :show-file-list="false"
              :on-success="handleUploadSuccess"
              :on-error="handleUploadError"
              :before-upload="beforeImgUpload"
          >
            <el-image
                v-if="modifyForm.drugImg"
                class="drugimg"
                :src="modifyForm.drugImg"
                fit="contain"
            />
            <i v-else class="el-icon-plus drugimg-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="销售药店" prop="saleIds" :rules="rules.requiredRules">
          <el-select v-model="modifyForm.saleIds" multiple placeholder="请选择销售地点">
            <el-option
                v-for="sale in salePlaceInfo.list"
                :key="sale.saleId"
                :label="sale.saleName"
                :value="sale.saleId"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="modifyFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleModifyDrug('modifyForm')">确 定</el-button>
        </div>
      </template>
    </el-dialog>
  </el-container>
</template>

<script>
import { mapGetters } from "vuex";
import rules from "../../utils/validator";

export default {
  name: "DrugManage",
  data() {
    return {
      currentPage: 1,
      pageSize: 5,
      keywordDefault: "",
      addFormVisible: false,
      addForm: {
        drugName: "",
        drugInfo: "",
        drugEffect: "",
        drugImg: "",
        saleIds: [],
      },
      modifyFormVisible: false,
      modifyForm: {
        drugId: "",
        drugName: "",
        drugInfo: "",
        drugEffect: "",
        drugImg: "",
        saleIds: [],
      },
      rules,
      token: localStorage.getItem("token"),
      actionUrl: "http://localhost:8080/api/base/upload",
      uploading: false,
      percentage: 0,
      status: null,
    };
  },
  methods: {
    getDrugInfo(pn = this.currentPage) {
      this.$store.dispatch("drugInfoManage/getDrugInfo", {
        pn,
        size: this.pageSize,
        keyword: this.keyword,
      });
    },
    handleCurrentChange(newPage) {
      this.currentPage = newPage;
      this.getDrugInfo(newPage);
    },
    handleQuery() {
      this.currentPage = 1;
      this.getDrugInfo(1);
    },
    handleAddDrug(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.addFormVisible = false;
          this.$store.dispatch("drugInfoManage/addDrug", {
            ...this.addForm,
            size: this.pageSize,
            drugPublisher: JSON.parse(localStorage.getItem("userInfo")).realname,
          }).then(() => {
            this.currentPage = 1;
            this.getDrugInfo(1);
          });
        } else {
          this.$message.warning("请检查输入的内容是否合规");
        }
      });
    },
    handleDeleteDrug(drugId, drugName) {
      this.$confirm(`确定要删除“${drugName}”的相关信息吗？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
          .then(() => {
            this.$store.dispatch("drugInfoManage/deleteDrug", {
              drugId,
              pn: this.currentPage,
              size: this.pageSize,
              keyword: this.keyword,
            }).then(() => {
              this.getDrugInfo();
            });
          })
          .catch(() => {
            this.$message.info("已取消删除");
          });
    },
    handleModifyFormVisible(
        drugId,
        drugName,
        drugInfo,
        drugEffect,
        drugImg,
        drugSales
    ) {
      this.modifyForm = {
        drugId,
        drugName,
        drugInfo,
        drugEffect,
        drugImg,
        saleIds: drugSales.map((s) => s.saleId),
      };
      this.modifyFormVisible = true;
    },
    handleModifyDrug(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.modifyFormVisible = false;
          this.$store.dispatch("drugInfoManage/modifyDrugInfo", {
            ...this.modifyForm,
            pn: this.currentPage,
            size: this.pageSize,
            keyword: this.keyword,
          }).then(() => {
            this.getDrugInfo();
          });
        } else {
          this.$message.warning("请检查输入的内容是否合规");
        }
      });
    },
    handleUploading() {
      this.uploading = true;
      let num = 0;
      const t = setInterval(() => {
        num += 10;
        this.percentage = num;
        if (num >= 90) {
          clearInterval(t);
          this.percentage = 100;
          this.status = "success";
        }
      }, 50);
    },
    handleUploadSuccess(res) {
      this.$message.success("上传成功");
      setTimeout(() => {
        this.uploading = false;
        this.percentage = 0;
        this.status = null;
        const target = this.addFormVisible ? this.addForm : this.modifyForm;
        target.drugImg = res.data.url;
      }, 800);
    },
    handleUploadError(err) {
      this.$message.error("上传失败，请重试");
      this.uploading = false;
      this.percentage = 0;
      this.status = null;
      console.error(err);
    },
    beforeImgUpload(file) {
      const isValid = (file.type === "image/jpeg" || file.type === "image/png") &&
          file.size / 1024 / 1024 < 2;
      if (!isValid) {
        this.$message.error("仅支持 JPG/PNG，且小于 2MB");
      }
      return isValid;
    },
    handleAddClose() {
      this.$refs.addForm.resetFields();
    },
    handleModifyClose() {
      this.$refs.modifyForm.resetFields();
    },
  },
  mounted() {
    this.getDrugInfo();
    this.$store.dispatch("saleInfoManage/getAllSalePlaceInfo");
  },
  computed: {
    ...mapGetters({
      tableData: "drugInfo",
      salePlaceInfo: "salePlaceInfo",
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

.drugimg-uploader {
  width: 178px; height: 178px;
  border: 1px dashed #d9d9d9;
  border-radius: 6px; cursor: pointer;
  position: relative; overflow: hidden;
}
.drugimg-uploader-icon {
  font-size: 28px; color: #8c939d;
  width: 178px; height: 178px; line-height: 178px;
  text-align: center;
}
.drugimg {
  width: 100%; height: 100%;
  display: block;
}
</style>
