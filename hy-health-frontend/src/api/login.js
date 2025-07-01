import request from "../utils/request";
import Qs from "qs";

export function login(username, password) {
  return request({
    url: "/login",
    method: "POST",
    data: Qs.stringify({ username, password }),
  });
}

export function getMenuList(roleName) {
  return request({
    url: "/permissions",
    method: "GET",
    params: {
      roleName
    }
  })
}