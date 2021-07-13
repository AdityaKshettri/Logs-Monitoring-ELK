# Logs-Monitoring-ELK
In this project, I have worked on logs monitoring for Spring Boot Application using ELK Stack (Elastic search, Logstash and Kibana).

# Requirements :

1 - Download Elasticsearch .zip file from [Elasticsearch Downloads](https://www.elastic.co/downloads/elasticsearch). Extract the .zip file and run "elasticsearch.bat" file in /bin folder.

2 - Download Kibana .zip file from [Kibana Downloads](https://www.elastic.co/downloads/kibana). Extract the .zip file and run "kibana.bat" file in /bin folder.

3 - Download Logstash .zip file from [Logstash Downloads](https://www.elastic.co/downloads/logstash). Extract the .zip file. Create "logstash.conf" file inside the folder and add the following content into it : 

input {
  file {
    type => "java"
    path => "D:/Projects/SpringBoot/logs-monitoring/spring-boot-elk.log"
    codec => multiline {
      pattern => "^%{YEAR}-%{MONTHNUM}-%{MONTHDAY} %{TIME}.*"
      negate => "true"
      what => "previous"
    }
  }
}
 
filter {
  #If log line contains tab character followed by 'at' then we will tag that entry as stacktrace
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
  # Sending properly parsed log events to elasticsearch
  elasticsearch {
    hosts => ["localhost:9200"]
  }
}

