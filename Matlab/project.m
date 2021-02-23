clc;

uniFname = 'unirecords.xlsx';
bnkFname = 'bnkrecords.xlsx';
resFname = 'resrecords.xlsx';

uniStdNums = xlsread(uniFname, 'A2:A50');
uniPaidStds = uniStdNums(xlsread(uniFname, 'C2:C50') > 0);
bnkPaidStds = xlsread(bnkFname, 'A2:A50');

failedPayments = setdiff(uniPaidStds, bnkPaidStds);
xlswrite(resFname, failedPayments);
