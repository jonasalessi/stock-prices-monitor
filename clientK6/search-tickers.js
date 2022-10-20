import http from "k6/http";
import { check } from "k6";
import papaparse from 'https://jslib.k6.io/papaparse/5.1.1/index.js';
import { SharedArray } from 'k6/data';
import { scenario } from 'k6/execution';

const data = new SharedArray('Companies', function () {
  return papaparse.parse(open('./companies.csv'), { header: true }).data;
}); 
export const options = {
  scenarios: {
    'use-all-the-data': {
      executor: 'shared-iterations',
      vus: 276,
      iterations: data.length,
      maxDuration: '15m',
    },
  },
};

export default function () {
  const company = data[scenario.iterationInTest];
  if (!company.ticker) return; 
  const res = http.get(`http://host.docker.internal:8080/companies?query=${company.ticker}`);
   check(res, {
    [`is ticker ${company.name} search returned ok`]: (res) => res.status === 200 && JSON.parse(res.body).length > 0,
  });
}

