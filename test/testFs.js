const fs = require('fs');

let buff = new Buffer("ASdfASDFEWFAWEGVAERTASETWEARTASDVXZCGESYRY", 'base64');

fs.writeFileSync('./pictures/test2.png', buff);