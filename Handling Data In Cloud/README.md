# CodesPractice

## Content

# Cassandra
### Configure Cassandra Cluster  
  
    
./stopalldbs.sh  
./cassandra/cassandraconfig.sh  
sudo service cassandra start  
nodetool status  
thisNode=$(hostname -I)  
echo $thisNode  
cqlsh $thisNode  
  
### Cassandra on a Single Node  
  
    
desc keyspaces;  
CREATE KEYSPACE flights_ks WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };  
use flights_ks;  
cqlsh:flights_ks>  
CREATE TABLE flightdetails  
(  
   airline varchar PRIMARY KEY,  
   kms int,  
   flights int,  
   hrs float,  
   pass int  
);  
   
desc schema;  
INSERT INTO flightdetails (Airline, Kms, Flights, Hrs, Pass) VALUES ('BA City Flyer', 300, 545, 686, 30031);  
SELECT * FROM flightdetails;  
INSERT INTO flightdetails (Airline, Kms, Flights, Hrs, Pass) VALUES ('Aurigny Air Services', 192, 1388, 887, 26585);  
SELECT * FROM flightdetails WHERE airline = 'Aurigny Air Services';  
SELECT * FROM flightdetails WHERE kms = 192;  
CREATE INDEX IF NOT EXISTS idx_kms ON flightdetails (kms);  
INSERT INTO flightdetails (Airline, Kms, Flights, Hrs, Pass) VALUES ('BMI Group', 192, 2435, 2922.7, 142804);  
COPY flightdetails (airline, kms, flights, hrs, pass) FROM '/home/student/cassandra/flightDetails.csv';  
UPDATE flightdetails   
    SET Kms = 1000,   
    Flights = 2000,   
    Hrs = 3000,   
    Pass = 4000  
WHERE Airline = 'BA City Flyer';  
  
  
### Cassandra Cluster and Replication  
  
cqlsh $thisNode  
CREATE KEYSPACE customers_ks WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 2 };  
use customers_ks;  
CREATE TABLE customers_table   
(  
   customerid int PRIMARY KEY,  
   name varchar,  
   address varchar  
);  
INSERT INTO customers_table (customerid, name, address) VALUES (1, 'Joe Bloggs', '12 Acacia Ave, Sheffield, S44 1PQ');  
INSERT INTO customers_table (customerid, name, address) VALUES (2, 'Mary Smith', '42 Furnival Ave, Sheffield, S1 9AB');  
desc schema;  
cqlsh $thisNode  
desc schema;  
quit  
sudo service cassandra stop  
cqlsh $thisNode  
desc schema;  
cat > cassandratimetest  
use users_ks;  
SELECT * FROM users_table;  
cat cassandratimetest  
cqlsh $thisNode < cassandratimetest  
time cqlsh $thisNode < cassandratimetest  
  
     
          
                   
                
# Neo4j
  
## Content
  
  
### Movies Graph database  
  
  
hostname -I  
./stopalldbs.sh  
./neo4j/setneo4jdatabase.sh  
sudo service neo4j start  
http://<Cloud Data VM IP Address>:7474/browser/  
    
MATCH (actor)-[:ACTED_IN]->(movie)   
RETURN actor, movie  
  
MATCH (actor:Person)-[:ACTED_IN]->(movie:Movie)  
RETURN actor.name AS Actor, collect(movie.title) AS Movies  
  
MATCH (actor)-[:ACTED_IN]->(movie)   
WHERE movie.title IN ['The Matrix']  
RETURN actor, movie  
  
MATCH (actor)-[:ACTED_IN]->(movie)   
WHERE movie.title IN ['The Matrix']   
OR movie.title IN ['The Matrix Reloaded']  
RETURN actor, movie  
  
// Add movie  
CREATE (BillnTed:Movie {title:'Bill and Teds Excellent Adventure', released:1989})  
  
// Add new persons  
CREATE (AlexW:Person {name:'Alex Winter', born:1965})  
CREATE (GeorgeC:Person {name:'George Carlin', born:1937})  
CREATE (Stephk:Person {name:'Stephen Herek', born:1958})  
  
// Create ACTED_IN & DIERECTED relationships  
CREATE   
  (AlexW)-[:ACTED_IN {roles:['Bill Preston']}]->(BillnTed),  
  (GeorgeC)-[:ACTED_IN {roles:['Rufus']}]->(BillnTed),  
  (Stephk)-[:DIRECTED]->(BillnTed)  
// Retrieve Person node for Keanu Reeves who already exists  
MATCH (p:Person {name:'Keanu Reeves'})   
  
// Retrieve the Movie node - just added  
MATCH (m:Movie {title:'Bill and Teds Excellent Adventure'})  
  
// Create an ACTED_IN relationship between Keanu Reeves & movie  
CREATE (p)-[:ACTED_IN {roles:['Ted Logan']}]->(m)  
sudo service neo4j stop  
    
    
       
### Northwind Example  
  
    
A)	MATCH (actor)-[:ACTED_IN]->(movie)   
WHERE actor.name IN ['Keanu Reeves']  
RETURN movie.title  
  
MATCH (actor)-[:ACTED_IN]->(movie)   
WHERE movie.title IN ['Johnny Mnemonic']   
RETURN actor, movie  
  
MATCH (cust:Customer)-[:PURCHASED]->(ord:Order)   
RETURN DISTINCT cust.companyName as Customers, collect(ord.orderID) AS Orders  
  
MATCH (cust:Customer)-[:PURCHASED]->(:Order)-[o:ORDERS]->(p:Product)  
RETURN  cust.companyName as Customers, collect(distinct p.productName) AS Products  
  
MATCH (cust:Customer)-[:PURCHASED]->(:Order)-[o:ORDERS]->(p:Product)  
WHERE cust.contactName = 'Roland Mendel'  
RETURN DISTINCT cust.contactName as CustomerName, SUM(o.quantity) AS TotalProductsPurchased  
    
# MongDB
  
## Content
  
  

### Configure MongoDB
  
./stopalldbs.sh  
./mongo/mongoconfig.sh  
sudo service mongod start  
mongo --host $(hostname -I) --quiet  
show dbs  
  
## CRUD Operations in MongoDB  
  
use location  
zip1 = {  
	"city": "TWENTYNINE PALMS",   
	"loc": [-116.06041, 34.237969],   
	"pop": 11412,   
	"state": "CA",   
	"_id": "92278"}  
db.zipCodes.insert(zip1)  
WriteResult({ "nInserted" : 1 })  
db.zipCodes.find({"_id": "92278"})  
db.zipCodes.find()  
db.zipCodes.find({pop: 1470})  
db.zipCodes.remove({population: 1470})  
zip200 = {  
"city": "WOODBRIDGE",   
"coordinates": [-74.284542, 40.555973],   
"pop": 15827,   
"state": "NJ",   
"_id": "07095",   
"typeoftrain": "Steam locomotive"}  
db.getCollectionNames()  
or  
show collections  
db.zipCodes.count()  
db.zipCodes.count({pop: {$gt: 10000} })  
db.zipCodes.find(  
	{ $and: [  
		{pop: {$gt : 64000}},   
		{"_id": {$gt:"15000"}}  
	]}  
)  
db.zipCodes.find(  
	{ $and: [  
		{pop: {$gt : 64000}},  
		{"_id": {$gt:"15000"}}  
	]}  
).pretty()  
  
  
  
db.zipCodes.find(  
	{ $or: [  
		{ pop: {$gt : 64000}},   
		{ "_id": {$gt:"96000"}}  
	]}  
)  
var pop_range = {$gt: 60000, $lt: 70000}  
db.zipCodes.find({ "pop" :  pop_range }).limit(10)  
db.zipCodes.find( { state: { $in: [ "NY", "CT"] } } )  
db.zipCodes.find().sort({city: 1}).limit(10)  
db.zipCodes.find().sort({pop: -1}).limit(5)  
db.zipCodes.find({typeoftrain: {$exists: true} })  
db.zipCodes.ensureIndex({pop: -1 })  
db.zipCodes.getIndexes()  
db.zipCodes.dropIndex({pop: -1})  
db.zipCodes.update(  
	{"_id": "07095"},   
	{ $set: {"typeoftrain": "electric locomotives"}}  
)  
WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })  
db.zipCodes.update(  
	{"_id": "07095"},   
	{ $rename: {"typeoftrain": "trainType"}}  
)  
db.zipCodes.update(   
	{"_id": "07095"},   
	{$set: {"trackLine": "Single"}}  
)  
db.zipCodes.update(  
	{"_id": "07095"},   
	{ $unset: { "trainType": "", "trackLine": "" }}  
)  
db.zipCodes.update(  
	{"_id": "08732"},   
	{  
		"city": "ISLAND HEIGHTS",   
		"loc": [-74.146787, 39.943197],   
		"pop": 1470,   
		"state": "NJ",   
		"_id": "08732"  
	},   
	{upsert: true}  
)  
db.zipCodes.drop()  
db.dropDatabase()


## Moving bulk data into Mongo

cd mongo  
ls -l  
grades.json  
students.csv  
cat grades.json  
cat students.csv  
mongoimport --host $(hostname -I) --db studentDB --collection grades --file /home/student/mongo/grades.json  
mongoimport --host $(hostname -I) --db studentDB --collection students --type=csv --file /home/student/mongo/students.csv --headerline    
--fields=<field>[,<field>]...  
	
## Query using a reference  
  
  
mongo --host $(hostname -I) --quiet  
stu = db.students.findOne({"name": "Valentin Easton"})  
db.grades.find({"student_id": stu._id}).pretty()  
db.students.aggregate( [  
	{$match: { _id: 1} },  
	{$lookup: {  
		from: "grades",   
		localField: "_id",  
		foreignField: "student_id",  
		as: "grades"}}  
] ).pretty()  
{  
   $lookup:  
   {  
      from: <collection to join>,  
      localField: <field from the input documents>,  
      foreignField: <field in documents of "from" collection>,  
      as: <output array field>  
   }  
}  
cat > mongotimetest  
use studentDB  
db.students.find().sort({age: -1}).limit(5)  
cat mongotimetest  
mongo --host $(hostname -I) --quiet < mongotimetest  
time mongo --host $(hostname -I) --quiet < mongotimetest  
hostname -I  
  
    
      
        
        
  
# Oracle APEX - GUI Environment (SQL PLUS)  
## Performance Tuning  
  
select a.cityname, b.countryname  
from cities a, countries b  
where b.isocode = a.countrycode  
Order By a.cityname ;  
  
    
select a.cityname, b.countryname  
from cities a, countries b  
where b.isocode = a.countrycode  
and b.countryname = 'United Kingdom'  
Order By a.cityname ;  
  
  
select a.cityname, b.countryname  
from cities a, countries b  
where b.isocode = a.countrycode  
and a.population > 1000000  
and a.cityname like 'Z%'  
and b.countryname <> 'China' ;  
  
  
select /*+ FIRST_ROWS(2) */ a.cityname, b.countryname  
from cities a, countries b   
where b.isocode = a.countrycode  
and a.population > 1000000  
and a.cityname like 'Z%'  
and b.countryname <> 'China' ;  
  
  
select * from bigtab1  
where created_date < '01-Jun-2014' ;  
  
  
SET TIMING ON  
  
CREATE TABLE mybig as SELECT * from bigtab1 ;  
  
  
select * from mybig where created_date < '01-Jun-2014' ;  
  
  
Create Index cdate on mybig(created_date) ;  
  
  
select * from mybig where lookup id = 2 ;  
  
  
select * from mybig where id between 6677 and 7777 ;  
  
  
create index idindex on mybig(id) ;  
  
create bitmap index lookupB on mybig(lookup_id) ;  
select * from mybig where lookup id = 2 ;  
  
  
-- template testbed script  
-- stop displaying output to the monitor as this slows things down  
SET TERMOUT OFF  
-- start the STAT trace  
SET AUTOTRACE ON STAT  
--spool the output to a file for future ref  
SPOOL f:\tutorials\testresults.txt  
--start the timer  
Timing START timer1  
-- call the SQL script containing the test DML/DDL  
START f:\tutorials\testme  
--stop timer  
Timing STOP timer1  
SET TERMOUT ON  
SET AUTOTRACE OFF  
SPOOL off  
  
  
start f:\tutorials\test_template  
  
-- IOT must have a primary key  
CREATE TABLE CDSIOT(CDID Integer NOT NULL,  
TITLE VARCHAR2(50) NOT NULL,  
CONDUCTOR VARCHAR2(30),  
ORCHESTRA VARCHAR2(30),  
COMPOSERID Integer NOT NULL,  
PRIMARY KEY(CDID),  
FOREIGN KEY(COMPOSERID) REFERENCES COMPOSERS(COMPOSERID))  
ORGANIZATION INDEX ;  
  
  
INSERT INTO CDSIOT  
SELECT cdid, Title, Conductor, Orchestra, composerid  
FROM CDS ;  
  
