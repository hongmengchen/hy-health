// 手机号验证器
function validatorPhone(rule, value, callback) {
  if (value === "") {
    callback(new Error(" 手机号不能为空 "));
  } else if (!/^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/.test(value)) {
    callback(new Error(" 手机号格式错误 "));
  } else {
    callback();
  }
}
// 姓名验证器
function validatorName(rule, value, callback) {
  if (value === "") {
    callback(new Error(" 姓名不能为空 "));
  } else if (!/^[\u4E00-\u9FA5]{2,4}$/.test(value)) {
    callback(new Error(" 姓名应为汉字并且长度在2到4位 "));
  } else {
    callback();
  }
}
// 年龄验证器
function validatorAge(rule, value, callback) {
  if (!value) {
    return callback(new Error("年龄不能为空"));
  }
  if (!Number.isInteger(value)) {
    callback(new Error("请输入数字值"));
  } else {
    if (value > 100 || value < 18) {
      callback(new Error("请检查年龄大小是否合理"));
    } else {
      callback();
    }
  }
} 
// 搜索栏数字验证
function validatorInt(rule, value, callback) {
  if (/\D/g.test(value)) {
    callback(new Error(" 编号应为数字类型 "));
  } else {
    callback();
  }
}
// 搜索条件验证
export const searchRules = {
  intRules: [
    {
      validator: validatorInt,
      trigger: "change",
    },
  ],
}
// 验证密码   密码，以字母开头，长度在5~18之间，只能包含字母、数字和下划线
export function validatePass(str) {
  const reg = /^[a-zA-Z]\w{4,18}$/;
  return reg.test(str);
}
// 通用验证规则
export default {
  phoneRules: [
    {
      required: true,
      validator: validatorPhone,
      trigger: "change",
    },
  ],
  nameRules: [
    { required: true, message: "名称不能为空", trigger: "change" },
    {
      min: 4,
      max: 24,
      message: "名称长度应为 4 到 24 个字符",
      trigger: "change",
    },
  ],
  infoRules: [
    { required: true, message: "该项不能为空", trigger: "change" },
    {
      min: 4,
      max: 480,
      message: "名称长度应为 4 到 480 个字符",
      trigger: "change",
    },
  ],
  requiredRules: [
    { required: true, message: "此内容不可为空", trigger: "change" },
  ],
};
// 医生信息验证规则
export const doctorRules = {
  phoneRules: [
    {
      required: true,
      validator: validatorPhone,
      trigger: "change",
    },
  ],
  nameRules: [
    { required: true,validator: validatorName, trigger: "change" },
  ],
  requiredRules: [
    { required: true, message: "此内容不可为空", trigger: "change" },
  ],
  ageRules: [
    {
      required: true,
      validator: validatorAge,
      trigger: "change",
    },
  ],
};
// 登录规则
export const loginRules = {
  usernameRules: [
    {
      required: true,
      message: "用户名不能为空",
      trigger: "blur",
    },
    {
      min: 3,
      max: 11,
      message: "用户名长度在 3 到 11 个字符",
      trigger: "blur",
    },
  ],
  passwordRules: [
    { required: true, message: "密码不能为空", trigger: "blur" },
    {
      min: 5,
      max: 18,
      message: "密码长度在 5 到 18 个字符",
      trigger: "blur",
    },
  ],
};