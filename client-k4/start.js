import http from "k6/http";
import { sleep, check } from "k6";

export default function () {
  const httpresponse = (res) => {
    check(res, {
      "is status 200": (res) => res.status === 200,
    });
  };
  let params = { async: true, callback: httpresponse };
  http.get(`http://localhost:8080/stock/prices`, params);
 
}
