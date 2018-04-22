1
/******************************/******************************/
/* */
/* DI Data Cleaning */
/* */
/* Example 1.1 */
/* */
/******************************/
title 'Frequency Tables for Character Variables';
proc freq data = clean.patients;
tables GENDER DX AE / nocum nopercent;
run;
title 'Horizontal Bar Charts for Character Variables';
proc gchart data = clean.patients;
hbar GENDER DX AE / missing;
run;
quit;
title 'Pie Charts for Character Variables';
proc gchart data = clean.patients;
pie GENDER DX AE / missing;
run;
quit;
title;
2
/******************************/******************************/
/* */
/* DMDQ Data Cleaning */
/* */
/* Example 1.2 */
/* */
/******************************/
title1 'Listing of Character Variable Missing Values';
data _null_;
set clean.patients;
file print;
if PATNO = ' ' then put 'Obs '_N_ 'Missing PATNO';
if GENDER =' ' then put 'Obs ' _N_ PATNO = 'Missing GENDER';
if DX = ' ' then put 'Obs ' _N_ PATNO = 'Missing DX';
if AE =' ' then put 'Obs ' _N_ PATNO = 'Missing AE';
run;
title1 'Listing of Invalid Character Variable Values';
title2 'Excluding Missing Values';
data _null_;
set clean.patients;
file print;
if verify (PATNO, ' 0123456789') ne 0 then
put 'Obs '_N_ 'Invalid 'PATNO =;
if GENDER not in ('F' 'M' ' ')then
put 'Obs ' _N_ PATNO = GENDER =;
if verify (DX, ' 0123456789') ne 0 then
put 'Obs ' _N_ PATNO = DX =;
if AE not in ('0' '1' ' ') then
put 'Obs ' _N_ PATNO = AE =;
run;
title;
3
/******************************/******************************/
/* */
/* DMDQ Data Cleaning */
/* */
/* Example 1.3 */
/* */
/******************************/
title1 'Listing of Missing Values for PATNO';
proc print data = clean.patients;
where PATNO = ' ';
var PATNO;
run;
title1 'Listing of Missing Values for GENDER';
proc print data = clean.patients;
where GENDER = ' ';
var PATNO GENDER;
run;
title1 'Listing of Missing Values for DX';
proc print data = clean.patients;
where DX = ' ';
var PATNO DX;
run;
title1 'Listing of Missing Values for AE';
proc print data = clean.patients;
where AE = ' ';
var PATNO AE;
run;
title1 'Listing of Invalid Values for PATNO';
title2 'Excluding Missing Values';
proc print data = clean.patients;
where verify (PATNO, ' 0123456789') ne 0;
var PATNO;
run;
title1 'Listing of Invalid Values for GENDER';
proc print data = clean.patients;
where GENDER not in ('F' 'M' ' ');
var PATNO GENDER;
run;
4
title1 'Listing of Invalid Values for DX';
proc print data = clean.patients;
where verify (DX, ' 0123456789') ne 0;
var PATNO DX;
run;
title1 'Listing of Invalid Values for AE';
proc print data = clean.patients;
where AE not in ('0' '1' ' ');
var PATNO AE;
run;
title;
5
/******************************/******************************/
/* */
/* DMDQ Data Cleaning */
/* */
/* Example 1.4 */
/* */
/******************************/
proc format library = Library;
value $PATNO '001' - '999' = 'Valid'
' ' = 'Missing'
other = 'Invalid';
value $GENDER 'F' , 'M' = 'Valid'
' ' = 'Missing'
other = 'Invalid';
value $DX '001' - '999' = 'Valid'
' ' = 'Missing'
other = 'Invalid';
value $AE '0','1' = 'Valid'
' ' = 'Missing'
other = 'Invalid';
run;
6
/******************************/******************************/
/* */
/* DMDQ Data Cleaning */
/* */
/* Example 1.5 */
/* */
/******************************/
title1 'Using Formats to Identify Invalid Values';
title2 'In Frequency Tables';
proc freq data = clean.patients;
format PATNO $PATNO. GENDER $GENDER. DX $DX. AE $AE.;
tables PATNO GENDER DX AE / nocum nopercent missing;
run;
title2 'In Horizontal Bar Charts';
proc gchart data = clean.patients;
format PATNO $PATNO. GENDER $GENDER. DX $DX. ae $AE.;
hbar PATNO GENDER DX AE / missing;
run;
quit;
title2 'In Pie Charts';
proc gchart data = clean.patients;
format PATNO $PATNO. GENDER $GENDER. DX $DX. ae $AE.;
pie PATNO GENDER DX AE / missing;
run;
quit;
title;
7
/******************************/******************************/
/* */
/* DMDQ Data Cleaning */
/* */
/* Example 1.6 */
/* */
/******************************/
title 'Listing of Missing and Invalid Values for PATNO';
proc print data = clean.patients;
where put(PATNO,$PATNO.) ne 'Valid';
format PATNO $PATNO.;
var PATNO;
run;
title 'Listing of Missing and Invalid Values for GENDER';
proc print data = clean.patients;
where put(GENDER,$GENDER.) ne 'Valid';
format GENDER $GENDER.;
var PATNO GENDER;
run;
title 'Listing of Missing and Invalid Values for DX';
proc print data = clean.patients;
where put(DX,$DX.) ne 'Valid';
format DX $DX.;
var PATNO DX;
run;
title 'Listing of Missing and Invalid Values for AE';
proc print data = clean.patients;
where put(AE,$AE.) ne 'Valid';
format AE $AE.;
var PATNO AE;
run;
title;  
  
    
      
        
          
1
/******************************/******************************/
/* */
/* DI Data Cleaning */
/* */
/* Example 2.1 */
/* */
/******************************/
title 'Means Procedure for Numeric Variables';
proc means data = clean.patients maxdec = 2
n nmiss mean stddev min max;
var HR SBP DBP;
run;
title 'Vertical Bar Charts for Numeric Variables';
proc gchart data = clean.patients;
vbar HR SBP DBP / missing;
run;
quit;
title;
2
/******************************/******************************/
/* */
/* DMDQ Data Cleaning */
/* */
/* Example 2.2 */
/* */
/******************************/
title1 'Listing of Numeric Variable Missing Values';
data _null_;
set clean.patients;
file print;
if HR = . then put 'Obs '_N_ PARNO = 'Missing HR';
if SBP = . then put 'Obs ' _N_ PATNO = 'Missing SBP';
if DBP = . then put 'Obs ' _N_ PATNO = 'Missing DBP';
run;
title1 'Listing of Invalid Numeric Variable Values';
title2 'Excluding Missing Values';
data _null_;
set clean.patients;
file print;
if (HR lt 40 or HR gt 100) and HR ne . then
put 'Obs '_N_ PATNO = 'Invalid HR = ' HR;
if (SBP lt 80 or SBP gt 200) and SBP ne . then
put 'Obs ' _N_ PATNO = 'Invalid SBP = ' SBP;
if (DBP lt 60 or DBP gt 120) and DBP ne . then
put 'Obs ' _N_ PATNO = 'Invalid DBP = ' DBP;
run;
title;
3
/******************************/******************************/
/* */
/* DMDQ Data Cleaning */
/* */
/* Example 2.3 */
/* */
/******************************/
title1 'Listing of Missing Values for HR';
proc print data = clean.patients;
where HR = .;
var PATNO HR;
run;
title1 'Listing of Missing Values for SBP';
proc print data = clean.patients;
where SBP = .;
var PATNO SBP;
run;
title1 'Listing of Missing Values for DBP';
proc print data = clean.patients;
where DBP = .;
var PATNO DBP;
run;
title1 'Listing of Invalid (Out of Range) Values for HR';
title2 'Excluding Missing Values';
proc print data = clean.patients;
where (HR not between 40 and 100) and (HR is not missing);
var PATNO HR;
run;
title1 'Listing of Invalid (Out of Range) Values for SBP';
proc print data = clean.patients;
where (SBP not between 80 and 200) and (SBP is not missing);
var PATNO SBP;
run;
title1 'Listing of Invalid (Out of Range) Values for DBP';
proc print data = clean.patients;
where (DBP not between 60 and 120) and (DBP is not missing);
var PATNO DBP;
run;
title;
4
/******************************/******************************/
/* */
/* DMDQ Data Cleaning */
/* */
/* Example 2.4 */
/* */
/******************************/
proc format library = Library;
value HRcheck 40-100 = 'OK'
. = 'Missing'
other = 'Invalid';
value SBPcheck 80-200 = 'OK'
. = 'Missing'
other = 'Invalid';
value DBPcheck 60-120 = 'OK'
. = 'Missing'
other = 'Invalid';
run;
/******************************/
/* */
/* DMDQ Data Cleaning */
/* */
/* Example 2.5 */
/* */
/******************************/
title1 'Using Formats to Identify Invalid Values';
title2 'In Frequency Tables';
proc freq data = clean.patients;
format HR HRcheck. SBP SBPcheck. DBP DBPcheck. ;
tables HR SBP DBP / nocum nopercent missing;
run;
title;
5
/******************************/******************************/
/* */
/* DMDQ Data Cleaning */
/* */
/* Example 2.6 */
/* */
/******************************/
title 'Listing of Missing and Invalid (Out of Range) Values for HR';
proc print data = clean.patients;
where put(HR,HRcheck.) ne 'OK';
var PATNO HR;
run;
title 'Listing of Missing and Invalid (Out of Range) Values for SBP';
proc print data = clean.patients;
where put(SBP,SBPcheck.) ne 'OK';
var PATNO SBP;
run;
title 'Listing of Missing and Invalid (Out of Range) Values for DBP';
proc print data = clean.patients;
where put(DBP,DBPcheck.) ne 'OK';
var PATNO DBP;
run;
title;
6
/************************/******************************/
/* */
/* DMDQ Data Cleaning */
/* */
/* Example 2.7a */
/* */
/******************************/
title 'Normal Plots for Detection of Potential Outliers';
proc capability data = clean.patients noprint;
qqplot HR SBP DBP;
run;
title;
/******************************/
/* */
/* DMDQ Data Cleaning */
/* */
/* Example 2.7b */
/* */
/******************************/
title 'Normal Plots for Detection of Potential Outliers';
proc capability data = clean.patients noprint;
qqplot HR;
where HR lt 600;
run;
title;  
  
    
      
       
        
          
  
  
