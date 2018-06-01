# Big Data & Distributed Systems Module
## Acquiring, Manipulating Data in Hadoop, Hive, Pig (Hue) - Wordcount program
  
    
Run Hadoop and pass it some parameters that tell it to do the wordcount.  
The first word is hadoop, to call the hadoop executable. The parameters   
that follow in this case are:    
• jar – to tell hadoop to run a jar file  
• the location and filename of the jar file that hadoop should run  
• the class to call from within the jar file (wordcount in this case)  
• the location and filename of the document to be counted (which must be stored in HDFS)  
• the location that output should be stored in – again in HDFS. (This should not already exist)  
For interest, in the version of the Sandbox we are using, the hadoop executable is stored in:  
/usr/hdp/2.2.0.0-2041/hadoop/bin.  
The wordcount function can be found in this jar: /usr/hdp/2.2.0.0-2041/hadoop-mapreduce/hadoopmapreduce-examples.jar  
So the full command we need to type into the PuTTY session is:  
hadoop jar /usr/hdp/2.2.0.0-2041/hadoop-mapreduce/hadoop-mapreduce-examples.jar  
wordcount /user/hue/tutorials/4300.txt /user/hue/tutorials/UlyssesOutput  
  
## HCatalog, Hive and Pig – World Airports Dataset  
  
    
# Hive Query Language  
  
  
#### How many Heliports are there in India?
  
select * from airports  
where iso_country = "IN"  
AND type = "heliport" ;  
  
    
#### Count of heliports in India. 
      
select count(*) as Number from airports  
where iso_country = "IN"  
AND type = "heliport" ;  
  
    
#### How many heliports there are in each country?
      
select iso_country as CountryCode, count(ident) as NumberofHeliports  
from airports  
where type = "heliport"  
Group By iso_country ;  
  
    
#### Which large airport in India is at the highest elevation
    
select name, elevation_ft from airports  
where iso_country = "IN"  
AND type = "large_airport" ;  
  
    
     
#### ORDER BY clause to present the data return above in sorted order
     
select name, elevation_ft from airports  
where iso_country = "IN"  
AND type = "large_airport"  
ORDER BY elevation_ft ;   
   
   
 #### which was THE highest airport in SQL  
   
select name, elevation_ft from airports  
where elevation_ft =  
(select max(elevation_ft) from airports  
where iso_country = "IN"  
AND type = "large_airport" ) ;  
       
         
           
  
 #### which was THE highest airport in HIVEQL  
   
      
FROM airports  
JOIN  
( SELECT Max(elevation_ft) AS maxelevation  
FROM airports  
WHERE iso_country = "IN"  
AND type = "large_airport"  
) maxdata  
ON airports.elevation_ft = maxdata.maxelevation  
WHERE iso_country = "IN" ;  
  
#### JOIN Tables  
  
select A.name, B.passengers_2011  
from airports A, Top100 B  
where A.iata_code = B.code ;  
  
#### For each country, how many airports appear in the Top 100 list?     
       
select A.iso_country, count(A.iata_code) as NumberofAirportsinTop100  
from airports A, Top100 B  
where A.iata_code = B.code  
Group by A.iso_country ;  
  
    
        
#### A three-way join so that the output has country name instead of the code.  
    
      
select C.countryname, count(A.iata_code) as NumberofAirportsinTop100  
from airports A, Top100 B, countrycodes C  
where A.iata_code = B.code  
AND A.iso_country = C.countrycode  
Group by C.countryname ;  
  
    
#### Create a table to accept the data  
  
    
Create Table T100 (  
rank INT,  
reg STRING,  
code STRING,  
airport STRING,  
airport_location STRING ,  
passengers_2011 INT,  
passengers_2010 INT,  
change DOUBLE )  
ROW FORMAT DELIMITED  
FIELDS TERMINATED BY ","  
ESCAPED BY '"' ;  
    
# Using Pig  
   
## PIG - Data Manipulatio Tool for Hadoop  
  
    
allairports = LOAD 'default.airports' USING org.apache.hive.hcatalog.pig.HCatLoader();
DUMP 'allairports';

      
     
### Filtering in PIG  
  
    
#### Take allairports and restrict its contents to airports in Great Britain (GB country code).   
  
    
allairports = LOAD 'airports' USING org.apache.hive.hcatalog.pig.HCatLoader();  
gbairports = filter allairports by iso_country == 'GB' ;  
DUMP gbairports ;  
  
    
      
### Outputting to file from Pig  
   
allairports = LOAD 'airports' USING org.apache.hive.hcatalog.pig.HCatLoader();  
gbairports = filter allairports by iso_country == 'GB' ;  
gbcols = FOREACH gbairports GENERATE $13, $3, $6 ; ;  
STORE gbcols INTO '/user/hue/tutorials/GBData' USING PigStorage(',');  
  
    
#### To AND filtering conditions together and excludes records with no IATA code  

gbairports = filter allairports by (iso_country == 'GB') AND NOT (iata_code == '') ;
  
  
### Joining and Sorting in Pig  
  
    
      
#### Which GB Airports are in the Top 100 and then present them in a sorted order  
  
    
allairports = LOAD 'airports' USING org.apache.hive.hcatalog.pig.HCatLoader();  
gbairports = filter allairports by (iso_country == 'GB') AND NOT (iata_code == '') ;  
gbcols = FOREACH gbairports GENERATE $13 as iata, $3 as airportname ;  
T100 = LOAD 'top100' USING org.apache.hive.hcatalog.pig.HCatLoader();  
Some100 = FOREACH T100 GENERATE $2 as T_iata, $6 as passengers2011 ;  
JoinData = JOIN gbcols BY (iata), Some100 BY (T_iata);  
SortJoined = ORDER JoinData BY passengers2011 DESC ;  
Dump SortJoined ;  
  
    
### Using Inbuilt functions  
  
  
#### Group By Function
  
#### How many passengers there were in 2011 across all three London airports?  
  
     
T100 = LOAD 'top100' USING org.apache.hive.hcatalog.pig.HCatLoader();  
  
-- Removing everything to the right of the open bracket in the airport-location field  
  
Some100 = FOREACH T100 GENERATE TRIM(SUBSTRING($4, 0, INDEXOF($4, '(', 0))) as cityname, (ROUND($6/1000)) as Kpass2011 ;  
  
-- summing up passenger numbers by cityname  
  
G100 = FOREACH (GROUP Some100 BY cityname) GENERATE group, SUM(Some100.Kpass2011);  
Dump G100 ;  
  
### Loading data from files  
  
  
#### Use Load to bring data in directly in from a CSV file and use it in a Pig query.  
  
gb = LOAD '/user/hue/tutorials/GBData/part-m-00000' USING PigStorage(',') ;
dump gb ;  
  
#### reintroduce the argument if you also read from HCatalog, a join with Top 100  
  
gb = LOAD '/user/hue/tutorials/GBData/part-m-00000' USING PigStorage(',') AS  
(iatacode: chararray, name: chararray, elevation: int) ;  
T100 = LOAD 'top100' USING org.apache.hive.hcatalog.pig.HCatLoader();  
Some100 = FOREACH T100 GENERATE $2 as T_iata, $6 as passengers2011 ;  
JoinData = JOIN gb BY (iatacode), Some100 BY (T_iata);  
Dump JoinData  
  
    
### JSON formatted data  
  
#### The Current Weather For London  
   
##### londonweather.json     
       
{"coord":{"lon":-0.13,"lat":51.51},"sys":  
{"type":3,"id":60992,"message":0.0107,"country":"GB","sunrise":1424329544,"sunset":142436657  
1},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10d"}],"base":"cmc  
stations","main":  
{"temp":280.45,"humidity":84,"pressure":1019.4,"temp_min":280.45,"temp_max":280.45},"wind":  
{"speed":1,"gust":4.5,"deg":270},"rain":{"3h":2},"clouds":  
{"all":92},"dt":1424356829,"id":2643743,"name":"London","cod":200}    
   
##### loading the data into Pig, need to provide schema that describes the incoming data and pass that schema as a parameter.  
     
jdata = LOAD '/user/hue/tutorials/londonweather.json' USING JsonLoader('coord:  
(lon:double,lat:double), sys:(type:int,id:long, message:double, country: chararray, sunrise:  
double, sunset: double), weather:{(idd: long, main: chararray, desc: chararray, icon: chararray)},  
base: chararray, maindata: (temp: double, humidity: int, pressure: double, tempmin: double,  
tempmax:double), wind: (speed: double, gust: double, degr: int), rain: (hh: int), clouds: (all: int),  
dt: long, iddd: long, placename: chararray,cod: int') ;  
  
    
##### the schema allows to work with particular data items
  
    
tt = FOREACH jdata GENERATE placename, weather.descr ;  
dump tt ;  
  
 ## Running Pig from the Command Line  
   
yum install -y nano  
  
mkdir pigscripts  
  
cd pigscripts  
  
nano itairports.pig  
  
allairports = LOAD 'airports' USING org.apache.hive.hcatalog.pig.HCatLoader();  
itairports = filter allairports by (iso_country == 'IT') AND NOT (iata_code == '') ;  
itcols = FOREACH itairports GENERATE $13, $3, $6 ; ;  
STORE itcols INTO '/user/maria_dev/tutorials/ITData' USING PigStorage(',');  
  
    
pig -useHCatalog  
  
hadoop fs -get /user/maria_dev/tutorials/ITData/part-m-00000 ./  
  
#### cat command to view the content of the file
cat part-m-00000  
  
## Writing UDFs (User Defined Functions)  
  
fttom.outputSchema = "meters:float";  
function fttom(num){  
return num/3.281;  
}  
  
-- Calling a javascript function so need to register it  
Register '/usr/hdp/2.4.0.0-169/jsstuff.js' using javascript as jsstuff;  
allairports = LOAD 'airports' USING org.apache.hive.hcatalog.pig.HCatLoader();  
elemeters = FOREACH allairports GENERATE $3, jsstuff.fttom($6) as M ;  
dump elemeters ;  
  
#### Same output using Pig alone    
elemeters = FOREACH allairports GENERATE $3, $6/3.281 as M

#SQOOP  
  
# Usimg Sqoop to share data between hadoop and other databases
## Starting MySQL
chkconfig --levels 235 mysqld on  
  
service mysqld start  
mysql -u root  
  
###  Create Databse in sandbox terminal
  
CREATE DATABASE forhadoop;  
  
### Allow full access to forhadoop database  
  
GRANT ALL PRIVILEGES ON forhadoop.* TO root@localhost IDENTIFIED BY '1111';  
  
mysql -u root -p  
connect forhadoop  
  
### Creates country code table in Mysql  
  
create table countrycodes(ccode char(2), country_name varchar(30), PRIMARY KEY (ccode));  
desc countrycodes;  
  
#### CSV Out from Hadoop to local system  
hadoop fs copyToLocal hdfs://192.168.8.13.13.130/user/maria_dev/tutorials/countrycode.csv /root/countrycode.csv  
  
#### Using a file from the local system  
mysql forhadoop --local_infile=1 -u root -p
  
#### Loading Data in Mysql Database  
  
LOAD DATA LOCAL INFILE '/root/countrycode.csv' INTO TABLE countrycodes fields terminated by ',' LINES TERMINATED BY '\r\n';  
  
## Installing sqoop from command line  
  
yum install sqoop  
yum install mysql-connector-java  
  
## Importing from MySQL to Hadoop  
  
    
sqoop import --connect jdbc:mysql://localhost:3306/forhadoop --driver com.mysql.jdbc.Driver --  
username root --password 1111 --table countrycodes --target-dir /user/maria_dev/tutorials/frommysql  
  
#### Save MySQL data directly into Hive table one step by changing the final parameter and running the hive-import command  
  
sqoop import --connect jdbc:mysql://localhost:3306/forhadoop --driver com.mysql.jdbc.Driver --  
username root --password 1111 --table countrycodes --hive-import --hive-table default.ccodes  
  
#### Multiple tables in a MySQL database we can chose to bring them all into hive  
  
sqoop import-all-tables --connect jdbc:mysql://localhost:3306/forhadoop --username root --password 1111 –hive-import  
  
#### Selective about which rows to import using the –where parameter, it only import codes beginning with the letter U  
  
sqoop import --connect jdbc:mysql://localhost:3306/forhadoop --driver com.mysql.jdbc.Driver --  
username root --password 1111 --table countrycodes --where 'left(ccode,1)="U"' --hive-import --hivetable  
default.Ucodes  
  
## Exporting from Hadoop to MySQL  
  
allairports = LOAD 'airports' USING org.apache.hive.hcatalog.pig.HCatLoader();  
gbairports = filter allairports by iso_country == 'GB' ;  
gbcols = FOREACH gbairports GENERATE $13, $3, $6 ;  
STORE gbcols INTO '/user/hue/tutorials/GBData' USING PigStorage(',');  
  
#### Log into MySQL and create the table  
  
mysql forhadoop -u root -p  
use forhadoop ;  
create table gb2 (iata char(3), airport_name varchar(30), ele int, PRIMARY KEY (iata)) ;  
desc gb2 ;  
exit  
  
#### Use Sqoop export to take the data from hdfs and insert it into the MySQL table  
  
sqoop export --connect jdbc:mysql://localhost:3306/forhadoop --driver com.mysql.jdbc.Driver --  
username root --password 1111 --table gb2 --export-dir /user/maria_dev/tutorials/GBData  
  
select * from gb ;  
  
# Nifi
  
## Getting Nifi working  
  
curl -o install-nifi.sh https://raw.githubusercontent.com/hortonworks/tutorials/hdp/assets/realtime-event-processing/install-nifi.sh  
  
chmod +x ./install-nifi.sh  
#### get the tar file  
wget http://d3d0kdwqv675cq.cloudfront.net/HDF/centos6/1.x/updates/1.2.0.1/HDF-1.2.0.1-1.tar.gz  
tar -xvf HDF-1.2.0.1-1.tar.gz  
cd /root/HDF-1.2.0.1-1/nifi/conf  
nano nifi.properties  
#### change the port from 8080 to 8090 (Under the Web Properties section)  
nifi.web.http.port=8090  
bin/nifi.sh install  
Service nifi installed  
service nifi start  
service nifi status  
  
#### To move the countrycodes.csv onto Linux:  
hadoop fs -get /user/maria_dev/tutorials/countrycode.csv /tmp/nifi/input/  
  
#### Use the countrycode.csv and turn it into json format.  
  
  
# Hadoop Using PIG  
    
#### Load input data from local input directory  
A = LOAD 'wasbs:///example/data/gutenberg/davinci.txt';  
#### TOKENIZE splits the line into a field for each word.  
#### flatten will take the collection of records returned by  
#### Parse and clean input data  
B = FOREACH A GENERATE FLATTEN(TOKENIZE((chararray)$0)) AS word;  
C = FILTER B BY word MATCHES '\\w+';  
  
#### Explicit the GROUP-BY: group them together by each word.  
D = GROUP C BY word;  
  
#### Generate output data in the form: <word, counts>  
E = FOREACH D GENERATE group, COUNT(C);  
  
#### Store output data in local output directory  
STORE E INTO 'wasbs:///example/data/wordCount1';  
  
# Java MapReduce  
  
yarn jar wordcountjava-1.0-SNAPSHOT.jar  
org.apache.hadoop.examples.WordCount  
wasbs:///example/data/gutenberg/davinci.txt  
wasbs:///example/data/wordcountout3  
  
#### view the results  
  
hdfs dfs -cat wasbs:///example/data/wordcountout3/*  
  
    
## Using pig functions to transform the data  
  
/*  
Load stop words  
*/  
stop_words_list = LOAD  
'wasbs:///example/data/gutenberg/stopwords.txt' USING PigStorage();  
stopwords = FOREACH stop_words_list GENERATE  
FLATTEN(TOKENIZE($0));  
  
/*  
Load the document corpus and tokenize to extract the words  
*/  
doc1 = LOAD  
'wasbs:///example/data/gutenberg/comp.txt' AS (words:chararray);  
docWords1 = FOREACH doc1 GENERATE FLATTEN(TOKENIZE(words)) AS  
word;  
  
/*  
Combine the contents of the relations docWords1 and docWords2  
*/  
combined_docs = docWords1;  
/*  
Convert to lowercase, remove stopwords, punctuations, spaces, numbers.  
Replace nulls with the value "dummy string"  
*/  
lowercase_data = FOREACH combined_docs GENERATE  
FLATTEN(TOKENIZE(LOWER($0))) as word;  
joind = JOIN stopwords BY $0 RIGHT OUTER, lowercase_data BY $0;  
stop_words_removed = FILTER joind BY $0 IS NULL;  
punctuation_removed = FOREACH stop_words_removed  
{  
replace_punct = REPLACE($1,'[\\p{Punct}]','');  
replace_space = REPLACE(replace_punct,'[\\s]','');  
replace_numbers = REPLACE(replace_space,'[\\d]','');  
GENERATE replace_numbers AS replaced_words;  
}  
replaced_nulls = FOREACH punctuation_removed GENERATE  
(SIZE($0) > 0 ? $0 : 'dummy string') as word;  
  
B = FOREACH replaced_nulls GENERATE FLATTEN(TOKENIZE((chararray)$0)) AS word;  
  
C = FILTER B BY word MATCHES '\\w+';  
/* Explicit the GROUP-BY: group them together by each word.*/  
D = GROUP C BY word;  
  
/* Generate output data in the form: <word, counts> */  
E = FOREACH D GENERATE group, COUNT(C);  
DUMP E;  
  
# Pig User Defined Functions (UDF)
### Basic Python UDF  
  
pig/udfs/my_first_udf.py

from pig_util import outputSchema
@outputSchema('value:int') 
def return_one():
   """
   Return the integer value 1
   """
   return 1
  
#### Pig script registers the Python UDF and calls the return_one() function in a FOREACH statement  
  
REGISTER '/home/hduser/udf/my_first_udf.py' USING streaming_python AS pyudfs;  
A = LOAD 'wasbs:///example/data/gutenberg/davinci.txt';  
B = FOREACH A GENERATE pyudfs.return_one();  
STORE B INTO 'wasbs:///example/data/wordCount7';  
  

#### 1) EVAL UDFs  
    
REGISTER '/home/hduser/udf/upper.py' USING streaming_python AS my_udfs;  
users = LOAD 'user_data' AS (name: chararray);  
upper_users = FOREACH users GENERATE my_udfs.to_upper_case(name);  
  
#### 2) Filter UDFs  
  
user_messages = LOAD 'user_twits' AS (name:chararray, message:chararray);  
rude_messages = FILTER user_messages by my_udfs.contains_negative_words(message);  
  
  
