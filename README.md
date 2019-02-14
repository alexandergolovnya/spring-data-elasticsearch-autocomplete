# Spring Data Elasticsearch Autocomplete Example

This is an implementation for autocomplete feature using Elasticsearch as backend data storage and query procssing layer.

## Technology stack
  - Java 1.8
  - Spring Boot 2.1.2
  - Elasticsearch 6.6.0
  
## Libraries used
- Spring Boot
- Spring REST Controller
- Spring Data Elastic Search

## Define entries for local elasticsearch cluster
- `spring.data.elasticsearch.clusterName` - "elasticsearch" for standard settings
- `spring.data.elasticsearch.clusterNodes` - "localhost:9200" for standard settings

## Compilation and run command
- `mvn clean install` - plain maven clean and install
- `mvn spring-boot:run` - run project on http://localhost:8080

## References
  - https://github.com/spring-projects/spring-data-elasticsearch
  - https://www.baeldung.com/spring-data-elasticsearch-tutorial
  - https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/
  - https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.6/_usage.html
  - https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.6/java-rest-high-search.html
  - https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.6/_changing_the_client_8217_s_initialization_code.html
  - https://www.elastic.co/guide/en/elasticsearch/client/java-api/6.6/transport-client.html
  - https://www.elastic.co/guide/en/elasticsearch/reference/current/glossary.html
  - https://github.com/spring-projects/spring-data-elasticsearch/blob/master/src/test/java/org/springframework/data/elasticsearch/core/completion/ElasticsearchTemplateCompletionTests.java
  - https://hackernoon.com/elasticsearch-building-autocomplete-functionality-494fcf81a7cf
  - https://stackoverflow.com/questions/19510659/java-io-streamcorruptedexception-invalid-internal-transport-message-format
  - https://raymondhlee.wordpress.com/2018/08/16/implementing-apis-to-support-autocomplete-with-spring-and-elasticsearch/
  - https://stackoverflow.com/questions/35137479/elasticsearch-completion-suggester-with-java-api
  - http://www.mkyong.com/spring-boot/spring-boot-spring-data-elasticsearch-example/
  - https://medium.com/sourav.pati09/how-to-use-java-high-level-rest-client-with-spring-boot-to-talk-to-aws-elasticsearch-2b6106f2e2c
  - https://blog.patricktriest.com/text-search-docker-elasticsearch/
  - https://blog.bitsrc.io/how-to-build-an-autocomplete-widget-with-react-and-elastic-search-dd4f846f784
  
  
  
