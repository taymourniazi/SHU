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
  
    
## Hive Query Language  
  
  
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
  
    
      
      
