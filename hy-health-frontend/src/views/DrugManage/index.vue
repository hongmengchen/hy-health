<!-- 药品信息管理 -->
<template>
  <el-container>
    <!-- 头部区域 -->
    <el-header height="76px">
      <h2 v-if="hasRole">药品信息管理</h2>
      <h2 v-else>药品信息查询</h2>
      <!-- 面包屑导航区域 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item v-if="hasRole">药品信息管理</el-breadcrumb-item>
        <el-breadcrumb-item v-else>药品信息查询</el-breadcrumb-item>
      </el-breadcrumb>
    </el-header>
    <!-- 主体内容区域 -->
    <el-main>
      <!--header -->
      <div class="main-title">
        <h3>药品信息列表</h3>
        <button
            class="new-add"
            @click="addFormVisible = true"
            v-if="hasRole"
        />
      </div>
      <!-- 搜索 -->
      <el-row :gutter="20">
        <el-col :span="23" class="search-col">
          <keep-alive>
            <el-input
                placeholder="查询（输入要查询的药品名称）"
                size="small"
                v-model="keyword"
                @input="handelQuery"
            >
            </el-input>
          </keep-alive>
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
          <template v-slot="scope">
            <el-image
                style="width: 150px; height: 100px;"
                :src="scope.row.drugImg"
                fit="contain"
            ></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="drugName" label="药品名称"/>
        <el-table-column prop="drugSales.saleName" label="销售地点">
          <template v-slot="scope">
            <span v-for="i in scope.row.drugSales" :key="i.saleId"
            >{{ i.saleName }}&nbsp;&nbsp;</span
            >
          </template>
        </el-table-column>
        <el-table-column prop="drugPublisher" label="发布者" sortable/>
        <el-table-column label="操作" v-if="hasRole">
          <template v-slot="scope">
            <button
                class="table-btn-delete"
                @click="handleDeleteDrug(scope.row.drugId, scope.row.drugName)"
            ></button>
            <button
                class="table-btn-update"
                @click="
                handleModifyFormVisible(
                  scope.row.drugId,
                  scope.row.drugName,
                  scope.row.drugInfo,
                  scope.row.drugEffect,
                  scope.row.drugImg,
                  scope.row.drugSales
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
        title="新增药品"
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
        <el-form-item label="药品名称" prop="drugName" :rules="rules.nameRules">
          <el-input
              v-model.trim="addForm.drugName"
              autocomplete="off"
              required
          ></el-input>
        </el-form-item>
        <el-form-item label="药品信息" prop="drugInfo" :rules="rules.infoRules">
          <el-input
              v-model.trim="addForm.drugInfo"
              autocomplete="off"
              required
              autosize
              type="textarea"
          ></el-input>
        </el-form-item>
        <el-form-item
            label="药品功效"
            prop="drugEffect"
            :rules="rules.infoRules"
        >
          <el-input
              v-model.trim="addForm.drugEffect"
              autocomplete="off"
              required
              autosize
              type="textarea"
          ></el-input>
        </el-form-item>
        <el-form-item
            label="药品图片"
            prop="drugImg"
            :rules="rules.requiredRules"
        >
          <el-upload
              class="drugimg-uploader"
              :action="actionUrl"
              :headers="{ Authorization: token }"
              :show-file-list="false"
              :on-success="handleUploadSuccess"
              :on-error="handleUploadError"
              :on-progress="handleUploading"
              :before-upload="beforeImgUpload"
          >
            <el-image
                v-if="addForm.drugImg"
                class="drugimg"
                :src="addForm.drugImg"
                fit="contain"
            ></el-image>
            <el-progress
                v-if="uploading"
                type="circle"
                :percentage="percentage"
                :width="179"
                :status="status"
            />
            <i v-else class="el-icon-plus drugimg-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item
            label="销售药店"
            prop="saleIds"
            :rules="rules.requiredRules"
        >
          <el-select
              v-model="addForm.saleIds"
              multiple
              placeholder="请选择销售地点"
          >
            <el-option
                v-for="sale in salePlaceInfo.list"
                :key="sale.saleId"
                :label="sale.saleName"
                :value="sale.saleId"
            >
            </el-option>
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
    <!-- 点击修改后的弹窗 -->
    <el-dialog
        title="修改药品信息"
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
        <el-form-item label="药品名称" prop="drugName" :rules="rules.nameRules">
          <el-input
              v-model.trim="modifyForm.drugName"
              autocomplete="off"
              required
          ></el-input>
        </el-form-item>
        <el-form-item label="药品信息" prop="drugInfo" :rules="rules.infoRules">
          <el-input
              v-model.trim="modifyForm.drugInfo"
              autocomplete="off"
              required
              autosize
              type="textarea"
          ></el-input>
        </el-form-item>
        <el-form-item
            label="药品功效"
            prop="drugEffect"
            :rules="rules.infoRules"
        >
          <el-input
              v-model.trim="modifyForm.drugEffect"
              autocomplete="off"
              required
              autosize
              type="textarea"
          ></el-input>
        </el-form-item>
        <el-form-item
            label="药品图片"
            prop="drugImg"
            :rules="rules.requiredRules"
        >
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
            ></el-image>
            <i v-else class="el-icon-plus drugimg-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item
            label="销售药店"
            prop="saleIds"
            :rules="rules.requiredRules"
        >
          <el-select
              v-model="modifyForm.saleIds"
              multiple
              placeholder="请选择销售地点"
          >
            <el-option
                v-for="sale in salePlaceInfo.list"
                :key="sale.saleId"
                :label="sale.saleName"
                :value="sale.saleId"
            >
            </el-option>
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
import Pagination from "../../components/Pagination";
import {mapGetters} from "vuex";
import rules from "../../utils/validator";

export default {
  name: "CompanyManage",
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
        drugName: "",
        drugInfo: "",
        drugEffect: "",
        drugImg: "",
        saleIds: [],
      },
      modifyFormVisible: false, // 控制修改信息页面的显示
      modifyForm: {
        drugId: "",
        drugName: "",
        drugInfo: "",
        drugEffect: "",
        drugImg: "",
        saleIds: [],
      },
      rules, // 封装好的表单验证
      token: localStorage.getItem("token"), //用于给上传操作添加token请求头
      actionUrl: 'http://localhost:8080/api/base/upload', //图片上传接口
      uploading: false, // 是否显示上传进度条
      percentage: 0, // 进度条进度
      status: null, // 进度条状态
    };
  },
  methods: {
    // 显示进度条
    handleUploading() {
      this.uploading = true;
      let num = 0;
      let t = setInterval(() => {
        num = num + 10;
        this.percentage = num;
        if (num == 90) {
          clearInterval(t);
          this.percentage = 100;
          this.status = "success";
        }
      }, 50);
    },
    //选择完图片后自动上传，并拿到服务器返回的图片url地址
    handleUploadSuccess(res) {
      this.$message.success("上传成功");
      setTimeout(() => {
        this.uploading = false;
        this.percentage = 0;
        this.status = null;
        if (this.addFormVisible) {
          this.addForm.drugImg = res.data.url;
        } else {
          this.modifyForm.drugImg = res.data.url;
        }
      }, 800);
    },
    handleUploadError(err) {
      this.$message.error("上传失败，请重试");
      this.uploading = false;
      this.percentage = 0;
      this.status = null;
      console.log(err)
      let myError = err.toString();//转字符串
      myError = myError.replace("Error: ", "") // 去掉前面的" Error: "
      myError = JSON.parse(myError);//转对象
      console.log(myError);
    },
    // 对上传的文件类型及大小进行限制
    beforeImgUpload(file) {
      const isJPG = file.type === "image/jpeg" || file.type === "image/png";
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG) {
        this.$message.error("上传的图片只能是 JPG 格式或 PNG 格式");
      }
      if (!isLt2M) {
        this.$message.error("上传药品图片大小不能超过 2MB");
      }
      return isJPG && isLt2M;
    },
    // 切换分页及首次进入获取数据
    getDrugInfo() {
      this.$store.dispatch("drugInfoManage/getDrugInfo", {
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
        this.getDrugInfo();
      }
    },
    // 通过关键字查询数据
    handelQuery(keyword) {
      this.$store.dispatch("drugInfoManage/getDrugInfo", {
        pn: this.currentPage,
        size: this.pageSize,
        keyword,
      });
    },
    //新增药品
    handleAddDrug(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.addFormVisible = false;
          this.$store.dispatch("drugInfoManage/addDrug", {
            drugName: this.addForm.drugName,
            drugInfo: this.addForm.drugInfo,
            drugEffect: this.addForm.drugEffect,
            drugImg: this.addForm.drugImg,
            saleIds: this.addForm.saleIds,
            size: this.pageSize,
            drugPublisher: JSON.parse(localStorage.getItem("userInfo"))
                .realname,
          });
        } else {
          this.$message.warning("请检查输入的内容是否合规");
          return false;
        }
      });
    },
    // 删除药品
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
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除",
            });
          });
    },
    // 控制修改药品信息的表单弹出
    handleModifyFormVisible(
        drugId,
        drugName,
        drugInfo,
        drugEffect,
        drugImg,
        drugSales
    ) {
      let saleIds = drugSales.map((item) => {
        return item.saleId;
      });
      this.modifyForm = {
        drugId,
        drugName,
        drugInfo,
        drugEffect,
        drugImg,
        saleIds,
      };
      this.modifyFormVisible = true;
    },
    // 修改药品信息
    handleModifyDrug(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.modifyFormVisible = false;
          this.$store.dispatch("drugInfoManage/modifyDrugInfo", {
            drugId: this.modifyForm.drugId,
            drugName: this.modifyForm.drugName,
            drugInfo: this.modifyForm.drugInfo,
            drugEffect: this.modifyForm.drugEffect,
            drugImg: this.modifyForm.drugImg,
            saleIds: this.modifyForm.saleIds,
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
    // 每次关闭表单的时候清除验证器和输入框内容
    handleAddClose() {
      this.$refs.addForm.resetFields();
    },
    handleModifyClose() {
      this.$refs.modifyForm.clearValidate();
    },
  },
  mounted() {
    this.getDrugInfo(); // 首次渲染
    this.$store.dispatch("saleInfoManage/getAllSalePlaceInfo");
  },
  computed: {
    ...mapGetters({
      tableData: "drugInfo",
      salePlaceInfo: "salePlaceInfo",
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

.drugimg-uploader {
  width: 178px;
  height: 178px;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.drugimg-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.drugimg {
  width: 100%;
  height: 100%;
  display: block;
}
</style>