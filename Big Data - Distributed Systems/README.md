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
  
