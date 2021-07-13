# Logs-Monitoring-ELK
In this project, I have worked on logs monitoring for Spring Boot Application using ELK Stack (Elastic search, Logstash and Kibana).

# How to Run :

1 - Download Elasticsearch .zip file from [Elasticsearch Downloads](https://www.elastic.co/downloads/elasticsearch). Extract the .zip file and run "elasticsearch.bat" file in /bin folder. Goto http://localhost:9200/ and you can see the Elasticsearch webpage.

2 - Download Kibana .zip file from [Kibana Downloads](https://www.elastic.co/downloads/kibana). Extract the .zip file and run "kibana.bat" file in /bin folder. Goto http://localhost:5601/ and you can see the Kibana webpage. Create a new Index "logstash-*". Goto Discover Page and you will see the logs there when you run the spring boot application.

3 - Download Logstash .zip file from [Logstash Downloads](https://www.elastic.co/downloads/logstash). Extract the .zip file. Create "logstash.conf" file inside the folder and add the following content into it, replace the PROJECT_PATH and save it: 

input {
  file {
    type => "java"
    path => "PROJECT_PATH/spring-boot-elk.log"
    codec => multiline {
      pattern => "^%{YEAR}-%{MONTHNUM}-%{MONTHDAY} %{TIME}.*"
      negate => "true"
      what => "previous"
    }
  }
}
 
filter {
  if [message] =~ "\tat" {
    grok {
      match => ["message", "^(\tat)"]
      add_tag => ["stacktrace"]
    }
  }
}
 
output {
  stdout {
    codec => rubydebug
  }
  elasticsearch {
    hosts => ["localhost:9200"]
  }
}

4 - Run "bin/logstash -f logstash.conf" in command prompt inside the extracted folder.

5 - Now, run the Spring Boot Application. Goto http://localhost:8080/elk to see the success logs and http://localhost:8080/exception to see the error stacktrace logs.
