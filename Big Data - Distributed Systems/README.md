# Big Data & Distributed Systems Module
## Acquiring, Manipulating Data in Hadoop, Hive, Pig (Hue) - Wordcount program
  
    
run Hadoop and pass it some parameters that tell it to do the wordcount.  
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
  
  
