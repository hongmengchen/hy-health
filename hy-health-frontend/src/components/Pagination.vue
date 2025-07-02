<template>
  <div class="pagination-container">
    <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="localPageSize"
        :layout="layout"
        :total="total"
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"
    />
  </div>
</template>

<script>
export default {
  name: "PaginationView",

  data() {
    return {
      localPageSize: this.pageSize,
    };
  },

  props: {
    total: {
      required: true,
    },
    page: {
      type: Number,
      default: 1,
    },
    layout: {
      type: String,
      default: "total, sizes, prev, pager, next, jumper",
    },
    pageSize: {
      type: Number,
      default: 10, // 建议默认值设为常用分页大小，如10
    },
  },

  watch: {
    pageSize(newVal) {
      this.localPageSize = newVal;
    },
  },

  computed: {
    currentPage: {
      get() {
        return this.page;
      },
      set(val) {
        this.$emit("update:page", val);
      },
    },
  },

  methods: {
    handleCurrentChange(val) {
      this.$emit("currentChange", {page: val, limit: this.localPageSize});
    },
    handleSizeChange(val) {
      this.localPageSize = val;
      this.$emit("update:pageSize", val);
      this.$emit("currentChange", {page: this.page, limit: val});
    },
  },
};
</script>
