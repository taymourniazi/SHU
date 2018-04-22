/******************************/******************************/
/* */
/* DI Data Cleaning */
/* */
/* Example 3.1 */
/* */
/******************************/
title1 'Listing of Missing Date Values';
data _null_;
set clean.patients;
file print;
if VISIT = . then put 'Obs '_N_ PATNO = 'Missing VISIT';
run;
title1 'Listing of Invalid (Out of Range) Date Values';
title2 'Excluding Missing Values';
data _null_;
set clean.patients;
file print;
if (VISIT lt '01Jun1998'D or VISIT gt '15Oct1999'D) and VISIT ne . then
put 'Obs '_N_ PATNO = 'Invalid VISIT = ' VISIT date9.;
run;
title;
/******************************/******************************/
/* */
/* DMDQ Data Cleaning */
/* */
/* Example 3.2 */
/* */
/******************************/
title1 'Listing of Missing Values for VISIT';
proc print data = clean.patients;
where VISIT = . ;
var PATNO VISIT;
format VISIT date9.;
run;
title1 'Listing of Invalid (Out of Range) Values for VISIT';
title2 'Excluding Missing Values';
proc print data = clean.patients;
where (VISIT not between '01Jun1998'D and '15Oct1999'D)
and (VISIT is not missing);
var PATNO VISIT;
format VISIT date9.;
run;
title;
