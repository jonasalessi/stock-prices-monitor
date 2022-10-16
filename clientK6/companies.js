import http from "k6/http";
import { check } from "k6";
import papaparse from 'https://jslib.k6.io/papaparse/5.1.1/index.js';
import { SharedArray } from 'k6/data';
import { scenario } from 'k6/execution';


const csvData = new SharedArray('Companies', function () {
  return papaparse.parse(open('./companies.csv'), { header: true }).data;
});

export const options = {
  scenarios: {
    'use-all-the-data': {
      executor: 'shared-iterations',
      vus: 50,
      iterations: csvData.length,
      maxDuration: '15m',
    },
  },
};

export default function () {
  const company = csvData[scenario.iterationInTest];
  if (!company.name || !company.ticker) return;
  const res = http.post(`http://host.docker.internal:8080/company`, company);
  check(res, {
    [`is company ${company.name} created`]: (res) => res.status === 201,
  });
}

