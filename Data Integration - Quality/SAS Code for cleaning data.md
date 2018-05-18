title1 'Listing of Numeric Variable Missing Values';  
data _null_;  
set clean.flights;  
file print;  
if BOARDED = . then put 'Obs '_N_ PARNO = 'Missing BOARDED';  
if FREIGHT = . then put 'Obs ' _N_ ID = 'Missing FREIGHT';  
if MAIL = . then put 'Obs ' _N_ ID = 'Missing MAIL';  
if revenue = . then put 'Obs ' _N_ ID = 'Missing revenue';  
run;  
title1 'Listing of Invalid Numeric Variable Values';  
title2 'Excluding Missing Values';  
data _null_;  
set clean.flights;  
file print;  
if (BOARDED lt 200 or BOARDED gt 500) and BOARDED ne . then  
put 'Obs '_N_ ID = 'Invalid BOARDED = ' BOARDED;  
if (FREIGHT lt 150 or FREIGHT gt 550) and FREIGHT ne . then  
put 'Obs ' _N_ ID = 'Invalid FREIGHT = ' FREIGHT;  
if (MAIL lt 100 or MAIL gt 250) and MAIL ne . then  
put 'Obs ' _N_ ID = 'Invalid MAIL = ' MAIL;  
if (revenue lt 25000 or revenue gt 65000) and revenue ne . then  
put 'Obs ' _N_ ID = 'Invalid revenue = ' revenue;  
run;  
title;  
  
• Boarded should lie between 200 and 500  
• Freight should lie between 150 and 550  
• Mail should lie between 100 and 250  
• Revenue should lie between 25000 and 65000  
  
  
   
title1 'Listing of Missing Values for BOARDED';  
proc print data = clean.flights;  
where BOARDED = .;  
var ID BOARDED;  
run;  
title1 'Listing of Missing Values for FREIGHT';  
proc print data = clean.flights;  
where FREIGHT = .;  
var ID FREIGHT;  
run;  
title1 'Listing of Missing Values for MAIL';  
proc print data = clean.flights;  
where MAIL = .;  
var ID MAIL;  
run;  
title1 'Listing of Missing Values for Revenue';  
proc print data = clean.flights;  
where Revenue = .;  
var ID Revenue;  
run;  
title1 'Listing of Invalid (Out of Range) Values for BOARDED';  
title2 'Excluding Missing Values';  
proc print data = clean.flights;  
where (BOARDED not between 200 and 500) and (BOARDED is not missing);  
var ID BOARDED;  
run;  
title1 'Listing of Invalid (Out of Range) Values for FREIGHT';  
proc print data = clean.flights;  
where (FREIGHT not between 150 and 550) and (FREIGHT is not missing);  
var ID FREIGHT;  
run;  
title1 'Listing of Invalid (Out of Range) Values for MAIL';  
proc print data = clean.flights;  
where (MAIL not between 100 and 250) and (MAIL is not missing);  
var ID MAIL;  
run;  
title1 'Listing of Invalid (Out of Range) Values for revenue';  
proc print data = clean.flights;  
where (Revenue not between 25000 and 65000) and (Revenue is not missing);  
var ID Revenue;  
run;  
title;  
  
• Boarded should lie between 200 and 500  
• Freight should lie between 150 and 550   
• Mail should lie between 100 and 250  
• Revenue should lie between 25000 and 65000  
proc format library = Library;  
value Boardedcheck 200-500 = 'OK'  
. = 'Missing'  
other = 'Invalid';  
value Freightcheck 150-550 = 'OK'  
. = 'Missing'  
other = 'Invalid';  
value Mailcheck 100-250 = 'OK'  
. = 'Missing'  
other = 'Invalid';  
value Revenuecheck 25000-65000 = 'OK'  
. = 'Missing'  
other = 'Invalid';  
run;  
  
title1 'Using Formats to Identify Invalid Values';  
title2 'In Frequency Tables';  
proc freq data = clean.flights;  
format Boarded Boardedcheck. Freight Freightcheck. Mail Mailcheck. Revenue Revenuecheck;  
tables Boarded Freight Mail Revenue / nocum nopercent missing;  
run;  
title;  
  
• Boarded should lie between 200 and 500  
• Freight should lie between 150 and 550  
• Mail should lie between 100 and 250  
• Revenue should lie between 25000 and 65000  
  
title 'Listing of Missing and Invalid (Out of Range) Values for Boarded';  
proc print data = clean.flights;  
where put(Boarded,Boardedcheck.) ne 'OK';  
var Id Boarded;  
run;  
title 'Listing of Missing and Invalid (Out of Range) Values for Freight';  
proc print data = clean.flights;  
where put(Freight,Freightcheck.) ne 'OK';  
var Id Freight;  
run;  
title 'Listing of Missing and Invalid (Out of Range) Values for Mail';  
proc print data = clean.flights;  
where put(Mail, Mailcheck.) ne 'OK';  
var Id Mail;  
run;  
title 'Listing of Missing and Invalid (Out of Range) Values for Revenue';  
proc print data = clean.flights;  
where put(Revenue, Revenuecheck.) ne 'OK';  
var Id Revenue;  
run;  
  
title;  
  
